package com.example.modulith.demo.bootstrap;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

import com.example.modulith.demo.ModulithDemoApplication;

public class ArchitectureTests {

    @Test
    void modulith_checkModules() {
        ApplicationModules modules = ApplicationModules.of(ModulithDemoApplication.class);
        modules.forEach(System.out::println);
        modules.verify();
    }

    @Test
    void modulith_createDocumentation() {
        ApplicationModules modules = ApplicationModules.of(ModulithDemoApplication.class);
        new Documenter(modules).writeDocumentation().writeIndividualModulesAsPlantUml();
    }
}
