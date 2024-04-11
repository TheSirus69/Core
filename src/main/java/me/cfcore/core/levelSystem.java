package me.cfcore.core;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class levelSystem {

    public void levelUp (Player p){
        FileConfiguration playerData = Core.plugin.getPlayerData();
        Core pluginInstance = Core.plugin;
        UUID playerId = p.getUniqueId();

        if (pluginInstance != null)
        {
            Map<String, Object> Data = pluginInstance.loadPlayerData(playerId);
            int level = (int) Data.get("level");
            int exp = (int) Data.get("exp");
            int reqexp = (int) Data.get("reqexp");
            String playerClass = (String) Data.get("playerClass");

            if (exp >= reqexp) {
                exp = 0;
                reqexp *= 1.2;
                reqexp = (int) Math.floor(reqexp);
                level += 1;
                System.out.println("Levelup Successful");
            } else {
                System.out.println("Levelup Unsuccessful");
            }
            Core.plugin.savePlayerData(playerId, level, exp, playerClass, reqexp);
        }
    }
}
