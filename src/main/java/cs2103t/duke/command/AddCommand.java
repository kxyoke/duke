package cs2103t.duke.command;

import cs2103t.duke.exception.DukeException;
import cs2103t.duke.file.Storage;
import cs2103t.duke.task.Task;
import cs2103t.duke.task.TaskList;
import cs2103t.duke.task.TaskType;
import cs2103t.duke.ui.Ui;

/**
 * Represents an add task command. Command has 2 main attributes: task type and full description of task to add.
 */
public class AddCommand extends Command {
    /** Task type of task to add. */
    private TaskType taskType;
    /** Task type of task to add. */
    private String fullDescription;

    /**
     * Constructs an add command.
     * @param taskType task type of task.
     * @param descr description of task.
     */
    public AddCommand(TaskType taskType, String descr) {
        this.fullDescription = descr;
        this.taskType = taskType;
        super.isExit = false;
    }

    /**
     * Creates and adds new task to list of tasks.
     * @param tasks TaskList agent to handle list of tasks.
     * @param ui Ui in charge of printing.
     * @param storage Storage agent in charge of writing to file.
     * @throws DukeException if command is invalid or cannot write to file.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        Task newTask = TaskList.createTask(this.taskType, this.fullDescription);
        tasks.addData(newTask);

        storage.updateFile(tasks);

        return ui.dukeRespond("Got it. I've added this task:",
                "  " + newTask.toString(),
                String.format("Now you have %d tasks in the list", tasks.getSize()));
    }
}
