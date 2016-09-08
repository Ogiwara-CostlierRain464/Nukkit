package me.ogiwara.MSGEConnecter;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler; 
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;

//DataBase
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

//List
import java.util.*;
import java.io.File;

import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.player.PlayerKickEvent;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import cn.nukkit.event.server.ServerCommandEvent;

public class MSGEConnecter extends PluginBase implements Listener{
	
	final public String ADDPLAYER = "<AP>";
	
	final public String DELETEPLAYER = "<DP>";
	
	final public String STOPSERVER = "<SS>";
	
	final public String NOTICE = "<NO>";
	
	//DataBaseはあとで…
	
	public Connection DB;
	
	public List<String> Visiters = new ArrayList<String>();
	
	public List<String> NewPlayers = new ArrayList<String>();
	
	public List<String> BanPlayers = new ArrayList<String>();
	
	public void onEnable(){
		
		try{
			Class.forName("org.sqlite.JDBC");
		}catch(Exception e){
			this.getLogger().info(e.getMessage());
			//Disableしなくて大丈夫?
		}
		
		this.MakeSaveData();
		
		this.getServer().getPluginManager().registerEvents(this, this); 
	}
	
	@EventHandler
	public void onLogin(PlayerPreLoginEvent event){
		this.AddNewPlayers(event.getPlayer());
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		this.AddPlayerList(event.getPlayer());
		
		this.AddVisiters(event.getPlayer());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		this.DeletePlayerList(event.getPlayer());
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event){
		this.DeletePlayerList(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent event){
		String command = event.getMessage();
		
		if(HasGUITag(command)){
			event.setCancelled();
			event.getPlayer().sendMessage("§c[MSGE] Don't use GUI Tag in chat.");
		}
	}
	
	@EventHandler
	public void onPreCommand(PlayerCommandPreprocessEvent event){
		
		String command = event.getMessage();
		
		if(HasGUITag(command)){
			event.setCancelled();
			event.getPlayer().sendMessage("§c[MSGE] Don't use GUI Tag in chat.");
		}
		
		if(this.IsBanCommand(command)){
			this.AddBanPlayers(this.GetArg(command));
		}
	}
	
	@EventHandler
	public void onServerCommand(ServerCommandEvent event){
		
		String command = event.getCommand();
	
		if(this.IsBanCommand(command)){
			this.AddBanPlayers(this.GetArg(command));
		}
		
	}
	
	public void onDisable(){
		this.StopServer();
		int v = this.Visiters.size();
		int n = this.NewPlayers.size();
		int b = this.BanPlayers.size();
		
		try{
			this.ConnectDB();
			this.DB.createStatement().executeUpdate("insert into Count (Visit,New,Ban) values ('"+ String.valueOf(v) +"','" + String.valueOf(n) +"','" + String.valueOf(b) + "')");
			this.DB.close();
		}catch(SQLException e){
			this.getLogger().info(e.getMessage());
		}
	}
	
	private boolean HasGUITag(String message){
		if(message.contains("<GUI>") || message.contains("</GUI>")){
			return true;
		}else{
			return false;
		}
	}
	
	public void GUIHandlerAPI(String message){
		String str = "<GUI>" + message + "</GUI>";
		
		this.getLogger().info(str);
	}
	
	public void AddPlayerList(Player player){
		String name = player.getName();
		
		this.GUIHandlerAPI(this.ADDPLAYER + name);
	}
	
	public void DeletePlayerList(Player player){
		String name = player.getName();
		this.GUIHandlerAPI(this.DELETEPLAYER + name);
	}
	
	public void StopServer(){
		this.GUIHandlerAPI(this.STOPSERVER);
	}
	
	public void AddNewPlayers(Player player){
		String lowname = player.getName().toLowerCase();
		
		File file = new File(this.getServer().getDataPath() + "players/" + lowname + ".dat");
		
		if(!file.exists()){
			this.NewPlayers.add(lowname);
		}
	}
	
	public void AddVisiters(Player player){
		String name = player.getName();
		if(this.Visiters.indexOf(name) == -1){
			this.Visiters.add(name);
		}
	}
	
	public void AddBanPlayers(String name){
		if(this.BanPlayers.indexOf(name) == -1){
			this.BanPlayers.add(name);
		}
	}
	
	public boolean IsBanCommand(String command){
		if(command.indexOf("ban") != -1){
			if(command.indexOf("un") != -1 || command.indexOf("pardon") != -1){
				return false;
			}else{
				String[] spl = command.split(" ");
				if(spl.length >= 2){
					return true;
				}else{
					return false;
				}
			}
		}else{
			return false;
		}
	}
	
	public String GetArg(String str){
		String[] splits = str.split(" ");
		
		return splits[1];
	}
	
	//DATA BASE
	public void MakeSaveData(){
		File file = this.getDataFolder();
		if(!file.exists()){
			file.mkdir();
		}
		
		try{
		this.ConnectDB();
		}catch(SQLException en){
			this.getLogger().info(en.getMessage());
		}
		
		try{
			this.DB.createStatement().executeUpdate("create table if not exists Count(Boot integer primary key autoincrement,Visit integer,New integer,Ban integer)");
			this.DB.close();
		}catch(SQLException e){
			this.getLogger().info(e.getMessage());
		}
	}
	
	public void ConnectDB() throws SQLException { 
			this.DB = DriverManager.getConnection("jdbc:sqlite:"+getDataFolder()+"/" + "ServerInfo.db");
	}
}
