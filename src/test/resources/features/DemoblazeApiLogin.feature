@REQ_PQBP-110 @DemoblazeAPIRestAssured @cucumber @R1 @Agente1
Feature: Iniciar sesion mediante Api
  #RESTASSURED con ScreenPlay
  @id:1 @loginApi
  Scenario Outline: T-API-PQBP-110-CA1- Iniciar sesión con credenciales correctas en el Api de Demoblaze
    Given que el usuario tester ingresa a la Api de Demoblaze e ingrese las credenciales
      | user | pass |
      | <user> | <pass> |
    Then el recibe el codigo de autenticacion
    Examples:
      | @externaldata@demoblazeloginok.csv |

    #RESTASSURED con ScreenPlay
  @id:2 @loginApiError
  Scenario Outline: T-API-PQBP-110-CA2- Iniciar sesión con usuario invalido en el Api de Demoblaze
    Given que el usuario tester ingresa a la Api de Demoblaze e ingrese las credenciales
      | user | pass |
      | <user> | <pass> |
    Then el recibe el codigo error
    Examples:
      | @externaldata@demoblazeloginerror.csv |

#Se esta utilizando la siguiente pagina de prueba: https://reqres.in para ejemplo de RESTASSURED puro
  @id:3 @restAssured @user_api
  Scenario: T-API-PQBP-110-CA3- Crear un nuevo usuario y actualizarlo
    Given que creo al nuevo usuario "Juan" con el trabajo "Bombero"
    And obtengo el id del nuevo usuario
    When actualizo al usuario creado con el nuevo trabajo "Policia"
    Then valido que el nuevo trabajo del usuario creado haya sido actualizado
