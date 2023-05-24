open module modulith.demo.bootstrap.main {
    requires org.slf4j;
    requires org.jetbrains.annotations;

    requires spring.web;
    requires spring.context;

    requires spring.boot;
    requires spring.boot.autoconfigure;

    requires spring.data.commons;

    requires spring.security.core;
    requires spring.security.oauth2.core;
    requires spring.security.oauth2.jose;
    requires spring.security.oauth2.resource.server;

    requires jakarta.servlet;

//    requires modulith.demo.cargo.management;
//    requires modulith.demo.notifications.management.application;

    requires java.sql;
}
