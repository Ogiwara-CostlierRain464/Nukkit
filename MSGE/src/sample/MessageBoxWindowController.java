package sample;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Created by ogiwara on 2016/09/01.
 */
public class MessageBoxWindowController {

    public Label MessageLabel;
    public Pane MessageBoxWindow;

    public void initialize(){
        MessageLabel.setText(MessageBox.message);
    }

    public void onOKClick(MouseEvent mouseEvent) {
        MessageLabel.getScene().getWindow().hide();
    }
}
