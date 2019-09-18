import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    /** To auto close using code to close button? */
    @FXML
    private Button closeButton;


    private Duke duke;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/walle.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/eve.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = duke.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();

        if (input.contains("bye") || input.equalsIgnoreCase("exit")) {
            closeButtonAction();
        }
    }

    /**
     * Closes GUI. This is a onClick callback function, but can be used
     * for this purpose.
     */
    @FXML
    private void closeButtonAction() {
        //get a handle to the stage?
        Stage stage = (Stage) closeButton.getScene().getWindow();
        //ahhh then what i have to do when this fn is called aka close
        stage.close();
    }
}