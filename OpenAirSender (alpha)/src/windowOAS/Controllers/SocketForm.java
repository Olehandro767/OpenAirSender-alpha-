package windowOAS.Controllers;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ResourceBundle;
import com.sun.jmx.snmp.tasks.Task;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import windowOAS.InetInterfaceANDSocket;

public class SocketForm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox VBox1;

    @FXML
    private AnchorPane APane;

    @FXML
    private ProgressBar ProgBar;

    @FXML
    public Button BtnCansel;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    void initialize() {
        assert VBox1 != null : "fx:id=\"VBox1\" was not injected: check your FXML file 'SocketForm.fxml'.";
        assert APane != null : "fx:id=\"APane\" was not injected: check your FXML file 'SocketForm.fxml'.";
        assert ProgBar != null : "fx:id=\"ProgBar\" was not injected: check your FXML file 'SocketForm.fxml'.";
        assert BtnCansel != null : "fx:id=\"BtnCansel\" was not injected: check your FXML file 'SocketForm.fxml'.";
        assert scrollPane != null : "fx:id=\"scrollPane\" was not injected: check your FXML file 'SocketForm.fxml'.";

        new Thread(() -> {
            VBox1.getStylesheets().add(getClass().getResource("Styles/SocketForm.css").toExternalForm());
            for (File file:
                    files) {
                System.out.println(file.getPath());
            }
            aBoolean = true;
            Task task = SocketAndProgBar();
            task.run();
        }).start();
    }

    private List<File> files;
    public Boolean aBoolean;

    public SocketForm(List<File> filesB){
        files = filesB;
    }

    private void BtnCl(Socket sock, String host, int port, DataInputStream inputStream) {
        final Socket[] socket = {sock};
        Platform.runLater(() -> {
            try {
                aBoolean = false;
                socket[0] = new Socket(host,port);
                DataOutputStream outputStream = new DataOutputStream(socket[0].getOutputStream());
                new InetInterfaceANDSocket().SocketSendFile(outputStream, inputStream, files);
                APane.getChildren().clear();
                Text text = new Text();
                text.setText("Finish");
                text.setLayoutX(APane.getPrefWidth()/2.1);
                text.setLayoutY(APane.getPrefHeight()/2);
                APane.getChildren().add(text);
                ProgBar.setProgress(1);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private Task SocketAndProgBar(){
        return new Task() {
            @Override
            public void cancel() { }

            @Override
            public void run() {
                try {
                    String MyIP2 = new InetInterfaceANDSocket().GetIP_p3();
                    System.out.println(new InetInterfaceANDSocket().GetIP_p3());

                    double percet = 0;
                    final int[] i1 = {0};
                    int timeout = 100;
                    int Ybtn = 0;
                    for (final int[] i = {1}; i[0] < 256 && aBoolean; i[0]++)
                    {
                        i1[0] = (int)percet;
                        percet = ((double) i[0] / 255) * 100;
                        String host = MyIP2 + "." + i[0];
                        System.out.println(host);

                        if (InetAddress.getByName(host).isReachable(timeout)){
                            try {
                                int port = 8889;
                                Socket sock = new Socket(host, port);
                                System.out.println("Connecting...");
                                final DataInputStream inputStream = new DataInputStream(sock.getInputStream());
                                String name = inputStream.readUTF();
                                System.out.println(name);
                                Button button = new Button(name);
                                button.setLayoutX(APane.getPrefWidth()/2.1);
                                button.setLayoutY(10 + Ybtn);
                                Ybtn = ((int)button.getHeight() - 10) + (int)button.getLayoutY();
                                Platform.runLater(() -> {
                                    button.setOnAction(event -> {
                                        BtnCl(sock, host, port, inputStream);
                                    });
                                    APane.getChildren().add(button);
                                });

                            }catch (Exception E){}
                        }

                        if (i1[0] != (int)percet){
                            ProgBar.setProgress(percet/100);
                        }
                    }
                } catch (SocketException | UnknownHostException socketException) {
                    socketException.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

}