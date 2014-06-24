package me.ende124.Busja;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	private static Plugin instance;
	public static Plugin getInstance() { return instance; }

	@Override
	public void onEnable() {
		getLogger().info("Busja is activated");

		final FileConfiguration config = this.getConfig();
		config.addDefault("MOTD", "Welcome to my server!");
		config.options().copyDefaults(true);
		saveConfig();

		instance = this;
		getCommand("hey").setExecutor(new CommandExecuter(this));
		getCommand("randomtp").setExecutor(new CommandExecuter(this));
		getCommand("heal").setExecutor(new CommandExecuter(this));
		getCommand("spawn").setExecutor(new CommandExecuter(this));
		getCommand("setspawn").setExecutor(new CommandExecuter(this));
		getCommand("motd").setExecutor(new CommandExecuter(this));
		getCommand("setmotd").setExecutor(new CommandExecuter(this));

		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {
		getLogger().info("Busja is now offline");
	}

	@EventHandler
	public void onPlayerJoin (PlayerJoinEvent e){
		Player player = e.getPlayer();
		player.sendMessage(getConfig().getString("MOTD").replaceAll("(&([a-f0-9k-o]))", "\u00A7$2"));
	}
}