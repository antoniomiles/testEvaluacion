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
Para correr el proyecto de manera local se debe realizar los siguientes pasos:
1. Definir la tag de los tipos de tests que se van a ejecutar, esto lo hacemos en el archivo WebRunnerTest, para el ejemplo se va a correr todos los test menos los manuales, api y las pruebas móviles. Se correra los test de aplicaciones web.
```
tags = "not @karate and not @ManualTest and not @Mobiletest"
```

2. Definir el driver a usarse en serenity.properties y comentar la elección del driver mediante variables de entorno. Para el ejemplo vamos a correr pruebas de una aplicación web por lo tanto vamos a seleccionar el driver de chrome
```
####Configuracion driver
#para corrida local
webdriver.driver=chrome
#para Azure pipeline
#webdriver.driver=${TIPO_DRIVER}
```

3. En el archivo serenity.properties comentar las siguientes líneas para ejecución local.
   (Estas líneas deben ir descomentadas solo cuando se requiere ejecutar pruebas móviles mediante el pipeline)
```
    ###BrowserStack Genérico
    #appium.deviceName=${DEVICE_NAME}
    #appium.platformVersion=${PLATFORM_VERSION}
    #appium.platformName=${PLATFORM_NAME}
    #appium.app=${APPIUM_APP}
    #appium.hub=https://${BROWSERSTACK_USER}:${BROWSERSTACK_KEY}@hub-cloud.browserstack.com/wd/hub
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

Para correr el proyecto en el pipeline se debe configurar las variables de la library APPIUM ahi se debe setear las desired capabilities del dispositivo de la granja y credenciales de acceso.

Para correr de manera local se debe realizar los siguientes pasos:
1. Definir la tag que debe correr el runner, esto lo hacemos en el archivo WebRunnerTest:
```
tags = "@Mobiletest"
```

2. Definir el driver de appium en serenity.properties y comentar la elección del driver mediante variables de entorno
```
####Configuracion driver
#para corrida local
webdriver.driver=appium
#para Azure pipeline
#webdriver.driver=${TIPO_DRIVER}
```

3. Definir el dispositivo para correr la prueba. A partir de la linea 33 en el archivo serenity.properties se define los diferentes dispositivos y escenarios en los que se puede probar la automatización. Descomentar solamente un escenario y configurar las desired capabilities del dispositivo a probar por ejemplo si se quiere probar en dispositivo Android localmente solamente debe estar descomentada la sección Local-Android es decir las líneas:

```
   ###Local
   ##Android
   appium.platformName=Android
   appium.platformVersion=7.0 NRD90M
   appium.deviceName=29d10d5d0704
   appium.automationName=UiAutomator2
   appium.appActivity=com.yellowpepper.pichincha.MainActivity
   appium.appPackage=com.yellowpepper.pichincha
   appium.hub=http://127.0.0.1:4723/wd/hub
   appium.app=/Users/bpuio01610019lm/Documents/BancaMoil/BancaMovil.apk

```
------------
