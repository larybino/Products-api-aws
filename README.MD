# 🛒 Products API

API REST desenvolvida em Java com Spring Boot para gerenciar listas de produtos por loja, com armazenamento em arquivos JSON no AWS S3 ou em uma pasta local. O projeto realiza validações nos dados e oferece uma configuração flexível para alternar entre armazenamento na nuvem ou local.

---

## 🚀 Objetivo da Atividade

Desenvolver uma API REST com dois endpoints para gerenciar listas de produtos de lojas, salvando os dados em arquivos JSON armazenados em um bucket AWS S3 (ou, alternativamente, em uma pasta local). A aplicação deve ser implantada em uma instância EC2 e iniciar automaticamente com o sistema.

---

## 📋 Requisitos

### ✅ Requisições suportadas:

#### **PUT /products/{store_name}**

- `store_name` (path param): nome da loja em *snake_case*, sem acentos.
- Corpo da requisição: JSON contendo **1 a 10 produtos**, onde cada produto deve ter:
  - `name` (string): nome do produto
  - `price` (number): preço > 0
  - `discount_percent` (number): percentual de desconto entre 0 e 100
- Salva a lista em `store_name.json` no bucket S3 (ou local), sobrescrevendo o arquivo, se já existir.

#### **GET /products/{store_name}**

- Retorna o conteúdo do arquivo `store_name.json` salvo anteriormente.
- Retorna erro (404) se o arquivo não existir.

---

## 💾 Armazenamento

- Por padrão, a API utiliza o AWS S3.
- Se não for possível utilizar o bucketec2-, a aplicação usará uma **pasta local** (`/data/`) para armazenar os arquivos.
- A escolha entre S3 e local é feita através do arquivo de propriedades:  
  ```properties
  storage.type=s3        # ou: local
```