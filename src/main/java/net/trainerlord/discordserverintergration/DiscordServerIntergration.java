package net.trainerlord.discordserverintergration;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.io.File;

public class DiscordServerIntergration extends JavaPlugin {

    public static JDA discordBot;
    public static DiscordServerIntergration plugin;
    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        if (plugin == null) {
            plugin = this;
        }
        String DiscordToken = getConfig().getString("Discord_api_token");
         getServer().getPluginManager().registerEvents(new PlayerEvents(), this);//(new PlayerEvents(), this);
        if(DiscordToken == null) {
            getServer().getPluginManager().disablePlugin(this);
            getLogger().severe("No Discord_api_token the the config file. Please remedy this.");
            return;
        }

        try {
            this.discordBot = JDABuilder.createDefault(DiscordToken).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        discordBot.addEventListener(new DiscordEvents());
        getCommand("discord").setExecutor(new ServerEvents());
    }

//Bukkit.broadcastMessage("It works too, but for console & players!");
    @Override
    public void onDisable() {
        discordBot.shutdown();
    }


}
