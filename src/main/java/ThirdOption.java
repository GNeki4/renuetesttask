import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class ThirdOption {
    public static void main(String[] args) {
        int column = readColumn() - 1;

        Long start = System.nanoTime();
        TreeMap<String, String> treeMap = readColumn2(column);
        Long end = System.nanoTime();

        System.out.println("Preparations took: " + getTime(start, end));
        System.out.println("Amount of keys: " + treeMap.size());

        var value = readRow();
        findCol2(treeMap, value);
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
            }
        });

        Date newTime = new Date(System.currentTimeMillis());

        for (String row : list) {
            System.out.println(row);
        }
        System.out.println("Total amount of found values is: " + i[0]);
        System.out.println("The search took: " + (newTime.getTime() - currentTime.getTime()) + " m'scs.");
    }

    public static void findCol2(TreeMap<String, String> treeMap, String value) {
        Long start = System.nanoTime();
        ArrayList<String> list = new ArrayList<>();

        Map.Entry<String, String> entry = treeMap.ceilingEntry(value);
        while (entry != null && entry.getKey().startsWith(value)) {
            list.add(entry.getValue());
            entry = treeMap.higherEntry(entry.getKey());
        }

        Long end = System.nanoTime();

        for (String row : list) {
            System.out.println(row);
        }

        System.out.println("Total amount of found values is: " + list.size());
        System.out.println("The search took: "  + getTime(start, end));
    }

    public static void printMemory() {
        Runtime runtime = Runtime.getRuntime();

        var totalMemory = runtime.totalMemory();
        var freeMemory = runtime.freeMemory();
        var usedMemory = (double) (totalMemory - freeMemory) / (double) (1024 * 1024);

        System.out.println("Used memory: " + usedMemory + " mb's.");
    }

    public static String getTime(Long start, Long end) {
        long nanos = end - start;
        long milis = nanos / 1000000;
        return milis + " m'scs " + nanos % 1000000 + "n'scs";
    }

    public static TreeMap<String, String> readColumn2(Integer column) {
        TreeMap<String, String> airportsMap = new TreeMap<>();

        try (Stream<String> stream = Files.lines(Paths.get("airports.dat"))) {
            stream.forEach(line -> {
                String neededColumn = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[column]
                        .replaceAll("\"", "");
                if (airportsMap.containsKey(neededColumn))
                    airportsMap.put(neededColumn, line + "\n" + airportsMap.get(neededColumn));
                else
                    airportsMap.put(neededColumn, line);
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
