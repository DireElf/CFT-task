import java.io.*;
import java.util.ArrayList;

public class NumbersSort {

    static ArrayList<Integer> sortTwoLists(boolean ascOrder, ArrayList<Integer> list1, ArrayList<Integer> list2) {
        ArrayList<Integer> result = new ArrayList<>();
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
                    if ((list1.get(count1) * switchOrder) <= list2.get(count2) * switchOrder) {
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

    static ArrayList<Integer> readAndSortInt(boolean ascOrder, String path) {
        File inputFile = new File(path);
        int switchOrder = ascOrder ? 1 : -1;
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();
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
                    if (line.isEmpty()) {
                        line = reader.readLine();
                        continue;
                    }
                    try {
                        if (temp.isEmpty()) {
                            temp.add(Integer.parseInt(line));
                        } else {
                            int number = Integer.parseInt(line);
                            if (temp.get(temp.size() - 1) * switchOrder > number * switchOrder) {
                                result = sortTwoLists(ascOrder, result, temp);
                                temp = new ArrayList<>();
                            }
                            temp.add(number);
                        }
                        line = reader.readLine();
                    } catch (NumberFormatException e) {
                        line = reader.readLine();
                    }
                }
            }
        } catch (IOException p) {
            p.printStackTrace();
        }
        return result;
    }
}