# Foro Hub - API REST
## DESCRIPCION

**Foro Hub** es una API REST desarrollada como parte del desafío Back End de **Alura Latam** (Oracle Next Education). El objetivo principal es replicar el funcionamiento interno de un foro, permitiendo a los usuarios:

* Crear un nuevo tópico.
* Mostrar todos los tópicos creados.
* Mostrar un tópico específico.
* Actualizar un tópico.
* Eliminar un tópico.

Todo esto protegido bajo una capa de seguridad y autenticación mediante **Spring Security** y **JWT**.

## Tecnologías Utilizadas

* **Java 21**: Lenguaje de programación.
* **Spring Boot 3**: Framework para el desarrollo de la API.
* **Spring Data JPA (Hibernate)**: Para el mapeo objeto-relacional y persistencia de datos.
* **Maven**: Gestor de dependencias.
* **Spring Security**: Gestión de autenticación y autorización.
* **JWT (Auth0)**: Generación y validación de tokens de acceso.
* **Flyway / Hibernate DDL**: Migración y generación de base de datos.
* **PostgreSQL**: Base de datos relacional.
* **Lombok**: Biblioteca para reducir el código boilerplate.

## Funcionalidades

### Autenticación
* `POST /login`: Permite iniciar sesión con correo y contraseña. Devuelve un **Token JWT** tipo Bearer.

### Tópicos (Requiere Token)
* `POST /topicos`: Registra un nuevo tópico (Valida duplicados y datos vacíos).
* `GET /topicos`: Lista todos los tópicos activos en la base de datos.
* `GET /topicos/{id}`: Detalla la información de un tópico específico.
* `PUT /topicos`: Actualiza el título, mensaje o estado de un tópico.
* `DELETE /topicos/{id}`: Elimina un tópico de la base de datos.

## Configuración y Ejecución

### Prerrequisitos
1.  Tener instalado **Java 21**.
2.  Tener instalado **PostgreSQL** y crear una base de datos llamada `foro_hub`.

### Pasos para correr el proyecto
1.  Clonar el repositorio:
    ```bash
    git clone [https://github.com/SantiagoBorgna/foro-hub-alura.git](https://github.com/SantiagoBorgna/foro-hub-alura.git)
    ```
2.  Configurar las variables de entorno o modificar el archivo `src/main/resources/application.properties` con tus credenciales de base de datos:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/foro_hub
    spring.datasource.username=TU_USUARIO_POSTGRES
    spring.datasource.password=TU_CONTRASEÑA
    api.security.secret=CLAVE_SECRETA_PARA_JWT
    ```
3.  Ejecutar la aplicación desde tu IDE o consola.

## Autor
Santiago Borgna - Desarrollador Backend
