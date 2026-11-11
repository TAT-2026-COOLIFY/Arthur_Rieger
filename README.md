# 🃏 TCG Card Shop — Sistema de Gestão de Estoque (CRUD)

Aplicação web completa em **Python** para gerenciamento de inventário e vendas de cartas de Trading Card Games (Yu-Gi-Oh!, Pokémon TCG e Magic: The Gathering). O sistema utiliza arquitetura desacoplada em containers **Docker**, com **FastAPI** no Back-end, **Streamlit** no Front-end, **SQLite** com persistência em volume e pipeline de **CI/CD via GitHub Actions + Coolify**.

---

## 🏗️ Arquitetura e Estrutura de Pastas

```text
tcg-card-shop/
├── .github/
│   └── workflows/
│       └── deploy.yml          # Pipeline CI/CD (GitHub Actions -> Coolify)
├── backend/                    # Serviço de API REST (FastAPI)
│   ├── app/
│   │   ├── database.py         # Conexão SQLAlchemy + SQLite
│   │   ├── models.py           # Modelo ORM da tabela 'cards'
│   │   ├── schemas.py          # Validação de dados com Pydantic
│   │   ├── seed.py             # Carga inicial automática de cartas
│   │   ├── main.py             # Inicialização da API, CORS e Healthcheck
│   │   └── routers/
│   │       └── cards.py        # Rotas do CRUD e baixa rápida de estoque
│   ├── Dockerfile              # Imagem Docker do Back-end
│   └── requirements.txt        # Dependências do Back-end
├── frontend/                   # Serviço de Interface Gráfica (Streamlit)
│   ├── app.py                  # Dashboard e telas de CRUD consumindo a API
│   ├── Dockerfile              # Imagem Docker do Front-end
│   └── requirements.txt        # Dependências do Front-end
├── .env.example                # Exemplo de variáveis de ambiente
├── .gitignore                  # Exclusão de cache, venv e arquivos locais
├── docker-compose.yml          # Orquestração de containers, rede e volume
└── README.md                   # Documentação do projeto