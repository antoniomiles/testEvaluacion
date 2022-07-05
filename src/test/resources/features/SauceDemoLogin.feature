@REQ_PQBP-511 @Saucedemo @cucumber @R1
Feature: Iniciar sesion en la pagina SauceDemo

  @id:1 @login
  Scenario Outline: Iniciar sesión con credenciales correctas
    Given que el cliente admin ingresa a la pagina SauceDemo
    When el ingresa sus credenciales para iniciar sesion
      | user   | pass   |
      | <user> | <pass> |
    Then el deberia ingresar a ver los productos disponibles
    Examples:
      | @externaldata@datasaucedemo.csv |

  @id:2 @login @loginFallido
  Scenario Outline: Iniciar sesion con un usuario bloqueado
    Given que el cliente admin ingresa a la pagina SauceDemo
    When el ingresa sus credenciales para iniciar sesion
      | user   | pass   |
      | <user> | <pass> |
    Then se deberia mostrar el siguiente mensaje de error
   """
   Epic sadface: Sorry, this user has been locked out
   """
    Examples:
      | @externaldata@sausedemo\DataSaucedemo2.csv |

  @id:3 @login @loginPrueba
  Scenario Outline: Iniciar sesión con credenciales correctas esto es una prueba de lectura de data
    Given que el cliente admin ingresa a la pagina SauceDemo
    When el ingresa sus credenciales para iniciar sesion
      | user   | pass   |
      | <user> | <pass> |
    Then el deberia ingresar a ver los productos disponibles
    Examples:
      | @externaldata@datasaucedemo.csv |

  @id:4 @login @loginPrueba @dataEstatica
  Scenario Outline: Iniciar sesión con credenciales correctas, creacion de escenario estatico
    Given que el cliente admin ingresa a la pagina SauceDemo
    When el ingresa sus credenciales para iniciar sesion
      | user   | pass   |
      | <user> | <pass> |
    Then el deberia ingresar a ver los productos disponibles
    Examples:
      | user   | pass   |
      | standard_user | secret_sauce |
