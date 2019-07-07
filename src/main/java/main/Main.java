package main;

import services.generating.BigFileService;
import services.sorting.ExternalSortingService;

import java.io.IOException;

import static helpers.GlobalProperties.DATA_INPUT_TXT;
import static helpers.GlobalProperties.DATA_OUTPUT_TXT;
import static helpers.GlobalProperties.IN_MEMORY_LIMIT_BYTES_FOR_READ;
import static helpers.GlobalProperties.LINES_COUNT_IN_GENERATING_FILE;
import static helpers.GlobalProperties.MAX_CHARS_PER_LINE;
import static helpers.GlobalProperties.TEMP_DIR_NAME;
import static java.lang.System.exit;

public class Main {

    static {
        System.out.println("====================================================");
        System.out.println("TEMP_DIR_NAME: " + TEMP_DIR_NAME);
        System.out.println("IN_MEMORY_LIMIT_BYTES_FOR_READ: " + IN_MEMORY_LIMIT_BYTES_FOR_READ + " bytes");
        System.out.println("====================================================");
    }

    public static void main(String[] args) throws IOException {

        if (args.length > 1) {
            System.out.println("Incorrect arguments");
            exit(0);
        }

        if (args.length == 0 || "generate".equals(args[0])) {
            BigFileService.generateNewFile(DATA_INPUT_TXT, LINES_COUNT_IN_GENERATING_FILE, MAX_CHARS_PER_LINE);
        }

        if (args.length == 0 || "sort".equals(args[0])) {
            ExternalSortingService.sortAndSave(DATA_INPUT_TXT, DATA_OUTPUT_TXT);
        }

        System.out.println(System.lineSeparator() + "> Request completed");
    }
}
