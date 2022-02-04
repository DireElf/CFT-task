import java.io.*;
import java.util.ArrayList;

public class StringsSort {

    static ArrayList<String> sortTwoLists(boolean ascOrder, ArrayList<String> list1, ArrayList<String> list2) {
        ArrayList<String> result = new ArrayList<>();
        int switchOrder = ascOrder ? 1 : -1;
        int count1 = 0;
        int count2 = 0;
        while (true) {
            boolean list1Processed = count1 == list1.size();
            boolean list2Processed = count2 == list2.size();
            if (list1Processed && list2Processed) break;
            else {
                if (list2Processed) {
                    result.add(list1.get(count1));
                    count1++;
                } else if (list1Processed) {
                    result.add(list2.get(count2));
                    count2++;
                } else {
                    if (list1.get(count1).compareTo(list2.get(count2)) * switchOrder <= 0) {
                        result.add(list1.get(count1));
                        count1++;
                    } else {
                        result.add(list2.get(count2));
                        count2++;
                    }
                }
            }
        }
        return result;
    }

    static ArrayList<String> readAndSortStrings(boolean ascOrder, String path) {
        File inputFile = new File(path);
        int switchOrder = ascOrder ? 1 : -1;
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(inputFile);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            while (true) {

                if (line == null) {
                    if (!temp.isEmpty()) {
                        result = sortTwoLists(ascOrder, result, temp);
                        break;
                    }
                } else {
                    if (line.isEmpty()) continue;
                    if (!temp.isEmpty()) {
                        if (temp.get(temp.size() - 1).compareTo(line) * switchOrder > 0) {
                            result = sortTwoLists(ascOrder, result, temp);
                            temp = new ArrayList<>();
                        }
                    }
                    temp.add(line);
                    line = reader.readLine();
                }
            }
        } catch (IOException p) {
            p.printStackTrace();
        }
        return result;
    }
}


