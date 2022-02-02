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
            NumbersSort.writeListToFile(sortedNumbers, info.getPathToOutput());
            System.out.println(sortedNumbers);
        } else {
            for (String path : info.getPathsToFiles()) {
                sortedStrings = StringsSort.sortTwoLists(info.isAscOrder(), sortedStrings, StringsSort.readAndSortStrings(info.isAscOrder(), path));
            }
            StringsSort.writeListToFile(sortedStrings, info.getPathToOutput());
            System.out.println(sortedStrings);
        }

    }
}