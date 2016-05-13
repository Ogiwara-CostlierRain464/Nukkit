package me.ogiwara.PMDataBase;

import java.io.File;
import java.security.MessageDigest;
import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
import java.util.HashMap; 


import cn.nukkit.Player; 
import cn.nukkit.event.EventHandler; 
import cn.nukkit.event.Listener; 
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class PMDataBase extends PluginBase implements Listener{
	
	private static HashMap<String,String> ppp = new HashMap<String,String>();
	
	public void onEnable(){
		
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
		Player player = event.getPlayer();
		String name = player.getName().toLowerCase();
		if(ppp.containsKey(name)){
			
		}else{
			String ip = player.getAddress();
			String cid = player.getClientSecret();
			//登録
			Connection connection = null;
			try{
				connection = DriverManager.getConnection("jdbc:sqlite:"+getDataFolder()+"/killb.db"); 
				Statement statement = connection.createStatement(); 
				statement.setQueryTimeout(30);  // set timeout to 30 sec. 
				statement.executeUpdate("insert into player (name,ip,cid,count,firstjoin,lastjoin) values ('"+ name +"','" + ip+"','" + cid +"',1,'ww','ww',)");
			}catch(SQLException e){
				System.err.println(e.getMessage()); 
					}finally{try{ 
						if(connection != null) 
						connection.close();
					}catch(SQLException e){ 
						System.err.println(e);event.setCancelled();
					} 
			}

		}

	}
	
	@EventHandler
	public void quit(PlayerQuitEvent event){
	
	}
}
