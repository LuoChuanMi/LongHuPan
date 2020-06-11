package pro.sandiao.plugin.longhupan.money;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

public class PointMoney implements IMoney {

    private String name;
    private PlayerPoints playerPoints;

    public PointMoney(String name){
        this.name = name;
    }

    @Override
    public boolean canRegister() {
        Plugin pp = Bukkit.getPluginManager().getPlugin("PlayerPoints");
        if (pp == null)
        return false;
        playerPoints = (PlayerPoints) pp;
        return true;
    }

    @Override
    public String getNmae() {
        return name;
    }

    @Override
    public int getMoney(OfflinePlayer player) {
        return playerPoints.getAPI().look(player.getUniqueId());
    }

    @Override
    public boolean giveMoney(OfflinePlayer player, int money) {
        return playerPoints.getAPI().give(player.getUniqueId(), money);
    }

    @Override
    public boolean takeMoney(OfflinePlayer player, int money) {
        return playerPoints.getAPI().take(player.getUniqueId(), money);
    }
}