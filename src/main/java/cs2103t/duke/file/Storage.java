package cs2103t.duke.file;

import cs2103t.duke.exception.DukeException;
import cs2103t.duke.task.Task;
import cs2103t.duke.task.TaskList;
import cs2103t.duke.task.TaskType;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates logic and format to write files to and read files from.
 * File should contain the following format for each line:
 * {@code [T/D/E] | [✗/✓] | [description [| date as per required input]]}.
 * For example, {@code D | ✗ | this is a deadline description | 31/8/2019 2359}
 * or {@code T | ✓ | this is a todo description}.
 */
public class Storage {
    /** Path to file to read/write. */
    private String filepath;

    /**
     * Constructs a Storage object.
     * @param filepath path to file.
     */
    public Storage(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Writes to file the contents of taskList, without appending to original file contents.
     * @param taskList list of tasks to write to file.
     * @throws DukeException if cannot write to file.
     */
    public void updateFile(TaskList taskList) throws DukeException {
        List<Task> tasks = taskList.getTaskList();
        try {
            File file = new File(filepath);
            FileWriter fr = new FileWriter(file);
            file.mkdirs();
            file.createNewFile();
            fr.close();

            fr = new FileWriter(new File(filepath), true);
            BufferedWriter br = new BufferedWriter(fr);

            for (Task t : tasks) {
                br.write(getTaskDetailToPrint(t));
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            throw new DukeException("cannot write to file");
        }
    }

    private String getTaskDetailToPrint(Task t) {
        return String.format("%s | %d | %s\r\n",
                t.getTaskType(), boolToInt(t.isCompleted()), t.getDescription());
    }

    /**
     * Reads and loads tasks stored in file.
     * Returns an empty List if file is empty.
     * @return list of tasks.
     * @throws DukeException if cannot read from file.
     */
    public List<Task> load() throws DukeException {
        List<Task> tasks = new ArrayList<>();
        try {
            File file = new File(filepath);
            file.mkdirs();
            BufferedReader br = new BufferedReader(
                    new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                Task task = parseLine(line);
                tasks.add(task);
                line = br.readLine();
            }

            br.close();
        } catch (IOException e) {
            throw new DukeException("cannot read file");
        }
        return tasks;
    }

    private Task parseLine(String line) {
        final int TASKTYPE = 0, COMPLETE = 1, DESCR = 2, DATE_IF_APPLICABLE = 3;

        String[] sections = line.split(" \\| ");
        TaskType taskType = TaskType.convertToTaskType(sections[TASKTYPE]);
        boolean completed = intStrToBool(sections[COMPLETE]);
        String description = sections[DESCR];
        String datetime = "";
        String term = "";
        if (taskType == TaskType.E) {       //actually can just store as you read in one... but ok
            term = " /at ";
        } else if (taskType == TaskType.D) {
            term = " /by ";
        }
        if (sections.length > DATE_IF_APPLICABLE) {
            datetime = term + sections[DATE_IF_APPLICABLE];
        }

        Task task = TaskList.createTask(taskType, description + datetime);
        if (completed) {
            task.setCompleted();
        }
        return task;
    }

    private static int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    private static boolean intStrToBool(String intStr) {
        return intStr.equals("1");
    }
}
