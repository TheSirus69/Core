package me.cfcore.core.commands;

import me.cfcore.core.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class exp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player p){
            if (command.getName().equalsIgnoreCase("exp")){
                UUID playerId = p.getUniqueId();
                FileConfiguration playerData = Core.plugin.getPlayerData();
                String playerIdStr = playerId.toString();
                Core pluginInstance = Core.plugin;

                if (pluginInstance != null) {
                    Map<String, Object> Data = pluginInstance.loadPlayerData(playerId);
                    int level = (int) Data.get("level");
                    String playerClass = (String) Data.get("playerClass");

                    if (args.length == 0){
                        p.sendMessage("Input a number!");
                    } else if (args.length == 1) {
                        int expCommand = Integer.parseInt(args[0]);
                        playerData.set(playerIdStr + ".exp", expCommand);
                        p.sendMessage("Exp set to " + expCommand);
                        Core.plugin.savePlayerData(playerId, level, expCommand, playerClass);
                    }else{
                        StringBuilder builder = new StringBuilder();

                        for (int i = 0; i < args.length; i++) {
                            builder.append(args[i]);
                        }
                        int finalExp = Integer.parseInt(builder.toString());
                        playerData.set(playerIdStr + ".exp", finalExp);
                        p.sendMessage("Exp set to " + finalExp);
                        Core.plugin.savePlayerData(playerId, level, finalExp, playerClass);

                    }
                }
// Test Commit Text Message
            }

        }
        return true;
    }
}
