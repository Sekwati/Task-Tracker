import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private static final Path TASK_FILE = Path.of("tasks.json");
    private List<Task> tasks;
    
    public TaskManager() {
        this.tasks = loadTasks();
    }

    // Loads tasks from the JSON file or returns an empty list
    private List<Task> loadTasks() {
        if (!Files.exists(TASK_FILE)) {
            return new ArrayList<>();
        }

        try {
            String content = Files.readString(TASK_FILE);
            return Task.fromJsonList(content); // Parses JSON to List<Task>
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Saves tasks back to the JSON file
    private void saveTasks() {
        try {
            String json = Task.toJsonList(tasks);
            Files.writeString(TASK_FILE, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Adds a new task and saves it
    public void addTask(String description) {
        int id = tasks.size() + 1;
        Task task = new Task(id, description);
        tasks.add(task);
        saveTasks();
        System.out.println("Task added: " + description);
    }

}
