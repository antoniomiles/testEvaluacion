@Saucedemo @cucumber
Feature: Iniciar sesion en la pagina SauceDemo

  Scenario Outline: Iniciar sesión con credenciales correctas
    Given que el cliente admin ingresa a la pagina SauceDemo
    When el ingresa sus credenciales para iniciar sesion
      | user          | pass         |
      | <user> | <pass> |
    Then el deberia ingresar a ver los productos disponibles
    Examples:
      | user          | pass         |
  #    | standard_user | secret_sauce |
      ##@externaldata@.\src\test\resources\data\datasaucedemo.csv
     ##end
  Scenario Outline: Iniciar sesion con un usuario bloqueado
    Given que el cliente admin ingresa a la pagina SauceDemo
    When el ingresa sus credenciales para iniciar sesion
      |user|pass|
      | <user> | <pass> |
    Then se deberia mostrar el siguiente mensaje de error
     """
     Epic sadface: Sorry, this user has been locked out
     """
    Examples:
      | user          | pass         |
  #    | standard_user1 | secret_sauce1 |
      ##@externaldata@.\src\test\resources\data\datasaucedemo2.csv
     ##end
