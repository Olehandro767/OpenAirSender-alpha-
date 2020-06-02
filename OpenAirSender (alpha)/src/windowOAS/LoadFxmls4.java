package windowOAS;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadFxmls4 {

    private Stage stage;

    public LoadFxmls4(String URL, Object contr) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Controllers/" + URL));
        loader.setController(contr);
        Parent parent = loader.load();
        stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public LoadFxmls4(String URL) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("Controllers/" + URL));
        stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void StClose(){
        stage.close();
    }

    public void StExit() {
        stage.setOnCloseRequest(event -> {
            System.out.println("exit");
            System.exit(0);
        });
    }

    public LoadFxmls4(String URL, Object contr, boolean b) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Controllers/" + URL));
        loader.setController(contr);
        Parent parent = loader.load();
        stage = new Stage();
        stage.setScene(new Scene(parent));
        StExit();
        stage.show();
    }
}
