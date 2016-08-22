package me.ogiwara.I;

import java.util.HashMap;

import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.Plugin;

import cn.nukkit.command.Command;  
import cn.nukkit.command.CommandSender; 

public class I extends PluginBase implements Listener{
	
	public void onEnable(){
		this.getLogger().info(Class.forName("me.ogiwara.PMDataBase.PMDataBase").getClass().getName()); 
		if(this.getServer().getPluginManager().getPlugin("PMDataBase") !=null){
			this.getLogger().info("Connected to PMDataBase");
		}else{
			for(int i= 0;i <= 9;++i){
				this.getLogger().info("this plugin requires PMDataBase!");
			}
			this.getServer().getPluginManager().disablePlugin(this);
		}
		
		this.getLogger().info("I Loaded");
	}
	
	/*public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){
		switch(command.getName()){
			case "i":
				try{ 
					if(args[0] != null){
						//意図的なエラー
					} 
				}catch(ArrayIndexOutOfBoundsException e){
					sender.sendMessage("Please enter player name");
					return false;
				}
				String arg = args[0];
				String name = getServer().getPluginManager().getPlugin("PMDataBase").checkifplayerexists(arg);
				if(name == "NONE"){
					sender.sendMessage(args[0] + "does not exist.");
					return false;
				}else{
					HashMap info = getServer().getPluginManager().getPlugin("PMDataBase").getplayerinfo(name);
					String cid = info.get("cid").toString().substring(0, 4);
					sender.sendMessage("info of "+name);
					sender.sendMessage("id "+info.get("id"));
					sender.sendMessage("ip "+info.get("ip"));
					sender.sendMessage("cid "+cid);
					sender.sendMessage("count "+info.get("count"));
					sender.sendMessage("firstjoin "+info.get("firstjoin"));
					sender.sendMessage("lastjoin "+info.get("lastjoin"));
				}
			return true;
		}
		return false;
	}*/
	
}
