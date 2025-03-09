import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private int id;
    private String description;
    private Status status; // "todo", "in-progress", "done"
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

     // DateTimeFormatter for serializing/deserializing dates
     private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

     public enum Status {
        TODO, IN_PROGRESS, DONE
    }
    

    public Task(int id, String description, String status, String createdAt, String updatedAt) {
        this.id = id;
        this.description = description;
        this.status = Status.TODO; // Default status
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Method to update the task description and timestamp
    public void updateDescription(String newDescription) {
        if (newDescription == null || newDescription.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty!");
        }
        this.description = newDescription;
        this.updatedAt = LocalDateTime.now();
    }

    // Method to mark as "in-progress"
    public void markInProgress() {
        this.status = Status.IN_PROGRESS;
        this.updatedAt = LocalDateTime.now();
    }

    // Method to mark as "done"
    public void markDone() {
        this.status = Status.DONE;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters for accessing fields
    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public String toJson() {
        return "{"
            + "\"id\":" + id + ", "
            + "\"description\":\"" + description.strip() + "\", "
            + "\"status\":\"" + status + "\", "
            + "\"createdAt\":\"" + createdAt.format(formatter) + "\", "
            + "\"updatedAt\":\"" + updatedAt.format(formatter) + "\""
            + "}";
    }

    public static Task fromJson(String json) {
        String[] parts = json.replace("{", "").replace("}", "").split(",");
        int id = Integer.parseInt(parts[0].split(":")[1].trim());
        String description = parts[1].split(":")[1].replace("\"", "").trim();
        String status = parts[2].split(":")[1].replace("\"", "").trim();
        String createdAt = parts[3].split(":")[1].replace("\"", "").trim();
        String updatedAt = parts[4].split(":")[1].replace("\"", "").trim();
        return new Task(id, description, status, createdAt, updatedAt);
    }
    



    @Override
    public String toString() {
        return "Task{id=" + id + ", description='" + description + "', status='" + status + "', createdAt='" + createdAt + "', updatedAt='" + updatedAt + "'}";
    }
}
