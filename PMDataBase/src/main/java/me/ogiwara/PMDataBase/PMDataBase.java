package me.ogiwara.PMDataBase;

import java.io.File;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Calendar;


import cn.nukkit.Server;
import cn.nukkit.Player; 
import cn.nukkit.event.EventHandler; 
import cn.nukkit.event.Listener; 
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class PMDataBase extends PluginBase implements Listener{
	
	public void onEnable() {

		 this.getServer().getPluginManager().registerEvents(this, this);
		getDataFolder().mkdir();
		 // load the sqlite-JDBC driver using the current class loader

		try{
			Class.forName("org.sqlite.JDBC");
			}catch(Exception e){
				 System.err.println(e.getMessage());
			}
		   Connection connection = null;
		   try
		   {
		     connection = DriverManager.getConnection("jdbc:sqlite:"+getDataFolder()+"/PMDataBase.db");
		     Statement statement = connection.createStatement();
		     statement.executeUpdate("create table if not exists player(id integer primary key autoincrement,name text,ip text,cid text,count integer,firstjoin text,lastjoin text)");
			  statement.executeUpdate("create table if not exists chatlog(id integer primary key autoincrement,name text,chat name,type text,time text)");
		   }
		   catch(SQLException e)
		   {

		     System.err.println(e.getMessage());
		   }
		   finally
		   {
			   try
			      {
			        if(connection != null)
			          connection.close();
			      }
			      catch(SQLException e)
			      {
			        // connection close failed.
			        System.err.println(e);
			      }
		   }


        this.getServer().getLogger().info("[PMDataBase] Loaded");

	}	

	@EventHandler
	public void join(PlayerLoginEvent event){
		
		boolean check = true;
		
		Player player = event.getPlayer();
		String name = player.getName().toLowerCase();
		Connection connection = null;
		try{
			connection = DriverManager.getConnection("jdbc:sqlite:"+getDataFolder()+"/PMDataBase.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			ResultSet rs = statement.executeQuery("select * from player where name ='" + name + "'");
			if(rs.getString("count") != null){
				check = false;
			}
		}catch(SQLException e){
			check = true;	
		}catch(Exception e){
			this.getLogger().info("Sqlite3 error!");
		}
		
		try{
		if(check == true){
			String ip = player.getAddress();
			String cid = player.getClientId().toString();
			String firstjoin = "2343444";
			connection = DriverManager.getConnection("jdbc:sqlite:"+getDataFolder()+"/PMDataBase.db");
			Statement statement = connection.createStatement();
			statement.executeUpdate("insert into player (name,ip,cid,count,firstjoin,lastjoin) values ('"+name+"','"+ip+"','"+cid+"',1,'"+firstjoin+"','"+firstjoin+"')");
		}
		}catch(SQLException e){
			
		}
		finally
		   {
			   try
			      {
			        if(connection != null)
			          connection.close();
			      }
			      catch(SQLException e)
			      {
			        // connection close failed.
			        System.err.println(e);
			      }
		   }
	}
	
	@EventHandler
	public void chat(PlayerCommandPreprocessEvent event){
		
		Player player = event.getPlayer();
		String name = player.getName().toLowerCase();
		String type = "chat";
		String message = event.getMessage();
		Calendar cal = Calendar.getInstance();
		String time = Integer.toString(cal.get(Calendar.YEAR)) + Integer.toString(cal.get(Calendar.MONTH)) + Integer.toString(cal.get(Calendar.DATE))  + Integer.toString(cal.get(Calendar.HOUR_OF_DAY))  + Integer.toString(cal.get(Calendar.MINUTE)) ;
		if(message.startsWith("/")){
			type = "command";
		}else{
			type = "chat";
		}
		
		Connection connection = null;
		   try
		   {
		     connection = DriverManager.getConnection("jdbc:sqlite:"+getDataFolder()+"/PMDataBase.db");
		     Statement statement = connection.createStatement();
			statement.executeUpdate("insert into chatlog (name, chat, type, time) values ('"+ name +"','"+ message +"','"+ type +"','"+ time +"')");
		   }
		   catch(SQLException e)
		   {
		     System.err.println(e.getMessage());
		   }
		   finally
		   {
			   try
			      {
			        if(connection != null)
			          connection.close();
			      }
			      catch(SQLException e)
			      {
			        // connection close failed.
			        System.err.println(e);
			      }
		   }		
	}
}
