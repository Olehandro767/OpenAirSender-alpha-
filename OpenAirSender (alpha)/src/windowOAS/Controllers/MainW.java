package windowOAS.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.stage.Stage;
import windowOAS.LoadFxmls4;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class MainW {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private HBox HBox1;

    @FXML
    private Button BtnSendFile;

    @FXML
    private Button BtnAcceptFile;

    @FXML
    void BtnAcceptFileClick(ActionEvent event) {
        HBox1.getScene().getWindow().hide();
        AcceptFileForm acceptFileForm = new AcceptFileForm();
        Platform.runLater(() -> {
            try{
                LoadFxmls4 loadFxmls4 = new LoadFxmls4("FXMLs/AcceptFileForm.fxml", acceptFileForm, true);
                acceptFileForm.BtnClose.setOnAction(actionEvent -> {
                    ((Stage)HBox1.getScene().getWindow()).show();
                    acceptFileForm.aBoolean = false;
                });
            }catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void BtnSendFileClick(ActionEvent event) {
        HBox1.getScene().getWindow().hide();
        SendFile sendFile = new SendFile();
        Platform.runLater(() -> {
            try {
                fxmls4 = new LoadFxmls4("FXMLs/SendFile.fxml", sendFile);
                sendFile.BtnCLOSE.setOnAction(actionEvent -> {
                    ShowThisWindow();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void initialize() {
        assert HBox1 != null : "fx:id=\"HBox\" was not injected: check your FXML file 'mainW.fxml'.";
        assert BtnSendFile != null : "fx:id=\"BtnSendFile\" was not injected: check your FXML file 'mainW.fxml'.";
        assert BtnAcceptFile != null : "fx:id=\"BtnAcceptFile\" was not injected: check your FXML file 'mainW.fxml'.";

        new Thread(() -> {
            HBox1.getStylesheets().add(getClass().getResource("Styles/MainW.css").toExternalForm());
        }).start();
    }

    private LoadFxmls4 fxmls4;

    //private void Style(){
    //    HBox1.getStylesheets().add(getClass().getResource("Styles/MainW.css").toExternalForm());
    //}

    private void ShowThisWindow(){
        fxmls4.StClose();
        ((Stage)HBox1.getScene().getWindow()).show();
    }
}
