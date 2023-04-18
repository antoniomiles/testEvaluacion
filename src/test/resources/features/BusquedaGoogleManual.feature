@REQ_PQBP-110 @ManualTest @cucumber @Agente1 @TestNoFuncional
Feature: Pruebas manuales Google

  @id:1 @manual @manual-last-tested:sprint-1 @manual-test-evidence:[Evidencia_Google](assets/psr_google.zip),[Evidencia_tipoPDF](assets/Evidence3.pdf)
  Scenario: T-E2E-PQBP-110-CA4- Busqueda Google
    Given que el cliente ingresa a la pagina de Google
    When el ingresa su criterio de busqueda
    Then se deberia mostrar el resultado de la busqueda
