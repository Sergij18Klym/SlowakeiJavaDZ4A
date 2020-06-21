import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
  * Classname: Main
  *
  * @version     19 June 2020
  * @author      Klymenko Sergij, ONPU
  *
  *  Module 4, task 1. Files management.
  *
  * 1. Parse the file logs.txt (see the attachment).
  * 2. Calculate the total number of logs (lines).
  * 3. Calculate the total number of ERROR logs. Use previous skills - String.split
  * 4. Calculate the total number of ERROR logs. Use Files.lines.
  * 5. Compare two results.
  *
  **/

public class Main {

    public static void main(String[] args) throws IOException {

        /**
         * Parsing the file containing logs into String 'text'
         */
        String text = new String(Files.readAllBytes(Paths.get("E:\\logs.txt")));

        /**
         * Splitting the String 'text' variable with line separators
         * and putting into an array.
         * The length of the array corresponds to the number of split lines in the doc
         */
        final String[] Array1 = text.split("\n");
        System.out.println("Number of lines: " + Array1.length);

        /**
         * Calculating the number of ERROR-log lines in the array using old-school java-core
         */
        LocalDateTime start1 = LocalDateTime.now();

        int errorCount = 0;

        for(int i=0; i<Array1.length; i++){
            if (Array1[i].contains("ERROR")){
                errorCount++;
            }
        }

        LocalDateTime fin1 = LocalDateTime.now();

        System.out.println("Number of ERROR lines: " + errorCount);
        long dif1 = ChronoUnit.MILLIS.between(start1,fin1);
        System.out.println("The process has taken " + dif1 + " ms");


        /**
         * Calculating the number of ERROR-log lines in the array using streams
         */
        LocalDateTime start2 = LocalDateTime.now();

        final long errors = Files.lines(Paths.get("E:\\logs.txt"))
            .filter(line -> line.contains("ERROR"))
            .count();

        LocalDateTime fin2 = LocalDateTime.now();

        System.out.println("\nvia streams:\nNumber of ERROR lines: " + errors);
        long dif2 = ChronoUnit.MILLIS.between(start2,fin2);
        System.out.println("The process has taken " + dif2 + " ms");

        /**
         * Comparing the duration results of both methods
         */
        if (dif2 < dif1) {
            System.out.println("\nCalculating the number of ERROR logs is quicker using streams.");
        }
        else {
            System.out.println("\nCalculating the number of ERROR logs is quicker using java-core.");
        }

        /**
         * Writing all the ERROR-logs of a specific date into a file using streams
         */
        List<String> specLog = Files.lines(Paths.get("E:\\logs.txt"))
                .filter(line -> line.contains("2019-10-13"))
                .collect(Collectors.toList());

        String stringToFile = "";

        for (String line:specLog) {
            stringToFile += line + System.lineSeparator();
        }
        Path path = Paths.get("E:\\ERROR-2019-10-13.log");
        Files.write(path, stringToFile.getBytes());

        System.out.println("\nFile 'ERROR-2019-10-13.log' has been successfully written.");

    }
}
