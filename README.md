# Instrucciones para configurar el proyecto

Este proyecto requiere varias bibliotecas que deben ser descargadas manualmente desde Maven Central y añadidas al proyecto como archivos `.jar`. A continuación se describen los pasos para hacerlo.

## Librerías necesarias

Las siguientes librerías deben ser descargadas desde Maven Central:

1. **JUnit 4.13.2**  
   URL: [JUnit 4.13.2](https://search.maven.org/artifact/org.junit.jupiter/junit-jupiter-api/4.13.2/jar)

2. **Jackson Databind 2.15.2**  
   URL: [Jackson Databind 2.15.2](https://search.maven.org/artifact/com.fasterxml.jackson.core/jackson-databind/2.15.2/jar)

3. **Jackson Core 2.15.2**  
   URL: [Jackson Core 2.15.2](https://search.maven.org/artifact/com.fasterxml.jackson.core/jackson-core/2.15.2/jar)

4. **Jackson Annotations 2.15.2**  
   URL: [Jackson Annotations 2.15.2](https://search.maven.org/artifact/com.fasterxml.jackson.core/jackson-annotations/2.15.2/jar)

5. **JavaFX SDK 21.0.5**  
   Descargar la versión correspondiente desde el [sitio web oficial de JavaFX](https://gluonhq.com/products/javafx/).  
   Las siguientes bibliotecas JavaFX deben ser incluidas:
   - `javafx.base.jar`
   - `javafx.controls.jar`
   - `javafx.fxml.jar`
   - `javafx.graphics.jar`
   - `javafx.media.jar`
   - `javafx.swing.jar`
   - `javafx.web.jar`
   - `javafx-swt.jar`

## Pasos para agregar las librerías

1. **Descargar los archivos JAR** desde los enlaces proporcionados para las bibliotecas JUnit y Jackson.
2. **Descomprimir el JavaFX SDK** descargado y ubicar los archivos `.jar` de JavaFX en el directorio correspondiente.
3. **Agregar los archivos JAR a tu proyecto**:
   - Coloca los archivos `.jar` descargados en una carpeta de tu proyecto (por ejemplo, `lib`).
   - Asegúrate de que las rutas de las bibliotecas en tu archivo `.classpath` apunten correctamente a la ubicación de los archivos JAR en tu proyecto.

## Configuración en el archivo `.classpath`

El archivo `.classpath` de tu proyecto debe contener las entradas de las bibliotecas de la siguiente manera:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<classpath>
    <classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-21">
        <attributes>
            <attribute name="module" value="true"/>
        </attributes>
    </classpathentry>
    <classpathentry kind="src" path="src"/>
    <classpathentry kind="lib" path="C:/Users/dustf/Downloads/junit-4.13.2.jar"/>
    <classpathentry kind="lib" path="D:/software/javafx-sdk-21.0.5/lib/javafx.base.jar"/>
    <classpathentry kind="lib" path="D:/software/javafx-sdk-21.0.5/lib/javafx.controls.jar"/>
    <classpathentry kind="lib" path="D:/software/javafx-sdk-21.0.5/lib/javafx.fxml.jar"/>
    <classpathentry kind="lib" path="D:/software/javafx-sdk-21.0.5/lib/javafx.graphics.jar"/>
    <classpathentry kind="lib" path="D:/software/javafx-sdk-21.0.5/lib/javafx.media.jar"/>
    <classpathentry kind="lib" path="D:/software/javafx-sdk-21.0.5/lib/javafx.swing.jar"/>
    <classpathentry kind="lib" path="D:/software/javafx-sdk-21.0.5/lib/javafx.web.jar"/>
    <classpathentry kind="lib" path="D:/software/javafx-sdk-21.0.5/lib/javafx-swt.jar"/>
    <classpathentry kind="lib" path="C:/Users/dustf/Downloads/jackson-databind-2.15.2.jar"/>
    <classpathentry kind="lib" path="C:/Users/dustf/Downloads/jackson-core-2.15.2.jar"/>
    <classpathentry kind="lib" path="C:/Users/dustf/Downloads/jackson-annotations-2.15.2.jar"/>
    <classpathentry kind="output" path="bin"/>
</classpath>
