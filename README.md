# simple-user-service

[PT-BR]

Projeto criado com um CRUD simples de Usuário e Endereço, expondo o serviço de CRUD via SOAP e REST (REST em construção)

## Observações ##

- Projeto criado com últimas versões do Maven, Java 1.8 e SpringBoot.
- Atualmente rodando com um banco em memória H2
- Serviços expostos pela URL `http://<dominio>/ws`
- Atualmente o projeto é iniciado com uma massa inicial de 3 usuários com endereços relacionados, sendo necessário a exclusão desse código no futuro
- Não foram adicionadas muitas validações por enquanto (TODO).
- **Projeto em desenvolvimento com muitas melhorias a serem realizadas. É ESPERADO QUE HAJA BUGS.**

## Rodando localmente ##

1. Executar `mvn spring-boot:run` na pasta raiz do projeto onde se encontra o `pom.xml`

### Testando localmente ###

Baixar arquivos de requests de exemplo encontrados em [https://github.com/Maiconfz/simple-user-service/tree/master/sample/requests]

#### Buscando um usuário ####

Para buscar um usuário, é necessário a informação do CPF registrado.

`curl --header "content-type: text/xml" -d @getUserRequest.xml http://localhost:8080/ws`

#### Listando todos os usuários ####

Não consegui achar uma maneira de realizar uma requisição vazia para listar todos os usuário, atualmente é necessário passar uma informação qualquer no campo msg, e o serviço retornará as informação de todos os usuários. [TODO] Definir melhor estratégia para essa listagem (Permitir filtros talvez?)

`curl --header "content-type: text/xml" -d @getAllUsersRequest.xml http://localhost:8080/ws`

#### Criando um novo usuário ####

Para criar um novo usuário, fazer uma requisição para o endereço do serviço passando as informações do usuário + endereço sem preencher o ID de ambos.

`curl --header "content-type: text/xml" -d @saveUserRequest.xml http://localhost:8080/ws`

#### Criando um novo usuário com preenchimento automático de informações de endereço ####

Foi adicionado ao serviço de criação de usuários, o preenchimento automático de algumas informações de endereço utilizando o Serviço de ConsultaCEP dos correios. Quando a informação de endereço, bairro ou UF não serem preenchidas, ele preencherá automáticamente, desde que tenha sido informado o CEP

`curl --header "content-type: text/xml" -d @saveUserRequestWithCorreiosAutoFill.xml http://localhost:8080/ws`

#### Atualizando um usuário ####

Atualmente é necessário passar as informações de id e cpf do usuário para atualizar as outras informações.

`curl --header "content-type: text/xml" -d @updateUserRequest.xml http://localhost:8080/ws`

#### Removendo um usuário ####

Para remover um usuário, basta passar a informação de ID do Usuário.

`curl --header "content-type: text/xml" -d @deleteUserRequest.xml http://localhost:8080/ws`
