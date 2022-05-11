@REQ_PQBP-110 @ManualTest @cucumber
Feature: Pruebas manuales Web

  @id:1 @manual @manual-last-tested:sprint-1 @manual-test-evidence:[Evidencia_Given](assets/1screenshot.png),[Evidencia_When](assets/2screenshot.png),[Evidencia_Then](assets/3screenshot.png)
  Scenario: Iniciar sesión con credenciales correctas Mantest
    Given que el cliente ingresa a la pagina Mantest
    When quien ingresa sus cred para iniciar sesion Mantest
    Then el ingresa a ver los productos disponibles Mantest

  @id:2 @manual @manual-last-tested:sprint-1 @manual-test-evidence:[Evidencia_Given](assets/1screenshot.png),[Evidencia_When/Then](assets/4screenshot.png)
  Scenario: Iniciar sesion con un usuario bloqueado Mantest
    Given que el cliente ingresa a la pagina SauceDemo Mantest
    When el ingresa sus cred para iniciar sesion Mantest
    Then se deberia mostrar el mensaje de error Mantest

  @id:3 @manual @manual-last-tested:sprint-1 @manual-test-evidence:[Evidencia_Given](assets/1screenshot.png),[Evidencia_When/Then](assets/4screenshot.png),[Evidencia_Concluciones](assets/EvidenceScn1.txt)
  Scenario: Iniciar prueba Mantest
    Given que el cliente ingresa a la pagina SauceDemo2 Mantest
    When el ingresa sus cred para iniciar sesion2 Mantest
    Then se deberia mostrar el mensaje de error2 Mantest
