./mvnw -Pproduction clean package docker:build -Dspring
./mvnw docker:push