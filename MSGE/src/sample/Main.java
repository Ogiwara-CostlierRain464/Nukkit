package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import sample.utils.*;

import javax.swing.*;
import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        if(!DirectoryChecker.CheckDirectory()){
            Parent root = FXMLLoader.load(getClass().getResource("PreviousWindow.fxml"));
            primaryStage.setTitle("MCPE Server GUI Edition -help");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();
        }else {
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            primaryStage.setTitle("MCPE Server GUI Edition -DashBoard");
            primaryStage.setScene(new Scene(root, 1000, 750));
            primaryStage.show();
        }
    }

    @Override
    public void stop(){
        try {
            ConsoleWindowControll.getInstance().CloseConsole();
            RemoteAccessThread.getInstance().kill();
        }catch (Exception e){//may be null pointer Exception
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
