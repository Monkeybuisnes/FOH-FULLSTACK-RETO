docker build -t foh-backend .
docker run -e SPRING_DATASOURCE_URL=jdbc:oracle:thin:@<host>:1521/XE \
           -e SPRING_DATASOURCE_USERNAME=system \
           -e SPRING_DATASOURCE_PASSWORD=oracle \
           -p 8080:8080 foh-backend
