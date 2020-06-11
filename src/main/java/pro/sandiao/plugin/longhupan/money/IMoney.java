package pro.sandiao.plugin.longhupan.money;

import org.bukkit.OfflinePlayer;

public interface IMoney {
    abstract public String getNmae();
    abstract public boolean canRegister();

    abstract public int getMoney(OfflinePlayer player);
    abstract public boolean giveMoney(OfflinePlayer player, int money);
    abstract public boolean takeMoney(OfflinePlayer player, int money);
}