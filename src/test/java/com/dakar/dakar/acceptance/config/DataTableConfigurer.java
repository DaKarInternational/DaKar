package com.dakar.dakar.acceptance.config;

import com.dakar.dakar.models.Journey;
import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableEntryTransformer;

import java.util.Locale;
import java.util.Map;

/*
 * Maps datatables in feature files to custom domain objects.
 */
public class DataTableConfigurer implements TypeRegistryConfigurer {

    @Override
    public Locale locale() {
        return Locale.ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry registry) {
        registry.defineDataTableType(new DataTableType(Journey.class, new TableEntryTransformer<Journey>() {
            @Override
            public Journey transform(Map<String, String> entry) {
                return new Journey(entry.get("id"), entry.get("price"), entry.get("destination"), entry.get("owner"));
            }
        }));

        // Pour ajouter une autre class, faire un autre registry : https://stackoverflow.com/questions/50771856/cucumber-jvm-3-io-cucumber-datatable-undefineddatatabletypeexception/50773192#50773192
    }

}