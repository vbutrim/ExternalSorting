package services.sizing;


import static helpers.GlobalProperties.IS_64BIT_SYSTEM;

/*
 * https://habr.com/ru/post/134102/
 */
public final class StringSizeEstimator {


    private static final long CONST_SIZE_OF_STRING;

    static {
        long headerSize = IS_64BIT_SYSTEM ? 16 : 8;
        long intFieldSize = IS_64BIT_SYSTEM ? 8 : 4; // #3
        int referenceSize = IS_64BIT_SYSTEM ? 8 : 4;

        CONST_SIZE_OF_STRING = headerSize + 3 * intFieldSize + referenceSize;
    }

    private StringSizeEstimator() {
    }

    /*
     * return bytes used for keeping String in memory
     */
    public static long getSizeOf(String s) {
        return s.length() * 2 + CONST_SIZE_OF_STRING;       // char (2 bytes)
    }
}
