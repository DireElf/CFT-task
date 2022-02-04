import java.nio.file.Files;
import java.nio.file.Paths;


public class InputInfo {

    private boolean ascOrder;
    private boolean dataTypeInt;
    private String[] pathsToFiles;
    private int firstPathPosition;
    private String pathToOutput;

    public InputInfo(String[] args) {
        this.ascOrder = !args[0].equals("-d");
        this.dataTypeInt = args[0].equals("-i") || args[1].equals("-i");
        this.firstPathPosition = args[0].equals("-d") || args[0].equals("-a") ? 2 : 1;
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

        if (arguments.length == 0) {
            System.out.println("No arguments");
            return false;
        }
        if (!arguments[0].equals("-i") && !arguments[0].equals("-s")) {
            if (!arguments[0].equals("-a") && !arguments[0].equals("-d")) {
                System.out.println("The first argument is invalid");
                return false;
            } else if (!arguments[1].equals("-i") && !arguments[1].equals("-s")) {
                System.out.println("The second argument is invalid");
                return false;
            }
        }

        int firstPathPosition = arguments[0].equals("-d") || arguments[0].equals("-a") ? 2 : 1;
        if (firstPathPosition == arguments.length) {
            System.out.println("Output file is unavailable");
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
