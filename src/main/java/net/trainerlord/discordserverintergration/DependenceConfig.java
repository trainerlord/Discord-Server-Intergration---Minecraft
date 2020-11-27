package net.trainerlord.discordserverintergration;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class DependenceConfig {
    private UUID uuid;

    public DependenceConfig() {
        setConfig();
    }

    @SuppressWarnings("deprecation")
    public DependenceConfig(String s) {
        OfflinePlayer op = null;
        try {
            op = Bukkit.getOfflinePlayer(UUID.fromString(s));
        } catch (Exception ignored) {
        }
        if (op == null) {
            op = Bukkit.getOfflinePlayer(s);
        }
        uuid = op.getUniqueId();
    }

    public DependenceConfig(UUID uuid) {
        this.uuid = uuid;
    }

    public String getPlayerAccount() {
        File dconfig = new File("plugins//Discord-Server-Intergration//dependence.yml");
        YamlConfiguration dcfg = YamlConfiguration.loadConfiguration(dconfig);
        return dcfg.getString(this.uuid.toString() + ".id");
    }

    private void setConfig() {
        File dconfig = new File("plugins//Discord-Server-Intergration//dependence.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(dconfig);
        try {
            cfg.save(dconfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void linkPlayerAccount(String accountId) {
        File dconfig = new File("plugins//Discord-Server-Intergration//dependence.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(dconfig);
        String uuid = this.uuid.toString();
        cfg.set("users." + uuid + ".id", accountId);
        try {
            cfg.save(dconfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startLinkingPlayerAccount(String Username, String code) {
        File dconfig = new File("plugins//Discord-Server-Intergration//dependence.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(dconfig);
        String uuid = this.uuid.toString();
        cfg.set("linker." + Username + ".link", true);
        cfg.set("linker." + Username + ".code", code);
        cfg.set("linker." + Username + ".uuid", this.uuid.toString());
        try {
            cfg.save(dconfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean isLinking(String Username) {
        File dconfig = new File("plugins//Discord-Server-Intergration//dependence.yml");
        YamlConfiguration dcfg = YamlConfiguration.loadConfiguration(dconfig);
        return dcfg.getBoolean("linker." + Username + ".link");
    }
    public static String getSecurityCode(String Username) {
        File dconfig = new File("plugins//Discord-Server-Intergration//dependence.yml");
        YamlConfiguration dcfg = YamlConfiguration.loadConfiguration(dconfig);
        return dcfg.getString("linker." + Username + ".code");
    }
    public static String linkedUUID(String Username) {
        File dconfig = new File("plugins//Discord-Server-Intergration//dependence.yml");
        YamlConfiguration dcfg = YamlConfiguration.loadConfiguration(dconfig);
        return dcfg.getString("linker." + Username + ".uuid");
    }



    public OfflinePlayer getOwner() {
        return Bukkit.getOfflinePlayer(uuid);
    }
}
