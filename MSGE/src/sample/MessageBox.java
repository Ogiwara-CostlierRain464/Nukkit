package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by ogiwara on 2016/09/01.
 */
public class MessageBox {

    public static String message;

    public MessageBox(String message){
        this.message = message;
    }

    public void show(){
        try {
            Stage primaryStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("MessageBoxWindow.fxml"));
            primaryStage.initModality(Modality.APPLICATION_MODAL);
            primaryStage.setTitle("MessageBox");
            primaryStage.setScene(new Scene(root, 300, 150));
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
