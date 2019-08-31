package cs2103t.duke.task;

import cs2103t.duke.exception.DukeException;
import cs2103t.duke.exception.InvalidIDException;
import cs2103t.duke.exception.InvalidKeywordException;
import cs2103t.duke.ui.Ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an agent that handles the list of tasks.
 * This includes adding and deleting tasks from the list,
 * marking tasks as done, and directing the creation of tasks.
 */
public class TaskList {
    /** List of tasks to keep track of. */
    private List<Task> taskList;

    /**
     * Constructs a TaskList with an empty list of tasks.
     */
    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with given taskList.
     * @param taskList list of tasks to start with.
     */
    public TaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Gets number of tasks in list.
     * @return number of tasks in list.
     */
    public int getSize() {
        return this.taskList.size();
    }

    /**
     * Gets list of tasks.
     * @return list of tasks.
     */
    public List<Task> getTaskList() {
        return this.taskList;
    }

    /**
     * Prints out the list of tasks.
     * @param ui ui in charge of printing.
     */
    public void listTasks(Ui ui) {
        String[] taskStrings = new String[this.getSize() + 1];
        taskStrings[0] = "Here are the tasks in your list:";
        int listIndex = 0;
        for (Task t : this.taskList) {
            listIndex++;
            //int id = t.getId();
            //ASSUMPTION: ok fine need to at least print new id, if i dont wanna change the init id
            taskStrings[listIndex] = String.format("%d.%s", listIndex, t.toString());
        }
        ui.dukeRespond(taskStrings);
    }

    /**
     * Adds given task into current list of tasks.
     * @param task the task to be added.
     */
    public void addData(Task task) {
        this.taskList.add(task);
    }

    /**
     * Marks task at position id as done.
     * @param id the position at which the corresponding task in list is to be marked as done.
     * @return the completed task.
     * @throws DukeException if id is invalid.
     */
    public Task markDone(int id) throws DukeException {
        if (id > getSize() || id < 1) {
            throw new InvalidIDException("" + id);
        }


        Task task = this.taskList.get(id - 1);
        task.setCompleted();
        return task;
    }

    /**
     * Deletes task at position id from list of tasks
     * Pre-condition: id is always valid.
     * @param id the position at which the corresponding task in list is to be deleted.
     * @return the removed task.
     * @throws DukeException if id is invalid.
     */
    public Task deleteTask(int id) throws DukeException {
        if (id > getSize() || id < 1) {
            throw new InvalidIDException(""+id);
        }

        Task task = this.taskList.remove(id - 1);
        return task;
    }

    /**
     * Creates the corresponding task.
     * @param taskType the task type of task to create.
     * @param description description of task.
     * @return new task.
     * @throws DukeException if command keyword is invalid.
     */
    public static Task createTask(TaskType taskType, String description) throws DukeException {
        if (taskType == null) {
            throw new InvalidKeywordException("wrong keyword");
        }

        Task t = null;
        switch (taskType) {
        case T:
            t = Todo.create(description);
            break;
        case D:
            t = Deadline.create(description);
            break;
        case E:
            t = Event.create(description);
            break;
        default:
            throw new InvalidKeywordException("wrong keyword");
        }
        return t;
    }
}
