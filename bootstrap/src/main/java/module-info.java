open module modulith.demo.bootstrap.main {
    requires lombok;

    requires org.slf4j;
    requires org.jetbrains.annotations;

    requires org.axonframework.config;
    requires org.axonframework.messaging;
    requires org.axonframework.eventsourcing;

    requires org.hibernate.orm.core;

    requires spring.web;
    requires spring.webmvc;
    requires spring.context;

    requires spring.boot;
    requires spring.boot.autoconfigure;

    requires spring.data.commons;

    requires spring.security.core;
    requires spring.security.oauth2.core;
    requires spring.security.oauth2.jose;
    requires spring.security.oauth2.resource.server;

    requires jakarta.servlet;

    requires context.propagation;

    requires java.sql;
}
