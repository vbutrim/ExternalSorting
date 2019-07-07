package helpers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GlobalExecutorService {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(1000);

    private GlobalExecutorService() {
    }

    public static ExecutorService getExecutorService() {
        return executorService;
    }
}
