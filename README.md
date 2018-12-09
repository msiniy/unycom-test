# unycom-test
Unycom coding task solution.

## Requirements
* Java 8
* Maven 3

The frontend part is built with node/npm/webpack using [com.github.eirslett:frontend-maven-plugin](https://github.com/eirslett/frontend-maven-plugin), which downloads and installs all
the requirements automatically. Therefore, no preinstalled node/npm/webpack packages are required.

## Build
This is a regular maven project with a spring-boot-maven-plugin. 
You can build and run it as usual:

```shell
mvn package
java -jar target/coding-example-0.0.1-SNAPSHOT.jar
```
or
```
mvn spring-boot:run
```
