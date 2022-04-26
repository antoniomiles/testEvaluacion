Feature: Consulta servicio WSClientes0007
  @karate
  @TCS
  Scenario: Consulta contacto transaccional WSClientes0007
    * header authorization = 'Basic YmpqYXJhOnBpY2hpbmNoYTE='
    * header content-type = 'application/json'
    Given url 'https://api-test.pichincha.com/tcs/WSClientes0007'
    And def user = read('classpath:../data/TCS/consultaContactoTransaccionalData.json')
    And request user
    When method POST
    Then status 200
    And print response
    And match response.ConsultarContactoTransaccional01Response.error.mensajeNegocio contains 'Transaccion exitosa.'
