package services.sorting;

import main.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public final class ExternalSortingService {

    private final FileSplitterService fileSplitterService;
    private final MergeSortService mergeSortService;

    private ExternalSortingService(FileSplitterService fileSplitterService, MergeSortService mergeSortService) {
        this.fileSplitterService = fileSplitterService;
        this.mergeSortService = mergeSortService;
    }

    public static void sortAndSave(String pathToRead, String pathToSave) throws IOException {
        if (pathToRead == null || pathToRead.isEmpty()) {
            throw new FileNotFoundException(String.format("Unknown file path '%s'", pathToRead));
        }

        File file = new File(pathToRead);

        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException(String.format("Unknown file '%s'", pathToRead));
        }

        System.out.println("File input: " + pathToRead);

        ExternalSortingService sorting = new ExternalSortingService(new FileSplitterService(), new MergeSortService());

        Map<String, File> separatedSortedFiles = sorting.fileSplitterService.split(file);

        sorting.mergeSortService.mergeSortedFilesAndSave(separatedSortedFiles, pathToSave);
    }
}
