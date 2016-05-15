package me.ogiwara.batu;

import java.io.File; 
import java.util.LinkedHashMap; 

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler; 
import cn.nukkit.event.Listener; 
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import cn.nukkit.command.Command; 
import cn.nukkit.command.CommandSender;

public class batu extends PluginBase implements Listener{
	
	Config batuname;
	
	Config batuip;
	
	Config batucid;
	
	public void onEnable(){

		this.getServer().getPluginManager().registerEvents(this, this); 
		getDataFolder().mkdir();
 
		batuname = new Config(new File(this.getDataFolder(), "batuname.yml"),Config.YAML);
		batuip = new Config(new File(this.getDataFolder(), "batuip.yml"),Config.YAML);
		batucid = new Config(new File(this.getDataFolder(), "batucid.yml"),Config.YAML);
		
		this.getServer().getLogger().info("§c[罰] Loaded…");
	}
	
	@EventHandler
	public void join(PlayerJoinEvent event){
	}
	
	@EventHandler
	public void join(PlayerJoinEvent event){
	}
	
	//////////////////BATU CHECK////////////////
	
	public boolean batuname(){
		
	}
	
	public boolean batuip(){
		
	}
	
	public boolean batucid(){
		
	}
	
	//////////////////BATU ADD///////////////////
	
	public void addname(){
		
	}
	
	public void addip(){
		
	}
	
	public void addcid(){
		
	}
	
	//////////////////BATU DELETE////////////////
	
	public void delname(){
		
	}
	
	public void delip(){
		
	}
	
	public void delcid(){
		
	}
	
	
}
