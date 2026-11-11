from contextlib import asynccontextmanager
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware

from .database import Base, engine, SessionLocal
from .seed import seed_database
from .routers import cards


@asynccontextmanager
async def lifespan(app: FastAPI):
    # Cria as tabelas no SQLite e popula os dados iniciais se estiver vazio
    Base.metadata.create_all(bind=engine)
    db = SessionLocal()
    try:
        seed_database(db)
    finally:
        db.close()
    yield


app = FastAPI(
    title="TCG Card Shop API",
    description="API REST para gerenciamento de estoque e vendas de cartas de TCG.",
    version="1.0.0",
    lifespan=lifespan,
)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(cards.router)


@app.get("/health", tags=["Health"])
def health_check():
    """Rota de verificação de saúde para Docker e Coolify."""
    return {"status": "ok", "service": "tcg-card-shop-backend"}