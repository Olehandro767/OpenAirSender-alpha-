package windowOAS.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import windowOAS.ServSock;

public class AcceptFileForm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane BPane;

    @FXML
    private Text Txt;

    @FXML
    public Button BtnClose;

    @FXML
    void initialize() {
        assert BPane != null : "fx:id=\"BPane\" was not injected: check your FXML file 'AcceptFileForm.fxml'.";
        assert Txt != null : "fx:id=\"Txt\" was not injected: check your FXML file 'AcceptFileForm.fxml'.";
        assert BtnClose != null : "fx:id=\"BtnClose\" was not injected: check your FXML file 'AcceptFileForm.fxml'.";

        BPane.getStylesheets().add(getClass().getResource("Styles/AcceptFileForm.css").toExternalForm());
        Task task = ServSocketTask();
        Platform.runLater(() -> {
            new Thread(task).start();
        });
    }

    boolean aBoolean;

    private Task ServSocketTask(){
        return new Task() {
            @Override
            protected Object call() throws Exception {
                aBoolean = true;
                new ServSock(Txt, aBoolean);
                return null;
            }
        };
    }
}
