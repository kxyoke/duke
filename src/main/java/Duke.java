import cs2103t.duke.command.Command;
import cs2103t.duke.exception.DukeException;
import cs2103t.duke.file.Storage;
import cs2103t.duke.parse.Parser;
import cs2103t.duke.task.TaskList;
import cs2103t.duke.ui.Ui;

/**
 * Acts as a chatbot to remember your tasks at hand.
 * There will be three kinds of tasks: Todo, Deadline, Event.
 * Valid inputs include:
 * {@code list} to list the tasks it remembered so far;
 * {@code todo some todo description} to save a new Todo task with description "some todo description";
 * {@code deadline something /by dd/MM/yyyy HHmm} to save a new Deadline task with description "something"
 * and deadline dd/MM/yyyy HHmm;
 * {@code event some event /at datetime} to save a new Event task with description "some event" held at datetime;
 * {@code done id} to mark the (id)th task as completed;
 * {@code delete id} to remove the (id)th task from list of tasks to remember;
 * {@code bye} or {@code exit} to exit from duke.
 * Duke will save the tasks in a file ("./data/tasks.txt") and read from it.
 */
public class Duke {
    /** Handles file reading and writing. */
    private Storage storage;
    /** Handles direct changes to list of tasks. */
    private TaskList tasks;
    /** Handles system i/o in duke format. */
    private Ui ui;

    private String dataFilepath = "./data/tasks.txt";

    /**
     * Constructs Duke object to be called by Application launcher, javafx.
     * Storage filepath used default ./data/tasks.txt.
     */
    public Duke() {
        this.ui = new Ui();
        this.storage = new Storage(dataFilepath);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            this.tasks = new TaskList();
        }
    }

    /**
     * Constructs a Duke chatbot that reads from and writes to file located at datafilepath.
     * @param dataFilepath file path to read and write data.
     */
    public Duke(String dataFilepath) {
        this.ui = new Ui();
        this.storage = new Storage(dataFilepath);
        try {
            this.tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            this.tasks = new TaskList();
        }
    }

    /**
     * Gets response from duke logic given a command.
     * @param fullCommand the entire command given to duke.
     * @return the response by duke.
     */
    public String getResponse(String fullCommand) {
        String res;
        try {
            Command c = Parser.parse(fullCommand);
            res = c.execute(this.tasks, this.ui, this.storage);
        } catch (DukeException e) {
            res = e.toString();
        }

        assert res != null : "getResponse cannot work properly";

        return res;
    }

}
