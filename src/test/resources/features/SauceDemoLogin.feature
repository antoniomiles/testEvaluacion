@REQ_PQBP-511 @Saucedemo @cucumber @R1
Feature: Iniciar sesion en la pagina SauceDemo
  @id:1 @login
  Scenario Outline: Iniciar sesión con credenciales correctas
    Given que el cliente admin ingresa a la pagina SauceDemo
    When el ingresa sus credenciales para iniciar sesion
      | user          | pass         |
      | <user> | <pass> |
    Then el deberia ingresar a ver los productos disponibles
    Examples:
    | @externaldata@.\src\test\resources\data\datasaucedemo.csv |

  @id:2 @login @loginFallido
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
    | @externaldata@.\src\test\resources\data\datasaucedemo2.csv |
