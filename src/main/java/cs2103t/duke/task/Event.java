package cs2103t.duke.task;

import cs2103t.duke.exception.EmptyDescriptionException;
import cs2103t.duke.exception.IncorrectTaskFormatException;

/**
 * Represents an event task. Event tasks have 4 main attributes: their type, description, at when, and
 * whether it is completed.
 */
public class Event extends Task {
    /** Description of event task without the date time. */
    private String description;
    /** Date time information in the following format: (at: date time). */
    private String notesInBrackets;
    /** String containing only date and time. */
    private String datetime;

    private Event() {

    }

    private Event(String descr, boolean completed) throws IncorrectTaskFormatException {
        super.completed = completed;
        super.taskType = TaskType.E;

        setupDetails(descr);
    }

    private void setupDetails(String input) throws IncorrectTaskFormatException {
        final int DESCR = 0, DATE_INDEX = 1, NUM_MUST_HAVE_SECTIONS = 2;
        String[] sections = input.split("\\s+/at\\s+");
        this.description = sections[DESCR];
        //inputs should only have <=1 '/' characters
        if (sections.length < NUM_MUST_HAVE_SECTIONS) {
            throw new IncorrectTaskFormatException("at");
        }

        String term = "at";
        String datetime = sections[1];

        if (datetime.equals("")) {
            throw new IncorrectTaskFormatException("at");
        }

        this.notesInBrackets = String.format("%s: %s", term, datetime);

        super.description = String.format("%s (%s)", this.description, this.notesInBrackets);
        this.datetime = datetime;
    }

    /**
     * Creates an event task with the description of task (including date time information).
     * @param descr Description of task including date and time.
     * @return Event object representing task with the description.
     * @throws EmptyDescriptionException if description is empty.
     * @throws IncorrectTaskFormatException if task format is incorrect.
     */
    public static Event create(String descr) throws EmptyDescriptionException, IncorrectTaskFormatException {
        if (descr.equals("")) {
            throw new EmptyDescriptionException("an event");
        }

        Event newTask = new Event(descr.trim(), false);
        return newTask;
    }

    @Override
    public String getDescription() {
        return String.format("%s | %s", this.description, this.datetime);
    }
}
