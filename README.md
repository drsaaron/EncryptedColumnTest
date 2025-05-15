# EncryptedColumnTest

This project will demonstrate the use of encrypted columns via spring JPA.
The main sources I've used are

* https://thoughts-on-java.org/how-to-use-jpa-type-converter-to/ to define the converter
* https://www.baeldung.com/jpa-attribute-converters for integration into spring JPA

The application will write a row to a table, with the name encrypted.  A `@Convert` annotation
is added to the person name property in the entity bean, and spring jpa does the rest.