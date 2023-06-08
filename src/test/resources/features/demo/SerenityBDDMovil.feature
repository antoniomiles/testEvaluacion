@REQ_PQBP-1927 @SerenityBDDMovil @Mobiletest
Feature: Pruebas para automatizacion en moviles desde SerenityBDD

    #Versión de BM usadas: IOS=20230302.6 Android=20230302.5
  @id:1 @Enrolamiento
  Scenario Outline: T-E2E-PQBP-1927-CA01 - Banca Movil Enrolamiento
    Given que el usuario UsuarioPrueba esta en la aplicación y es cliente
    When el usuario completa el enrollamiento ingresando usuario "<user>", contraseña "<password>" y pin "<pin>"
    Then se muestra la pantalla de login
    Examples:
      | @externaldata@demo/bancamovilenrolamiento.csv |
