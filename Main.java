
public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        // Step 1: Ensure the user provides a command
        if (args.length == 0) {
            System.out.println("Usage: task-cli <command> [options]");
            return;
        }

        // Step 2: Handle different commands
        switch (args[0]) {
            case "add":
                // Ensure a task description is provided
                if (args.length < 2) {
                    System.out.println("Usage: task-cli add <task_description>");
                    return;
                }
                taskManager.addTask(args[1]);
                System.out.println("Task added successfully");
                break;

            case "update":
                if (args.length < 3) {
                    System.out.println("Usage: task-cli update <id> <new description>");
                    return;
                }
                taskManager.updateTask(args[1], args[2]);
                System.out.println("Task updated successfully (ID: " + args[1] + ")");
                break;

            case "delete":
                if (args.length < 2) {
                    System.out.println("Usage: task-cli delete <id>");
                    return;
                }
                taskManager.deleteTask(args[1]);
                System.out.println("Task deleted successfully (ID: " + args[1] + ")");
                break;

            case "mark-in-progress":
                if (args.length < 2) {
                    System.out.println("Usage: task-cli mark-in-progress <id>");
                    return;
                }
                taskManager.markInProgress(args[1]);
                System.out.println("Task marked as in progress (ID: " + args[1] + ")");
                break;

            case "mark-done":
                if (args.length < 2) {
                    System.out.println("Usage: task-cli mark-done <id>");
                    return;
                }
                taskManager.markDone(args[1]);
                System.out.println("Task marked as done (ID: " + args[1] + ")");
                break;

                case "list":
                if (args.length < 2) {
                    taskManager.listTasks("All");
                } else {
                    Status filterStatus;
                    try {
                        filterStatus = Status.valueOf(args[1].toUpperCase().replace("-", "_"));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid status: " + args[1]);
                        return;
                    }
                    taskManager.listTasks(filterStatus.toString());
                }
                break;

            default:
                System.out.println("Unknown command: " + args[0]);
                System.out.println("Available actions: add, update, delete, list, mark-done, mark-in-progress");
        }
    }
}
