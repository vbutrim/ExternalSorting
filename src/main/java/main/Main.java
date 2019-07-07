package main;

import com.google.inject.Guice;
import com.google.inject.Injector;
import helpers.InjectingModule;
import services.sorting.ExternalSortingService;

import java.io.IOException;

import static helpers.GlobalProperties.DATA_INPUT_TXT;
import static helpers.GlobalProperties.DATA_OUTPUT_TXT;
import static helpers.GlobalProperties.IN_MEMORY_LIMIT_BYTES_FOR_READ;
import static helpers.GlobalProperties.IS_64BIT_SYSTEM;
import static helpers.GlobalProperties.TEMP_DIR_NAME;

public class Main {

    private static final Injector injector = Guice.createInjector(new InjectingModule());


    static {
        System.out.println("====================================================");
        System.out.println("IS_64BIT_SYSTEM: " + IS_64BIT_SYSTEM);
        System.out.println("TEMP_DIR_NAME: " + TEMP_DIR_NAME);
        System.out.println("IN_MEMORY_LIMIT_BYTES_FOR_READ_WRITE: " + IN_MEMORY_LIMIT_BYTES_FOR_READ + " bytes");
        System.out.println("====================================================");
    }

    public static void main(String[] args) throws IOException {
        ExternalSortingService.sortAndSave(DATA_INPUT_TXT, DATA_OUTPUT_TXT);
    }

    public static Injector getInjector() {
        return injector;
    }
}
