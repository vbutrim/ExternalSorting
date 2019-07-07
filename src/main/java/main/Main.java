package main;

import services.generating.BigFileService;
import services.sorting.ExternalSortingService;

import java.io.IOException;

import static helpers.GlobalProperties.DATA_FOLDER;
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
        System.out.println("IN_MEMORY_LIMIT_BYTES_FOR_READ: " + IN_MEMORY_LIMIT_BYTES_FOR_READ + " bytes / " + IN_MEMORY_LIMIT_BYTES_FOR_READ / (1024 * 1024) + " megabytes");
        System.out.println("====================================================");
    }

    public static void main(String[] args) throws IOException {

        if (args.length == 1 && "--demonstrate".equals(args[0])) {
            BigFileService.generateNewFile(DATA_INPUT_TXT, LINES_COUNT_IN_GENERATING_FILE, MAX_CHARS_PER_LINE);
            ExternalSortingService.sortAndSave(DATA_INPUT_TXT, DATA_OUTPUT_TXT);
            exitProgram();
        }

        if (args.length == 2 && "--generate".equals(args[0])) {
            String fileName = args[1].trim().toLowerCase();
            BigFileService.generateNewFile(DATA_FOLDER + fileName, LINES_COUNT_IN_GENERATING_FILE, MAX_CHARS_PER_LINE);
            exitProgram();
        }

        if (args.length == 3 && "--sort".equals(args[0])) {
            String fileNameInput = args[1].trim().toLowerCase();
            String fileNameOutput = args[2].trim().toLowerCase();
            ExternalSortingService.sortAndSave(DATA_FOLDER + fileNameInput, DATA_FOLDER + fileNameOutput);
            exitProgram();
        }

        System.out.println("Incorrect arguments");
    }

    private static void exitProgram() {
        System.out.println(System.lineSeparator() + "> Request completed");
        exit(0);
    }
}
