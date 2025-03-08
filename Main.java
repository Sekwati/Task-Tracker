
public class Main {
    public static void main(String[] args) {
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
                System.out.println("Task added successfully");
                break;

            case "update":
                if (args.length < 3) {
                    System.out.println("Usage: task-cli update <id> <new description>");
                    return;
                }
                System.out.println("Task updated successfully (ID: " + args[1] + ")");
                break;

            case "delete":
                if (args.length < 2) {
                    System.out.println("Usage: task-cli delete <id>");
                    return;
                }
                System.out.println("Task deleted successfully (ID: " + args[1] + ")");
                break;

            case "mark-in-progress":
                if (args.length < 2) {
                    System.out.println("Usage: task-cli mark-in-progress <id>");
                    return;
                }
                System.out.println("Task marked as in progress (ID: " + args[1] + ")");
                break;

            case "mark-done":
                if (args.length < 2) {
                    System.out.println("Usage: task-cli mark-done <id>");
                    return;
                }
                System.out.println("Task marked as done (ID: " + args[1] + ")");
                break;


            default:
                System.out.println("Unknown command: " + args[0]);
                System.out.println("Available actions: add, update, delete, list, mark-done, mark-in-progress");
        }
    }
}
