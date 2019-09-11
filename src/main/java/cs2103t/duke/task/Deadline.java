package cs2103t.duke.task;

import cs2103t.duke.exception.EmptyDescriptionException;
import cs2103t.duke.exception.IncorrectTaskFormatException;
import cs2103t.duke.parse.Parser;

import java.util.Date;

/**
 * Represents a task with deadline. Deadline tasks have 4 main attributes: their type, description, by when, and
 * whether it is completed.
 */
public class Deadline extends Task {
    /** Description of deadline task without the date time. */
    private String description;
    /** Date time information in the following format: (by: date time). */
    private String notesInBrackets;
    /** String containing only date and time. */
    private String datetime;
    /** Date object representing date and time. */
    private Date date;

    private Deadline() {

    }

    private Deadline(String descr, boolean completed) throws IncorrectTaskFormatException {
        super.completed = completed;
        super.taskType = TaskType.D;

        setupDetails(descr);
    }

    private void setupDetails(String input) throws IncorrectTaskFormatException {
        final int DESCR = 0, DATE_INDEX = 1, NUM_MUST_HAVE_SECTIONS = 2;
        String[] sections = input.split("\\s+/by\\s+");
        //inputs should only have <=1 '/' characters
        if (sections.length < NUM_MUST_HAVE_SECTIONS) {
            throw new IncorrectTaskFormatException("by");
        }

        this.description = sections[DESCR];


        String term = "by";
        String date = sections[DATE_INDEX];

        if (date.equals("")) {
            throw new IncorrectTaskFormatException("by");
        }

        this.notesInBrackets = String.format("%s: %s", term, date);

        super.description = String.format("%s (%s)", this.description, this.notesInBrackets);

        this.datetime = date;
        this.date = Parser.convertToDate(date);

        assert this.date != null;
    }

    /**
     * Creates a deadline task with the description of task (including date time information).
     * @param descr Description of task including date and time.
     * @return Deadline object representing task with the description.
     * @throws EmptyDescriptionException if description is empty.
     * @throws IncorrectTaskFormatException if task format is incorrect.
     */
    public static Deadline create(String descr) throws EmptyDescriptionException, IncorrectTaskFormatException {
        if (descr.equals("")) {
            throw new EmptyDescriptionException("a deadline");
        }

        Deadline newTask = new Deadline(descr.trim(), false);
        return newTask;
    }

    @Override
    public String getDescription() {
        return String.format("%s | %s", this.description, this.datetime);
    }
}
