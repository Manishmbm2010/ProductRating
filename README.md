### Project : Adidas product Info & Review Micorservice 

### Technology stack

1. Spring Boot 2.0.4 for writing Microservice
2. STS(Spring tool suite) for code development
3. Swagger for API documentation
4. In memory derby database
5. Maven for dependency management
6. Docker for containerizing the solution

### Code provide "Product Info & Review" as a service that runs in a docker container.

You can do below things via rest services

1. Submit product review.
2. Retrive the average rating and number of review for products.
3. Get the complete product info along with reviews

### Microservice Archtiecture

Overall Solution contains 2 microservices(Product Info and Product Review), running in separate docker containers.

1. Product review service , which opens endpoint to accept reviews for the product as well as allows to update and delete reviews 
2. Product review service also provide end point to retrieve Average product rating with number of reviews information. 
3. Product Info service allows to get the complete product info along with product reviews.

* Proper timeouts has been configured for /product/{productId} endpoint , In case of review service is down or not responsing in 2 seconds , only product info excluding reviews will be returned in response. This makes product service higly available and lossely coupled with review service.
* To minimize the number of database calls and make reveiw service more efficient, caching has been implemneted for many database calls.
* For big installation separate redis server for cache can be deployed. Other caching solution can also be configured for highly sccalable and available solutions.
* Centralized Exception handling is implemented and all the execptions would be redirected to global exception handler and from there final response would be sent to rest client with proper exception message.
* All updates are protected by spring Security (Basic Authentication  username = manish , pasword=manish1$3) 


### Getting Started

These instructions will get you a copy of the project up and running in your local machine for development and testing purposes. See deployment notes below to get the project deployed in your machine.

### Prerequisites

* If you want to follow docker deployment instructions Docker machine should be up and running and docker-compose should be installed for containerized deployment
* For normal instruction, maven and java should be installed.



### Deployment

## Docker Deployment

1. git clone https://Manishmbm2010@bitbucket.org/Manishmbm2010/productreviewservice.git
2. cd productreviewservice/AdidasProductReview
3. docker run -it --rm -v "$PWD":/usr/src/app/ --volume "$HOME"/.m2:/root/.m2 -w /usr/src/app/ maven:3-jdk-8-alpine mvn clean install
4. cd ../AdidasProductInfo/
5. sed -i -e "s/localhost/adidasproductreview/g" src/main/resources/application.properties
6. docker run -it --rm -v "$PWD":/usr/src/app/ --volume "$HOME"/.m2:/root/.m2 -w /usr/src/app/ maven:3-jdk-8-alpine mvn clean install
7. docker-compose up --build

## Deployment instruction for Linux Machine

1. git clone https://Manishmbm2010@bitbucket.org/Manishmbm2010/productreviewservice.git
2. cd productreviewservice/AdidasProductReview/
3. mvn clean install
4. java -jar ./target/adidas-product-review-1.0.jar
5. cd ../AdidasProductInfo
6. mvn clean install
7. java -jar ../AdidasProductInfo/target/adidas-product-info-1.0.jar

### Rest endpoints

#### Rest endpoints for Product Review Service :

* http://localhost:8081/review/				(method=POST)
* Input Data : {"userId":"Manish", "productId": "M20324","rating":2.5 }
* http://localhost:8081/review/{productId}		(method=GET)
* http://localhost:8081/review/{userId}/{productId}	(method=PATCH)
* Input : {"rating":6.5 } 
* Output :  {"productId": "M20324",    "userId": "Manish",    "rating": 6.5}
* http://localhost:8081/review/{userId}/{productId}	(method=DELETE)
* http://localhost:8081/review/admin/health		(method=GET)
* http://localhost:8081/review/admin/info		(method=GET)
* http://localhost:8081/review/admin/shutdown 		(method=POST)

* Swagger UI
* http://localhost:8081/swagger-ui.html

#### Rest endpoints for Product Info Service

* http://localhost:8080/product/{productId}	(method=GET)
* http://localhost:8081/review/health		(method=GET)
* http://localhost:8081/review/admin/info	(method=GET)
* http://localhost:8081/review/admin/shutdown 	(method=POST)  -- Should be sensitive

* Swagger UI
* http://localhost:8080/swagger-ui.html


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

### Scope for improvement 

* Currently solution has been designed with embedded derby database , can be configured with separate standalone database infa for persistent storage.
* Logging can be enhanced.
* Comments can be improved and extended further to make the maintenace of code much simpler later on.


## Author

 **Manish Jain**
