# springboot-gradle-rxjava-mysql-webapi

Este projeto tem como objetivo demonstrar o uso das tecnologias SpringBoot, RxJava3, Docker, docker-compose, Terraform, MySql, Swagger, Gradle, Groovy e gradle-docker-compose-plugin para a construção de webapis não blocantes (reativas), conteinerizadas e com as infraestruturas necessárias para execução e testes também conteinerizadas.

## Infraestrutura
A web-api foi construída utilizando o framework SpringBoot na versão 2.4.1 e referencia uma base de dados MySql que é configurada através do Docker, docker-compose e do plugin Gradle gradle-docker-compose-plugin configurados pelos arquivos docker-compose.yml, docker-compose-integration-tests.yml, Dockerfile e build.gradle.

Para manter os componentes do docker necessários para a web-api utilize as seguintes tasks Gradle:
* runAppInfrastructureComposeBuild: cria as imagens necessárias a web-api de acordo com o arquivo docker-compose.yml.
* runAppInfrastructureComposeUp: inicia a execução das imagens configuradas através da task runAppInfrastructureComposeBuild.
* runAppInfrastructureComposeDown: para a execução das imagens iniciadas através da task runAppInfrastructureComposeUp.

Alternativamente pode-se utilizar o terraform, configurado através do arquivo terraform/main.tf, para manter os componentes docker através dos comandos abaixo executados com o usuário na pasta terraform:
* terraform init/plan/apply/destroy

### Base de dados
A base de dados contém um schema dumb_db, que contém uma tabela dumb_entity, que contém somente um campo (chave primária) long.

## WEB-API
A web-api expõe 2 endpoints:
* /dumb : método HTTP GET, retorna todos os registros da tabela dumb_entity com mais um campo transiente formado a partir do valor do campo id.
* /dumb : método HTTP POST, insere um registro na tabela dumb_entity com o id informado no corpo da requisição, caso já exista registro com o id fornecido retorna um erro.

O processamento feito através da web-api acontece de forma não blocante (reativa) através da utilização do framework RxJava3 

A documentação da web-api foi feita com o Swagger e está exposta através do endpoint /swagger-ui.html.

Para executar a aplicação inicie a execução da infraestrutura através da task runAppInfrastructureComposeUp e execute a task bootRun.

## Testes de integração
O projeto contém somente um teste que ao ser executado inicia o contexto da aplicação SpringBoot que faz a verificação da conexão com a infraestrutura utilizada que é instanciada durante a execução dos testes através da configuração da task test para executar as tasks integrationTestsComposeBuild, integrationTestsComposeUp e integrationTestsComposeDown configuradas através do arquivo docker-compose-integration-tests.yml.

Para executar o teste execute a task test.