import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        int column = readColumn() - 1;
        TreeMap<String, String> treeMap = readAll(column);

        var value = readRow();

        for (int i = 1; i < 2; i++) {
            findSomething(treeMap, value);
        }

        printMemory();
    }

    public static void findSomething(TreeMap<String, String> treeMap, String value) {
        Date currentTime = new Date(System.currentTimeMillis());
        ArrayList<String> list = new ArrayList<>();

        final int[] i = {0};

        treeMap.entrySet().stream().parallel().forEach(row -> {
            if (row.getKey().startsWith(value)) {
                i[0]++;
                list.add(row.getValue());
                //System.out.println(row.getKey());
            }
        });

        Date newTime = new Date(System.currentTimeMillis());


        /*
        for (String row : treeMap.keySet()) {
            if (row.startsWith(value)) {
                i[0]++;
                System.out.println(row);
            }
        }
         */


        for (String row : list) {
            System.out.println(row);
        }
        System.out.println("Total amount of found values is: " + i[0]);
        System.out.println("Preparations took: " + (newTime.getTime() - currentTime.getTime()) + " m'scs.");
    }

    /*
    public static void findSomething(Map<String, String> treeMap, String value) {
        int i = 0;

        for (String row : treeMap.keySet()) {
            if (row.startsWith(value)) {
                i++;
                System.out.println(row);
            }
        }

        System.out.println("Total amount of found values is: " + i);
    }

     */

    public static void printMemory() {
        Runtime runtime = Runtime.getRuntime();

        var totalMemory = runtime.totalMemory();
        var freeMemory = runtime.freeMemory();
        var usedMemory = (double) (totalMemory - freeMemory) / (double) (1024 * 1024);

        System.out.println("Used memory: " + usedMemory + " mb's.");
    }

    public static TreeMap<String, String> readAll(Integer column) {
        TreeMap<String, String> airportsMap = new TreeMap<>();

        try (Stream<String> stream = Files.lines(Paths.get("airports.dat"))) {
            stream.forEach(line -> {
                airportsMap.put(line.split(",")[column].replaceAll("\"", ""), line);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return airportsMap;
    }

    public static Integer readColumn() {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        String column = "1";
        try {
            System.out.println("Input column: ");
            column = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Integer.parseInt(column);
    }

    public static String readRow() {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        String row = "a";
        try {
            System.out.println("Введите строку для поиска: ");
            row = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return row;
    }
}
