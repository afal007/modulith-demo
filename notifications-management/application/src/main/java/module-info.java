module modulith.demo.notifications.management.application {
    requires modulith.demo.notifications.management.api;

    requires lombok;

    requires spring.web;
    requires spring.context;
    requires spring.security.core;
    requires spring.security.oauth2.jose;
    requires spring.security.oauth2.resource.server;

    requires jakarta.persistence;

    requires org.slf4j;

    requires org.jmolecules.event;

    requires org.hibernate.orm.core;

    requires org.axonframework.messaging;
}
