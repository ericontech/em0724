# em0724

## System Requirements

- Java 11 or greater
- Maven 3.9 or greater

## Build

`mvn package`

## How to run

`java -jar tool-rental-1.0-SNAPSHOT.jar <toolCode> <rentalDays> <discountPercent> <checkoutDate>`

Example:

`java -jar target/tool-rental-app-1.0-SNAPSHOT.jar JAKR 5 10 7/18/24`

## Unit tests

`mvn test`
