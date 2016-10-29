package sample;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sample.utils.ServerPropertiesManager;

import java.util.ArrayList;

/**
 * Created by ogiwara on 2016/08/30.
 */
public class serverpropertiesWindowControll {
    public Pane serverpropertiesWindow;

    public ServerPropertiesManager properties;
    public ListView serverpropertiesListView;
    public Label KeyLabel;
    public TextField ValueTextField;

    public void initialize(){
        properties = new ServerPropertiesManager();
        ArrayList<String> list = properties.getKeys();
        for(String str : list){
            serverpropertiesListView.getItems().add(str);
        }
    }

    public void onListClick(MouseEvent mouseEvent) {
        try {
            String a = serverpropertiesListView.getSelectionModel().getSelectedItem().toString();
            KeyLabel.setText(a);
            ValueTextField.setText(properties.get(a));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onOKClick(MouseEvent mouseEvent) {
        MessageBox box = new MessageBox("サーバーの設定は保存されました");
        box.show();
        serverpropertiesWindow.getScene().getWindow().hide();
    }

    public void onValueTextFieldChange(KeyEvent keyEvent) {
        if(ValueTextField.getText().toString() == "") return;

        properties.set(KeyLabel.getText().toString(),ValueTextField.getText().toString());
        properties.save("Changed by ServerPropertiesWindow");
    }
}
