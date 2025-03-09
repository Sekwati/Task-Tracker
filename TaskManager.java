import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
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
        List<Task> stored_tasks = new ArrayList<>();

        if (!Files.exists(TASK_FILE)){
            return new ArrayList<>();
        }

        try {
            String jsonContent = Files.readString(TASK_FILE);
            String[] taskList = jsonContent.replace("[", "")
                                            .replace("]", "")
                                            .split("},");
            for (String taskJson : taskList){
                if (!taskJson.endsWith("}")){
                    taskJson = taskJson + "}";
                    stored_tasks.add(Task.fromJson(taskJson));
                } else {
                    stored_tasks.add(Task.fromJson(taskJson));
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return stored_tasks;
    }

    // Saves tasks back to the JSON file
    public void saveTasks(){
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < tasks.size(); i++){
            sb.append(tasks.get(i).toJson());
            if (i < tasks.size() - 1){
                sb.append(",\n");
            }
        }
        sb.append("\n]");

        String jsonContent = sb.toString();
        try {
            Files.writeString(TASK_FILE, jsonContent);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // Adds a new task and saves it
    public void addTask(String description) {
        int id = tasks.size() + 1;
        String status = "TODO";
        String now = LocalDateTime.now().toString();
        Task task = new Task(id, description, status, now, now);
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
            String status = task.getStatus().toString();

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
                        .filter(task -> task.getId() == taskId)
                        .findFirst();
        } 
        catch (NumberFormatException e) {
            System.out.println("Invalid task ID: " + id);
            return Optional.empty();
        }
    }

}
