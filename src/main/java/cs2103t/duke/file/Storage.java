package cs2103t.duke.file;

import cs2103t.duke.exception.DukeException;
import cs2103t.duke.parse.Parser;
import cs2103t.duke.task.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates logic and format to write files to and read files from. Each storage object is in charge of a single
 * file. Duke keeps track of 2 files: 1. Files containing tasks; 2. Files containing notes.
 * A Task file should contain the following format for each line:
 * {@code [T/D/E] | [✗/✓] | [note_id] | [description [| date as per required input]]}.
 * For example, {@code D | ✗ | 1 | this is a deadline description | 31/8/2019 2359}
 * or {@code T | ✓ | 0 | this is a todo description}.
 * A Note file should contain the following format for each line:
 * {@code [0/1] | content_of_note}
 * For example, {@code 0 | this note is not bound, aka it is a general note.}
 * or {@code 1 | this is a bound note; it's bound to some task but it doesn't know.}
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
     * @param noteList
     * @throws DukeException if cannot write to file.
     */
    public void updateFileWithTask(TaskList taskList, NoteList noteList) throws DukeException {
        List<Task> tasks = taskList.getTaskList();
        try {
            File file = new File(filepath);
            FileWriter fr = new FileWriter(file);
            file.getParentFile().mkdirs();
            file.createNewFile();
            fr.close();

            fr = new FileWriter(new File(filepath), true);
            BufferedWriter br = new BufferedWriter(fr);

            for (Task t : tasks) {
                if (t.hasNotes()) {
                    t.setNoteId(noteList.getUpdatedTaskNoteId(t.getNoteId()));
                }
                br.write(getTaskDetailToPrint(t));
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            throw new DukeException("cannot write to file");
        }
    }

    private String getTaskDetailToPrint(Task t) {
        String s = String.format("%s | %d | %d | %s\r\n",
                t.getTaskType(), boolToInt(t.isCompleted()), t.getNoteId(), t.getDescription());
        System.out.println(s);
        return s;
    }

    /**
     * Reads and loads tasks stored in file.
     * Returns an empty List if file is empty.
     * @return list of tasks.
     * @throws DukeException if cannot read from file.
     */
    public List<Task> loadTaskFromFile() throws DukeException {
        List<Task> tasks = new ArrayList<>();
        try {
            File file = new File(filepath);
            BufferedReader br = new BufferedReader(
                    new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                Task task = parseTaskLine(line);
                tasks.add(task);
                line = br.readLine();
            }

            br.close();
        } catch (IOException e) {
            throw new DukeException("cannot read file");
        }
        return tasks;
    }

    private Task parseTaskLine(String line) {
        final int TASKTYPE = 0, COMPLETE = 1, DESCR = 3, NOTES_ID = 2, DATE_IF_APPLICABLE = 4;

        String[] sections = line.split(" \\| ");
        TaskType taskType = TaskType.convertToTaskType(sections[TASKTYPE]);
        boolean completed = intStrToBool(sections[COMPLETE]);
        String description = sections[DESCR];
        int notesId = Parser.parseStrToInt(sections[NOTES_ID]);
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
        if (notesId != 0) {
            task.setNoteId(notesId);
        }
        assert notesId >= 0 : "for some reason notesId read from task file can be -ve O.O";
        return task;
    }

    private static int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    private static boolean intStrToBool(String intStr) {
        assert intStr.length() == 1 : "Neither '0' nor '1' was passed into intStrToBool";
        return intStr.equals("1");
    }

    public List<Note> loadNotesFromFile() {
        List<Note> notes = new ArrayList<>();
        try {
            File file = new File(filepath);
            //file.mkdirs();
            BufferedReader br = new BufferedReader(
                    new FileReader(file));
            String line = br.readLine();
            //NEED TO PARSE ALSO: ISBOUND | CONTENT
            while (line != null) {
                Note note = parseNoteLine(line);
                notes.add(note);
                line = br.readLine();
            }

            br.close();
        } catch (IOException e) {
            throw new DukeException("cannot read file");
        }
        return notes;
    }

    private Note parseNoteLine(String line) {
        final int IS_BOUND = 0, CONTENT = 1;

        String[] sections = line.split(" \\| ");
        boolean isBound = intStrToBool(sections[IS_BOUND]);
        String content = sections[CONTENT];
        return new Note(content, isBound);
    }

    public void updateFileWithNote(NoteList notes) throws DukeException {
        List<Note> noteList = notes.getNoteList();
        try {
            File file = new File(filepath);
            FileWriter fr = new FileWriter(file);
            file.getParentFile().mkdirs();
            file.createNewFile();
            fr.close();

            fr = new FileWriter(new File(filepath), true);
            BufferedWriter br = new BufferedWriter(fr);

            for (Note n : noteList) {
                if (n.isDeleted()) {
                    continue;
                }
                br.write(getNoteDetailToPrint(n));
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            throw new DukeException("cannot write to file");
        }
    }

    private String getNoteDetailToPrint(Note n) {
        return String.format("%d | %s\r\n", boolToInt(!n.isGeneral()), n.getContent());
    }
}
