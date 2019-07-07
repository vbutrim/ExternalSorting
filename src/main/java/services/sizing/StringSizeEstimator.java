package services.sizing;


import static helpers.GlobalProperties.IS_64BIT_SYSTEM;

/*
 * https://habr.com/ru/post/134102/
 */
public final class StringSizeEstimator {



    private static final long CONST_SIZE_OF_STRING;

    static {
        CONST_SIZE_OF_STRING = IS_64BIT_SYSTEM ? 16 : 8 +   // HEADER
                3 * (IS_64BIT_SYSTEM ? 8 : 4) +             // INT fields (x3)
                (IS_64BIT_SYSTEM ? 8 : 4);                  // Reference on Object
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
