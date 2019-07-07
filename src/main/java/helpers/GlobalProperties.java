package helpers;

public class GlobalProperties {

    public static final boolean IS_64BIT_SYSTEM = true;

    public static final String TEMP_DIR_NAME = "temp";

    public static final long IN_MEMORY_LIMIT_BYTES_FOR_READ = 100_000L * 9 / 10; // 10% space for checking

    private GlobalProperties() {
    }
}
