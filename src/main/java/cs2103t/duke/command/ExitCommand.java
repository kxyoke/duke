package cs2103t.duke.command;

import cs2103t.duke.file.Storage;
import cs2103t.duke.task.TaskList;
import cs2103t.duke.ui.Ui;

/**
 * Represents an exit command.
 */
public class ExitCommand extends Command {
    /**
     * Constructs an exit command.
     */
    public ExitCommand() {
        super.isExit = true;
    }

    /**
     * Creates and adds new task to list of tasks.
     * @param taskList TaskList agent to handle list of tasks.
     * @param ui Ui in charge of printing.
     * @param storage Storage agent in charge of writing to file.
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        return ui.dukeRespond("Bye. Hope to see you again soon!");
    }
}
