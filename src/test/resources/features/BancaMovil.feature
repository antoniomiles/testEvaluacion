@REQ_PQBP-1927 @Mobiletest
Feature: Demo Banca Movil Enrolamiento
@id:1 @Enrollamiento
Scenario Outline: T-E2E-PQBP-1927-CA1- Enrolamiento
    Given que el usuario UsuarioPrueba esta en la aplicación y es cliente
    When que el usuario ingrese usuario "<user>" y contraseña "<password>"
    Then envio de codigo activacion
    Examples:
|user|password|
|Brakith90|Abc123!!|
##        | @externaldata@bancamovilenrolamiento.csv |
