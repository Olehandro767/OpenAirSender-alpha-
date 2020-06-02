package windowOAS;

import javafx.scene.control.Alert;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class InetInterfaceANDSocket {

    private String IP;
    private String MyIP2;


    public InetInterfaceANDSocket() {
        IP = "";
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration e2 = n.getInetAddresses();
                while (e2.hasMoreElements()) {
                    InetAddress i = (InetAddress) e2.nextElement();
                    try {
                        String sd = "";

                        for (int j = 0; j < 3; j++) {
                            sd += i.getHostAddress().charAt(j);
                        }
                        if (Integer.parseInt(sd) > 127) {
                            IP = i.getHostAddress();
                        }
                    } catch (Exception E) {
                    }
                }
            }
        }catch (SocketException socketException){
            socketException.printStackTrace();
        }
    }

    public String GetIP(){
        return IP;
    }

    public String GetIP_p3() {
        MyIP2 = "";
        byte CSTR = 0;
        for (byte i = 0; i < IP.length(); i++){
            if (IP.charAt(i) == '.'){
                CSTR = i;
            }
        }
        for (byte i = 0; i < CSTR; i++){
            MyIP2 += IP.charAt(i);
        }
        return MyIP2;
    }

    void SortA(long[] array){
        for (int left = 0; left < array.length; left++) {
            int minInd = left;
            for (int i = left; i < array.length; i++) {
                if (array[i] < array[minInd]) {
                    minInd = i;
                }
            }
            long tmp = array[left];
            array[left] = array[minInd];
            array[minInd] = tmp;
        }
    }

    public void SocketSendFile(DataOutputStream outputStream, DataInputStream inputStream, List<File> files) {
        try{
            System.out.println();
            outputStream.writeUTF("You want get files?");
            int consentCode = inputStream.read();
            if (consentCode == 121){
                outputStream.flush();
                ArrayList<File> files1 = new ArrayList<File>();
                int i = 0;
                for (File file:
                        files){
                    i++;
                    files1.add(file);
                }
                outputStream.write(i);
                long[] l = new long[i];
                for (int j = 0; j < i; j++){
                    l[j] = files1.get(j).length();
                }
                SortA(l);
                ArrayList<File> files2 = new ArrayList<File>();
                for (int j = 0; j < i; j++){
                    for (int k = 0; k < i; k++){
                        if (l[j] == files1.get(k).length()){
                            System.out.println(l[j] + " " + files1.get(k).length());
                            files2.add(files1.get(k));
                        }
                    }
                }
                for (File file:
                        files2) {
                    outputStream.writeUTF(file.getName());
                    System.out.println(file.getName());
                    outputStream.writeDouble((file.length() * 1024));
                    System.out.println((file.length() * 1024));
                    //send
                    long quantitySends = 0;
                    int Read;
                    byte[] Buff = new byte[4096];
                    InputStream inputStream_F = new FileInputStream(file);
                    while ((Read = inputStream_F.read(Buff)) > 0){
                        quantitySends += 1;
                    }
                    outputStream.writeLong(quantitySends);
                    outputStream.flush();
                    inputStream_F = new FileInputStream(file);
                    System.out.println(quantitySends);
                    for (long j = 0; j < quantitySends; j++){
                        Read = inputStream_F.read(Buff);
                        outputStream.write(Buff, 0, Read);
                    }
                    System.out.println();
                    System.out.println(inputStream.readBoolean());
                    System.out.println();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("File not sent");
                alert.setHeaderText("File not delivered");
                alert.setContentText("Click OK");
                alert.showAndWait();
            }
            outputStream.flush();
            System.out.println("Finnish");
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
}