# **App Generator** (appgen)

#### A Java framework for bootstrapping restful web services 

# Congfiguration
Configuration for setting database connection parameters and **REST** server port can be found in:
`appgen/src/main/resources/application.yaml`

# Create Models:
**Appgen** makes use of the **ORMLite** ORM for annotating models, the documentation for **ORMLite** can be be found [HERE](https://ormlite.com/javadoc/ormlite-core/doc-files/ormlite.html)
Models that you create must extend the `DatabaseEntity` superclass which essentially just ensures every model has a generated integer id. If you prefer a different kind of ID key feel free to change this and recompile.
Models **MUST** be located in the package `appgen/src/main/java/com/appgen/models/`

# Testing
You can easily write test cases using the **Spring Test Framework**. You can find an example under `appgen/src/test/java/com/appgen/`
To execute tests simple execute `./gradlew test --info` from the root directory.

# Building
To build an executable `.jar` of **Appgen** execute `./gradlew build` from the root directory.

# RUN
To start **Appgen** simply execute `./gradlew bootRun`
This will attempt to connect to and create the schema for your models if one does not exist. It will then expose REST services for your models in the following pattern:
> **GET**: `/v1/{model_name_camel_case}`
> **GET**: `/v1/{model_name_camel_case}/{id}`
> **POST**: `/v1/{model_name_camel_case}`
> **PUT**: `/v1/{model_name_camel_case}/{id}`
