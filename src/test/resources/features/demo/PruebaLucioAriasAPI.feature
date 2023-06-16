@REQ_PQBP-2848 @R2 @Agente1 @Evaluaciones
Feature: Prueba API Lucio Arias

  @id:1 @PruebaConsulta1 @EvaluacionAPI
  Scenario Outline: T-API-PQBP-2848-CA2- Prueba productos validos FakeAPI
    Given el cliente ingresa a la pagina "https://fakestoreapi.com"
    When consulta el endpoint "/products"
    Then verifica que exista un producto con rate <rate> tenga un id: <id>, categoria "<categoria>" y con titulo "<titulo>"
    Examples:
      | @externaldata@datosFakeAPIProductosValidos.csv |

  @id:2 @PruebaConsulta2 @EvaluacionAPI
  Scenario Outline: T-API-PQBP-2848-CA3- Prueba productos por categoria
    Given el cliente ingresa a la pagina "https://fakestoreapi.com"
    When consulta el endpoint "/products/category/<categoria>"
    Then validar que contenga un total de <totalProductos> y exista el producto con id: <idProducto>
    Examples:
      | @externaldata@datosFakeAPIProductosPorCategoria.csv |

  @id:2 @PruebaConsulta3 @EvaluacionAPI
  Scenario Outline: T-API-PQBP-2848-CA4- Prueba productos por categoria y limite
    Given el cliente ingresa a la pagina "https://fakestoreapi.com"
    When consulta el endpoint "/products/category/<categoria>" y limite de <limite> elementos
    Then validar que solo tenga el limite de <limite> elementos solicitados
    Examples:
      | @externaldata@datosFakeAPIProductosEnCategoriaPorLimite.csv |


