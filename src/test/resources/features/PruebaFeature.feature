@REQ_PQBP-583 @Saucedemo @cucumber
Feature: Iniciar sesion en la pagina Prueba
  @id:1 @testFeature
  Scenario Outline: Iniciar sesión con credenciales de Jira
    Given que el cliente admin ingresa a la pagina SauceDemo
    When el ingresa sus credenciales para iniciar sesion
      | user          | pass         |
      | <user> | <pass> |
    Then el deberia ingresar a ver los productos disponibles
    Examples:
    | @externaldata@.\src\test\resources\data\datasaucedemo.csv |
