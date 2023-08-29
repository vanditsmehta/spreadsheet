# spreadsheet

## REQUIREMENTS
1. OpenJDK 17
2. Set JAVA_HOME

## RUN APPLICATION
1. Clone the repository (git clone git@github.com:vanditsmehta/spreadsheet.git)
2. ./mvnw install
3. ./mvnw spring-boot:run

## Test Application
After starting the application, you can test it via
### Swagger UI at the URL:
http://localhost:8080/swagger-ui/index.html#/

### Curl
1. Get Cell Value
```shell
curl -X GET "http://localhost:8080/api/sheet/{cellId}" -H "accept: */*"
```

2. Set Cell Value
```shell
curl -X POST "http://localhost:8080/api/sheet/{cellId}" -H "accept: */*" -H "Content-Type: text/plain" -d "=(A1+A2)*3"
```

### Postman or other similar tool
