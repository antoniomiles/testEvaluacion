@REQ_PQBP-2848 @EvaluacionE2E @R1 @Agente1
Feature: Prueba Web Lucio Arias

  @id:1 @PruebaCalculadora
  Scenario Outline: T-E2E-PQBP-2848-CA1- Prueba Calculadora
    Given que el cliente navega por la calculadora de hipotecario
    When selecciona el producto "<productoInteres>", con un costo de $"<costo>", un monto a solicitar $"<montoSolicitud>", un plazo de "<plazoAnios>" años y una amortizacion "<amortizacion>"
    Then visualiza los resultados del calculo y valida la cuota mensual aproximada de $"<cuotaMensual>", la tasa de interes del "<tasaInteres>"% y gastos de avaluo del $"<gastosAvaluo>"
    Examples:
      | @externaldata@datosCalculadora.csv |
