package me.cfcore.core.commands;

import me.cfcore.core.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class level implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player p){
            if (command.getName().equalsIgnoreCase("level")){
                UUID playerId = p.getUniqueId();
                FileConfiguration playerData = Core.plugin.getPlayerData();
                String playerIdStr = playerId.toString();
                Core pluginInstance = Core.plugin;

                if (pluginInstance != null) {
                    Map<String, Object> Data = pluginInstance.loadPlayerData(playerId);
                    int exp = (int) Data.get("exp");
                    int reqexp = (int) Data.get("reqexp");
                    String playerClass = (String) Data.get("playerClass");

                    if (args.length == 0){
                        p.sendMessage("Input a number!");
                    } else if (args.length == 1) {
                        int levelCommand = Integer.parseInt(args[0]);
                        playerData.set(playerIdStr + ".exp", levelCommand);
                        p.sendMessage("Level set to " + levelCommand);
                        Core.plugin.savePlayerData(playerId, levelCommand, exp, playerClass, reqexp);
                    }else{
                        StringBuilder builder = new StringBuilder();

                        for (int i = 0; i < args.length; i++) {
                            builder.append(args[i]);
                        }
                        int finalLevel = Integer.parseInt(builder.toString());
                        playerData.set(playerIdStr + ".level", finalLevel);
                        p.sendMessage("Level set to " + finalLevel);
                        Core.plugin.savePlayerData(playerId, finalLevel, exp, playerClass, reqexp);

                    }
                }

            }

        }
        return true;
    }
}
