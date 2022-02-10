import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Stream;

public class TestField {
    //РЕШАЕМ ДУБЛИКАТИКИ
    public static void main(String[] args) {
        for (int i = 6; i < 8; i++){
            TreeMap<String, String> treeMap = readColumnMap(i);
            ArrayList<String> list = readColumnList(i);

            int ncounter = 0;
            for (int j = 0; j < list.size(); j++) {
                //System.out.println(list.get(j));

                System.out.println(list.get(j));
                /*
                if (Objects.equals(list.get(j), "\\N")) {
                    ncounter++;
                }

                 */
            }

            System.out.println("Column: " + (i + 1));
            //System.out.println("Values inside map: " + treeMap.size());
            //System.out.println("Values inside list: " + list.size());
            //System.out.println("Difference: " + Math.abs(treeMap.size() - list.size()));
            System.out.println("Amount of \\N's: " + ncounter);

            //printMemory();
        }
    }

    public static void findCol(TreeMap<String, ArrayList<String>> treeMap, String value) {
        Long start = System.nanoTime();
        ArrayList<String> list = new ArrayList<>();

        Map.Entry<String, ArrayList<String>> entry = treeMap.ceilingEntry(value);
        int i = 0;
        while (entry != null && entry.getKey().startsWith(value)) {
            i++;
            list.addAll(entry.getValue());
            entry = treeMap.higherEntry(entry.getKey());
        }

        Long end = System.nanoTime();

        for (String row : list) {
            System.out.println(row);
        }

        System.out.println("Total amount of found values is: " + list.size());
        System.out.println("Among them duplicates: " + (list.size() - i));
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

    public static TreeMap<String, String> readColumnMap(Integer column) {
        TreeMap<String, String> airportsMap = new TreeMap<>();

        try (Stream<String> stream = Files.lines(Paths.get("airports.dat"))) {
            stream.forEach(line -> {
                String neededColumn = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[column].replaceAll("\"", "");
                airportsMap.put(neededColumn, line);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return airportsMap;
    }

    public static ArrayList<String> readColumnList(Integer column) {
        ArrayList<String> airportsList = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get("airports.dat"))) {
            stream.forEach(line -> {
                String neededColumn = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[column].replaceAll("\"", "");
                airportsList.add(neededColumn);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return airportsList;
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
