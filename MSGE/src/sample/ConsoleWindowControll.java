package sample;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import sample.utils.DirectoryChecker;
import sample.utils.SettingFileManager;

import java.awt.*;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by ogiwara on 2016/08/31.
 */
public class ConsoleWindowControll {

    private static ConsoleWindowControll instance;

    public ListView ConsoleListView;
    public TextField CommandTextField;
    public ListView PlayerListView;
    public ListView NotificationListView;
    public CheckBox RestartCheckBox;
    public Button BanButton;
    public Button LockButton;
    public Button kickButton;

    public Process process;

    public OutputStream outputStream;

    public AsyncLogReader asyncLogReader;

    public RemoteAccessThread remoteAccessThread;

    //Flags
    public String platform;

    public String bancommad;

    public String lockcommand;

    public String kickcommand;


    public void initialize(){
        instance = this;
        LoadCommands();
        StartConsole();
        remoteAccessThread = new RemoteAccessThread();
        remoteAccessThread.start();
    }

    public static ConsoleWindowControll getInstance(){
        return instance;
    }

    public void onExecuteButtonClick(MouseEvent mouseEvent) {
        ExecuteCommand(CommandTextField.getText().toString());
        CommandTextField.setText("");
    }

    private void StartConsole(){
        ArrayList<String> args = DirectoryChecker.getArgAndPlatform();
        try{
            process = Runtime.getRuntime().exec(args.get(0));
            outputStream = process.getOutputStream();
            StartLogReadThread();
            platform = args.get(1);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void CloseConsole(){
        try {
            process.getOutputStream().close();
            process.getInputStream().close();
            process.getErrorStream().close();
            process.destroy();
            process = null;
            asyncLogReader.cancel();
            asyncLogReader = null;
            outputStream = null;
        }catch (Exception e){//may be null pointer Exception
            e.printStackTrace();
        }
    }

    public void RestartConsole(){
        CloseConsole();
        StartConsole();
    }

    protected void StartLogReadThread(){
        asyncLogReader = new AsyncLogReader(ConsoleListView,process);
        asyncLogReader.restart();
    }

    private void ExecuteCommand(String command){

        if(command == "") return;

        if(process == null) return;

        if(!process.isAlive()) return;

        AddLog(command);
        try{
            BufferedOutputStream jw = new BufferedOutputStream(outputStream);
            jw.write((command + System.getProperty("line.separator")).getBytes());
            jw.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void AddLogFromAnotherThread(String log){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                AddLog(log);
            }
        });
    }

    private void AddLog(String log){
        ConsoleListView.getItems().add(log);
    }

    public void onKeyPress(KeyEvent keyEvent) {

        switch (keyEvent.getCode()){
            case ENTER:
                ExecuteCommand(CommandTextField.getText().toString());
                CommandTextField.setText("");
            break;
        }

    }

    private void LoadCommands(){
        SettingFileManager settingFileManager = new SettingFileManager();
        kickcommand = "kick";
        bancommad = settingFileManager.get("ban");
        lockcommand = settingFileManager.get("playerlock");
        BanButton.setText(bancommad);
        LockButton.setText(lockcommand);
        kickButton.setText(kickcommand);
    }

    public void onBanButtonClick(MouseEvent mouseEvent) {
        try {
            ExecuteCommand(bancommad + " " + PlayerListView.getSelectionModel().getSelectedItem().toString());
        }catch (Exception e){}
    }

    public void onLockButtonClick(MouseEvent mouseEvent) {
        try {
            ExecuteCommand(lockcommand + " " + PlayerListView.getSelectionModel().getSelectedItem().toString());
        }catch (Exception e){}
    }

    public void onKickButtonClick(MouseEvent mouseEvent) {
        try {
            ExecuteCommand(kickcommand + " " + PlayerListView.getSelectionModel().getSelectedItem().toString());
        }catch (Exception e){}
    }

    public void onClearNotificationButtonClick(MouseEvent mouseEvent) {
        NotificationListView.getItems().clear();
    }

    public void onGarbageImageViewClick(MouseEvent mouseEvent) {
        ConsoleListView.getItems().clear();
    }
}
