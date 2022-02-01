import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // проверка валидности аргументов
        System.out.println("Checking arguments...");
        if (!isInputValid(args)) return;
        // анализ аргументов
        System.out.println("Analysing...");
        boolean ascOrder = !args[0].equals("-d");
        boolean orderSpecified = !ascOrder || args[0].equals("-a");
        boolean dataTypeInt = args[0].equals("-i") || args[1].equals("-i");
        int filesToSort = orderSpecified ? args.length - 3 : args.length - 2;
        // создание списка со списками строк, считывание файлов в списки
        ArrayList<ArrayList<String>> lists = new ArrayList<>();
        for (int i = args.length - filesToSort; i < args.length; i++) {
            lists.add(readFromFileToList(dataTypeInt, args[i]));
        }
        // перезапись списков в обратном порядке, если требуется сортировка в обратном порядке
        if (!ascOrder) {
            System.out.println("Reversing order...");
            lists = reverseLists(dataTypeInt, lists);
        }
        // сортировка
        System.out.println("Sorting...");
        ArrayList<String> sortedList = new ArrayList<>();
        if (lists.size() == 1) {
            sortedList = sortOneList(ascOrder, dataTypeInt, lists.get(0));
        } else {
            int path = 0;
            while (path < lists.size()) {
                sortedList = sortTwoLists(ascOrder, dataTypeInt, lists.get(path), sortedList);
                path++;
            }
        }
        // запись в файл
        writeListToFile(sortedList, args[args.length - filesToSort - 1]);
    }

    // сортировка слиянием одного списка
    static ArrayList<String> sortOneList(Boolean ascOrder, boolean dataTypeInt, ArrayList<String> data) {
        if (data.size() < 2) return data;
        int middle = data.size() / 2;
        ArrayList<String> list1 = new ArrayList<>();
        for (int i = 0; i < middle; i++) {
            list1.add(data.get(i));
        }
        ArrayList<String> list2 = new ArrayList<>();
        for (int i = middle; i < data.size(); i++) {
            list2.add(data.get(i));
        }
        return sortTwoLists(ascOrder, dataTypeInt, sortOneList(ascOrder, dataTypeInt, list1), sortOneList(ascOrder, dataTypeInt, list2));
    }

    // сортировка слиянием двух списков
    static ArrayList<String> sortTwoLists(boolean ascOrder, boolean dataTypeInt, ArrayList<String> list1, ArrayList<String> list2) {
        ArrayList<String> result = new ArrayList<>();
        int orderSwitcher = ascOrder ? 1 : -1;
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
                    if (dataTypeInt) {
                        if (((Integer.parseInt(list1.get(count1)) * orderSwitcher) <= Integer.parseInt(list2.get(count2)) * orderSwitcher)) {
                            result.add(list1.get(count1));
                            count1++;
                        } else {
                            result.add(list2.get(count2));
                            count2++;
                        }
                    } else {
                        if (list1.get(count1).compareTo(list2.get(count2)) * orderSwitcher <= 0) {
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

    // проверка аргументов CL на валидность
    static Boolean isInputValid(String[] arguments) {
        boolean result = true;
        // проверка корректного порядка аргументов и типа данных
        if (!arguments[0].equals("-i") && !arguments[0].equals("-s")) {
            if (!arguments[0].equals("-a") && !arguments[0].equals("-d")) {
                System.out.println("The first argument is invalid");
                return false;
            } else if (!arguments[1].equals("-i") && !arguments[1].equals("-s")) {
                System.out.println("The second argument is invalid");
                return false;
            }
        }
        // проверка наличия и расширений файлов
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

    // чтение файла в список (пробелы игнорируются)
    static ArrayList<String> readFromFileToList(boolean needInteger, String path) {
        File inputFile = new File(path);
        ArrayList<String> result = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(inputFile);
            BufferedReader reader = new BufferedReader(fileReader);
            String line = reader.readLine();
            while (line != null) {
                if (!line.isEmpty()) {
                    if (needInteger) {
                        try {
                            int temp = Integer.parseInt(line);
                        } catch (NumberFormatException e) {
                            line = reader.readLine();
                            continue;
                        }
                    }
                    result.add(line);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // запись списка в файл (существующий перезаписывается)
    static void writeListToFile(ArrayList<String> list, String destination) {
        File file = new File(destination);
        try (FileWriter writer = new FileWriter(file)) {
            for (String line : list) {
                writer.write(line + System.getProperty("line.separator"));
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // перезапись списка в обратном порядке сортировкой слиянием
    static ArrayList<ArrayList<String>> reverseLists(boolean typeInteger, ArrayList<ArrayList<String>> listWithLists) {
        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        for (ArrayList<String> list : listWithLists) {
            list = sortOneList(false, typeInteger, list);
            temp.add(list);
        }
        return temp;
    }
}
