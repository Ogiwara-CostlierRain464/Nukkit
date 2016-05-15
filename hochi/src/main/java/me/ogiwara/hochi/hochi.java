package me.ogiwara.hochi;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.entity.Entity;

import cn.nukkit.event.Listener; 
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class hochi extends PluginBase implements Listener{
	
	HashMap<String,Boolean> hochi = new HashMap<String,Boolean>();
	
	public void onEnable(){
		
		this.getServer().getPluginManager().registerEvents(this,this);
		
		getDataFolder().mkdir(); 
 
		Config config = new Config( 
		new File(this.getDataFolder(), "config.yml"),Config.YAML, 
		new LinkedHashMap<String, Object>() { 
						{ 
							put("starthochi", "§b(-д-)zZZ@p is sleeping…"); 
							put("endhochi", "§b@p wake up!");
							put("nametag","§e(-д-)zzz");
						} 
					}); 
		config.save(); 		
	}
	
	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){
		switch(command.getName()){
			case "hochi":
			if(sender instanceof Player){
				String name = sender.getName();
				if(hochi.containsKey(name)){
					unlock(name);
				}else{
					lock(name);
				} 				
			}else{
				sender.sendMessage("Only player can run this command.");
			}
			return true;
		}
		return false;
	}
	
	public void lock(String name){
		Player player = this.getServer().getPlayer(name);
		String starthochi = getConfig().get("starthochi").toString();
		starthochi = starthochi.replaceAll("@p",name);
		this.getServer().broadcastMessage(starthochi);
		hochi.put(name,true);
		player.setDisplayName(getConfig().get("nametag").toString() + player.getDisplayName());
		player.sendMessage("§a>>/hochi to unlock this.");
	}
	
	public void unlock(String name){
		Player player = this.getServer().getPlayer(name);
		String endhochi = getConfig().get("endhochi").toString();
		endhochi = endhochi.replaceAll("@p",name);
		this.getServer().broadcastMessage(endhochi);
		player.setDisplayName(player.getDisplayName().replaceAll(getConfig().get("nametag").toString(),""));
		hochi.remove(name);
	}

	@EventHandler
	public void move(PlayerMoveEvent event){
		Player player = event.getPlayer();
		String name = player.getName();
			if(hochi.containsKey(name)){
				event.setCancelled();
				player.sendTip("§a>>/hochi to unlock this.");
			} 
	}
	
	@EventHandler
	public void touch(PlayerInteractEvent event){
		Player player = event.getPlayer();
		String name = player.getName();
			if(hochi.containsKey(name)){
				event.setCancelled();
				player.sendMessage("§a>>/hochi to unlock this.");
			} 
	}
	@EventHandler
	public void place(BlockPlaceEvent event){
		Player player = event.getPlayer();
		String name = player.getName();
			if(hochi.containsKey(name)){
				event.setCancelled();
				player.sendMessage("§a>>/hochi to unlock this.");				
			} 
	}
	
	@EventHandler
	public void bbreak(BlockBreakEvent event){
		Player player = event.getPlayer();
		String name = player.getName();
			if(hochi.containsKey(name)){
				event.setCancelled();
				player.sendMessage("§a>>/hochi to unlock this.");
			}  								
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent event){
		Player player = event.getPlayer();
		String name = player.getName();
			if(hochi.containsKey(name)){
				hochi.remove(name);//これでunsetと同じらしい
			} 
	}
}
