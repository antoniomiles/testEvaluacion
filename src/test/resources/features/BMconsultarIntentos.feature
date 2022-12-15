@REQ_PQBP-557 @karate
Feature: Consulta Intentos
  @id:1 @BancaMovil @Consultaintentos
  Scenario: T-API-PQBP-557-CA1- Consulta Intentos
    * header content-type = 'application/json'
    Given url 'https://app-security-username-attempts-dot-pmovil-app-test.ue.r.appspot.com/app/security/biometric/identification/attempts/v1'
    And def user = read('classpath:../data/BancaMovil/BMconsultaIntentosUsuarioData.json')
    And request user
    When method POST
    Then status 201
    And print response
