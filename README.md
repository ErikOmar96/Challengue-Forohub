# FOROHUB

Api basica para la administración de un foro mediante el uso de SpringBoot,
este es un proyecto parte de los challenges propuestos en [#AluraLatam](https://www.aluracursos.com/)
con [#OracleNexEducation](https://app.aluracursos.com/form-one/registro/latam-general),
en la formación de **Java y Spring Boot G6 - ONE**

# ✨ Features

- 📋 Variables de entorno con [Spring Dotenv](https://github.com/paulschwarz/spring-dotenv).
- 🚀 Implementación de Autenticación y autoriazación meditante JWT tokens.
- 🔎 Documentación de la API mediante Swagger y springdoc.
- 📚 Uso de Spring JPA, derived queries and jpql.
- 📦 Manejo de dependencias con maven.
- 🛠️ Base de datos en MariaDB .
- 📁 Administración de la base de datos como servicio mediante [Docker](https://www.docker.com/).
- 💪 Automaticación para la gestión del contenedor Docker de Base de datos usando [GNU Make](https://www.makigas.es/series/make#:~:text=Make%20es%20una%20utilidad%20del,%2C%20CMake...).).

# ⚡️ Notas de ejecución

A continuación se daran las instrucciones para ejecutar al aplicación en modo desarrollo
Es necesario tener instalado [Java](https://www.java.com/es/download/ie_manual.jsp) y
opcionalmente [Maven](https://maven.apache.org/download.cgi).
Maven ya viene integrado en algunos IDE'S como Netbeans ó Intellij IDEA,
sin embargo a continuación un tutorial rapido de [como instalar Maven](https://www.youtube.com/watch?v=biBOXvSNaXg&list=PLvimn1Ins-40atMWQkxD8r8pRyPLAU0iQ&index=2&ab_channel=MitoCode).

### 1. Inicializar el proyecto

- Clonar el repositorio
  ``` bash
  git clone https://github.com/OscarDevCPP/forohub.git
  ```

- Copiar archivo .env.example con nuevo nombre .env <br>
  Dentro del proyecto existe un archivo con el nombre ".env.example", se debe
  hacer una copia de este archivo dentro del mismo directorio con el nuevo nombre ".env"
  **Importante, no olvidar el punto '.' es parte del nombre del archivo.** <br>
  **Alternativamente**, se puede ejecutar el siguiente comando para completar este paso.
  ``` bash
  cp .env.example .env
  ```

### 2. Configurar la base de datos
Se tiene dos opciones:
* La primera es usar una [base de datos externa](#base-de-datos-externa).
* Y la Segunda es mediante un contenedor [Docker](#usando-docker) de MariaDB (Requisito, tener instalado [Docker](https://www.docker.com/) y GNU Make).

#### Base de datos externa
- Solo necesitas editar los datos necesarios en el .env, para conectar a tu base de datos

  ``` dotenv
  DB_HOST=localhost
  DB_PORT=6602
  DB_USER=admin
  DB_DATABASE=forohub_db
  DB_PASSWORD=12345678
  DEBUG=true
  JWT_SECRET=your_secret
  ```
#### Usando docker
- Copiar el archivo .docker/.env.dist a .docker/.env
  ```bash
  cp .docker/.env.dist .docker/.env
  ```
- Editar el archivo .docker/.env (o dejarlo con los valores por defecto)

  ``` dotenv
  APP_NAME=forohub
  
  # Dev MySQL Settings
  MYSQL_DATABASE=forohub_db
  MYSQL_USER=admin
  MYSQL_PASSWORD=12345678
  MYSQL_ROOT_PASSWORD=12345678
  ```
- Editar archivo .env segun los datos de .docker/.env
  ``` dotenv
  DB_HOST=localhost
  DB_PORT=6602
  DB_USER=admin
  DB_DATABASE=forohub_db
  DB_PASSWORD=12345678
  DEBUG=true
  JWT_SECRET=your_secret
  ```
- Ejecutar el contenedor de la base de datos
  ``` bash
  make run params=-d
  ```
Notas: 
- Puedes conectarte a tu base de datos usando cualquier gestor de base de datos MySQL.
- Pero recomiendo usar el IDE Intellij IDEA, y conectarte a la base de datos mediante la creación de un [data source](https://www.jetbrains.com/help/idea/managing-data-sources.html#-wcxhqt_400)
  
### 3. Ejecutar la aplicación.

- Esto se puede hacer con el IDE de turno (Intellij Idea u otro). El proyecto ya viene configurado según Spring Boot.

### 4. Probrar la API.

1. Para probar todas los endpoints primero se tiene que [registrar un usuario](http://localhost:8080/swagger-ui/index.html#/user-controller/create).
2. [Obtener el token](http://localhost:8080/swagger-ui/index.html#/auth-controller/authUser) para el nuevo usuario registrado, (el token expira cada 2 horas).
3. Pegar el token en la caja de texto "Value" haciendo click al boton "authorize" de contorno verde situado al [inicio de la documentación de la api](http://localhost:8080/swagger-ui/index.html).
4. Finalmente se puede probar todas las funcionalidades de la api accediendo a la [documentación de la api generada](http://localhost:8080/swagger-ui/index.html) por Springdoc.


# 🌈 Funcionalidades Basicas

1. [Autenticación y autorización](http://localhost:8080/swagger-ui/index.html#/auth-controller) mediante Json Web Tokens.
2. [Creación de usuarios](http://localhost:8080/swagger-ui/index.html#/user-controller/create) (users).
3. [Creación de cursos](http://localhost:8080/swagger-ui/index.html#/subject-controller/create_2) (subjets).
4. [CRUD completo](http://localhost:8080/swagger-ui/index.html#/topic-controller) para el manejo de topicos (topics).
5. [Creación de respuestas](http://localhost:8080/swagger-ui/index.html#/answer-controller/create_3) para los topicos registrados.

Muchas gracias por pasar por este repositorio. (@OscarDevCPP)
