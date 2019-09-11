package cs2103t.duke.command;

import cs2103t.duke.file.Storage;
import cs2103t.duke.task.Task;
import cs2103t.duke.task.TaskList;
import cs2103t.duke.ui.Ui;

import java.util.List;

/**
 * Represents a find command.
 */
public class FindCommand extends Command {
    /** Keyword to find in task strings. */
    private String wordToFind;

    /**
     * Constructs a find command.
     * @param keyword the keyword/phrase to find in task strings.
     */
    public FindCommand(String keyword) {
        this.wordToFind = keyword;
        super.isExit = false;
    }

    /**
     * Finds tasks that contain keyword.
     * @param taskList TaskList agent to handle list of tasks.
     * @param ui Ui in charge of printing.
     * @param storage Storage agent in charge of reading/writing to file.
     * @return String containing list of tasks that have that keyword.
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        List<Task> foundTasks = taskList.findTasks(this.wordToFind);
        if (foundTasks.isEmpty()) {
            return ui.dukeRespond("Sorry! I can't find any matching tasks in your list. Try another phrase?");
        }

        return ui.dukeRespond(getStringArray(foundTasks));
    }

    private String[] getStringArray(List<Task> tasks) {
        String[] strings = new String[tasks.size() + 1];
        strings[0] = "Here are the matching tasks in your list:";
        int listIndex = 0;
        for (Task task : tasks) {
            listIndex++;
            strings[listIndex] = String.format("%d.%s", listIndex, task.toString());
        }
        return strings;
    }
}
