package pro.sandiao.plugin.longhupan.money;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class VaultMoney implements IMoney {

    private String name;
	private Economy eco;

    public VaultMoney(String name){
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null)
        eco = economyProvider.getProvider();

        this.name = name;
    }

    @Override
    public boolean canRegister() {
        return Bukkit.getPluginManager().getPlugin("Vault") != null;
    }

    @Override
    public String getNmae() {
        return name;
    }

    @Override
    public int getMoney(OfflinePlayer player) {
        return (int) eco.getBalance(player);
    }

    @Override
    public boolean giveMoney(OfflinePlayer player, int money) {
		EconomyResponse r = eco.depositPlayer(player, money);
		return r.transactionSuccess();
    }

    @Override
    public boolean takeMoney(OfflinePlayer player, int money) {
		EconomyResponse r = eco.withdrawPlayer(player, money);
		return r.transactionSuccess();
    }
    
}