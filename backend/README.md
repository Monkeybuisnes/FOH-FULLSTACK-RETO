# Backend FOH FullStack

## Requisitos previos
- Java 17 (o 21), Maven.
- Oracle XE corriendo (local o vía Docker).
- Variables de entorno (opcional):
  - `SPRING_DATASOURCE_URL` (p. ej. `jdbc:oracle:thin:@localhost:1521/XE`)
  - `SPRING_DATASOURCE_USERNAME` (p. ej. `system`)
  - `SPRING_DATASOURCE_PASSWORD` (p. ej. `oracle`)
  - `SERVER_PORT` (por defecto 8080)

## Ejecución local
1. Asegúrate de que Oracle está accesible en la URL/credenciales configuradas.
2. Desde la raíz del módulo backend:
   ```bash
   mvn clean spring-boot:run
