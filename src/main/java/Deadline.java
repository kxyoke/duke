import java.util.Scanner;

public class Deadline extends Task {
    //Deadlines can have (notes)
    private String description;
    private String notesInBrackets;
    private String datetime;

    private Deadline() {}
    private Deadline(String descr, boolean completed, int id) throws IncorrectTaskFormatException{
        super.completed = completed;
        super.id = id;
        super.taskType = TaskType.D;

        setupDetails(descr);
    }
    private void setupDetails(String input) throws IncorrectTaskFormatException {
        String[] tmp = input.split("\\s+/");
        //inputs should only have <=1 '/' characters
        this.description = tmp[0];

        if (tmp.length < 2) {
            throw new IncorrectTaskFormatException("by");
        }

        /*
        Scanner tmp2 = new Scanner(tmp[1]);
        String term = tmp2.next();
        String date = "";
        if (tmp2.hasNext()) {
            date = tmp2.nextLine();
        }
        tmp2.close();
         */
        tmp = tmp[1].split("\\s+");
        String term = tmp[0];
        String date = tmp[1];

        if (date.equals(""))
            throw new IncorrectTaskFormatException("by");
        this.notesInBrackets = String.format("%s: %s", term, date);

        super.description = String.format("%s (%s)", this.description, this.notesInBrackets);

        this.datetime = date;
    }

    public static Deadline create(String descr) throws EmptyDescriptionException, IncorrectTaskFormatException {
        if (descr.equals(""))
            throw new EmptyDescriptionException("a deadline");

        Task.totalNumOfTasks++;
        Deadline newTask = new Deadline(descr.trim(), false, Task.totalNumOfTasks);
        Task.taskList.add(newTask);
        return newTask;
    }

    @Override
    public String getDescription() {
        return String.format("%s | %s", this.description, this.datetime);
    }
}
