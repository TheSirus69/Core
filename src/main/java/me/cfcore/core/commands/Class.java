package me.cfcore.core.commands;

import me.cfcore.core.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class Class implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player p){
            if (command.getName().equalsIgnoreCase("class")){
                UUID playerId = p.getUniqueId();
                FileConfiguration playerData = Core.plugin.getPlayerData();
                String playerIdStr = playerId.toString();
                Core pluginInstance = Core.plugin;

                if (pluginInstance != null) {
                    Map<String, Object> Data = pluginInstance.loadPlayerData(playerId);
                    int level = (int) Data.get("level");
                    int exp = (int) Data.get("exp");
                    int reqexp = (int) Data.get("reqexp");

                    if (args.length == 0){
                        p.sendMessage("Input a string!");
                    } else if (args.length == 1) {
                        String classCommand = args[0];
                        playerData.set(playerIdStr + ".class", classCommand);
                        p.sendMessage("class set to " + classCommand);
                        Core.plugin.savePlayerData(playerId, level, exp, classCommand, reqexp);
                    }else{
                        StringBuilder builder = new StringBuilder();

                        for (int i = 0; i < args.length; i++) {
                            builder.append(args[i]);
                        }
                        String finalClass = builder.toString();
                        playerData.set(playerIdStr + ".class", finalClass);
                        p.sendMessage("class set to " + finalClass);
                        Core.plugin.savePlayerData(playerId, level, exp, finalClass, reqexp);

                    }
                } else {
                    System.out.println("Plugin instance == null");
                    p.sendMessage("Plugin instance is null");
                }

            }

        }
        return true;
    }
}
