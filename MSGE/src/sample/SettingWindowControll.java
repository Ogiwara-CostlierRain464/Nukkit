package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.utils.SettingFileManager;

import java.io.IOException;

/**
 * Created by ogiwara on 2016/08/30.
 */
public class SettingWindowControll {

    public TextField PlayerBanCommandTextField;
    public TextField PlayerLockCommandTextField;
    public Pane SettingWindow;
    public TextField RemoteViewPortTextField;
    public TextField RemoteViewPasswordTextField;

    public void initialize(){
        LoadSettings();
    }

    public void onServerPropertiesClick(MouseEvent mouseEvent) {
        try {
            OpenServerPropertiesWindow();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void LoadSettings(){
        SettingFileManager setting = new SettingFileManager();
        PlayerBanCommandTextField.setText(setting.get("ban"));
        PlayerLockCommandTextField.setText(setting.get("playerlock"));
        RemoteViewPortTextField.setText(setting.get("remoteview-port"));
        RemoteViewPasswordTextField.setText(setting.get("remoteview-password"));
    }

    private void OpenServerPropertiesWindow() throws IOException{
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("serverpropertiesWindow.fxml"));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("MCPE Server GUI Edition -properties");
        primaryStage.setScene(new Scene(root,700,600));
        primaryStage.show();
    }


    public void onOKClick(MouseEvent mouseEvent) {
        String error = getError();
        if(error != null){
            MessageBox box = new MessageBox(error);
            box.show();
        }else{
            SaveSettings();
            MessageBox box = new MessageBox("設定が保存されました");
            box.show();
            SettingWindow.getScene().getWindow().hide();
        }
    }

    private String getError(){
        if(PlayerBanCommandTextField.getText().length() == 0 ||
           PlayerLockCommandTextField.getText().length() == 0 ||
           RemoteViewPasswordTextField.getText().length() == 0 ||
           RemoteViewPortTextField.getText().length() == 0){
            return "入力不備があります";
        }

        if(RemoteViewPasswordTextField.getText().length() != 16){
            return "パスワードは英数字16文字である必要があります";
        }

        return null;
    }

    private void SaveSettings(){
        SettingFileManager setting = new SettingFileManager();
        setting.set("ban",PlayerBanCommandTextField.getText());
        setting.set("playerlock",PlayerLockCommandTextField.getText());
        setting.set("remoteview-port",RemoteViewPortTextField.getText());
        setting.set("remoteview-password",RemoteViewPasswordTextField.getText());
        setting.save("Saved by SettingWindow");
    }
}
