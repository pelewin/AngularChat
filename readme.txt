Require Java 8

Run: mvn spring-boot:run (embedded tomcat is used)

URLs:
get all customers:   GET    localhost:8080/customers
add customer:        POST   localhost:8080/customers
get one customer:    GET    localhost:8080/customers/{id}
delete customer:     DELETE localhost:8080/customers/{id}
update customer:     PUT    localhost:8080/customers/{id}

See examples in Soap-ui client.xml (SoapUI is required to open - http://www.soapui.org/)

Compile/test: mvn install