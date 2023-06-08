@REQ_PQBP-1845 @SerenityBDDDataBase @Database @Agente1
Feature: Pruebas para consumo de Bases de Datos mediante SerenityBDD

  @id:1 @SelectQuery
  Scenario: T-E2E-PQBP-1845-CA01 - Execute a select query in the database
    Given I am connected to the "MYSQL" database
    When I execute the following query "SELECT * FROM catalogo where id = 1"
    Then I expect the result value should be 'parametros query'

  @id:2 @InsertQuery
  Scenario: T-E2E-PQBP-1845-CA02 - Execute an insert query in the database
    Given I am connected to the "MYSQL" database
    When I execute the following modifying query "INSERT INTO catalogo (id, nombre, mnemonico, valor_cadena, valor_numero) VALUES(7, 'catalogo 7', 'CATALOGO_7', 'valor cadena 7', null)"
    Then I expect the result value should be 1

  @id:3 @DeleteQuery
  Scenario: T-E2E-PQBP-1845-CA03 - Execute an delete query in the database
    Given I am connected to the "MYSQL" database
    When I execute the following modifying query "DELETE FROM catalogo WHERE id = 7"
    Then I expect the result value should be 1

  @id:4 @FetchCOllection @Mongo
  Scenario: T-E2E-PQBP-1845-CA04 - Fetch a collection from the server using Mongo DB
    Given I am connected to the "MONGO" nosql database
    When I fetch a collection called "catalogo" from the server
    Then I expect the command's result should not be null
