package services.sorting;

import com.google.inject.Singleton;
import helpers.GlobalProperties;
import services.sizing.StringSizeEstimator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static helpers.GlobalProperties.TEMP_DIR_NAME;

@Singleton
class FileSplitterService {

    private static int barrierOfLines = 5;

    /*
     * Returns sorted files, that can be read in memory
     */

    Map<String, File> split(File file) {
        System.out.println("File size: " + file.length() + " bytes");
        File tempFolderDir = createTempDir(file);
        Map<String, File> files = readNLinesSortThemAndSavePerFile(file, tempFolderDir);

        System.out.println("Temp files:" + System.lineSeparator() + "    - " + String.join(System.lineSeparator() + "    - ", files.keySet()));

        return files;
    }

    private File createTempDir(File file) {
        File tempFolder = new File(file.getParentFile().getPath(), TEMP_DIR_NAME);
        if (!tempFolder.exists()) {
            tempFolder.mkdir();
        }

        System.out.println("Using temp folder: " + tempFolder);

        return tempFolder;
    }

    private Map<String, File> readNLinesSortThemAndSavePerFile(File fileToRead, File folderToWrite) {
        Map<String, File> filePerName = new HashMap<>();

        int countOfFile = 1;
        long currentInMemoryBytes = 0L;
        List<String> sortedStrings = new ArrayList<>(barrierOfLines);
        String nameOfFile = fileToRead.getName();

        try (FileInputStream inputStream = new FileInputStream(fileToRead.getAbsolutePath());
             Scanner sc = new Scanner(inputStream, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                sortedStrings.add(line);
                currentInMemoryBytes += StringSizeEstimator.getSizeOf(line);

                if (currentInMemoryBytes > GlobalProperties.IN_MEMORY_LIMIT_BYTES_FOR_READ) {
                    File newTempFile = new File(folderToWrite.getAbsolutePath(), nameOfFile + "_" + countOfFile++);
                    Collections.sort(sortedStrings);
                    writeNSortedLinesToFile(newTempFile.getAbsolutePath(), sortedStrings);
                    sortedStrings.clear();
                    currentInMemoryBytes = 0L;

                    filePerName.put(newTempFile.getName(), newTempFile);
                }
            }

            if (sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File newTempFile = new File(folderToWrite.getAbsolutePath(), nameOfFile + "_" + countOfFile);
        writeNSortedLinesToFile(newTempFile.getAbsolutePath(), sortedStrings);
        sortedStrings.clear();
        filePerName.put(newTempFile.getName(), newTempFile);

        return filePerName;
    }

    private void writeNSortedLinesToFile(String path, List<String> data) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8))) {
            for (String s : data) {
                pw.println(s);
            }
            pw.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
