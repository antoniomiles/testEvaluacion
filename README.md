# README
Desarrollo de pruebas automatizadas para la pagina demoblaze.

Realizadas por:  EquipoE2E

------------------------
<h1 id="pre0">Pre-requisitos</h1>

<h2 id="pre1">1. Programas y complementos</h2>
<br>


|**Intellij**|**Java**|**Gradle**|
| :----: | :----: | :----:  |
|[<img width="50" height="50" src="https://cdn.iconscout.com/icon/free/png-128/intellij-idea-569199.png">](https://www.jetbrains.com/es-es/idea/download/#section=windows)|[<img height="60" src="https://www.oracle.com/a/ocom/img/cb71-java-logo.png">](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)|[<img height="50" src="https://gradle.org/images/gradle-knowledge-graph-logo.png?20170228">](https://gradle.org/releases/)|
> **NOTA**: 
> * Una vez obtenido Intellij es necesario instalar los plugins de Gherkin y Cucumber. (*[Guia de instalación plugins en intellij](https://www.jetbrains.com/help/idea/managing-plugins.html)*) 
> * El navegador usado para la ejecución de las pruebas fue google chrome en su ultima versión ( 98.0.4758.102 )


<h2 id="pre2">3. Modificar código </h2>

- Para realizar modificaciones al codigo del proyecto. realizar los siguientes pasos: 

     
	 1. Importar el proyecto desde IntelliJ IDE bajo la estructura de un proyecto Gradle existente
	 2. Configurar JRE System Library con JavaSE-1.8
	 3. Configurar la codificación a UTF-8 al proyecto una vez sea importado
     4. Ejemplo de corrida ./gradlew test --tests *RegularRunnerTest

<h2 id="pre4">4. Para Ejecutar Las Pruebas </h2>

* Para realizar el proceso de ejecución de los escenarios de pruebas existentes dentro del proyecto se debe realizar el siguiente proceso:

    1. Ir a los runners src/test/java/com.pichincha.automationtest/runners y ejecutarselo
      2. Una vez finalizada la ejecución podremos observar el reporte de las pruebas en la ruta.
	~~~
	target\site\serenity\index.html
	~~~

> **NOTA**: 
> * Para ejecutar el proyecto se necesita Java JDK 1.8 y Gradle con la versión 4.10.2 o superior.
> * Otra alternativa para no instalar gradle es usar el comando gradlew al momento de ejecutar el proyecto como se muestro anteriormente.  
> * En caso de tener problemas con el web driver por la versión del google chrome, realizar el cambio del web driver (descarga) por una versión compatible con el google chrome instalado. tener en cuenta que la  ruta del web driver en el proyecto es \src\test\resources\drivers  
		 

<h2 id="p2">Construido Con </h2>

La automatización fue desarrollada con:

* BDD - Estrategia de desarrollo
* Screenplay 
* Gradle - Manejador de dependencias
* Cucumber - Framework para automatizar pruebas BDD
* Serenity BDD - Biblioteca de código abierto para la generación de reportes
* Gherkin - Lenguaje Business Readable DSL (Lenguaje especifico de dominio legible por el negocio)

------------------------