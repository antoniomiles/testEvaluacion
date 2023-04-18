@REQ_PQBP-110 @SimulacionCompraTelefono @cucumber @R1 @Agente2 @TestNoFuncional
Feature: Simulacion de la Compra de telefonos
  Ejecutar una simulacion de la compra de telefonos

  @id:1 @CompraTelefono @CompraTelefonoExitosa @testExitoso
  Scenario Outline: T-E2E-PQBP-110-CA1- Compra de productos tecnologicos, data desde excel
  Para realizar una compra exitosa de un producto
  como cliente sin realizar login en la aplicación
  necesito ser capaz de realizar y verificar la compra

    Given que el cliente ingresa a la pagina de demoblaze para la compra de telefonos selecciona el "<producto>"
    When el decide hacer la compra ingresa sus datos personales "<name>", "<country>", "<city>", "<card>", "<month>" y "<year>"
    Then el realiza la compra del producto exitosamente
  Examples:
  | @externaldata@DataCompraTelefono.xlsx..compra |

  @id:2 @CompraTelefono @CompraTelefonoFallida
  Scenario Outline: T-E2E-PQBP-110-CA2- Compra de productos tecnologicos, data desde csv
  Para realizar una compra exitosa de un producto
  como cliente sin realizar login en la aplicación
  necesito ser capaz de realizar y verificar la compra

    Given que el cliente ingresa a la pagina de demoblaze para la compra de telefonos selecciona el "<producto>"
    When el decide hacer la compra ingresa sus datos personales "<name>", "<country>", "<city>", "<card>", "<month>" y "<year>"
    Then el realiza la compra del producto exitosamente
  Examples:
  | @externaldata@datacompratelefono.csv |


