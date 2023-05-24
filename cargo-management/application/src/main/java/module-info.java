module modulith.demo.cargo.management.application {
    requires modulith.demo.cargo.management.api;
    requires modulith.demo.notifications.management.api;

    requires lombok;

    requires spring.tx;
    requires spring.context;
    requires spring.aspects;

    requires spring.data.jpa;
    requires spring.data.commons;

    requires spring.security.core;
    requires spring.security.oauth2.jose;
    requires spring.security.oauth2.resource.server;

    requires jakarta.persistence;

    requires org.slf4j;

    requires org.jmolecules.ddd;
    requires org.jmolecules.event;

    requires org.hibernate.orm.core;

    requires org.jetbrains.annotations;

    requires org.axonframework.messaging;

    requires org.springframework.modulith.api;
}
