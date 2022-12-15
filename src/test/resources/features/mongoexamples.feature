@REQ_PQBP-1846 @Database @Mongo
Feature: Database Interaction example
  @id:1 @FetchCOllection
  Scenario: Fetch a collection from the server
    Given I am connected to the "MONGO" nosql database
    When I fetch a collection called "catalogo" from the server
    Then I expect the command's result should not be null