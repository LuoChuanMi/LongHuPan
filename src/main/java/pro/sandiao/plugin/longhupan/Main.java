package pro.sandiao.plugin.longhupan;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pro.sandiao.plugin.longhupan.game.AllGame;
import pro.sandiao.plugin.longhupan.game.LonghupanGame;
import pro.sandiao.plugin.longhupan.utils.Messages;

public class Main extends JavaPlugin {

    private static Main instance;
    private static BukkitAudiences bukkitAudiences;

    @Override
    public void onEnable() {
        instance = this;
        bukkitAudiences = BukkitAudiences.builder(this).build();

        AllGame.loadGameSettings();

        Messages.init();

        getCommand("dplan").setExecutor(new Commands());

        Bukkit.getPluginManager().registerEvents(new GameListener(), this);
    }

    @Override
    public void onDisable() {
        for (LonghupanGame game : AllGame.allLonghupanGame.values()) {
            game.closeGame(1);
        }
    }

    public static BukkitAudiences getBukkitAudiences() {
        return bukkitAudiences;
    }

    public static Main getInstance() {
        return instance;
    }
}
