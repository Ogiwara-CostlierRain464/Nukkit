package sample;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Created by ogiwara on 2016/08/31.
 */
public class CreditWindowControll {

    public Pane CreditWindow;

    public void onOKClick(MouseEvent mouseEvent) {

        CreditWindow.getScene().getWindow().hide();
    }
}
