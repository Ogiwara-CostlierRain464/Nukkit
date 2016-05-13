package me.ogiwara.Disabler; 

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

import cn.nukkit.event.Listener; 
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.PluginException;

public class Disabler extends PluginBase implements Listener{
	
	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){
		switch(command.getName()){
			case "disable":
			try{
				if(args[0] != null){
				}
			}catch(ArrayIndexOutOfBoundsException e){
				sender.sendMessage("Please enter plugin name.");
				return false;
			}

			try{
				this.getServer().getPluginManager().disablePlugin(this.getServer().getPluginManager().getPlugin(args[0]));
				sender.sendMessage("Disabling "+ args[0] +" ...");
			}catch(NullPointerException e){
				sender.sendMessage("Plugin name : '"+ args[0] + "' does not exists.");
				return false;
			}
			return true;
		}
		return false;
	}
}
