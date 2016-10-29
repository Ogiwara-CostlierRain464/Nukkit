package sample;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Created by ogiwara on 2016/08/29.
 */
public class PreviousWindowControll {


    public Pane PreviousWindow;

    public void onOKClick(MouseEvent mouseEvent) {

        PreviousWindow.getScene().getWindow().hide();

    }
}
