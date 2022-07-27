# Crear usuario

## Clonar dispositivo

Para clonar el repositorio es necesario ejecutar el siguiente comando.
Solo cuenta con un branch, por lo que no es necesario moverse una distinta.

````
git clone https://github.com/cpavezba/ejercicioey.git
````
## Precondiciones

Para poder compilar el proyecto es necesario contar con java 1.8+, ademas de gradle.
No es necesario ejecutar algun script SQL, dado que la base de datos que se utiliza esta en memoria (H2) 


## Compilar

Una vez descargado las fuentes se debe acceder a la raiz del proyecto y en una consola de comandos se debe ejecutar el siguiente comando.

````
gradle clean build
````

## Levantar aplicación

Una vez compilado el proyecto es necesario ejecutar el siguiente comando para levantar la aplicacion java.

````
gradle bootRun
````

## Probar

Con la aplicacion corriendo correctamente ejecutamos el siguinte comando curl.
También es posible ejecutar a través de postman.

````
curl --location --request POST 'http://localhost:8080/user' \
--header 'Content-Type: application/json' \
--data-raw '{
	"name": "Juan Rodriguez",
	"email": "juan@rodriguez.org",
	"password": "Hunter22",
	"phones": [
		{
			"number": "1234567",
			"citycode": "1",
			"countrycode": "57"
		}
	]
}'
````

Los test unitarios es posible ejecutarlos por medio del comando

````
gradle test
````