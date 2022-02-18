import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MergeSort {
    private final boolean ascOrder;
    private final boolean dataTypeInt;
    private final String[] pathsToFiles;

    public MergeSort(InputInfo info) {
        this.ascOrder = info.isAscOrder();
        this.dataTypeInt = info.isDataTypeInt();
        this.pathsToFiles = info.getPathsToFiles();
    }

    protected ArrayList<String> mergeSort() {
        ArrayList<String> result = new ArrayList<>();
        for (String path : pathsToFiles) {
            result = sortTwoLists(result, readAndSort(path));
        }
        return result;
    }

    private ArrayList<String> sortTwoLists(ArrayList<String> list1, ArrayList<String> list2) {
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
                    result.add(list1.get(count1++));
                } else if (list1Processed) {
                    result.add(list2.get(count2++));
                } else {
                    if (!dataTypeInt) {
                        if (list1.get(count1).compareTo(list2.get(count2)) * switchOrder <= 0) {
                            result.add(list1.get(count1++));
                        } else {
                            result.add(list2.get(count2++));
                        }
                    } else {
                        int num1 = Integer.parseInt(list1.get(count1));
                        int num2 = Integer.parseInt(list2.get(count2));
                        if (num1 * switchOrder <= num2 * switchOrder) {
                            result.add(list1.get(count1++));
                        } else {
                            result.add(list2.get(count2++));
                        }
                    }
                }
            }
        }
        return result;
    }

    private ArrayList<String> readAndSort(String path) {
        File inputFile = new File(path);
        int switchOrder = ascOrder ? 1 : -1;
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line = reader.readLine();
            if (!dataTypeInt) {
                while (true) {
                    if (line == null) {
                        if (!temp.isEmpty()) {
                            result = sortTwoLists(result, temp);
                            break;
                        }
                    } else {
                        if (line.isEmpty()) {
                            line = reader.readLine();
                            continue;
                        }
                        if (!temp.isEmpty()) {
                            if (temp.get(temp.size() - 1).compareTo(line) * switchOrder > 0) {
                                result = sortTwoLists(result, temp);
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
                            result = sortTwoLists(result, temp);
                            break;
                        }
                    } else {
                        if (line.isEmpty()) {
                            line = reader.readLine();
                            continue;
                        }
                        try {
                            if (temp.isEmpty()) {
                                temp.add(Integer.parseInt(line) + "");
                            } else {
                                int number = Integer.parseInt(line);
                                int num2 = Integer.parseInt(temp.get(temp.size() - 1));
                                if (num2 * switchOrder > number * switchOrder) {
                                    result = sortTwoLists(result, temp);
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
