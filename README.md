## Prime Numbers Service

This project has been completed in fulfillment of a technical test. This REST API service takes a GET request with a given number and returns all prime numbers up to and including that number. 

### Development

The application uses Java 8 and has been developed using Spring Boot. It is hosted in a Tomcat container. 

### Getting Started

The application uses Maven to build, test and run. After cloning the repository, this can be done by running the following commands from the root directory of the project:

* **Build**- ./mvnw install
* **Test**- ./mvnw test
* **Run**- ./mvnw spring-boot:run

### Endpoint

The service has one endpoint that can be queried only via GET Request Type:

```
/primes/{initialNum}
```

{initialNum} can be any integer, and the response will contain all prime numbers up to and including that number. Negative numbers, 0 and 1 will return no prime numbers. Non-numeric input, or no input at all, will return an error.

#### Example Request

```
GET localhost:8080/primes/10
```

#### Example Response

```
{
    "Initial": 10,
    "Primes": [
        2,
        3,
        5,
        7
    ]
}
```

#### Varying Return Content Types- JSON/XML

If the GET request doesn't have an 'Accept' header or has the 'Accept' header set to return any content type, the default return content type will be JSON, as shown in the example above. XML can be requested by setting the 'Accept' header value to 'application/xml'. Below is an example of an XML response from a request sent with an {initialNum} of 3.

```
<Primes>
    <initial>3</initial>
    <primes>
        <primes>2</primes>
        <primes>3</primes>
    </primes>
</Primes>
```

### Cache

The application uses a Cache class to store previous results, and uses previous result values to build responses for new requests. The cache holds a result for 5 minute before it is removed. The Cache class implementation comes from this example: https://medium.com/@marcellogpassos/creating-a-simple-and-generic-cache-manager-in-java-e62e4204a10e

### Concurrency

The application uses WebAsyncTask to return responses asynchronously across multiple threads, meaning requests can be handled concurrently.

### Alternate Algorithm

An alternate algorithm can be used by passing an optional parameter, 'alternateAlgo=true' to the request:

#### Example Request

```
GET localhost:8080/primes/10?alternateAlgo=true
```

The alternate algorithm uses the Sieve of Eratosthenes method taken from this example: https://www.baeldung.com/java-generate-prime-numbers
