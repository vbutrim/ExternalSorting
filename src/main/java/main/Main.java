package main;

import com.google.inject.Guice;
import com.google.inject.Injector;
import helpers.InjectingModule;
import services.sorting.ExternalSortingService;

import java.io.FileNotFoundException;

public class Main {

    private static final Injector injector = Guice.createInjector(new InjectingModule());

    public static void main(String[] args) throws FileNotFoundException {
        ExternalSortingService.sortAndSave("data/input.txt");
    }

    public static Injector getInjector() {
        return injector;
    }
}
