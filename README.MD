## 💻 Projeto

Essa API permite com que seja gerenciada as pessoas inscritas em um evento, permitindo com seja criado o evento em si, adicionar um ou mais participantes e realizar o checkIn.

## 📀 Como rodar

Após abaixar o arquivo deverá ser adicionado no arquivo [application.properties](src\main\resources\application.properties) e preencher com o url da base de dados, usuário e senha para que posso ser feita o armazenamento dos dodas assim como a realição das migrações pelo FlyWay, depois será apenas nescessario executar a aplicação com o uso da IDE ou através do comando [mvn spring-boot:run] no diretório ROOT do projeto.

## 🔧 Tecnologias

Foi usado nesse projeto :

- [Maven]
- [Spring Boot]

Dependecias:
- [Hibernate]
- [Flyway]
- [Lombok]
- [Postgresql]
- [Spring Web]
- [PostgreSQL Driver]

Dependecias para desenvolvimento:
-[Spring Boot DevTools]

## Modelo de entidade relacional

<p>
  <img src="">
</p>

## EndPoints da aplicação