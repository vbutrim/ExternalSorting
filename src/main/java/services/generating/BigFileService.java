package services.generating;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

import static helpers.GlobalProperties.LINES_COUNT_IN_GENERATING_FILE;
import static helpers.GlobalProperties.MAX_CHARS_PER_LINE;

public final class BigFileService {

    private static final Random symbolsCountRnd = new Random();

    private BigFileService() {
    }

    public static void generateNewFile(String pathToNewFile, long linesCount, int maxCharsInLine) throws IOException {
        logAssumptions();

        BufferedWriter bfWriter = getNewFileWriter(pathToNewFile);

        for (long i = 0; i < linesCount; ++i) {
            String nextRandomString = RandomStringUtils.random(symbolsCountRnd.nextInt(maxCharsInLine), true, true);
            bfWriter.write(nextRandomString);
            bfWriter.newLine();
        }

        bfWriter.close();
    }

    public static BufferedWriter getNewFileWriter(String pathToSave) throws IOException {
        System.out.println("File output: " + pathToSave);
        File outputFile = new File(pathToSave);

        if (outputFile.exists()) {
            outputFile.delete();
        }
        outputFile.createNewFile();

        try {
            return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile, true)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new Error("Runtime of service has been interrupted");
        }
    }

    private static void logAssumptions() {
        System.out.println("LINES_COUNT_IN_GENERATING_FILE: " + LINES_COUNT_IN_GENERATING_FILE);
        System.out.println("MAX_CHARS_PER_LINE: " + MAX_CHARS_PER_LINE);
        System.out.println("====================================================");
    }
}
