package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

public class Controller {
    public ListView PluginListView;
    public Label ServerNameLabel;
    public Label ServerPortLabel;
    public Pane DashBoardWindow;
    public LineChart PlayerLineChart;

    //Flag
    private boolean HasMSGEPlugin = false;

    public Controller(){
        //設定ファイルがないときは作成
        SettingFileManager.CreateDefaultSettingFile();
    }

    public void initialize(){
        LoadPluginList();//プラグインリスト読み込み
        LoadServerInfo();//Server name と Server port 読み込み
        LoadWidgets();
    }

    public void onStartButtonClick(MouseEvent mouseEvent) {
        //GOTO Console
        try{
            OpenConsoleWindow();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void onSettingButtonClick(MouseEvent mouseEvent) {
        //GOTO Setting
        try {
            OpenSettingWindow();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void LoadPluginList(){//ここでMSGEConnectpluginも準備
        ArrayList<String> list = PluginListLoader.getPluginList(System.getProperty("user.dir"));

        for(String name : list){
            PluginListView.getItems().add(name);
            if(name.startsWith("MSGEConnecter")){
                HasMSGEPlugin = true;
            }
        }

        if(HasMSGEPlugin == false){
            MessageBox messageBox = new MessageBox("MSGEConnecterプラグインをセットして下さい!");
            messageBox.show();
            //setMSGEConnecterPlugin();//May be I'll kill this!
        }
    }

    private void setMSGEConnecterPlugin(){
        try {
            InputStream in = getClass().getResourceAsStream("@plugin" + File.separator +"MSGEConnecter_v1.0.phar");
            Path outputPath = FileSystems.getDefault().getPath("plugins" + File.separator + "MSGEConnecter.phar");
            Files.copy(in,outputPath);
            InputStream in2 = getClass().getResourceAsStream("plugin" + File.separator +"MSGEConnecter-1.0.jar");
            Path outputPath2 = FileSystems.getDefault().getPath("plugins" + File.separator + "MSGEConnecter.jar");
            Files.copy(in2,outputPath2);
        }catch (Exception e){
            e.printStackTrace();
        }
        PluginListView.getItems().add("MSGEConnecter");
    }

    private void LoadServerInfo(){//Get Data From server.properties

        ServerPropertiesManager serverPropertiesManager = new ServerPropertiesManager();

        String name = serverPropertiesManager.get("server-name");

        if(name == null){
            name = serverPropertiesManager.get("motd");
        }

        String port = serverPropertiesManager.get("server-port");

        ServerNameLabel.setText(name);

        ServerPortLabel.setText(port);
    }

    private void LoadWidgets(){//ウイジェットとして読み込み!
        //In version 1, I only support graph.
        GraphMaker g = new GraphMaker();
        PlayerLineChart.getData().add(g.getBanPlayers());
        PlayerLineChart.getData().add(g.getNewPlayers());
        PlayerLineChart.getData().add(g.getVisiters());
    }

    private void OpenSettingWindow() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("SettingWindow.fxml"));
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("MCPE Server GUI Edition -settings");
        primaryStage.setScene(new Scene(root,800,600));
        primaryStage.show();
    }

    private void OpenConsoleWindow() throws IOException{
        if(HasMSGEPlugin == false){
            MessageBox messageBox = new MessageBox("MSGEConnecterプラグインをセットして下さい!");
            messageBox.show();
            return;
        }
        DashBoardWindow.getScene().getWindow().hide();
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("ConsoleWindow.fxml"));
        primaryStage.setTitle("MCPE Server GUI Edition -Console");
        primaryStage.setScene(new Scene(root,1000,750));
        primaryStage.show();
    }

    public void onCreditClick(MouseEvent mouseEvent) {
        try{
            OpenCreditWindow();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void OpenCreditWindow() throws IOException{
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("creditWindow.fxml"));
        primaryStage.setTitle("Credit");
        primaryStage.setScene(new Scene(root,800,600));
        primaryStage.show();
    }
}
