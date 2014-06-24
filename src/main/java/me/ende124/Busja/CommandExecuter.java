package me.ende124.Busja;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExecuter implements CommandExecutor {
	Main plugin;

	public CommandExecuter(Main instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = (Player) sender;

		if (label.equalsIgnoreCase("hey")) {
			player.sendMessage(ChatColor.GREEN + "Hey there!");
		}
		if (label.equalsIgnoreCase("randomtp")) {
			if (sender instanceof Player) {

				int randomX = (int) (Math.random() * 10000);
				int randomZ = (int) (Math.random() * 10000);
				int randomY = player.getWorld().getHighestBlockYAt(randomX, randomZ);

				Location teleport = new Location(player.getWorld(), randomX, randomY, randomZ);
				player.teleport(teleport);
				player.sendMessage(ChatColor.BLUE + "You got teleported to X: " + randomX + " Z: " + randomZ);
			}
		}
		if (label.equalsIgnoreCase("heal")) {
			if (sender instanceof Player) {
				player.setHealth(20);
				player.setFoodLevel(20);
				player.sendMessage(ChatColor.WHITE + "You have ben " + ChatColor.GREEN + "healed!");
			}
		}
		if (label.equalsIgnoreCase("setmotd")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.DARK_RED + "Usage: /setmotd <message>");
				return false;
			}
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
				str.append(args[i] + " ");
			}
			String motd = str.toString();
			plugin.getConfig().set("MOTD", motd);
			plugin.saveConfig();

			sender.sendMessage(ChatColor.GREEN + "MOTD set to: " + ChatColor.RESET + motd.replaceAll("(&([a-f0-9k-o]))", "\u00A7$2"));
		}
		if (label.equalsIgnoreCase("motd")) {
			sender.sendMessage(plugin.getConfig().getString("MOTD").replaceAll("(&([a-f0-9k-o]))", "\u00A7$2"));
		}
		if (label.equalsIgnoreCase("spawn")) {
			plugin.getConfig().set("spawn.world", player.getLocation().getWorld().getName());
			plugin.getConfig().set("spawn.x", player.getLocation().getX());
			plugin.getConfig().set("spawn.y", player.getLocation().getY());
			plugin.getConfig().set("spawn.z", player.getLocation().getZ());
			//plugin.getConfig().set("spawn.yaw", (double)player.getLocation().getYaw());
			//plugin.getConfig().set("spawn.pitch", (double)player.getLocation().getPitch());
			plugin.saveConfig();

			player.sendMessage(ChatColor.BLUE + "Spawnpoint set");
		}
		if (label.equalsIgnoreCase("setspawn")) {
			World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("spawn.world"));
			double x = plugin.getConfig().getDouble("spawn.x");
			double y = plugin.getConfig().getDouble("spawn.y");
			double z = plugin.getConfig().getDouble("spawn.z");
			//double yaw = plugin.getConfig().getDouble("spawn.x");
			//double pitch = plugin.getConfig().getDouble("spawn.x");

			Location teleport = new Location(w, x, y, z);
			player.teleport(teleport);
			player.sendMessage(ChatColor.BLUE + "You got teleported to spawn!");
		}
		return true;
	}
}