# Usa uma imagem leve do Python
FROM python:3.11-slim

WORKDIR /app

# Copia e instala os requisitos do front-end
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copia o código do front-end
COPY . .

# Expõe a porta padrão do Streamlit
EXPOSE 8501

# Comando para iniciar o Streamlit apontando para o IP público/interno correto
CMD ["streamlit", "run", "app.py", "--server.port=8501", "--server.address=0.0.0.0"]