# Sistema de gestión de parquímetro: anatomía de un proyecto Maven

Este proyecto contiene un API REST que vamos a usar para ver un ejemplo de un proyecto Maven diferente a la práctica que
hacemos a lo largo de la asignatura.

- [Sistema de gestión de parquímetro: anatomía de un proyecto Maven](#sistema-de-gesti-n-de-parqu-metro--anatom-a-de-un-proyecto-maven)
    * [¿Qué es eso de Maven y para qué sirve?](#-qu--es-eso-de-maven-y-para-qu--sirve-)
    * [Las coordenadas: ¿Cómo encontramos el proyecto?](#las-coordenadas---c-mo-encontramos-el-proyecto-)
    * [Las dependencias y el famoso "scope"](#las-dependencias-y-el-famoso--scope-)
        + [Dependencias de compilación (`compile`)](#dependencias-de-compilaci-n---compile--)
        + [Dependencias de test](#dependencias-de-test)
        + [Dependencias proporcionadas (`provided`)](#dependencias-proporcionadas---provided--)
    * [Versiones](#versiones)
        + [El versionado semántico](#el-versionado-sem-ntico)
    * [Conclusiones](#conclusiones)

## ¿Qué es eso de Maven y para qué sirve?

Vamos a hacer una analogía entre el software y la cocina: supongamos que vamos a cocinar algo. Necesitamos los ingredientes, 
utensilios y una receta. En ese caso, Maven es el _jefe de cocina_.

Maven es lo que llamamos un *sistema de gestión de construcción* (o _build system_). 
Básicamente, nos hace la vida mucho más fácil encargándose de estas tareas:

* Se encarga de los "recados": Si necesitamos una librería de fuera (por ejemplo, 
Javalin para montar el servidor o Jackson para manejar datos JSON),
Maven la busca en Internet, la descarga y la conecta a tu proyecto por ti automáticamente. 
Nos ahorramos la manualidad de buscar las dependencias y agregarlas a nuestro proyecto.

* Todo en su sitio: Maven impone orden. Todos sus proyectos usan la misma estructura de carpetas 
(como `src/main/java` para el código). 

* Lo hace todo solito: En lugar de darle a mil botones, con un solo comando Maven compila el proyecto, 
pasa los tests para ver que no has roto nada y genera el artefacto correspondiente (el fichero `.jar`) y
lo deja listo para entregar

Toda la información relevante está en el fichero `pom.xml` que está en la raíz del proyecto.

## Las coordenadas: ¿Cómo encontramos el proyecto?

Como hay millones de proyectos en Java por todo el mundo, 
necesitamos una forma de identificarlos de forma unívoca y que nuestro proyecto sea único. 
Para ello usamos tres datos clave que funcionan como una dirección de correos:

* *groupId* (`es.ucm.fdi.tp`): Es como el "apellido" de la familia o el nombre de la organización. 
Por convención, usamos la dirección de una web al revés. Aquí indica que este código nace en la FDI de la UCM.

* *artifactId* (`parkimeter-rest-api`): Es el nombre de la "criatura". Es el identificador específico 
de este proyecto dentro de la organización. Si hiciéramos otro proyecto para una app móvil, tendría otro `artifactId`.

* *version* (`1.0.0-SNAPSHOT`): Nos dice en qué punto del desarrollo estamos. 
Es fundamental para saber si lo que tenemos entre manos es una versión antigua, la nueva o una que todavía está a medio hacer.

## Las dependencias y el famoso "scope"

Las dependencias son piezas de código que otros programadores ya han escrito y que nosotros aprovechamos para no 
"reinventar la rueda". ¿Para qué escribir desde cero un servidor web si podemos usar uno que ya funciona?

En el `pom.xml` verás que cada librería tiene un _scope_ (ámbito). Es la forma de decirle a Maven cuándo 
debe cargar esa herramienta:

### Dependencias de compilación (`compile`)

Son las que necesitamos siempre. Forman parte del programa y sin ellas no podría funcionar. No se les llama dependencias
por casualidad

Por ejemplo: `javalin` (que es el motor del servicio REST) o `slf4j` (que nos ayuda a escribir mensajes de log en la consola).

### Dependencias de test

Estas son sólo para el tiempo de desarrollo. Solo se activan cuando estamos comprobando que todo va bien: durante los tests. 
Cuando termine el desarrollo y entreguemos el artefacto al usuario final, 
estas librerías no se entregan para no ocupar espacio innecesario en el móvil o servidor del cliente.

Por ejemplo: `junit` o `mockito`

### Dependencias proporcionadas (`provided`)

Aunque las vamos a usar de por el momento, está bien saber qué son. 
Son librerías que necesitamos para desarrollar en el IDE, 
pero que no guardamos en el paquete final porque sabemos que el entorno de donde se va a ejecutar nuestro programa
ya las incorpora (es decir, las "proporciona").

## Versiones
El software evoluciona de forma natural, y el número de versión así lo refleja. La realidad cercana tiene multitud
de ejemplos: iOS 26, Windows 11, Java 21/22/, Ubuntu 26.04,...

Nuestro proyecto no va a ser menos. Vaya por delante que cada desarrollador o equipo de producto versiona
su software como le viene en gana. A veces responde a criterios de marketing. Pero existen algunos convenios
y aquí vamos a hablar de uno de ellos: el [*versionado semántico*](https://semver.org/lang/es/). Se denomina así porque el número de la versión
tiene significado por sí mismo.

### El versionado semántico

Es un sistema de tres números separados por un punto: `MAYOR.MENOR.PARCHE` (en inglés, `MAJOR.MINOR.PATCH`).
Los incrementos en cada uno de los números significan una cosa distinta.

* PATCH: los incrementos en este apartado significa que se han arreglado errores o se han corregido erratas.
* MINOR: este número aumenta cuando se añaden funcionalidades que es compatible con las versiones anteriores. 
* MAJOR: los cambios en este número indican cambios incompatibles con versiones anteriores.

Esto quiere decir, por ejemplo, que el desarrollador sabe, en estos casos:

* Es seguro y recomendable pasar de `javalin 5.15.2` a `javalin 5.15.3` porque se ha arreglado algún error 
por el camino y nuestro proyecto, que lo tiene por dependencia, no tendrá impacto.
* Que pasar de `javalin 5.15.3` a `javalin 5.16.0` es seguro, porque incluye nuevas funcionalidades (que podríamos
querer aprovechar) y no tendrá impacto en nuestro proyecto porque la "interfaz" de javalin no ha cambiado.
* En cambio, pasar de `javalin 5.17.1` a `javalin 6.0.0` implica que habrá cosas que no se comporten igual, métodos o
clases han podido desaparecer o recibir distintos tipos de parámetros, ... En definitiva, si queremos subir de
versión, nos tocará evaluar el impacto y adaptar el código de nuestro proyecto.

¿Y lo de SNAPSHOT? Es una palabra que significa que el proyecto está "en desarrollo". 
Indica que es una versión inestable que estamos cambiando cada cinco minutos y sin previo aviso. 
Solo cuando estemos contentos, le quitaremos ese nombre y lanzaremos la versión oficial (que llamamos _liberar la 
release_)

> [!IMPORTANT]
> **NUNCA** Usamos versiones SNAPSHOT como dependencias en nuestro proyecto.

## Conclusiones

Para que no tengas que pelearte con los menús de configuración de tu IDE (IntelliJ, Eclipse o VS Code), 
hemos dejado listo atajo. Solo tienes que abrir la terminal en la carpeta del proyecto y escribir:
```
mvn exec:java
```

¿Qué hace esto por debajo? Maven lee el `pom.xml`, busca dónde le dijimos que estaba la clase principal del programa, 
prepara todas las librerías necesarias y arranca el programa.

¡Al turrón! 🚀
