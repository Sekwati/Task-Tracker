import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        } 
        catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Saves tasks back to the JSON file
    private void saveTasks() {
        try {
            String json = Task.toJsonList(tasks);
            Files.writeString(TASK_FILE, json);
        } 
        catch (IOException e) {
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

    // Updates a task's description after validating the input
    public void updateTask(String id, String newDescription){
        if (newDescription == null || newDescription.isBlank()) {
        throw new IllegalArgumentException("New description cannot be empty.");
    }

    Task task = findTask(id).orElseThrow(() -> 
        new IllegalArgumentException("Task with ID " + id + " not found!")
    );

    task.updateDescription(newDescription);
    saveTasks(); // Ensure changes persist
    }

    // Deletes a task safely
    public void deleteTask(String id) {
        Task task = findTask(id).orElseThrow(() -> 
            new IllegalArgumentException("Task with ID " + id + " not found!"));

    tasks.remove(task);
    saveTasks(); // Persist deletion
    }

    // Marks a task as "in-progress"
    public void markInProgress(String id) {
        Task task = findTask(id).orElseThrow(() -> 
            new IllegalArgumentException("Task with ID " + id + " not found!"));

    task.markInProgress();
    saveTasks(); // Save updated status
    }

    // Marks a task as "done"
    public void markDone(String id) {
        Task task = findTask(id).orElseThrow(() -> 
            new IllegalArgumentException("Task with ID " + id + " not found!"));

    task.markDone();
    saveTasks(); // Ensure it's recorded
}

    // Lists all tasks or by status (todo, done, in-progress)
    public void listTasks(String type){
        for (Task task : tasks){
            String status = task.getStatus().toString().strip();

            // Check if "All" is given or status matches (case-insensitive)
            if (type.equalsIgnoreCase("All") || status.equalsIgnoreCase(type)){
                System.out.println(task.toString());
            }
        }

        // If no tasks found
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        }
    }

     // Finds a task by ID
     public Optional<Task> findTask(String id) {
        try {
            int taskId = Integer.parseInt(id);
            return tasks.stream()
                        .filter(task -> task.getId().equals(taskId))
                        .findFirst();
        } 
        catch (NumberFormatException e) {
            System.out.println("Invalid task ID: " + id);
            return Optional.empty();
        }
    }

}
