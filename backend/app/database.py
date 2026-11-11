import os
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, declarative_base

# Garante que a pasta 'data' exista para salvar o SQLite
os.makedirs("data", exist_ok=True)

DATABASE_URL = os.getenv("DATABASE_URL", "sqlite:///./data/tcg_shop.db")

# check_same_thread=False é necessário para o SQLite trabalhar bem com o FastAPI
engine = create_engine(
    DATABASE_URL, connect_args={"check_same_thread": False}
)

SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)

Base = declarative_base()


def get_db():
    """Injeção de dependência para abrir e fechar a sessão a cada requisição."""
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()