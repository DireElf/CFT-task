import java.nio.file.Files;
import java.nio.file.Paths;


public class InputInfo {

    private final boolean ascOrder;
    private final boolean dataTypeInt;
    private final String[] pathsToFiles;
    private final String pathToOutput;

    public InputInfo(String[] args) {
        this.ascOrder = !args[0].equals("-d");
        this.dataTypeInt = args[0].equals("-i") || args[1].equals("-i");
        int firstPathPosition = args[0].equals("-d") || args[0].equals("-a") ? 2 : 1;
        this.pathToOutput = args[firstPathPosition];
        int filesToSort = !ascOrder || args[0].equals("-a") ? args.length - 3 : args.length - 2;
        this.pathsToFiles = collectPaths(args, filesToSort);
    }

    private String[] collectPaths (String[] arguments, int filesToSort) {
        String[] paths = new String[filesToSort];
        System.arraycopy(arguments, arguments.length - filesToSort, paths, 0, paths.length);
        return paths;
    }

    public static Boolean isInputValid(String[] arguments) {
        boolean result = true;
        if (arguments.length < 3) {
            System.out.println("At least three arguments are required");
            return false;
        }
        if (!arguments[0].equals("-i") && !arguments[0].equals("-s")) {
            if (!arguments[0].equals("-a") && !arguments[0].equals("-d")) {
                System.out.println("The first argument is invalid. Available values: -a, -d, -i, -s");
                return false;
            } else if (!arguments[1].equals("-i") && !arguments[1].equals("-s")) {
                System.out.println("The second argument is invalid. Available values: -i, -s");
                return false;
            }
        }
        int firstPathPosition = arguments[0].equals("-d") || arguments[0].equals("-a") ? 2 : 1;
        if (!Files.exists(Paths.get(arguments[firstPathPosition]))) {
            System.out.println("No output file");
            return false;
        }
        if (firstPathPosition + 1 == arguments.length) {
            System.out.println("No files to sort");
            return false;
        }
        for (int i = firstPathPosition; i < arguments.length; i++) {
            if (!Files.exists(Paths.get(arguments[i]))) {
                System.out.println("File " + arguments[i] + " is unavailable");
                result = false;
            } else {
                if (!arguments[i].toLowerCase().endsWith(".txt")) {
                    System.out.println("File " + arguments[i] + " has invalid extension");
                    result = false;
                }
            }
        }
        return result;
    }

    // getters
    public boolean isAscOrder() {
        return ascOrder;
    }
    public boolean isDataTypeInt() {
        return dataTypeInt;
    }
    public String[] getPathsToFiles() {
        return pathsToFiles;
    }
    public String getPathToOutput() {
        return pathToOutput;
    }
}