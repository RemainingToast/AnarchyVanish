package com.au2b2t.anarchyvanish;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {

    private final AnarchyVanish plugin;

    public VanishCommand(AnarchyVanish plugin) {
        this.plugin = plugin;
        plugin.loadVanished();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

        if (sender instanceof Player && sender.isOp()) {
            Player player = (Player) sender;

            if (plugin.isVanished(player)) {
                plugin.unVanishPlayer(player);
                message(player, "&6You are now unvanished!");
            } else {
                plugin.vanishPlayer(player);
                message(player, "&6You are now vanished!");
            }

        } else {
            message(sender, "&cYou need to be a player to do that!");
        }

        return false;
    }

    private void message(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
