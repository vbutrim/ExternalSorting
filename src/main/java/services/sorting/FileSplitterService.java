package services.sorting;

import com.google.inject.Singleton;

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

@Singleton
class FileSplitterService {

    private static final String TEMP_DIR_NAME = "temp";
    private static final int BARRIER_OF_LINES = 5;

    /*
     * Returns sorted files, that can be read in memory
     */

    Map<String, File> split(File file) {
        File tempFolderDir = createTempDir(file);
        return readNLinesSortThemAndSavePerFile(file, tempFolderDir);
    }

    private File createTempDir(File file) {
        File tempFolder = new File(file.getParentFile().getPath(), TEMP_DIR_NAME);
        System.out.println(tempFolder);
        if (!tempFolder.exists()) {
            tempFolder.mkdir();
        }

        return tempFolder;
    }

    private Map<String, File> readNLinesSortThemAndSavePerFile(File fileToRead, File folderToWrite) {
        Map<String, File> filePerName = new HashMap<>();

        int countOfFile = 1;
        int countOfInMemoryLines = 0;
        List<String> sortedStrings = new ArrayList<>(BARRIER_OF_LINES);
        String nameOfFile = fileToRead.getName();

        try (FileInputStream inputStream = new FileInputStream(fileToRead.getAbsolutePath());
             Scanner sc = new Scanner(inputStream, StandardCharsets.UTF_8)) {
            while (sc.hasNextLine()) {
                ++countOfInMemoryLines;
                sortedStrings.add(sc.nextLine());

                if (countOfInMemoryLines > BARRIER_OF_LINES - 1) {
                    File newTempFile = new File(folderToWrite.getAbsolutePath(), nameOfFile + "_" + countOfFile++);
                    Collections.sort(sortedStrings);
                    writeNSortedLinesToFile(newTempFile.getAbsolutePath(), sortedStrings);
                    sortedStrings.clear();
                    countOfInMemoryLines = 0;

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
