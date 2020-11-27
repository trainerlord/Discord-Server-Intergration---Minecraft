package net.trainerlord.discordserverintergration;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Utils {
    public static String MergeRoles(String Acountid, DiscordServerIntergration plugin,Player playerMC) {
        if (plugin.getConfig().getBoolean("Synced_Roles")) {
            JDA bot = plugin.discordBot;
            Guild discordServer = bot.getGuildById(plugin.getConfig().getString("Discord_Guild_Id"));
            Member player = discordServer.getMemberById(Acountid);
            if (player == null) {
                return "Member Not Cached. Just Send a Message in Discord to cache your account";
            }
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                LuckPerms api = provider.getProvider();
                User user = api.getPlayerAdapter(Player.class).getUser(playerMC);

                player.getRoles().forEach(role -> {
                    String lpGroup = plugin.getConfig().getString("roles." + role.getName());
                    user.data().add(Node.builder("group." + lpGroup).build());

                });
                return "Roles Merged";
            }
            return "Luck Perms Error";
        }
        return "Synced Roles Is Not Enabled";
    }
}
