package net.trainerlord.discordserverintergration;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Utils {
    public static void MergeRoles(String Acountid, DiscordServerIntergration plugin,Player playerMC) {
        if (plugin.getConfig().getBoolean("Synced_Roles")) {
            JDA bot = plugin.discordBot;
            Guild discordServer = bot.getGuildById(plugin.getConfig().getString("Discord_Guild_Id"));
            Member player = discordServer.getMemberById(Acountid);
            if (player == null) {
                System.out.println("Member Not Cached. Just Send a Message in Discord to cache your account");
                return;
            }
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                LuckPerms api = provider.getProvider();
                //User user = api.getPlayerAdapter(Player.class).getUser(playerMC);

                player.getRoles().forEach(role -> {
                    String lpGroup = plugin.getConfig().getString("Roles." + role.getName());
                    //System.out.println(role.getName());
                    Group group = api.getGroupManager().getGroup(lpGroup);

                    if (group == null) {
                        System.out.println("Group Doesn't Exist");
                        return;
                    }
                    InheritanceNode groupNode = InheritanceNode.builder(group).build();
                    api.getUserManager().modifyUser(playerMC.getUniqueId(), user -> {
                        // Add the permission
                        user.data().add(groupNode);
                    });
                    return;
                });
                System.out.println("Roles Merged");
                return;
            }
            System.out.println("Luck Perms Error");
            return;
        }
        System.out.println("Synced Roles Is Not Enabled");
        return;
    }
}
