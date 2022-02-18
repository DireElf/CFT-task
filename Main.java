import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        if (!InputInfo.isInputValid(args)) return;
        InputInfo info = new InputInfo(args);
        MergeSort mergeSort = new MergeSort(info);
        ArrayList<String> sortedStrings = mergeSort.mergeSort();
        File file = new File(info.getPathToOutput());
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : sortedStrings) {
                writer.write(line + System.getProperty("line.separator"));
            }
            writer.flush();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}