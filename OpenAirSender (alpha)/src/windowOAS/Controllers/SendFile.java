package windowOAS.Controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import windowOAS.LoadFxmls4;

public class SendFile {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane BPane;

    @FXML
    private Button BtnClean;

    @FXML
    private Button BtnSend;

    @FXML
    public Button BtnCLOSE;

    @FXML
    private TextArea TextArea;

    @FXML
    private ProgressBar ProgressBar1;

    @FXML
    void BtnCleanClick(ActionEvent event) {
        TextArea.setText("Send File");
        files.clear();
    }

    @FXML
    void BtnSendClick(ActionEvent event) throws IOException {
        if (!files.isEmpty()){
            SocketForm socketForm = new SocketForm(files);
            BPane.getScene().getWindow().hide();
            loadFxmls4 = new LoadFxmls4("FXMLs/SocketForm.fxml", socketForm);
            socketForm.BtnCansel.setOnAction(actionEvent -> {
                socketForm.aBoolean = false;
                ShowThisWindow();
            });
        }else {

        }
    }

    @FXML
    void TextAreaDragDropped(DragEvent event) {
        TextArea.clear();
        for (File file:
                event.getDragboard().getFiles()){
            TextArea.setText(TextArea.getText() + file.getPath() + "\n");
            files.add(file);
        }
    }

    @FXML
    void TextAreaDragOver(DragEvent event) {
        if(event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    void initialize() {
        assert BPane != null : "fx:id=\"BPane\" was not injected: check your FXML file 'SendFile.fxml'.";
        assert BtnClean != null : "fx:id=\"BtnClean\" was not injected: check your FXML file 'SendFile.fxml'.";
        assert BtnSend != null : "fx:id=\"BtnSend\" was not injected: check your FXML file 'SendFile.fxml'.";
        assert BtnCLOSE != null : "fx:id=\"BtnCLOSE\" was not injected: check your FXML file 'SendFile.fxml'.";
        assert TextArea != null : "fx:id=\"TextArea\" was not injected: check your FXML file 'SendFile.fxml'.";
        assert ProgressBar1 != null : "fx:id=\"ProgressBar1\" was not injected: check your FXML file 'SendFile.fxml'.";

        new Thread(() -> {
            BPane.getStylesheets().add(getClass().getResource("Styles/SendFile.css").toExternalForm());
            TextArea.setText("Send File");
        }).start();

        files = new ArrayList<File>();
    }

    private ArrayList<File> files;
    private LoadFxmls4 loadFxmls4;

    public void ShowThisWindow(){
        loadFxmls4.StClose();
        ((Stage)BPane.getScene().getWindow()).show();
    }
}