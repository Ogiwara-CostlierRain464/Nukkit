package me.ogiwara.PMDataBase;

import java.io.File;
import java.util.HashMap;
import java.sql.Connection; 
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.util.ArrayList; 
import java.util.List;

import cn.nukkit.Server;
import cn.nukkit.Player; 
import cn.nukkit.event.EventHandler; 
import cn.nukkit.event.Listener; 
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class PMDataBase extends PluginBase implements Listener{
	
	public static PMDataBase plugin;
	
	public void onEnable(){
	plugin = this;
	if(this.getServer().getPluginManager().getPlugin("DbLib") == null){
	this.getLogger().info("§cDbLib is required to this plugin.");
	this.getServer().getPluginManager().disablePlugin(this);
	}else{
		Connection connection = connectToSQLite("test.db");
	}

	
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getServer().getLogger().info("[PMDataBase] Loaded");
		
	}
	
	public Connection connectToSQLite(String filename) throws SQLException{
	Connection connection = this.getServer()./*getPluginManager().getPlugin("DbLib").*/getSQLiteConnection(getPlugin(),filename);
	return connection;
	}
	
	public PMDataBase getPlugin(){
		return plugin;
	}

	@EventHandler
	public void join(PlayerLoginEvent event){
		Player player = event.getPlayer();
		String name = player.getName().toLowerCase();
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent event){
	}
}
