# README

Arquetipo de pruebas automatizadas usando la herramienta SerenityBDD con Screenplay

> **Soporta automatización**:
> * Web
> * Apis
> * Movil
> * As400
> * Pruebas manuales 
> * Scenarios con Karate Framework


## Herramientas y Complementos

|                                                                               **IntelliJ**                                                                                |**Java**|**Gradle**|
|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------:| :----: | :----:  |
| [<img width="50" height="50" src="https://cdn.iconscout.com/icon/free/png-128/intellij-idea-569199.png">](https://www.jetbrains.com/es-es/idea/download/#section=windows) | [<img height="60" src="https://www.oracle.com/a/ocom/img/cb71-java-logo.png">](https://www.oracle.com/java/technologies/downloads/) | [<img height="50" src="https://gradle.org/images/gradle-knowledge-graph-logo.png?20170228">](https://gradle.org/releases/) |

> **NOTA**:

> * Para ejecutar el proyecto se recomienda minimo las siguientes versiones: 
>   * IntelliJ Community Edition 2021
>   * Java JDK 17
>   * Gradle 7.4
> * Una vez obtenido IntelliJ es necesario instalar los plugins de Gherkin y Cucumber for Java. (
    *[Guia de instalación plugins en intellij](https://www.jetbrains.com/help/idea/managing-plugins.html)*)

## Ejecución local

0. Clonar el proyecto

```bash
  git clone https://BancoPichinchaEC@dev.azure.com/BancoPichinchaEC/BP-Quality-Management/_git/sqa-aut-arq-serenitybdd
```

Para correr el proyecto de manera local se debe realizar los siguientes pasos:

1. Definir la tag de los tipos de tests que se van a ejecutar, esto lo hacemos en el archivo WebRunnerTest, para el
   ejemplo se va a correr todos los test menos los de karate, manuales y las pruebas móviles. 

```
tags = "not @karate and not @ManualTest and not @Mobiletest"
```

2. Definir el driver a usarse en serenity.properties.

```
#CONFIGURACION DRIVER
webdriver.driver=chrome
```

## Comandos

El arquetipo posee 2 runners activos, uno para pruebas con SErenityBDD y otro para pruebas con Karate Framework

### Comandos SerenityBDD

Para ejecutar todos los escenarios por linea de comandos

```bash
  ./gradlew clean test --tests "com.pichincha.automationtest.runners.WebRunnerTest"
```

Para ejecutar escenarios que contengan un tag especifico

```bash
  ./gradlew clean test --tests -Dcucumber.filter.tags="@test" com.pichincha.automationtest.runners.WebRunnerTest
```

Para ejecutar los escenarios enviando variables de ambiente

```bash
  ./gradlew clean test --tests "com.pichincha.automationtest.runners.WebRunnerTest" -Dvariable1=test
```

Para ejecutar las pruebas con 3 hilos en paralelo enviando variables de ambiente

```bash
  ./gradlew clean test -PmaxParallelForks=4 --tests "com.pichincha.automationtest.runners.parallel.*" aggregate -i -Dvariable=test
```

### Comandos Karate Framework

Para ejecutar todos los escenarios por linea de comandos

```bash
  ./gradlew clean test --tests "com.pichincha.automationtest.runners.ApiRunnerTest"
```

Para ejecutar escenarios que contengan un tag especifico

```bash
  ./gradlew clean test --tests "-Dkarate.options=--tags @test" "com.pichincha.automationtest.runners.ApiRunnerTest"
```

Para ejecutar los escenarios enviando variables de ambiente

```bash
  ./gradlew clean test --tests "-Dkarate.options=--tags @test" "com.pichincha.automationtest.runners.ApiRunnerTest" -Dvariable1=test
```




> **NOTA**:
> * Antes de empezar a usar el arquetipo para un nuevo proyecto se debe elimiar todos los objetso demo:
>   * 1 Todos los paquetes/carpetas de nombre "demo"
>   * 2 Contenido del paquete/carpeta "sqa-aut-arq-serenitybdd\src\test\resources\data\manualtest\"
> * Para las pruebas de SerenityBDD, el reporte serenity se genera en la ruta **target/site/serenity/index.html**, los reportes
    cucumber se generan en la carpeta **build/cucumber-reports/json**, en el archivo **cucumber.json**
> * Para las pruebas Karate Framework, el reporte se genera en la ruta **build/karate-reports**, en el archivo **karate-sumary.html**, y
    el reporte cucumber en la ruta **build/karate-reports/json**, en el archivo **cucumber.json**

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




# Pruebas automatizadas en dispositivos móviles

Para correr de manera local se debe realizar los siguientes pasos:

1. Definir la tag que debe correr el runner, esto lo hacemos en el archivo WebRunnerTest:

```
tags = "@Mobiletest"
```

2. Definir el driver de appium en serenity.properties

```
#CONFIGURACION DRIVER
webdriver.driver=chrome
```

3. Configurar las desired capabilities del dispositivo sobre el que se va a ejecutar las pruebas automatizadas, esto no
   es más datos referentes al dispositivo y la aplicación a ser probada

A continuación se detalla las principales desired capabilities y como se debe encontrar cada una:

| **Capabilities**       | **Description**                                                                                                                                                                                                                                                                                                                                                                                             |
|------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| platformName           | Plataforma del dispositivo a ser usado, Ppede ser Android o iOS                                                                                                                                                                                                                                                                                                                                             |
| platformVersion        | Es la versión de sistema operativo. <br /> Android: se puede obtener desde configuraciones -> acerca del teléfono <br /> IOS: Settings/ General/About                                                                                                                                                                                                                                                       |
| “DeviceName” or “UDID” | Nombre del dispositivo <br />Android<br />DeviceName: Se puede obtener ejecutando en la linea de comandos: adb devices<br />IOS<br />DeviceName: Se obtiene yendo a la ruta en el iPhone: Configuración->General->Información-> Nombre<br />UDID: El UDID del dispositivo se lo obtiene desde la Mac conectando el dispositivo y entrando al Finder haciendo dos veces clic bajo el nombre del dispositivo. |
| automationName         | Motor de automatización.<br />Para IOS se selecciona XCUITest y para Android se selecciona UIAutomator2                                                                                                                                                                                                                                                                                                     |
| hub                    | Es la dirección del servidor de Appium, por defecto es la siguiente dirección: <br /> http://127.0.0.1:4723/wd/hub                                                                                                                                                                                                                                                                                          |
| app                    | Es la ruta de la aplicación a usarse puede ser del formato .app, .ipa o apk                                                                                                                                                                                                                                                                                                                                 |
| noReset                | Si esta en false borra todos los datos de la aplicación es similar a cuando recién instalamos la aplicación. <br />Si esta en true guarda los datos de la aplicación                                                                                                                                                                                                                                        |

**“appActivity” and “appPackage”**
Para hallar estos datos de nuestra aplicación de Android, verificar que USB Debugging e Instalar vía USB estén
activados. Despues ejecutar la aplicación
y finalmente ejecutar en la línea de comandos:
<br />Mac-Linux:

```bash
adb shell dumpsys window | grep -E 'mCurrentFocus'  
``` 

Windows:

```bash
adb shell dumpsys window | find "mCurrentFocus"
``` 

Formato: package/activity

Las desired capabilities en serenity se configura en el archivo serenity.properties a continuación se muestra algunos
ejemplos
<br />Para dispositivo físico Android

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

```

Para dispositivo físico IOS:

```
##IOS
#appium.platformName=iOS
#appium.platformVersion=16.1
#appium.deviceName=iPhone 12 Mati
#appium.automationName=XCUITest
#appium.udid=00008101-000469341EF8001E
#appium.hub=http://127.0.0.1:4723/wd/hub
#appium.app=/Users/bpuio01610025lm/Apps/app.ipa
```

## Ejecución en BrowserStack

1. Para ejecutar nuestro proyecto en la granja BrowserStack primero se debe revisar en que modelo y versión de sistema
   operativo vamos a ejecutar la prueba, ingresando al siguiente link:

[Lista de dispositivos BrowserStack](https://www.browserstack.com/list-of-browsers-and-platforms/app_automate)

2. Para el caso de las granjas se debe configurar las siguientes desired capabilities de la siguiente forma:

**app:** una vez subida la aplicación en browserStack, la plataforma nos devuelve un link y este link se coloca en esta
propiedad.

**hub:** Colocamos la url del servidor de browserstack este sigue la siguiente estructura:
<br />https://${BROWSERSTACK_USER}:${BROWSERSTACK_KEY}@hub-cloud.browserstack.com/wd/hub
<br />Donde:
<br />{BROWSERSTACK_USER} -> Es el usuario de la cuenta de BrowserStack
<br />{BROWSERSTACK_KEY} -> Es la clave de la cuenta de BrowserStack

En las siguientes líneas se presentan dos ejemplos de la configuración de las desired capabilities para este caso:

Para IOS

```
###BrowserStack IOS
appium.deviceName=iPhone 12
appium.platformVersion=14
appium.platformName=iOS
appium.app=bs://d398f429005d405a82c11edcb827671b52c57db8
appium.hub=https://serenitymobile_FXptXh:okS2z2FqLsHDh3Baw2ag@hub-cloud.browserstack.com/wd/hub
```

Para Android

```
###BrowserStack Android
appium.deviceName=Samsung Galaxy S8
appium.platformVersion=7.0
appium.platformName=Android
appium.app=bs://09ed1fe3c8186c96bf8a7b7400a06d0e93691958
appium.hub=https://serenitymobile_FXptXh:okS2z2FqLsHDh3Baw2ag@hub-cloud.browserstack.com/wd/hub
```

## Ejecución en SouceLab

1. Para ver el listado de dispositivos y versiones de sitemas operativos abrir el siguiente link:
   <br />[Lista de dispositivos SouceLab](https://saucelabs.com/products/supported-browsers-devices)


2. **app:** Al igual que en browserStack se sube la aplicación a la plataforma, el formato que hay que seguir es el
   siguiente:
   <br />storage:filename=nombreDeLaAplicación.extensión
   <br />Ejemplo:
   <br />storage:filename=BancaMovil.apk

Ejemplos de desired capabilities para conexión con SouceLab.

Para Android

```
###SouceLab Android
appium.deviceName=Samsung Galaxy S9
appium.platformVersion=10
appium.platformName=Android
appium.app=storage:filename=BancaMovil.apk
appium.hub=https://oauth-edge.test90v2-41637:23addea2-5b1d-43ac-9b11-b844cc5fc543@ondemand.us-west-1.saucelabs.com:443/wd/hub
```

Para IOS

```
###SouceLab IOS
appium.deviceName=iPhone XR
appium.platformVersion=15.7
appium.platformName=iOS
appium.app=storage:filename=BancaMovil.ipa
appium.hub=https://oauth-edge.test90v2-41637:23addea2-5b1d-43ac-9b11-b844cc5fc543@ondemand.us-west-1.saucelabs.com:443/wd/hub
```

# Correr en el Pipeline pruebas móviles

<br />Para correr el proyecto en el pipeline:

1. Se debe configurar las variables de la library APPIUM ahi se debe setear las desired capabilities del dispositivo de
   la granja y credenciales de acceso.
2. Adicional en el serenity.properties agregar las siguiente líneas:

```
#Desired capabilities para azure pipeline
appium.deviceName=${DEVICE_NAME}
appium.platformVersion=${PLATFORM_VERSION}
appium.platformName=${PLATFORM_NAME}
appium.app=${APPIUM_APP}
appium.hub=https://${BROWSERSTACK_USER}:${BROWSERSTACK_KEY}@hub-cloud.browserstack.com/wd/hub
```