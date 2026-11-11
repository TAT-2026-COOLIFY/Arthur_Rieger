# Usa uma imagem oficial leve do Python
FROM python:3.11-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Instala dependências do sistema necessárias para compilações mínimas
RUN apt-get update && apt-get install -y --no-install-recommends gcc && rm -rf /var/lib/apt/lists/*

# Copia e instala os requisitos do back-end
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copia todo o código do back-end para dentro do container
COPY . .

# Expõe a porta que o FastAPI vai escutar no container
EXPOSE 8000

# Comando para iniciar o servidor em produção
CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]