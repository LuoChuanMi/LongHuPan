package pro.sandiao.plugin.longhupan.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import pro.sandiao.plugin.longhupan.Main;
import pro.sandiao.plugin.longhupan.utils.Messages;
import pro.sandiao.plugin.longhupan.event.LonghupanCountdownEvent;
import pro.sandiao.plugin.longhupan.event.LonghupanEndEvent;
import pro.sandiao.plugin.longhupan.event.LonghupanJoinEvent;
import pro.sandiao.plugin.longhupan.event.LonghupanStartEvent;
import pro.sandiao.plugin.longhupan.money.IMoney;

public class LonghupanGame {

    private GameSettings gameSettings;
    private OfflinePlayer owner;
    private List<OfflinePlayer> players = new ArrayList<>();
    private int money;
    private BukkitTask task;

    public LonghupanGame(OfflinePlayer player, GameSettings gameSettings, int money){
        this.owner = player;
        this.gameSettings = gameSettings;
        this.money = money;

        players.add(player);
        
        Bukkit.getPluginManager().callEvent(new LonghupanStartEvent(player, this));

        timer();
    }

    private void timer(){
        task = new BukkitRunnable(){
            int time = gameSettings.getDelay();
			@Override   
			public void run() {
                if (--time > 0){
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            Bukkit.getPluginManager().callEvent(new LonghupanCountdownEvent(LonghupanGame.this, time));
                        }
                    }.runTask(Main.getInstance());
                }else{
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            Bukkit.getPluginManager().callEvent(new LonghupanCountdownEvent(LonghupanGame.this, 0));
                            settlement();
                        }
                    }.runTask(Main.getInstance());
                    this.cancel();
                }
            }	
		}.runTaskTimerAsynchronously(Main.getInstance(), 0L, 20L);
    }

    public void settlement(){
        if (players.size() < gameSettings.getMinPlayer()){
            Messages.get("EndGamePeopleLess").parse(this).sendMessage(players);
            closeGame(501);
            return;
        }

        closeGame(0);
    }

    /**
     * 关闭游戏
     * 0代表正常结束 会计算获得对应奖励
     * 1代表强制结束 会直接返还所有金额
     * 501代表结算时人数不足
     * 暂未添加其它 如果传入其它 游戏结束货币丢失
     * @param closeCode 关闭码
     */
    public void closeGame(int closeCode){
        if (task != null){
            task.cancel();
        }

        int allMoney = money * players.size();
        List<OfflinePlayer> losers = new ArrayList<>();
        List<OfflinePlayer> winners = new ArrayList<>(players);;

        switch (closeCode) {
            case 0:
                allMoney = (int) (money * players.size() * (1 - gameSettings.getRate()));
                for (int i = 0; i < gameSettings.getLoserNum(); i++){
                    int n = new Random().nextInt(winners.size());
                    losers.add(winners.remove(n));
                }

                int reward = allMoney / winners.size();
                for(OfflinePlayer winner : winners){
                    getiMoney().giveMoney(winner, reward);
                }
                break;
            case 1:
            case 501:
                for(OfflinePlayer player : players){
                    getiMoney().giveMoney(player, money);
                }
                break;
            default:
                break;
        }

        Bukkit.getPluginManager().callEvent(new LonghupanEndEvent(owner, this, closeCode, allMoney, winners, losers));
        AllGame.allLonghupanGame.remove(owner);
        
    }
    
    public boolean delPlayer(OfflinePlayer player){
        return players.remove(player);
    }

    public boolean addPlayer(OfflinePlayer player){
        if (players.add(player)){
            Bukkit.getPluginManager().callEvent(new LonghupanJoinEvent(player, this));
            return true;
        }
        return false;
    }

    public OfflinePlayer getOwner() {
        return owner;
    }

    public int getPlayerNumber(){
        return players.size();
    }

    public List<OfflinePlayer> getAllPlayers() {
        return players;
    }

    public int getMoney() {
        return money;
    }

    public IMoney getiMoney() {
        return gameSettings.getiMoney();
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }
}