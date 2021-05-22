package com.au2b2t.anarchyvanish;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UnVanishEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final AnarchyVanish plugin;
    private final Player player;

    public UnVanishEvent(AnarchyVanish plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public AnarchyVanish getPlugin() {
        return plugin;
    }

    public Player getPlayer() {
        return player;
    }

    public static HandlerList getHandlerList() { return handlers; }

    @Override
    public HandlerList getHandlers() { return handlers; }
}
