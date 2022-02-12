import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        int column = Utils.parseColumn(args);

        TreeMap<String, String> treeMap = Utils.readFile(column);
        var value = Utils.readValue();

        Utils.find(treeMap, value);
        Utils.printMemory();
    }
}
