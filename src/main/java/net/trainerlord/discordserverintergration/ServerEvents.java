package net.trainerlord.discordserverintergration;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static net.trainerlord.discordserverintergration.Utils.MergeRoles;

public class ServerEvents implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args[0]) {
            case "link":
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    DependenceConfig dc = new DependenceConfig(p.getUniqueId());
                    int code = securityCode();
                    dc.startLinkingPlayerAccount(p.getName().toString(), "" + code);
                    p.sendMessage("§8[§3Discord§8]§3 Please Send me a Message stating '§6!link <Minecraft Username> " + code + "§3'");
                    return true;
                }
                return false;
            case "roles":
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (DiscordServerIntergration.plugin.getConfig().getBoolean("Synced_Roles")) {
                        DependenceConfig dc = new DependenceConfig(p.getUniqueId());
                        if (dc.getPlayerAccount() == null) {
                            p.sendMessage("§8[§3Discord§8]§3 Discord Account Not Linked");
                            return true;
                        }
                        p.sendMessage("§8[§3Discord§8]§3 Discord Roles Have Been Synced");
                        Utils.MergeRoles(dc.getPlayerAccount(),DiscordServerIntergration.plugin, p);
                    } else {
                        p.sendMessage("§8[§3Discord§8]§3 Syncing roles has not been enabled on this Server");
                    }
                    return true;
                }
                return false;
            case "help":
                sender.sendMessage("§8[§3Discord§8]§3 /discord help  : Lists All Commands");
                sender.sendMessage("§8[§3Discord§8]§3 /discord link  : Links your Discord Account to your minecraft Account");
                sender.sendMessage("§8[§3Discord§8]§3 /discord roles : Updates your role with you discord ones");

        }
        sender.sendMessage("§8[§3Discord§8]§3 Not A Valid Command Try /discord help");
        return true;
    }


    public int securityCode() {
        Random rand = new Random();
        return rand.nextInt((9999 - 100) + 1) + 10;
    }
}
