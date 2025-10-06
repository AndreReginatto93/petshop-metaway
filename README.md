# petshop-metaway

A simple REST API built with **Java, Spring Boot, Angular and PostgreSQL**.

## Table of Contents
- [Run Locally](#-run-locally)
- [Technologies](#-technologies)
- [API Endpoints](#auth)
    - [Auth](#auth)
    - [Clients](#clients)
    - [Contacts](#contacts)
    - [Adresses](#adresses)
    - [Pets](#pets)
    - [Breed](#breed)

## Run Locally
1. Clone the repository:
   ```bash
   git clone https://github.com/AndreReginatto93/petshop-metaway.git
   cd petshop-metaway
   ```
2. Run Docker compose to setup the database (postgres):
   ```bash
   docker-compose up -d
   ```
3. Run the Backend application
   1. Start the backend
   2. **Import the Postman collection** (`src/main/resources/postman/`)
   3. Test the endpoints using Postman

4. Run the Frontend application
   1. Navigate to the `frontend` directory:
      ```bash
      cd petshop-frontend
      ```
   2. Install dependencies:
      ```bash
      npm install
      ```
   3. Start the Angular development server:
      ```bash
      ng serve
      ```
   4. Open your browser and navigate to `http://localhost:4200`

## 🛠 Technologies

- **Java**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **pgAdmin** (database management)
- **IntelliJ IDEA** (development IDE)
- **Postman** (API testing)
- **Angular**
- **VsCode**

Below is a summary of the main endpoints for each module.  
For complete details and example requests, import the Postman collection from:
`src/main/resources/postman/`


**Important:** All operations that require authentication need the `Authorization` header with a valid JWT token.

The user login must be the same as CPF on the client to access "me" endpoints.

The first access can be done with the default **admin:admin** user, and then create new users as needed. If this user is deleted, just restart the backend application to recreate it.



## API Endpoints
### Auth
| Operations            | HTTP Method | Path                  | Access          |
|-----------------------|-------------|-----------------------|-----------------|
| Register a new user   | POST        | /api/v1/auth/register | Admin only      |
| Authenticate user     | POST        | /api/v1/auth/login    | Public          |
| Get all users         | GET         | /api/v1/auth          | Admin only      |
| Delete user           | DELETE      | /api/v1/auth/:id      | Admin only      |



### Clients
| Operations         | HTTP Method | Path                 | Access          |
|--------------------|-------------|----------------------|-----------------|
| Get all            | GET         | /api/v1/clientes     | Admin only      |
| Get a client       | GET         | /api/v1/clientes/:id | Admin only      |
| Get your client    | GET         | /api/v1/clientes/me  | Authenticated   |
| Create client      | POST        | /api/v1/clientes     | Admin only      |
| Update client      | PUT         | /api/v1/clientes/:id | Admin only      |
| Update your client | PUT         | /api/v1/clientes/me  | Authenticated   |
| Delete client      | DELETE      | /api/v1/clientes/:id | Admin only      |


### Contacts
| Operations          | HTTP Method | Path                    | Access          |
|---------------------|-------------|-------------------------|-----------------|
| Get all             | GET         | /api/v1/contatos        | Admin only      |
| Get a contact       | GET         | /api/v1/contatos/:id    | Admin only      |
| Get your contacts   | GET         | /api/v1/contatos/me     | Authenticated   |
| Create contact      | POST        | /api/v1/contatos        | Admin only      |
| Update contact      | PUT         | /api/v1/contatos/:id    | Admin only      |
| Update your contact | PUT         | /api/v1/contatos/me/:id | Authenticated   |
| Delete contact      | DELETE      | /api/v1/contatos/:id    | Admin only      |


### Adresses
| Operations          | HTTP Method | Path                     | Access          |
|---------------------|-------------|--------------------------|-----------------|
| Get all             | GET         | /api/v1/enderecos        | Admin only      |
| Get an address      | GET         | /api/v1/enderecos/:id    | Admin only      |
| Get your addresses  | GET         | /api/v1/enderecos/me     | Authenticated   |
| Create address      | POST        | /api/v1/enderecos        | Admin only      |
| Update address      | PUT         | /api/v1/enderecos/:id    | Admin only      |
| Update your address | PUT         | /api/v1/enderecos/me/:id | Authenticated   |
| Delete address      | DELETE      | /api/v1/enderecos/:id    | Admin only      |


### Pets
| Operations      | HTTP Method | Path                | Access          |
|-----------------|-------------|---------------------|-----------------|
| Get all         | GET         | /api/v1/pets        | Admin only      |
| Get a pet       | GET         | /api/v1/pets/:id    | Admin only      |
| Get your pets   | GET         | /api/v1/pets/me     | Authenticated   |
| Create pet      | POST        | /api/v1/pets        | Admin only      |
| Update pet      | PUT         | /api/v1/pets/:id    | Admin only      |
| Update your pet | PUT         | /api/v1/pets/me/:id | Authenticated   |
| Delete pet      | DELETE      | /api/v1/pets/:id    | Admin only      |


### Breed
| Operations   | HTTP Method | Path              | Access            |
|--------------|-------------|-------------------|-------------------|
| Get all      | GET         | /api/v1/racas     | Authenticated     |
| Get a breed  | GET         | /api/v1/racas/:id | Authenticated     |
| Create breed | POST        | /api/v1/racas     | Authenticated     |
| Update breed | PUT         | /api/v1/racas/:id | Authenticated     |
| Delete breed | DELETE      | /api/v1/racas/:id | Authenticated     |