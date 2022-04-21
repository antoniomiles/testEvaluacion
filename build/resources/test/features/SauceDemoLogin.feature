@Saucedemo
Feature: Iniciar sesion en la pagina SauceDemo

  Scenario: Iniciar sesión con credenciales correctas
    Given que el cliente admin ingresa a la pagina SauceDemo
    When el ingresa sus credenciales para iniciar sesion
      | user          | pass         |
      | standard_user | secret_sauce |
    Then el deberia ingresar a ver los productos disponibles


  Scenario: Iniciar sesion con un usuario bloqueado
    Given que el cliente admin ingresa a la pagina SauceDemo
    When el ingresa sus credenciales para iniciar sesion
      | user            | pass         |
      | locked_out_user | secret_sauce |
    Then se deberia mostrar el siguiente mensaje de error
     """
     Epic sadface: Sorry, this user has been locked out
     """