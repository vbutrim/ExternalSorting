package services.sorting;

import com.google.inject.Singleton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

@Singleton
final class MergeSortService {

    void mergeSortedFilesAndSave(Map<String, File> sortedFiles, String pathToSave) throws IOException {

        mergeSortedFiles(getInputReaders(sortedFiles.values()), getOutputWriter(pathToSave));

        for (File eachFile : sortedFiles.values()) {
            eachFile.delete();
        }
    }

    /*
     * Merge Sort
     *           --> pick an element from minHeap of all first lines of files
     *           --> add to the output file
     *           --> check if there is another line in used BufferedReader
     */
    private void mergeSortedFiles(List<SmartBufferedReader> bfReaders, BufferedWriter bfWriter) throws IOException {
        Queue<SmartBufferedReader> lexicographicalQueue = new PriorityQueue<>(
                bfReaders.size(),
                Comparator.comparing(SmartBufferedReader::peek));

        for (SmartBufferedReader bfReader : bfReaders) {
            if (!bfReader.isEmpty()) {
                lexicographicalQueue.add(bfReader);
            }
        }

        while (!lexicographicalQueue.isEmpty()) {
            SmartBufferedReader nextLineReader = lexicographicalQueue.poll();

            String nextLine = nextLineReader.pop();

            bfWriter.write(nextLine);
            bfWriter.newLine();

            if (!nextLineReader.isEmpty()) {
                lexicographicalQueue.add(nextLineReader);
            } else {
                nextLineReader.close();
            }
        }

        bfWriter.close();
    }

    private BufferedWriter getOutputWriter(String pathToSave) throws IOException {
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

    private List<SmartBufferedReader> getInputReaders(Collection<File> sortedFiles) throws IOException {
        List<SmartBufferedReader> bfReaders = new ArrayList<>(sortedFiles.size());

        for (File eachFile : sortedFiles) {
            InputStream in = null;
            try {
                in = new FileInputStream(eachFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new Error("Runtime of service has been interrupted");
            }

            bfReaders.add(new SmartBufferedReader(new BufferedReader(new InputStreamReader(in))));
        }

        return bfReaders;
    }

    private final class SmartBufferedReader {

        private final BufferedReader bfr;

        private String nextLine;

        SmartBufferedReader(BufferedReader bfr) throws IOException {
            this.bfr = bfr;
            cacheNextLine();
        }

        /*
         * check if EOF
         */
        boolean isEmpty() {
            return this.nextLine == null;
        }

        /*
         * look on nextLine
         */
        String peek() {
            return nextLine;
        }

        /*
         * return current nextLine and cache next one
         */
        String pop() throws IOException {
            String answer = peek();
            cacheNextLine();
            return answer;
        }

        /*
         * close BufferedReader
         * used after checking isEmpty()
         */
        void close() throws IOException {
            this.bfr.close();
        }

        /*
         * read new line
         */
        private void cacheNextLine() throws IOException {
            this.nextLine = bfr.readLine();
        }
    }
}
