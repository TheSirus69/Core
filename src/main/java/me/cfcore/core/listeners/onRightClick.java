package me.cfcore.core.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class onRightClick implements Listener {
    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {
        Player p = event.getPlayer();

        if (event.getMaterial() == Material.BOOK){
            Inventory gui = Bukkit.getServer().createInventory(p, 9, "Help");

            ItemStack ref1 = new ItemStack(Material.BOOK);
            ItemMeta metaref1 = ref1.getItemMeta();
            ArrayList<String> lore = new ArrayList<>();

            lore.add("Line 1");
            lore.add("Line 2");
            metaref1.setLore(lore);
            ref1.setItemMeta(metaref1);
            gui.setItem(0, ref1);

            p.openInventory(gui);
        }
    }

    @EventHandler
    private void inventoryClick(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equalsIgnoreCase("Help")) {
            e.setCancelled(true);
            if ((e.getCurrentItem() == null) || (e.getCurrentItem().getType().equals(Material.AIR))) {
                return;
            }
        }
    }
}
