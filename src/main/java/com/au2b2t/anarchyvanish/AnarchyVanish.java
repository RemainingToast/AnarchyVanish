package com.au2b2t.anarchyvanish;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

public final class AnarchyVanish extends JavaPlugin {

    private HashMap<UUID, Boolean> vanished = new HashMap<>();

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final Path path = Paths.get(getDataFolder().getAbsoluteFile() + "/vanished.json");

    @Override
    public void onEnable() {
        if(!Files.exists(path)){
            saveVanished();
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Expansion(this).register();
        }

        getCommand("vanish").setExecutor(new VanishCommand(this));

        loadVanished();
    }

    @Override
    public void onDisable() {
        saveVanished();
    }

    public void vanishPlayer(Player player) {
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target != player) {
                target.hidePlayer(this, player);
            }
        }

        VanishEvent event = new VanishEvent(this, player);
        getServer().getPluginManager().callEvent(event);

        vanished.put(player.getUniqueId(), true);

        saveVanished();
    }

    public void unVanishPlayer(Player player) {
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target != player) {
                target.showPlayer(this, player);
            }
        }

        UnVanishEvent event = new UnVanishEvent(this, player);
        getServer().getPluginManager().callEvent(event);

        vanished.put(player.getUniqueId(), false);

        saveVanished();
    }

    public boolean isVanished(Player player) {
        loadVanished(); return vanished.getOrDefault(player.getUniqueId(), false);
    }

    public void saveVanished() {
        try {
            String json = gson.toJson(vanished);
            Files.write(path, json.getBytes());
        } catch (IOException ignored) { }
    }

    public void loadVanished() {
        try {
            vanished.clear();
            Reader reader = Files.newBufferedReader(path);
            Type type = new TypeToken<HashMap<UUID, Boolean>>(){}.getType();
            vanished = gson.fromJson(reader, type);
        } catch (IOException ignored) { }
    }

    public int getPlayerCount() {
        int i = 0;
        for(Player online : Bukkit.getOnlinePlayers()) {
            if(!isVanished(online)) {
                i++;
            }
        }
        return i;
    }

    public int getVanishedCount() {
        int i = 0;
        for(Player online : Bukkit.getOnlinePlayers()) {
            if(isVanished(online)) {
                i++;
            }
        }
        return i;
    }
}
