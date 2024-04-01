package me.cfcore.core.listeners;

import me.cfcore.core.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.UUID;
import java.io.File;
import java.util.Map;
import java.lang.System;

public class onPlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoins(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        UUID playerId = player.getUniqueId();
        FileConfiguration playerData = Core.plugin.getPlayerData();
        String playerIdStr = playerId.toString();


        System.out.println("Player UUID: " + playerId);

        // Check if player data does not already exist
        if (playerData.get(playerIdStr) == null) {
            System.out.println("Player data did not exist, creating.");

            // Set initial data for this player
            playerData.set(playerIdStr + ".level", 1);
            playerData.set(playerIdStr + ".exp", 0);
            playerData.set(playerIdStr + ".class", "Human");

            File playerDataFile = new File(Core.plugin.getDataFolder(), "playerData.yml");

            try {
                Object playerDataObject = Core.plugin.loadPlayerData(playerId);

                Map<String, Object> playerDataMap = null;
                if (playerDataObject instanceof Map) {
                    playerDataMap = (Map<String, Object>) playerDataObject;
                } else {
                    System.out.println("Unexpected data type returned for player data.");
                }

                System.out.println("Generating");

                if (playerDataMap.isEmpty()) {
                    System.out.println("DataMap is empty");
                }

                playerData.save(playerDataFile);
            } catch (Exception e) {
                System.out.println("Error Code 101: " + e);
            }
        }
    }
}