package pro.sandiao.plugin.longhupan.game;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import pro.sandiao.plugin.longhupan.Main;
import pro.sandiao.plugin.longhupan.utils.EventCommand;
import pro.sandiao.plugin.longhupan.utils.Messages;
import pro.sandiao.plugin.longhupan.money.IMoney;
import pro.sandiao.plugin.longhupan.money.PointMoney;
import pro.sandiao.plugin.longhupan.money.VaultMoney;

public class AllGame {
    
    public static Map<String, GameSettings> allGameSettings = new HashMap<>();
    public static Map<OfflinePlayer, LonghupanGame> allLonghupanGame = new HashMap<>();

    /**
     * 创建一个龙虎盘游戏
     * @param player 玩家
     * @param gameSettings 游戏设定
     * @param money 金额
     * @return 龙虎盘游戏实例
     */
    public static LonghupanGame createGame(OfflinePlayer player, GameSettings gameSettings, int money){
        if (allLonghupanGame.containsKey(player)) {
            Messages.get("CreateGameContains").parse(player, gameSettings).parse("%money%", Integer.toString(money)).sendMessage(player);
            return null;
        }

        if (money < gameSettings.getMinMoney()) {
            Messages.get("CreateGameMinMoney").parse(player, gameSettings).parse("%money%", Integer.toString(money)).sendMessage(player);
            return null;
        }

        if (money > gameSettings.getMaxMoney()) {
            Messages.get("CreateGameMaxMoney").parse(player, gameSettings).parse("%money%", Integer.toString(money)).sendMessage(player);
            return null;
        }

        if (gameSettings.getiMoney().getMoney(player) < money) {
            Messages.get("CreateGameNoMoney").parse(player, gameSettings).parse("%money%", Integer.toString(money)).sendMessage(player);
            return null;
        }

        if (!gameSettings.getiMoney().takeMoney(player, money)) {
            Messages.get("CreateGameMoneyError").parse(player, gameSettings).parse("%money%", Integer.toString(money)).sendMessage(player);
            return null;
        }

        return allLonghupanGame.put(player, new LonghupanGame(player, gameSettings, money));
    }

    /**
     * 加一个龙虎盘游戏 
     * @param owner 所有者
     * @param player 玩家
     * @return 成功或失败
     */
    public static boolean joinGame(OfflinePlayer owner, OfflinePlayer player){
        if (!allLonghupanGame.containsKey(owner)) {
            Messages.get("JoinGameNoContains").parse(player).parse("%owner%", owner.getName()).sendMessage(player);
            return false;
        }

        LonghupanGame game = allLonghupanGame.get(owner);

        if (game.getGameSettings().getMaxPlayer() == game.getPlayerNumber()) {
            Messages.get("JoinGameMaxPlayer").parse(player, game).sendMessage(player);
            return false;
        }

        if (game.getAllPlayers().contains(player)) {
            Messages.get("JoinGameContains").parse(player, game).sendMessage(player);
            return false;
        }

        if (game.getiMoney().getMoney(player) < game.getMoney()) {
            Messages.get("JoinGameNoMoney").parse(player, game).sendMessage(player);
            return false;
        }

        if (!game.getiMoney().takeMoney(player, game.getMoney())) {
            Messages.get("JoinGameMoneyError").parse(player, game).sendMessage(player);
            return false;
        }

        return game.addPlayer(player);
    }

    public static void loadGameSettings(){
        File settingsFile = new File(Main.getInstance().getDataFolder(), "settings.yml");
        if (!settingsFile.exists()) Main.getInstance().saveResource("settings.yml", true);
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        AllGame.addGameSettings(settings);
    }

    /**
     * 添加游戏设置 
     * @param settings 配置文件
     */
    public static void addGameSettings(YamlConfiguration settings){
        for (String node : settings.getKeys(false)){
            if (node.equalsIgnoreCase("money")){
                IMoney iMoney = new VaultMoney(settings.getString(node + ".currency"));
                if (iMoney.canRegister()) addGameSetting(node, iMoney, settings);
            }
            else if (node.equalsIgnoreCase("point")){
                IMoney iMoney = new PointMoney(settings.getString(node + ".currency"));
                if (iMoney.canRegister()) addGameSetting(node, iMoney, settings);
            }
        }
    }

    public static void addGameSetting(String name, IMoney iMoney , YamlConfiguration settings){
        GameSettings gameSettings = new GameSettings(
            iMoney, 
            settings.getInt(name + ".min-money", 100), 
            settings.getInt(name + ".max-money", 10000000), 
            settings.getInt(name + ".min-player", 2), 
            settings.getInt(name + ".max-player", 40), 
            settings.getInt(name + ".loser", 1), 
            settings.getDouble(name + ".rate", 1), 
            settings.getInt(name + ".delay", 1),
            settings.getStringList(name + ".cdmsg"),
            new EventCommand(name, settings)
        );

        allGameSettings.put(name, gameSettings);
    }

    /**
     * 获取一个游戏设置 如果找不到会返回null
     * @param name 游戏设置名字
     * @return 游戏设置
     */
    public static GameSettings getGmaeSettings(String name){
        return allGameSettings.get(name);
    }

    /**
     * 获取一个龙虎盘游戏 如果找不到会返回null
     * @param owner 所有者
     * @return 龙虎盘实例
     */
    public static LonghupanGame getLonghupanGame(OfflinePlayer owner) {
        return allLonghupanGame.get(owner);
    }
}