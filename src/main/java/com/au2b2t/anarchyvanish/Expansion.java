package com.au2b2t.anarchyvanish;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Expansion extends PlaceholderExpansion {

    private final AnarchyVanish plugin;

    public Expansion(AnarchyVanish plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getAuthor() {
        return "RemainingToast";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "anarchyvanish";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (identifier.equals("online")) {
            return String.valueOf(plugin.getPlayerCount());
        }
        if (identifier.equals("vanished")) {
            return String.valueOf(plugin.getVanishedCount());
        }
        return null;
    }
}