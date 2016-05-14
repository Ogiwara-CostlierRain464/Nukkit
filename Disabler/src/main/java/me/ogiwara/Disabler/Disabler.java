package me.ogiwara.Disabler;  
2 
 
3 import cn.nukkit.command.Command; 
4 import cn.nukkit.command.CommandSender; 
5 
 
6 import cn.nukkit.event.Listener;  
7 import cn.nukkit.plugin.PluginBase; 
8 import cn.nukkit.utils.PluginException; 
9 
 
10 public class Disabler extends PluginBase implements Listener{ 
11 	 
12 	public boolean onCommand(final CommandSender sender, Command command, String label, String[] args){ 
13 		switch(command.getName()){ 
14 			case "disable": 
15 			try{ 
16 				if(args[0] != null){ 
17 				} 
18 			}catch(ArrayIndexOutOfBoundsException e){ 
19 				sender.sendMessage("Please enter plugin name."); 
20 				return false; 
21 			} 
22 
 
23 			try{ 
24 				this.getServer().getPluginManager().disablePlugin(this.getServer().getPluginManager().getPlugin(args[0])); 
25 				sender.sendMessage("Disabling "+ args[0] +" ..."); 
26 			}catch(NullPointerException e){ 
27 				sender.sendMessage("Plugin name : '"+ args[0] + "' does not exists."); 
28 				return false; 
29 			} 
30 			return true; 
31 		} 
32 		return false; 
33 	} 
34 } 
