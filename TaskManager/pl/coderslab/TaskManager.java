package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.*;

public class TaskManager {
    static String[][] dataCsv;

    public static void main(String[] args) {
        String FILE_NAME = "tasks.csv";
        optionsView();
        dataCsv = dataFromCsv(FILE_NAME);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.next();
            switch (input) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask(dataCsv);
                    break;
                case "list":
                    listTask(dataCsv);
                    break;
                case "exit":
                    exitTask(FILE_NAME);
                    System.out.println(ConsoleColors.RED + "Bye, bye.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
            optionsView();
        }
    }


    public static void optionsView() {
        System.out.println(ConsoleColors.BLUE + "Please select an option: " + ConsoleColors.RESET);
        String[] option = {"add", "remove", "list", "exit"};
        for (String s : option) {
            System.out.println(s);
        }
    }

    public static void addTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String descriptionInput = scanner.nextLine();
        System.out.println("Please add task due date");
        String dueDateInput = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        String isImportantInput = scanner.nextLine();
        dataCsv = Arrays.copyOf(dataCsv, dataCsv.length + 1);
        dataCsv[dataCsv.length - 1] = new String[3];
        dataCsv[dataCsv.length - 1][0] = descriptionInput;
        dataCsv[dataCsv.length - 1][1] = dueDateInput;
        dataCsv[dataCsv.length - 1][2] = isImportantInput;
    }

    public static void removeTask(String[][] tasks) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove.");
        try {
            int number = Integer.parseInt(scanner.nextLine());
            if (number < dataCsv.length) {
                dataCsv = ArrayUtils.remove(tasks, number);
                System.out.println("Value was successfully deleted.");
            } else {
                System.out.println("Number not exist");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Number is not correct");
        }
    }


    public static void listTask(String[][] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void exitTask(String fileName) {
        Path file = Paths.get(fileName);
        List<String> lines = new ArrayList<>();
        for (int i = 0; i < dataCsv.length; i++) {
            String line = String.join(",", dataCsv[i]);
            lines.add(line);
        }
        try {
            if (Files.exists(file)) {
                Files.write(file, lines);
            }
        } catch (IOException e) {
            System.out.println("Don't save file");
        }
    }

    public static String[][] dataFromCsv(String fileName) {
        Path file = Paths.get(fileName);
        String[][] data = null;
        try {
            if (Files.exists(file)) {
                List<String> lines = new ArrayList<>();
                for (String line : Files.readAllLines(file)) {
                    lines.add(line);
                }
                data = new String[lines.size()][];
                for (int i = 0; i < lines.size(); i++) {
                    data[i] = lines.get(i).split(",");

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}

