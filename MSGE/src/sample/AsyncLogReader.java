package sample;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ListView;
import sample.utils.TextFormat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ogiwara on 2016/09/01.
 */
public class AsyncLogReader extends Service{

    private ListView logger;

    private Process process;

    private InputStream inputStream;

    public AsyncLogReader(ListView listview, Process p){
        this.logger = listview;
        this.process = p;
        inputStream = this.process.getInputStream();
    }

    @Override
    protected Task createTask(){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                String result;
                while (true) {
                    InputStreamReader itr = new InputStreamReader(inputStream, TextFormat.getEncode(ConsoleWindowControll.getInstance().platform));
                    BufferedReader jr = new BufferedReader(itr);
                    while((result = jr.readLine()) != null){
                        if(result.length() > 9){
                            Analizecode(result);
                        }
                    }
                    itr.close();
                    jr.close();
                }
            }
        };
        return task;
    }

    private void Analizecode(String log){
        if(TextFormat.hasGUITag(log)){
            ProcessCode(TextFormat.getContents(log));
        }else{
            RemoteAccessThread.AddLogStack(log);
            ConsoleWindowControll.getInstance().AddLogFromAnotherThread(log);
        }
    }

    private void ProcessCode(String content){
        String tag = content.substring(0,4);
        switch (tag){
            case TextFormat.ADD_PLAYER_TAG:
                AddPlayerList(content.replace("<AP>",""));
            break;

            case TextFormat.DELETE_PLAYER_TAG:
                DeletefromPlayerList(content.replace("<DP>",""));
            break;

            case TextFormat.SERVER_STOP_TAG:
                onServerStopped();
            break;

            case TextFormat.NOTIFICATION_TAG://<GUI><NO><NAME>ogiwara</NAME><MESSAGE>ogiwara has runued away!</MESSAGE></GUI>
                AddNotification(TextFormat.getTaggedContents(content.replace("<NO>",""),"<MESSAGE>","</MESSAGE>"));
                RemoteAccessThread.AddNotificationStack(content.replace("<NO>",""));
            break;
        }
    }

    private void onServerStopped(){
        CleanPlayerListFromAnotherThread();
        if(ConsoleWindowControll.getInstance().RestartCheckBox.isSelected()){
            RestartServer();
        }else{
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    MessageBox messageBox = new MessageBox("サーバーは終了しました");
                    messageBox.show();
                }
            });
        }
    }

    private void RestartServer(){
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    logger.getItems().add(">サーバーは10秒後に再起動します...");
                }
            });
            Thread.sleep(10000);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ConsoleWindowControll.getInstance().RestartConsole();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void CleanPlayerListFromAnotherThread(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ConsoleWindowControll.getInstance().PlayerListView.getItems().clear();
            }
        });
    }

    private void AddPlayerList(String name){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ConsoleWindowControll.getInstance().PlayerListView.getItems().add(name);
            }
        });
    }

    private void DeletefromPlayerList(String name){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ConsoleWindowControll.getInstance().PlayerListView.getItems().remove(name);
            }
        });

    }

    private void AddNotification(String notification){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ConsoleWindowControll.getInstance().NotificationListView.getItems().add(notification);
            }
        });

    }
}
