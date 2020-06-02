package windowOAS;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class ServSock {
    public ServSock(Text text, boolean b){
        try{
            text.setText("Waiting");
            ServerSocket servsock = new ServerSocket(8889);
            while (b) {
                System.out.println("Waiting...");
                Socket sock = servsock.accept();
                DataOutputStream stream = new DataOutputStream(sock.getOutputStream());
                stream.writeUTF(System.getProperty("user.name"));
                sock = servsock.accept();
                DataInputStream stream1 = new DataInputStream(sock.getInputStream());
                String str = stream1.readUTF();
                System.out.println(str);
                int[] s = {0};
                Socket finalSock = sock;
                Platform.runLater(() -> {
                    ButtonType OkBtn = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    ButtonType CanselBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Accept File?", OkBtn, CanselBtn);
                    alert.setTitle("Accept File");
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if (buttonType.orElse(CanselBtn) == OkBtn){
                        s[0] = 121;
                        text.setText("Load");
                    }else {
                        s[0] = 125;
                    }
                    try{
                        if(s[0] == 121){
                            stream.write(121);
                            stream.flush();
                            int fdf = stream1.read();
                            System.out.println(fdf);

                            System.out.println();
                            stream.flush();
                            for (int i = 0; i < fdf; i++){
                                String n = stream1.readUTF();
                                System.out.println(n);
                                System.out.println(stream1.readDouble());
                                int ReadB;
                                byte[] Buff = new byte[4096];
                                InputStream inputStream = finalSock.getInputStream();
                                FileOutputStream fileOutputStream = new FileOutputStream(n);
                                long c = stream1.readLong();
                                for (long j = 0; j < c; j++){
                                    ReadB = inputStream.read(Buff);
                                    fileOutputStream.write(Buff, 0, ReadB);
                                }
                                stream.writeBoolean(true);
                                stream.flush();
                            }
                        }else {
                            stream.write(125);
                            stream.flush();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    System.out.println();
                    System.out.println("Finish");
                    text.setText("Finish");
                });
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
