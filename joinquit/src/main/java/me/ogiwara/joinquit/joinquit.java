package me.ogiwara.joinquit;

import java.io.File; 
import java.util.LinkedHashMap; 

import cn.nukkit.Player; 
import cn.nukkit.event.EventHandler; 
import cn.nukkit.event.Listener; 
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class joinquit extends PluginBase implements Listener{
	
	public void onEnable(){

		this.getServer().getPluginManager().registerEvents(this, this); 
		getDataFolder().mkdir(); 
 
		Config config = new Config( 
		new File(this.getDataFolder(), "config.yml"),Config.YAML, 
		new LinkedHashMap<String, Object>() { 
						{ 
							put("opjoin", "§cServer op @p joined"); 
							put("opquit", "§cSrever op @p quit"); 
							put("non-opjoin", "§e@p joined"); 
							put("non-opquit", "§e@p quit"); 
						} 
					}); 
		config.save(); 
		
		this.getServer().getLogger().info("joinquit Loaded");
	}
	
	@EventHandler
	public void join(PlayerJoinEvent event){
		event.setJoinMessage("");
		Player player = event.getPlayer();
			if(player.isOp()){
				String joinmes = getConfig().get("opjoin").toString();//sucsess ! toString() is required.
				joinmes = joinmes.replaceAll("@p",player.getName());
				this.getServer().broadcastMessage(joinmes);
				Player players = this.getServer().getOnlinePlayers();
				
				player.sendTip("tips");
				player.sendPopup("popup");
			}else{
				String joinmes = getConfig().get("non-opjoin").toString();
				joinmes = joinmes.replaceAll("@p",player.getName());
				this.getServer().broadcastMessage(joinmes);
			}
	}
	
	@EventHandler
	public void quit(PlayerQuitEvent event){
		event.setQuitMessage("");
		Player player = event.getPlayer();
			if(player.isOp()){
				String quitmes = getConfig().get("opquit").toString();
				quitmes = quitmes.replaceAll("@p",player.getName());
				this.getServer().broadcastMessage(quitmes);
			}else{
				String quitmes = getConfig().get("non-opquit").toString();
				quitmes = quitmes.replaceAll("@p",player.getName());
				this.getServer().broadcastMessage(quitmes);				
			}
	}
}
