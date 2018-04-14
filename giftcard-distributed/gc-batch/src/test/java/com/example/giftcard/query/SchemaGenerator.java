package com.example.giftcard.query;

import org.axonframework.eventhandling.tokenstore.jpa.TokenEntry;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.dialect.InnoDBStorageEngine;
import org.hibernate.dialect.MySQL57Dialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

import java.io.File;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class SchemaGenerator {

    public static void main(String[] args) {

        Map<String, Object> settings = new HashMap<>();

        settings.put("hibernate.dialect", MySQL57Dialect.class);
        settings.put("hibernate.dialect.storage_engine", InnoDBStorageEngine.class);
        /* See  https://vladmihalcea.com/why-should-not-use-the-auto-jpa-generationtype-with-mysql-and-hibernate/
                https://hibernate.atlassian.net/browse/HHH-11014 */
        settings.put("hibernate.id.new_generator_mappings", false);
        settings.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class);
        settings.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class);

        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(settings)
                .build();

        MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);

        /* Needed for tracking event processors. */
        metadataSources.addAnnotatedClass(TokenEntry.class);

        metadataSources.addResource("META-INF/orm.xml");
        Metadata metadata = metadataSources.buildMetadata();

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setFormat(true);
        schemaExport.setDelimiter(";");
        File outputFile = new File("V1__init.sql");
        if(outputFile.exists()) outputFile.delete();
        schemaExport.setOutputFile(outputFile.getAbsolutePath());
        schemaExport.createOnly(EnumSet.of(TargetType.STDOUT, TargetType.SCRIPT), metadata);
    }

}
