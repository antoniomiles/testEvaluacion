@REQ_PQBP-511 @SerenityBDDWeb @R2 @Agente2
Feature:  Pruebas para automatizacion WEB desde SerenityBDD
  Varios scenarios de pruebas Web con SerenityBDD

  @id:1 @SauceDemo @login @SDdataEstatica @smokeTest
  Scenario Outline: T-E2E-SMOKETEST-PQBP - Iniciar sesion con credenciales correctas, data estatica.
    Given que el cliente admin ingresa a la pagina SauceDemo
    When el ingresa sus credenciales para iniciar sesion
      | user   | pass   |
      | <user> | <pass> |
    Then el deberia ingresar a ver los productos disponibles
    Examples:
      | user          | pass         |
      | standard_user | secret_sauce |

  @id:2 @SauceDemo @login @SDtestExitoso
  Scenario Outline: T-E2E-PQBP-551-CA02 - Iniciar sesion con credenciales correctas.
    Given que el cliente admin ingresa a la pagina SauceDemo
    When el ingresa sus credenciales para iniciar sesion
      | user   | pass   |
      | <user> | <pass> |
    Then el deberia ingresar a ver los productos disponibles
    Examples:
      | @externaldata@demo/DataSaucedemo.csv |

  @id:3 @SauceDemo @login @SDloginFallido
  Scenario Outline: T-E2E-PQBP-551-CA03 - Iniciar sesion con un usuario bloqueado.
    Given que el cliente admin ingresa a la pagina SauceDemo
    When el ingresa sus credenciales para iniciar sesion
      | user   | pass   |
      | <user> | <pass> |
    Then se deberia mostrar el siguiente mensaje de error
   """
   Epic sadface: Sorry, this user has been locked out
   """
    Examples:
      | @externaldata@demo\DataSaucedemoLocked.csv |


  @id:4 @CompraTelefono @CompraTelefonoExitosa
  Scenario Outline: T-E2E-PQBP-511-CA04 - Compra de productos tecnologicos.
  Para realizar una compra exitosa de un producto  como cliente sin realizar login en la aplicación  necesito ser capaz de realizar y verificar la compra
    Given que el cliente ingresa a la pagina de demoblaze para seleccionar el "<producto>"
    When el decide hacer la compra ingresa sus datos personales "<name>", "<country>", "<city>", "<card>", "<month>" y "<year>"
    Then el realiza la compra del producto exitosamente
    Examples:
      | @externaldata@demo/DataCompraTelefono.xlsx..compra |


  @id:5 @LoginAzure
  Scenario: T-E2E-PQBP-511-CA1- Login Azure Usuario Generico
    Given que el cliente inicia sesion en el aplicativo con sus credenciales