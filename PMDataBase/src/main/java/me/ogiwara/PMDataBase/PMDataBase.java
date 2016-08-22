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
		try{
		exec("create table if not exists player(id integer primary key autoincrement,name text,ip text,cid text,count integer,firstjoin text,lastjoin text)",connect("PMDataBase.db"));
		exec("create table if not exists chatlog(id integer primary key autoincrement,name text,chat name,type text,time text)",connect("PMDataBase.db"));
		}catch(SQLException e){
			this.getLogger().info("SQlite3 error!");
			System.err.println(e);
		}
		
        this.getServer().getLogger().info("[PMDataBase] Loaded");

	}	

	@EventHandler
	public void Login(PlayerLoginEvent event){
		
		boolean check = true;
		
		Player player = event.getPlayer();
		String name = player.getName().toLowerCase();
		String ip = player.getAddress();
		String cid = String.valueOf(player.getClientId());
		Calendar cal = Calendar.getInstance();
		String time = Integer.toString(cal.get(Calendar.YEAR)) +"/"+ Integer.toString(cal.get(Calendar.MONTH)) +"/"+ Integer.toString(cal.get(Calendar.DATE))  +" "+ Integer.toString(cal.get(Calendar.HOUR_OF_DAY))  +":"+ Integer.toString(cal.get(Calendar.MINUTE)) ;
		Connection connection = null;
		try{
			ResultSet rs = query("select id,count,firstjoin from player where name ='" + name + "'",connect("PMDataBase.db"));
			if(rs.getString("count") != null){
				int count = Integer.parseInt(rs.getString("count"));
				++count;
				exec("replace into player (id,name, ip,cid,count,firstjoin,lastjoin) values ('"+rs.getString("id")+"','"+name+"','"+ip+"','"+cid+"','"+count+"','"+rs.getString("firstjoin")+"','"+time+"')",connect("PMDataBase.db"));
				check = false;
			}
		}catch(SQLException e){
			check = true;	
		}catch(Exception e){
			this.getLogger().info("Sqlite3 error!");
			System.err.println(e);
		}
		
		try{
			if(check == true){
			exec("insert into player (name,ip,cid,count,firstjoin,lastjoin) values ('"+name+"','"+ip+"','"+cid+"',1,'" +time+ "','" +time+ "')",connect("PMDataBase.db"));
			}
		}catch(SQLException e){
			
		}
	}
	
	@EventHandler
	public void chat(PlayerCommandPreprocessEvent event){
		
		Player player = event.getPlayer();
		String name = player.getName().toLowerCase();
		String type = "chat";
		String message = event.getMessage();
		Calendar cal = Calendar.getInstance();
		String time = Integer.toString(cal.get(Calendar.YEAR)) +"/"+ Integer.toString(cal.get(Calendar.MONTH)) +"/"+ Integer.toString(cal.get(Calendar.DATE))  +" "+ Integer.toString(cal.get(Calendar.HOUR_OF_DAY))  +":"+ Integer.toString(cal.get(Calendar.MINUTE)) ;
		if(message.startsWith("/")){
			type = "command";
		}else{
			type = "chat";
		}
		   try
		   {
		     exec("insert into chatlog (name, chat, type, time) values ('"+ name +"','"+ message +"','"+ type +"','"+ time +"')",connect("PMDataBase.db"));
		   }
		   catch(SQLException e)
		   {
		     System.err.println(e.getMessage());
		   }
	}
	
	public HashMap getplayerinfo(String name){
		name = name.toLowerCase();
		HashMap<String,String> ppp = new HashMap<String,String>();
		try{
		ResultSet rs = query("select id,ip,cid,count,firstjoin,lastjoin from player where name like '"+ name +"%'",connect("PMDataBase.db"));
			if(rs.getString("id") != null){
				ppp.put("id",rs.getString("id"));
				ppp.put("ip",rs.getString("ip"));
				ppp.put("cid",rs.getString("cid"));
				ppp.put("count",rs.getString("count"));
				ppp.put("firstjoin",rs.getString("firstjoin"));
				ppp.put("lastjoin",rs.getString("lastjoin"));
				return ppp;
			}
		}catch(SQLException e){
			
		}catch(Exception e){
			
		}
		ppp.put("id","NONE");
		return ppp;
	}
	
	//public String getplayerlist(){
		
	//}
	
	////////////////////////////OSI////////////////////
		/*try{
			connection = DriverManager.getConnection("jdbc:sqlite:"+getDataFolder()+"/PMDataBase.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
		}catch(SQLException e){

		}catch(Exception e){
			
		}*/
	protected String getplayerbyid(int id){
		try{
			ResultSet rs = query("select name from player where id =" + id,connect("PMDataBase.db"));
			if(rs.getString("name") != null){
				return rs.getString("name");
			}
		}catch(SQLException e){
			return "NONE";
		}catch(Exception e){
			this.getLogger().info("§cSqlite3 error!");
			System.err.println(e.getMessage());
		}
		return "NONE";
	}
	
	public String checkifplayerexists(String name){
		try{
			ResultSet rs = query("select name from player where name ='" + name + "'",connect("PMDataBase.db"));
			if(rs.getString("name") != null){
				return name;
			}
		}catch(SQLException e){
			Player player = this.getServer().getPlayer(name);
			if(player instanceof Player){
				return player.getName().toLowerCase();
			}else{
				String result = getplayerbyid(Integer.parseInt(name));
				return result;//もし見つからない場合は、NONEをreturn
			}
		}catch(Exception e){
			this.getLogger().info("§cSqlite3 error!");
			System.err.println(e.getMessage());
		}
		return "NONE";
	}
	
	
	//////////////////////////DATABASE API////////////////////////////////////
	
	public Connection connect(String filename) throws SQLException{
		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:sqlite:"+getDataFolder()+"/" +filename);
		return connection;
	}
	
	public void exec(String sql,Connection connection) throws SQLException{
		connection.createStatement().executeUpdate(sql);
		connection.close();
	}
	
	public ResultSet query(String sql,Connection connection) throws SQLException{
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(30);
		ResultSet rs = statement.executeQuery(sql);
		connection.close();
		return rs;
	}	
}
