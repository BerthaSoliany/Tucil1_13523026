package lib;

public class Color {
    private static String[] colors = {
        "\u001B[30m",
        "\u001B[31m",
        "\u001B[32m",
        "\u001B[33m",
        "\u001B[34m",
        "\u001B[35m",
        "\u001B[36m",
        "\u001B[91m",
        "\u001B[92m",
        "\u001B[93m",
        "\u001B[94m",
        "\u001B[95m",
        "\u001B[96m",
        "\u001B[97m",
        "\u001B[90m",
        "\u001B[41m",
        "\u001B[42m",
        "\u001B[43m",
        "\u001B[44m",
        "\u001B[45m",
        "\u001B[46m",
        "\u001B[101m",
        "\u001B[102m",
        "\u001B[103m",
        "\u001B[104m",
        "\u001B[105m" 
    };

    private static String RESET = "\u001B[0m";

    public static String getColor(char piece) {
        int idx = (piece-'A') % colors.length;
        return colors[idx];
    }

    public static String colorPrint(char piece) {
        return getColor(piece) + piece + RESET;
    }

}
