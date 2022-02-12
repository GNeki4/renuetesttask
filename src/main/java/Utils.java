import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Utils {
    public static TreeMap<String, String> readFile(Integer column) {
        TreeMap<String, String> airportsMap = new TreeMap<>();

        try (Stream<String> stream = Files.lines(Paths.get("classes/airports.dat"))) {
            stream.forEach(line -> {
                String[] split = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                String key = split[column].replaceAll("\"", "") + split[0];

                airportsMap.put(key, line);
            });

        } catch (IOException e) {
            System.out.println("Файл отсутствует.");
            System.exit(0);
        }

        return airportsMap;
    }

    public static int parseColumn(String[] args) {
        int column = 1;
        if (args.length < 1)
            return column;
        else {
            try {
                column = Integer.parseInt(args[0]);
                if (column < 1 || column > 14) {
                    System.out.println("Такого столбца нет. Значения столбцов лежат в диапазоне от 1 до 14 включительно.");
                    System.exit(0);
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Номером столбца может быть только число.");
                System.exit(0);
            }
        }

        return column - 1;
    }

    public static void find(TreeMap<String, String> treeMap, String value) {
        Long start = System.nanoTime();
        ArrayList<String> list = new ArrayList<>();

        Map.Entry<String, String> entry = treeMap.ceilingEntry(value);
        while (entry != null && entry.getKey().startsWith(value)) {
            list.add(entry.getValue());
            entry = treeMap.higherEntry(entry.getKey());
        }
        Long end = System.nanoTime();

        for (String row : list)
            System.out.println(row);

        System.out.println(
                "Количество найденных строк: " + list.size() +
                        " Время, затраченное на поиск: "  + getTime(start, end));
    }

    public static String readValue() {
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

    public static void printMemory() {
        Runtime runtime = Runtime.getRuntime();

        var totalMemory = runtime.totalMemory();
        var freeMemory = runtime.freeMemory();
        var usedMemory = (double) (totalMemory - freeMemory) / (double) (1024 * 1024);

        System.out.println("Used memory: " + usedMemory + " mb.");
    }

    private static String getTime(Long start, Long end) {
        long nanoseconds = end - start;
        long milliseconds = nanoseconds / 1000000;
        return milliseconds + " мс " + nanoseconds % 1000000 + " нс.";
    }
}
