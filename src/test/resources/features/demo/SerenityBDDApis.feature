@REQ_PQBP-110 @api @SerenityBDDApi @R1 @Agente1
Feature:  Pruebas para automatizacion de APIs desde SerenityBDD
  Varios scenarios de pruebas APIs con SerenityBDD y Restassured

  #RESTASSURED con ScreenPlay
  @id:1 @loginApi @smokeTest
  Scenario Outline: T-E2E-SMOKETEST-PQBP - Iniciar sesion con credenciales correctas en el Api-Demoblaze
    Given que "usuario" se conecta a la API "https://api.demoblaze.com"
    When inicia sesion en el endpoint "/login" con las credenciales
      | user   | pass   |
      | <user> | <pass> |
    Then visualiza el codigo de autenticacion y el status code 200
    Examples:
      | @externaldata@demo/demoblazeloginok.csv |

  #RESTASSURED con ScreenPlay
  @id:2 @loginApiError
  Scenario Outline: T-API-PQBP-110-CA02- Iniciar sesion con usuario invalido en el Api-Demoblaze
    Given que "usuario" se conecta a la API "https://api.demoblaze.com"
    When inicia sesion en el endpoint "/login" con las credenciales
      | user   | pass   |
      | <user> | <pass> |
    Then visualiza el statusCode 200 y el mensaje de error "User does not exist."
    Examples:
      | @externaldata@demo/demoblazeloginerror.csv |

  #RESTASSURED puro
  @id:3 @restAssured @user_api
  Scenario: T-API-PQBP-110-CA03- Crear un nuevo usuario y actualizarlo en Api-Reqres
    Given que creo al nuevo usuario "Juan" con el trabajo "Bombero" en el endPoint "https://reqres.in/api/users"
    When actualizo el trabajo a "Policia", en el endPoint "https://reqres.in/api/users/{userId}"
    Then visualizo el statusCode 200
