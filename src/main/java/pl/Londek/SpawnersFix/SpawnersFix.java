package pl.Londek.SpawnersFix;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class SpawnersFix extends JavaPlugin implements Listener, CommandExecutor {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        this.getLogger().info("SpawnersFix by LondekPolska has been enabled");
    }

    public void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.BLUE +"----- SpawnersFix -----");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.YELLOW + "/sf status - "+ ChatColor.GREEN +"zeby zobaczyc status spawneru na ktory sie patrzysz");
        sender.sendMessage(ChatColor.YELLOW + "/sf fix - " + ChatColor.GREEN + "zeby naprawic spawner na ktory sie patrzysz");
        sender.sendMessage(ChatColor.YELLOW + "/sf restore - " + ChatColor.GREEN + "zeby przywrocic domyslne wartosci Spigota dla spawneru na ktory sie patrzysz");
        sender.sendMessage(ChatColor.YELLOW + "/sf help - " + ChatColor.GREEN + "zeby wyswietlic to okno");
        sender.sendMessage("");
        sender.sendMessage(ChatColor.BLUE +"------ By Londek ------");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("spawnersfix")) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("This command isn't supported for nonplayer executors");
                return true;
            }
            Player executor = (Player) sender;

            if(args.length<1) {
                sendHelp(sender);
                return true;
            }
            String task = args[0];

            if(task.equalsIgnoreCase("status")) {
                BlockState lookingAt = executor.getTargetBlock(null, 6).getState();
                if(!(lookingAt instanceof CreatureSpawner)) {
                    sender.sendMessage(ChatColor.RED+"Nie patrzysz sie na spawner lub jestes zbyt daleko");
                    return true;
                }

                CreatureSpawner spawner = (CreatureSpawner) lookingAt;
                if(spawner.getRequiredPlayerRange() == 50 && spawner.getMaxNearbyEntities() == 20) {
                    sender.sendMessage(ChatColor.GREEN+"Spawner jest juz naprawiony");
                } else {
                    sender.sendMessage(ChatColor.RED+"Spawner jeszcze nie zostal naprawiony");
                    sender.sendMessage(ChatColor.RED+"Wpisz "+ ChatColor.YELLOW +"/sf fix"+ ChatColor.RED +" aby go naprawic");
                }
            } else if(task.equalsIgnoreCase("fix")) {
                BlockState lookingAt = executor.getTargetBlock(null, 6).getState();
                if(!(lookingAt instanceof CreatureSpawner)) {
                    sender.sendMessage(ChatColor.RED+"Nie patrzysz sie na spawner lub jestes zbyt daleko");
                    return true;
                }

                CreatureSpawner spawner = (CreatureSpawner) lookingAt;
                spawner.setRequiredPlayerRange(50);
                spawner.setMaxNearbyEntities(20);
                if(spawner.update()) {
                    sender.sendMessage(ChatColor.GREEN+"Sukces! Naprawiono spawner :)");
                } else {
                    sender.sendMessage(ChatColor.RED+"Nie naprawiono spawnera ;/");
                }
            } else {
                sendHelp(sender);
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (command.getName().equalsIgnoreCase("spawnersfix")) {
            return Arrays.asList("status", "fix", "restore", "help");
        }

        return null;
    }
}
