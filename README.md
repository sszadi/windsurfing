# Best windurfing spot service

## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

To run this application on your local machine you can execute the `main` method in the `com.sonalake.windsurfing.WindsurfingApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Example usage

###Find best windsurfing spot for a given date
Example request:

    curl -X GET \
       'http://localhost:8080/api/windsurfing/spot?date=2019-09-13' \
       -H 'content-type: application/json' \
     '
    }

Expected date format is "yyyy-MM-dd".

Example response:

    `{
    "spotName": "Jastarnia",
    "forecast": {
        "temperatureMax": 19.37,
        "temperatureMin": 15.75,
        "windSpeed": 9.29,
        "uvIndex": 3,
        "precipProbability": 0.56,
        "summary": "Possible drizzle in the morning and afternoon.",
        "sunsetTime": 1568394600
        }
    }`


If no place is suitable, API will return a blank answer.
You can extend the checked spots in the database in the table named _Spot_.
