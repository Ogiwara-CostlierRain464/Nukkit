package sample.utils;

import javafx.scene.chart.XYChart;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by ogiwara on 2016/09/04.
 */
public class GraphMaker {

    //取り敢えず全部表示…
    public ArrayList<Integer> newplayers = new ArrayList<Integer>();
    public ArrayList<Integer> banplayers = new ArrayList<Integer>();
    public ArrayList<Integer> visitedplayers = new ArrayList<Integer>();

    public GraphMaker(){
        Connection con = null;
        Statement smt = null;
        ResultSet rs = null;
        try{
            con = new DBManager("plugins" + File.separator + "MSGEConnecter" + File.separator + "ServerInfo.db").createConnection();
            smt = con.createStatement();
            rs = smt.executeQuery("Select Max(Boot) as maxno  From Count");

            Integer max = -1;

            while (rs.next()){
                max = rs.getInt("maxno");
            }

            int i = 1;
            while (i != (max + 1)){
                rs = smt.executeQuery("select * from Count where Boot = "+ String.valueOf(i));

                while (rs.next()){
                    newplayers.add(Integer.parseInt(rs.getString("New")));
                    banplayers.add(Integer.parseInt(rs.getString("Ban")));
                    visitedplayers.add(Integer.parseInt(rs.getString("Visit")));
                }
                i += 1;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                rs.close();
                smt.close();
                con.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public XYChart.Series getNewPlayers(){
        XYChart.Series s = new XYChart.Series();
        s.setName("New");
        Integer counter = 1;
        for(Integer i : newplayers){
            s.getData().add(new XYChart.Data(String.valueOf(counter),i));
            counter += 1;
        }
        return s;
    }

    public XYChart.Series getBanPlayers(){
        XYChart.Series s = new XYChart.Series();
        s.setName("Ban");
        Integer counter = 1;
        for(Integer i : banplayers){
            s.getData().add(new XYChart.Data(String.valueOf(counter),i));
            counter += 1;
        }
        return s;
    }

    public XYChart.Series getVisiters(){
        XYChart.Series s = new XYChart.Series();
        s.setName("Visited");
        Integer counter = 1;
        for(Integer i : visitedplayers){
            s.getData().add(new XYChart.Data(String.valueOf(counter),i));
            counter += 1;
        }
        return s;
    }
}
