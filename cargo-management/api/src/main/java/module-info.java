module modulith.demo.cargo.management.api {
    exports com.example.modulith.demo.cargo.management.api.model;
    exports com.example.modulith.demo.cargo.management.api.spring.web;

    requires spring.web;
    requires spring.context;

    requires jakarta.servlet;
    requires jakarta.annotation;
    requires jakarta.validation;

    requires com.fasterxml.jackson.databind;

    requires io.swagger.v3.oas.annotations;

    requires org.openapitools.jackson.nullable;
}
