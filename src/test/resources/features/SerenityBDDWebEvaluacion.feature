@REQ_PQBP-511 @SerenityBDDWeb @R2 @Agente2
Feature: Funcionalidad de la Calculadora de Hipotecas

  @id:1 @BPHipotecas

  Scenario Outline: T-E2E-credito-PQBP -Juan simula una hipoteca para una vivienda de interés público y social
    Given Juan navega por la calculadora
    When selecciona el producto de vivienda de interés público y social
    And proporciona los datos para el cálculo "<costo>" "<monto>" y "<plazo>"
    And selecciona el sistema de amortización deseas aplicar al crédito
    And da clic en el botón calcular
    Then visualizará los resultados del cálculo
    Examples:
      | costo  | monto  | plazo |
      | 26000  | 26000  |   21 |
