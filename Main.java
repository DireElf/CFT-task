
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        if (!InputInfo.isInputValid(args)) return;
        InputInfo info = new InputInfo(args);
        ArrayList<Integer> sortedNumbers = new ArrayList<>();
        ArrayList<String> sortedStrings = new ArrayList<>();
        if (info.isDataTypeInt()) {
            for (String path : info.getPathsToFiles()) {
                sortedNumbers = NumbersSort.sortTwoLists(info.isAscOrder(), sortedNumbers, NumbersSort.readAndSortInt(info.isAscOrder(), path));
            }
        } else {
            for (String path : info.getPathsToFiles()) {
                sortedStrings = StringsSort.sortTwoLists(info.isAscOrder(), sortedStrings, StringsSort.readAndSortStrings(info.isAscOrder(), path));
            }
        }
        ArrayList toWrite = info.isDataTypeInt() ? sortedNumbers : sortedStrings;
        File file = new File(info.getPathToOutput());
        try (FileWriter writer = new FileWriter(file)) {
            for (Object line : toWrite) {
                writer.write(line + System.getProperty("line.separator"));
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}