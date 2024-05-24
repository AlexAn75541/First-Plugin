package dev.aretzera.first;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class first extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Register the event listener
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("First plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("First plugin has been disabled.");
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        ItemStack[] items = player.getInventory().getContents();

        // Format the current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);

        // Prepare the death log message
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("Player Death Log\n");
        logMessage.append("Timestamp: ").append(timestamp).append("\n");
        logMessage.append("Player: ").append(player.getName()).append("\n");
        logMessage.append("Items: \n");

        // Add the player's inventory items to the log message
        for (ItemStack item : items) {
            if (item != null) {
                logMessage.append("- ").append(item.getType().name()).append(" x ").append(item.getAmount()).append("\n");
            }
        }

        // Write the log message to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("death_log.txt", true))) {
            writer.write(logMessage.toString());
            writer.newLine();
            writer.flush();
            player.sendMessage(ChatColor.GREEN + "Your death has been logged.");
        } catch (IOException e) {
            player.sendMessage(ChatColor.RED + "Failed to log your death. Please contact the server administrator.");
            getLogger().severe("Error writing to death_log.txt: " + e.getMessage());
        }
    }
}