import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        int column = readColumn() - 1;

        switch (column) {
            case 0, 5 -> {
                Long start = System.nanoTime();
                TreeMap<String, String> treeMap = read1(column);
                Long end = System.nanoTime();

                System.out.println("Preparations took: " + getTime(start, end));
                System.out.println("Amount of keys: " + treeMap.size());
                System.out.println("Used method: 1");

                var value = readRow();
                find1(treeMap, value);
                printMemory();
            }
            case 3, 8, 9, 10, 11 -> {
                Long start = System.nanoTime();
                TreeMap<String, ArrayList<String>> treeMap = read2(column);
                Long end = System.nanoTime();

                System.out.println("Preparations took: " + getTime(start, end));
                System.out.println("Amount of keys: " + treeMap.size());
                System.out.println("Used method: 2");

                var value = readRow();
                find2(treeMap, value);
                printMemory();
            }
            case 1, 2, 4, 6, 7 -> {
                Long start = System.nanoTime();
                TreeMap<String, String> treeMap = read3(column);
                Long end = System.nanoTime();

                System.out.println("Preparations took: " + getTime(start, end));
                System.out.println("Amount of keys: " + treeMap.size());
                System.out.println("Used method: 3");

                var value = readRow();
                find3(treeMap, value);
                printMemory();
            }
            case 12, 13 -> {
                Long start = System.nanoTime();
                ArrayList<String> treeList = read4(column);
                Long end = System.nanoTime();

                System.out.println("Preparations took: " + getTime(start, end));
                System.out.println("Amount of keys: " + treeList.size());
                System.out.println("Used method: 4");

                var value = readRow();
                find4(treeList, value);
                printMemory();
            }
            default -> {
                System.out.println("No such column!");
            }
        }
    }

    public static TreeMap<String, String> read1(Integer column) {
        TreeMap<String, String> airportsMap = new TreeMap<>();

        try (Stream<String> stream = Files.lines(Paths.get("airports.dat"))) {
            stream.forEach(line -> {
                airportsMap.put(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[column].replaceAll("\"", ""), line);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return airportsMap;
    }

    public static void find1(TreeMap<String, String> treeMap, String value) {
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

    public static TreeMap<String, ArrayList<String>> read2(Integer column) {
        TreeMap<String, ArrayList<String>> airportsMap = new TreeMap<>();

        try (Stream<String> stream = Files.lines(Paths.get("airports.dat"))) {
            stream.forEach(line -> {
                String neededColumn = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")[column].replaceAll("\"", "");

                if (airportsMap.containsKey(neededColumn)) {
                    airportsMap.get(neededColumn).add(line);
                } else {
                    ArrayList<String> list = new ArrayList<>();
                    list.add(line);
                    airportsMap.put(neededColumn, list);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return airportsMap;
    }

    public static void find2(TreeMap<String, ArrayList<String>> treeMap, String value) {
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

    public static TreeMap<String, String> read3(Integer column) {
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

    public static void find3(TreeMap<String, String> treeMap, String value) {
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

    public static ArrayList<String> read4(Integer column) {
        ArrayList<String> airportsList = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get("airports.dat"))) {
            stream.forEach(line -> {
                String[] neededColumnArray = line.split(",");
                String neededColumn = "";
                if (column == 12)
                    neededColumn = neededColumnArray[neededColumnArray.length - 2].replaceAll("\"", "");
                else if (column == 13)
                    neededColumn = neededColumnArray[neededColumnArray.length - 1].replaceAll("\"", "");
                airportsList.add(neededColumn);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return airportsList;
    }

    public static void find4(ArrayList<String> list, String value) {
        Long start = System.nanoTime();
        long end = 0L;

        if (list.size() > 0 && list.get(0).startsWith(value)) {
            end = System.nanoTime();

            for (String row : list) {
                System.out.println(row);
            }
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
