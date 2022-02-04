import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        if (!InputInfo.isInputValid(args)) return;
        InputInfo info = new InputInfo(args);
        ArrayList<String> sortedStrings = new ArrayList<>();
        for (String path : info.getPathsToFiles()) {
            sortedStrings = sortTwoLists(info.isAscOrder(), info.isDataTypeInt(), sortedStrings, readAndSortStrings(info.isAscOrder(), info.isDataTypeInt(), path));
        }
        File file = new File(info.getPathToOutput());
        try (
                FileWriter writer = new FileWriter(file)) {
            for (String line : sortedStrings) {
                writer.write(line + System.getProperty("line.separator"));
            }
            writer.flush();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<String> sortTwoLists(boolean ascOrder, boolean dataTypeInt, ArrayList<String> list1, ArrayList<String> list2) {
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
                    if (!dataTypeInt) {
                        if (list1.get(count1).compareTo(list2.get(count2)) * switchOrder <= 0) {
                            result.add(list1.get(count1));
                            count1++;
                        } else {
                            result.add(list2.get(count2));
                            count2++;
                        }
                    }
                    else {
                        int num1 = Integer.parseInt(list1.get(count1));
                        int num2 = Integer.parseInt(list2.get(count2));
                        if (num1 * switchOrder <= num2 * switchOrder) {
                            result.add(list1.get(count1));
                            count1++;
                        } else {
                            result.add(list2.get(count2));
                            count2++;
                        }
                    }
                }
            }
        }
        return result;
    }

    static ArrayList<String> readAndSortStrings(boolean ascOrder, boolean dataTypeInt, String path) {
        File inputFile = new File(path);
        int switchOrder = ascOrder ? 1 : -1;
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(inputFile);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            if (!dataTypeInt) {
                while (true) {
                    if (line == null) {
                        if (!temp.isEmpty()) {
                            result = sortTwoLists(ascOrder, false, result, temp);
                            break;
                        }
                    } else {
                        if (line.isEmpty()) continue;
                        if (!temp.isEmpty()) {
                            if (temp.get(temp.size() - 1).compareTo(line) * switchOrder > 0) {
                                result = sortTwoLists(ascOrder, false, result, temp);
                                temp = new ArrayList<>();
                            }
                        }
                        temp.add(line);
                        line = reader.readLine();
                    }
                }
            } else {
                while (true) {
                    if (line == null) {
                        if (!temp.isEmpty()) {
                            result = sortTwoLists(ascOrder, true, result, temp);
                            break;
                        }
                    } else {
                        if (line.isEmpty()) continue;
                        try {
                            if (temp.isEmpty()) {
                                temp.add(Integer.parseInt(line) + "");
                            } else {
                                int number = Integer.parseInt(line);
                                int num2 = Integer.parseInt(temp.get(temp.size() - 1));
                                if (num2 * switchOrder > number * switchOrder) {
                                    result = sortTwoLists(ascOrder, true, result, temp);
                                    temp = new ArrayList<>();
                                }
                                temp.add(number + "");
                            }
                            line = reader.readLine();
                        } catch (NumberFormatException e) {
                            line = reader.readLine();
                        }
                    }
                }
            }
        } catch (IOException p) {
            p.printStackTrace();
        }
        return result;
    }
}