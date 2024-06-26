package me.cfcore.core.listeners;

import me.cfcore.core.Core;
import me.cfcore.core.FastBoard;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.io.File;
import java.util.Map;
import java.lang.System;

import static org.bukkit.Bukkit.getServer;

public class onPlayerJoin implements Listener {

    private final Map<UUID, FastBoard> boards = new HashMap<>();

    ItemStack bookStack = new ItemStack(Material.BOOK);
    @EventHandler
    public void onPlayerJoins(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        UUID playerId = player.getUniqueId();
        FileConfiguration playerData = Core.plugin.getPlayerData();
        String playerIdStr = playerId.toString();

        ItemMeta bookMeta = bookStack.getItemMeta();
        Objects.requireNonNull(bookMeta).addEnchant(Enchantment.DURABILITY, 0, true);
        bookMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        bookMeta.setDisplayName("Player Profile");
        bookStack.setItemMeta(bookMeta);

        FastBoard board = new FastBoard(player);

        board.updateTitle(ChatColor.DARK_RED + "Scoreboard");

        this.boards.put(player.getUniqueId(), board);


        player.getInventory().setItem(8, bookStack);


        System.out.println("Player UUID: " + playerId);

        // Check if player data does not already exist
        if (playerData.get(playerIdStr) == null) {
            System.out.println("Player data did not exist, creating.");

            // Set initial data for this player
            playerData.set(playerIdStr + ".level", 1);
            playerData.set(playerIdStr + ".exp", 0);
            playerData.set(playerIdStr + ".class", "Human");
            playerData.set(playerIdStr + ".reqexp", 10);
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

    @EventHandler
    public void onBookMove(InventoryClickEvent event){
        Player p = (Player) event.getWhoClicked();
        if (event.getSlot() == 8){
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onBookDrop(PlayerDropItemEvent event){
        Player p = event.getPlayer();

        if (event.getItemDrop().getItemStack().getType() == Material.BOOK){
            if (Objects.requireNonNull(event.getItemDrop().getItemStack().getItemMeta()).getDisplayName().equalsIgnoreCase("Player Profile")){
                event.setCancelled(true);
            }
        }
    }

    private void updateBoard(FastBoard board) {
        board.updateLines(
                "",
                "Players: " + getServer().getOnlinePlayers().size(),
                "",
                "Kills: " + board.getPlayer().getStatistic(Statistic.PLAYER_KILLS),
                ""
        );
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        FastBoard board = this.boards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }
}