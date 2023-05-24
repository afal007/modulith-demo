module modulith.demo.notifications.management.api {
    exports com.example.modulith.demo.notifications.management.api.model;
    exports com.example.modulith.demo.notifications.management.api.command;
    exports com.example.modulith.demo.notifications.management.api.spring.web;

    requires org.springframework.modulith.api;

    requires spring.web;
    requires spring.context;

    requires jakarta.servlet;
    requires jakarta.annotation;
    requires jakarta.validation;

    requires org.jmolecules.event;

    requires com.fasterxml.jackson.databind;

    requires io.swagger.v3.oas.annotations;

    requires org.openapitools.jackson.nullable;
}
