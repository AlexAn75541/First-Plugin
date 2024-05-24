package dev.aretzera.first;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class first extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Register the command
        getCommand("first").setExecutor(this);

        // Register the event listener
        getServer().getPluginManager().registerEvents(this, this);
    }

    private ItemStack createLegendarySword() {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = sword.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Legendary Sword");
        meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
        sword.setItemMeta(meta);
        return sword;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("first")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.getInventory().addItem(createLegendarySword());
                player.sendMessage(ChatColor.GREEN + "You received a Legendary Sword!");
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().addItem(createLegendarySword());
        player.sendMessage(ChatColor.GREEN + "Welcome to the server! You have received a Legendary Sword.");
    }
}