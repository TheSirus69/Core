package me.cfcore.core;

import me.cfcore.core.commands.level;
import me.cfcore.core.listeners.onPlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Core extends JavaPlugin {
    private FileConfiguration playerData;
    public static Core plugin;

    @Override
    public void onEnable() {
        plugin = this;
        createPlayerData();
        getServer().getPluginManager().registerEvents(new onPlayerJoin(), this);
        Objects.requireNonNull(getCommand("level")).setExecutor(new level());
    }

    private void createPlayerData() {
        File playerDataFile = new File(getDataFolder(), "playerData.yml");

        if (!playerDataFile.exists()) {
            System.out.println("DataFile Created");
            playerDataFile.getParentFile().mkdirs();
            saveResource("playerData.yml", false);
        }

        playerData = new YamlConfiguration();
        try {
            playerData.load(playerDataFile);
        } catch (IOException | InvalidConfigurationException e) {
            System.out.println("Error Code 103:" + e);
        }
    }

    public FileConfiguration getPlayerData() {
        return this.playerData;
    }

    public void savePlayerData(UUID playerId, int level, int exp, String playerClass) {
        playerData.set(playerId+".level", level);
        playerData.set(playerId+".exp", exp);
        playerData.set(playerId+".class", playerClass);

        File playerDataFile = new File(getDataFolder(), "playerData.yml");
        try {
            playerData.save(playerDataFile);
        } catch (IOException e) {
            System.out.println("Error Code 102: " + e);
        }
    }

    public Map<String, Object> loadPlayerData(UUID playerId) {
        int level = playerData.getInt(playerId+".level", 0);
        int exp = playerData.getInt(playerId+".exp", 0);
        String playerClass = playerData.getString(playerId+".class");

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("level", level);
        dataMap.put("exp", exp);
        dataMap.put("class", playerClass);

        return dataMap;
    }

    @Override
    public void onDisable() {
        // Save data for all online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            savePlayerData(player);
            player.sendMessage("Data Saved");
        }
        System.out.println("Data Saved for all players");
    }

    private void savePlayerData(Player player) {
        UUID playerId = player.getUniqueId();
        int level = playerData.getInt(playerId+".level", 0);
        int exp = playerData.getInt(playerId+".exp", 0);
        String playerClass = playerData.getString(playerId+".class");

        // Call your existing method to save player data
        savePlayerData(playerId, level, exp, playerClass);
    }

}