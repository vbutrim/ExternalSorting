package services.sorting;

import main.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

public final class ExternalSortingService {

    private final FileSplitterService fileSplitterService;

    private ExternalSortingService(FileSplitterService fileSplitterService) {
        this.fileSplitterService = fileSplitterService;
    }

    public static void sortAndSave(String path) throws FileNotFoundException {
        if (path == null || path.isEmpty()) {
            throw new FileNotFoundException(String.format("Unknown file path '%s'", path));
        }

        File file = new File(path);

        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException(String.format("Unknown file '%s'", path));
        }

        ExternalSortingService sorting = new ExternalSortingService(Main.getInjector().getInstance(FileSplitterService.class));

        Map<String, File> separatedSortedFiles = sorting.fileSplitterService.split(file);

    }
}
