package cs2103t.duke.task;

/**
 * Represents the potential task type for Duke to do.
 * This includes creating tasks and other commands.
 * T represents todo task,
 * D represents deadline task,
 * E represents event task,
 * LIST represents list tasks,
 * DELETE represents delete tasks,
 * DONE represents mark tasks as done,
 * EXIT represents exit duke.
 */
public enum TaskType {
    T, D, E, LIST, DELETE, DONE, EXIT, FIND;

    /**
     * Converts command keyword to task type.
     * @param typeString command keyword.
     * @return TaskType of command.
     */
    public static TaskType convertToTaskType(String typeString) {
        TaskType type;
        switch (typeString.toUpperCase()) {
        case "T":
            //Fallthrough
        case "TODO":
            type = T;
            break;
        case "D":
            //Fallthrough
        case "DEADLINE":
            type = D;
            break;
        case "E":
            //Fallthrough
        case "EVENT":
            type = E;
            break;
        case "LIST":
            type = LIST;
            break;
        case "DELETE":
            type = DELETE;
            break;
        case "DONE":
            type = DONE;
            break;
        case "EXIT":
            //Fallthrough
        case "BYE":
            type = EXIT;
            break;
        case "FIND":
            type = FIND;
            break;
        default:
            type = null;
            break;
        }
        assert type != null : "TaskType should not be null";
        return type;
    }
}
