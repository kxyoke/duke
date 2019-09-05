package cs2103t.duke.command;

import cs2103t.duke.exception.DukeException;
import cs2103t.duke.exception.NoIdGivenException;
import cs2103t.duke.file.Storage;
import cs2103t.duke.parse.Parser;
import cs2103t.duke.task.Task;
import cs2103t.duke.task.TaskList;
import cs2103t.duke.ui.Ui;

/**
 * Represents a delete command.
 */
public class DeleteCommand extends Command {
    /** Position of task in list of tasks to remove. */
    private String taskId;

    /**
     * Constructs delete command.
     * @param idString position of task in list to remove.
     */
    public DeleteCommand(String idString) {
        this.taskId = idString;
        super.isExit = false;
    }

    /**
     * Deletes task from list of tasks.
     * @param tasks TaskList agent to handle list of tasks.
     * @param ui Ui in charge of printing.
     * @param storage Storage agent in charge of writing to file.
     * @throws DukeException if command is invalid or cannot write to file.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        if (this.taskId.equals("")) {
            throw new NoIdGivenException("delete");
        }
        int id = Parser.parseStrToInt(this.taskId);
        Task task = tasks.deleteTask(id);

        storage.updateFile(tasks);

        return ui.dukeRespond("Noted. I've removed this task:",
                "  " + task.toString(),
                String.format("Now you have %d tasks in the list.", tasks.getSize()));
    }
}
