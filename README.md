# maven01
##Ejemplo simple de maven


### Para generar jar con dependencias incluidas ejecutar
mvn clean package assembly:single

### Para generar jar con dependencias saltando test
mvn clean package assembly:single -Dmaven.test.skip=true

