package net.trainerlord.discordserverintergration;

import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        //event.getPlayer().sendMessage("Event");
        if (DiscordServerIntergration.plugin.getConfig().getBoolean("Discord_IRC")) {
        TextChannel channel = DiscordServerIntergration.discordBot.getTextChannelById(DiscordServerIntergration.plugin.getConfig().getString("Discord_IRC_Channel_Id"));
        channel.sendMessage("[Minecraft]<" + event.getPlayer().getName() + "> " + event.getMessage()).queue();
    }}
}
