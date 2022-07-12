import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Appinitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/StudentForm.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}