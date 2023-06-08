@REQ_PQBP-110 @ManualTest @R3 @Agente1 @TestNoFuncional
Feature: Pruebas manuales con serenityBDD

  @id:1 @manual @manual-last-tested:sprint-1 @manual-test-evidence:[Evidencia_PNG](assets/demo/1screenshot.png),[Evidencia_Word](assets/demo/Evidence1.docx),[Evidencia_Rar](assets/demo/Evidence2.rar),[Evidencia_Txt](assets/demo/ObserConcl.txt)
  Scenario: T-E2E-PQBP-110-CA01- Iniciar sesion con credenciales correctas - Ejemplo pruebas Manuales
    Given que el cliente ingresa a la pagina Mantest
    When quien ingresa sus cred para iniciar sesion Mantest
    Then el ingresa a ver los productos disponibles Mantest

  @id:2 @manual @manual-last-tested:sprint-1 @manual-test-evidence:[Evidencia_zip](assets/demo/psr_google.zip),[Evidencia_PDF](assets/demo/Evidence3.pdf)
  Scenario: T-E2E-PQBP-110-CA02- Busqueda Google - Ejemplo pruebas Manuales
    Given que el cliente ingresa a la pagina de Google
    When el ingresa su criterio de busqueda
    Then se deberia mostrar el resultado de la busqueda
