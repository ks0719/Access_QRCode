package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("전자출입명부 관리 프로그램");
        primaryStage.setScene(new Scene(root, 388, 166));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
