package helpers;

public class GlobalProperties {

    public static final boolean IS_64BIT_SYSTEM = true;

    public static final String TEMP_DIR_NAME = "temp";

    public static final long IN_MEMORY_LIMIT_BYTES_FOR_READ = 120_000_000L * 9 / 10; // 10% space for checking

    public static final String DATA_INPUT_TXT = "data/input.txt";

    public static final String DATA_OUTPUT_TXT = "data/output.txt";

    public static final long LINES_COUNT_IN_GENERATING_FILE = 1_000_000;

    public static final int MAX_CHARS_PER_LINE = 1000;

    private GlobalProperties() {
    }
}
