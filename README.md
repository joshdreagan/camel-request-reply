# Camel Request/Reply

## Requirements

- [Apache Maven 3.x](http://maven.apache.org)

## Preparing

Build the project source code

```
$ cd $PROJECT_ROOT
$ mvn clean install
```

## Running the example standalone

```
$ cd $PROJECT_ROOT
$ mvn spring-boot:run
```

## Testing

There is a SoapUI project in `src/main/soapui/` that includes a sample client as well as a mock callback service.