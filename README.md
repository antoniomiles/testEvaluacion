# README

Arquetipo de pruebas automatizadas E2E usando la herramienta SerenityBDD con Screenplay

Realizadas por: EquipoE2E

## Complementos


|**Intellij**|**Java**|**Gradle**|
| :----: | :----: | :----:  |
|[<img width="50" height="50" src="https://cdn.iconscout.com/icon/free/png-128/intellij-idea-569199.png">](https://www.jetbrains.com/es-es/idea/download/#section=windows)|[<img height="60" src="https://www.oracle.com/a/ocom/img/cb71-java-logo.png">](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)|[<img height="50" src="https://gradle.org/images/gradle-knowledge-graph-logo.png?20170228">](https://gradle.org/releases/)|
> **NOTA**: 
> * Una vez obtenido Intellij es necesario instalar los plugins de Gherkin y Cucumber. (*[Guia de instalación plugins en intellij](https://www.jetbrains.com/help/idea/managing-plugins.html)*) 
> * El navegador usado para la ejecución de las pruebas fue google chrome en su ultima versión ( 98.0.4758.102 )

## Ejecución local

Clonar el proyecto

```bash
  git clone https://BancoPichinchaEC@dev.azure.com/BancoPichinchaEC/BP-Quality-Management/_git/sqa-aut-arq-serenitybdd
```

Entrar al directorio del proyecto

```bash
  cd sqa-aut-arq-serenitybdd
```
## Modificación del codigo

- Para realizar modificaciones al codigo del proyecto. realizar los siguientes pasos: 

     
	 1. Importar el proyecto desde IntelliJ IDE bajo la estructura de un proyecto Gradle existente
	 2. Configurar JRE System Library con JavaSE-1.8
	 3. Configurar la codificación a UTF-8 al proyecto una vez sea importado

## Comandos

El arquetipo posee 2 runners activos, uno para pruebas E2E y otro pruebas de Arquetipo

### Comandos E2E

Para ejecutar todos los features por linea de comandos
```bash
  ./gradlew clean test --tests "com.pichincha.automationtest.runners.WebRunnerTest"
```

Para ejecutar todos los escenarios que contengan un tag especifico
```bash
  ./gradlew clean test --tests -Dcucumber.filter.tags="@test" com.pichincha.automationtest.runners.WebRunnerTest
```

Para ejecutar los  enviando variables de ambiente
```bash
  ./gradlew clean test --tests "com.pichincha.automationtest.runners.WebRunnerTest" -Dvariable1=test
```

Para ejecutar las pruebas con 3 hilos en paralelo  enviando variables de ambiente
```bash
  ./gradlew clean test -PmaxParallelForks=4 --tests "com.pichincha.automationtest.runners.parallel.*" aggregate -i -Dvariable=test
```

### Comandos API

Para ejecutar todos los features por linea de comandos
```bash
  ./gradlew clean test --tests "com.pichincha.automationtest.runners.ApiRunnerTest"
```

Para ejecutar todos los escenarios que contengan un tag especifico
```bash
  ./gradlew clean test --tests "-Dkarate.options=--tags @test" "com.pichincha.automationtest.runners.ApiRunnerTest"
```

Para ejecutar los  enviando variables de ambiente
```bash
  ./gradlew clean test --tests "-Dkarate.options=--tags @test" "com.pichincha.automationtest.runners.ApiRunnerTest" -Dvariable1=test
```

> **NOTA**:
> * Para ejecutar el proyecto se necesita Java JDK 1.8 y Gradle con la versión 4.10.2 o superior.
> * Otra alternativa para no instalar gradle es usar el comando gradlew al momento de ejecutar el proyecto como se muestro anteriormente.
> * En caso de tener problemas con el web driver por la versión del google chrome, realizar el cambio del web driver (descarga) por una versión compatible con el google chrome instalado. tener en cuenta que la  ruta del web driver en el proyecto es \src\test\resources\drivers
> * Para las pruebas E2E, el reporte serenity se genera en la ruta **target/site/serenity/index.html**, los reportes cucumber se generan en la carpeta **build/cucumber-reports/json**, el archivo **cucumber.json**
> * Para las pruebas API, el reporte se genera en la ruta **build/karate-reports**, el archivo **karate-sumary.html**, y el reporte cucumber en la ruta **build/karate-reports/json**, el archivo **cucumber.json**

## Construido con

La automatización fue desarrollada con:

* BDD - Estrategia de desarrollo
* Screenplay 
* Gradle - Manejador de dependencias
* Cucumber - Framework para automatizar pruebas BDD
* Serenity BDD - Biblioteca de código abierto para la generación de reportes
* Gherkin - Lenguaje Business Readable DSL (Lenguaje especifico de dominio legible por el negocio)

## Documentacion

[Manual SerenityBDD](https://pichincha.atlassian.net/wiki/spaces/CS/pages/2440757667/Manual+Arquetipo+SerenityBDD+ScreenPlay)


# Serenity Appium Mobile

La corrida en Azure Devops se la realiza cambiando las variables dentro de la librería de varibles de Azure APPIUM. 

La corrida es local se la realiza desde el WebRunnerTest. Para el cambio de las desired capabilities de appium, ir al archivo serenity.properties y descomentar la línea de appium

```
webdriver.driver=appium
```

En este archivo serenity-properties se puede cambiar el dispositivo, la plataforma del dispositivo e incluso la ubicación de la app. 

```
appium.deviceName = NOMBREDISPOSITIVO
appium.platformVersion = VERSION
appium.app = PATH_APP
```
------------
La aplicación debe contener dentro de sus dispositivos permitidos el UDID del dispositivo a usarse 