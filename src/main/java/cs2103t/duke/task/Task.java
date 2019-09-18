package cs2103t.duke.task;

import java.util.Objects;

/**
 * Represents tasks, each with 3 main attributes: task type, description, whether it is completed.
 * A task can have up to 1 note.
 */
public abstract class Task {
    /** Tick symbol. */
    public static final String TICK = "\u2713";
    /** Cross symbol. */
    public static final String CROSS = "\u2717";

    /** Description of task. */
    protected String description;
    /** Whether task is completed. */
    protected boolean isCompleted;
    /** Task type of task. */
    protected TaskType taskType;
    /** Notes of task. */
    protected Note note;
    /** Whether task has notes. */
    protected boolean hasNotes = false;
    /** Id of notes in Notes list. */
    protected int noteId;

    //getter mtds
    /**
     * Gets description of task.
     * @return description (notes, if any).
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets whether task is completed.
     * @return true if completed, false otherwise.
     */
    public boolean isCompleted() {
        return this.isCompleted;
    }

    /**
     * Gets task type of task.
     * @return TaskType of task.
     */
    public TaskType getTaskType() {
        return this.taskType;
    }

    /**
     * Gets task notes.
     * @return notes of task.
     */
    public Note getNotes() {
        assert this.hasNotes : "No notes available to be retrieved";
        return this.note;
    }

    /**
     * Gets whether task has notes.
     * @return true if task has notes, false otherwise.
     */
    public boolean hasNotes() {
        return this.hasNotes;
    }

    /**
     * Gets id of notes in Notes list.
     * @return note id.
     */
    public int getNoteId() {
        return this.noteId;
    }

    //setter mtds

    /**
     * Sets task to completed.
     */
    public void setCompleted() {
        this.isCompleted = true;
    }

    /**
     * Sets notes and id of notes of task.
     * @param note notes to set for task.
     * @param id id of said notes in list of notes.
     */
    public void setNotes(Note note, int id) {
        setNotes(note);
        this.hasNotes = true;
        this.noteId = id;
    }

    /**
     * Sets note of task as given note.
     * @param note note to set to task.
     */
    public void setNotes(Note note) {
        this.note = note;
    }

    /**
     * Sets notes id.
     * @param id id of notes.
     */
    public void setNoteId(int id) {
        this.noteId = id;
        this.hasNotes = true;
    }

    /**
     * Sets notes to null.
     */
    public void deleteNotes() {
        this.note = null;
        this.noteId = 0;
        this.hasNotes = false;
    }

    @Override
    public String toString() {
        String checked;
        if (this.isCompleted) {
            checked = TICK;
        } else {
            checked = CROSS;
        }
        return String.format("[%s][%s] %s%s", this.taskType, checked, this.description, getNotesString());
    }

    private String getNotesString() {
        if (hasNotes) {
            return String.format("\n|Notes:|\n  %s", this.note);
        } else {
            return "";
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.description, this.isCompleted);
    }
}
