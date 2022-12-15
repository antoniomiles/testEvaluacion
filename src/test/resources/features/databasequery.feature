@REQ_PQBP-1845 @Database
Feature: Database Interaction example

  @id:1 @SelectQuery
Scenario: Execute a select query in the database
  Given I am connected to the "MYSQL" database
  When I execute the following query "SELECT * FROM catalogo where id = 1"
  Then I expect the result value should be 'parametros query'
@id:2 @InsertQuery
Scenario: Execute an insert query in the database
  Given I am connected to the "MYSQL" database
  When I execute the following modifying query "INSERT INTO catalogo (id, nombre, mnemonico, valor_cadena, valor_numero) VALUES(7, 'catalogo 7', 'CATALOGO_7', 'valor cadena 7', null)"
  Then I expect the result value should be 1
@id:3 @DeleteQuery
Scenario: Execute an delete query in the database
  Given I am connected to the "MYSQL" database
  When I execute the following modifying query "DELETE FROM catalogo WHERE id = 7"
  Then I expect the result value should be 1
