package eu.slocraft.StoreNotifier;

import java.io.IOException;
import java.net.http.HttpClient;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getLogger;

public final class Main extends JavaPlugin{

    @Override
    public void onEnable() {
        loadConfig();
        // TODO Insert logic to be performed when the plugin is enabled
        this.getCommand("dispost").setExecutor(this);

        getLogger().info(getConfig().getString("apikey_url"));

    }

    private void loadConfig() {
        //Only create the default config if it doesn't exist
        saveDefaultConfig();

        //Append new key-value pairs to the config
        getConfig().options().copyDefaults(true);

        saveConfig();
    }

    @Override
    public void onDisable() {
        // TODO Insert logic to be performed when the plugin is disabled
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("dispost")) {
            int len = args.length;
            StringBuilder message = new StringBuilder();

            String color = args[0];

            for(int i = 1; i < len; i++){
                message.append(args[i]);
                message.append(" ");
            }

            //getLogger().info(color);
            //getLogger().info(message.toString());

            try {
                HttpHandler.main(color, message.toString(), this);
            } catch (IOException | InterruptedException e) {
                getLogger().severe("Failed to send post request");
            }

            return true;
        }
        return false;
    }

}


