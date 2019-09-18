package cs2103t.duke.ui;

/**
 * Represents the System I/O interface and layout of Duke chatbot.
 * This handles user input and Duke output.
 */
public class Ui {
    /** Divides responses as part of layout. */
    private static final String DIVIDER = "    " + "-".repeat(61);

    /**
     * Constructs a Ui.
     */
    public Ui() {
    }

    /**
     * Shows welcome response when Duke starts up.
     */
    public void showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        dukeRespond("Hello! I'm Duke", "What can I do for you?");
    }

    /**
     * Returns inputs in Duke layout.
     * @param inputs lines of String that Duke is supposed to respond.
     * @return string of inputs.
     */
    public String dukeRespond(String... inputs) {
        StringBuilder sb = new StringBuilder();
        for (String s : inputs) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

    private void showLine() {
        System.out.println(DIVIDER);
    }

    /**
     * Shows loading error when file cannot be read.
     */
    public void showLoadingError() {
        dukeRespond("Cannot read from file! Check if it exists?");
    }

    /**
     * Shows error when an Exception is caught.
     * @param msg message of the exception caught.
     */
    public void showError(String msg) {
        dukeRespond(msg);
    }

}
