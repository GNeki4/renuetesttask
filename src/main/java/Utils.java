public class Utils {
    public static void printMemory() {
        Runtime runtime = Runtime.getRuntime();

        var totalMemory = runtime.totalMemory();
        var freeMemory = runtime.freeMemory();
        var usedMemory = (double) (totalMemory - freeMemory) / (double) (1024 * 1024);

        System.out.println("Used memory: " + usedMemory + " mb.");
    }

    public static String getTime(Long start, Long end) {
        long nanoseconds = end - start;
        long milliseconds = nanoseconds / 1000000;
        return milliseconds + " мс " + nanoseconds % 1000000 + " нс.";
    }
}
