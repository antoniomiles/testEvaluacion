@SimulacionCompraTelefonoTng @cucumber
Feature: Simulacion de la Compra de telefonos
  Ejecutar una simulacion de la compra de telefonos

@TEST_PQBP-109
  Scenario Outline: Compra de productos tecnologicos
  Para realizar una compra exitosa de un producto
  como cliente sin realizar login en la aplicación
  necesito ser capaz de realizar y verificar la compra

    Given que el cliente ingresa a la pagina de demoblaze para la compra de telefonos selecciona el "<producto>"
    When el decide hacer la compra ingresa sus datos personales "<name>", "<country>", "<city>", "<card>", "<month>" y "<year>"
    Then el realiza la compra del producto exitosamente
  Examples:
    | producto         | name       | country | city  | card      | month | year |
   #| Nokia lumia 1520 | ClienteE2E | Ecuador | Quito | 123456789 | 08    | 23   |
     ##@externaldata@.\src\test\resources\data\datacompratelefono.csv
     ##end



