package net.trainerlord.discordserverintergration;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class DiscordEvents extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event)
    {
       // System.out.println(event.getAuthor().getName());
        //event.getChannel().sendMessage(event.getAuthor().getName()).queue();
        Message message = event.getMessage();
        //event.getChannel().sendMessage("Not a Valid Command: " + message.getContentRaw().split(" ")[0] ).queue();

        switch (message.getContentRaw().split(" ")[0]) {
            case "!link":
                if (DependenceConfig.isLinking(message.getContentRaw().split(" ")[1])) {
                    if (DependenceConfig.getSecurityCode(message.getContentRaw().split(" ")[1]).equalsIgnoreCase(message.getContentRaw().split(" ")[2])) {
                        DependenceConfig dc = new DependenceConfig(DependenceConfig.linkedUUID(message.getContentRaw().split(" ")[1]));
                        dc.linkPlayerAccount(event.getAuthor().getId());
                        Player p = Bukkit.getPlayer(message.getContentRaw().split(" ")[1]);
                        event.getChannel().sendMessage("Account Linked").queue();
                        p.sendMessage("§8[§3Discord§8]§3 Account Linked");
                    } else {
                        event.getChannel().sendMessage("Incorrect Sercurity Code").queue(); }
                } else {
                    event.getChannel().sendMessage("No Account to Link").queue();
                }
                break;
            default:
        }
        if (DiscordServerIntergration.plugin.getConfig().getBoolean("Discord_IRC")) {
            if (!event.getAuthor().isBot()) {
                if (event.getChannel().getId().equals(DiscordServerIntergration.plugin.getConfig().getString("Discord_IRC_Channel_Id"))) {
                    Bukkit.broadcastMessage("[Discord]<" + event.getAuthor().getName() + "> " + event.getMessage().getContentRaw());
                }
            }
        }
    }



}
