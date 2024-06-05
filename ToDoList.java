import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {
    private ArrayList<Task> tasks;

    public ToDoList() {
        tasks = new ArrayList<>();
    }

    public void addTask(String description) {
        tasks.add(new Task(description));
    }

    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        } else {
            System.out.println("Invalid task number.");
        }
    }

    public void markTaskAsDone(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsDone();
        } else {
            System.out.println("Invalid task number.");
        }
    }

    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks in your list.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    //Saving tasks
    public void saveToFile(String filename) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
        for (Task task : tasks) {
            writer.println(task.getDescription() + "|" + (task.isDone() ? "1" : "0"));
        }
        System.out.println("Tasks saved to " + filename);
    } catch (IOException e) {
        System.out.println("An error occurred while saving tasks: " + e.getMessage());
    }
}

//Load Method
public void loadFromFile(String filename) {
    try (Scanner fileScanner = new Scanner(new File(filename))) {
        tasks.clear();
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            String[] parts = line.split("\\|");
            if (parts.length == 2) {
                String description = parts[0];
                boolean isDone = parts[1].equals("1");
                Task task = new Task(description);
                if (isDone) {
                    task.markAsDone();
                }
                tasks.add(task);
            }
        }
        System.out.println("Tasks loaded from " + filename);
    } catch (FileNotFoundException e) {
        System.out.println("No saved tasks found.");
    }
}



    public static void main(String[] args) {
        ToDoList toDoList = new ToDoList();
        Scanner scanner = new Scanner(System.in);
        final String filename = "tasks.txt"; //the file for the task to be stored
        //Atempt to load file at startup
        toDoList.loadFromFile(filename);
        while (true) {
            System.out.println("\nTo-Do List:");
            toDoList.displayTasks();
            System.out.println("\nOptions:");
            System.out.println("1. Add task");
            System.out.println("2. Remove task");
            System.out.println("3. Mark task as done");
            System.out.println("4. Save tasks");
            System.out.println("5. Load tasks");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    toDoList.addTask(description);
                    break;
                case 2:
                    System.out.print("Enter task number to remove: ");
                    int removeIndex = scanner.nextInt() - 1;
                    toDoList.removeTask(removeIndex);
                    break;
                case 3:
                    System.out.print("Enter task number to mark as done: ");
                    int doneIndex = scanner.nextInt() - 1;
                    toDoList.markTaskAsDone(doneIndex);
                    break;
                case 4:
                    toDoList.saveToFile(filename);
                    break;
                case 5:
                    toDoList.loadFromFile(filename);
                    break;
                case 6:
                    toDoList.saveToFile(filename);
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}

