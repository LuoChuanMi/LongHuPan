package pro.sandiao.plugin.longhupan.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pro.sandiao.plugin.longhupan.Main;
import pro.sandiao.plugin.longhupan.event.LonghupanEndEvent;
import pro.sandiao.plugin.longhupan.game.GameSettings;
import pro.sandiao.plugin.longhupan.game.LonghupanGame;

import java.io.File;
import java.util.List;

public class Messages {

    private static YamlConfiguration messages;
    private static String delimiter;
    private String message;

    /**
     * 初始化Messages 重新读取messages.yml
     */
    public static void init() {
        File messagesFile = new File(Main.getInstance().getDataFolder(), "messages.yml");
        if (!messagesFile.exists())
            Main.getInstance().saveResource("messages.yml", true);
        messages = YamlConfiguration.loadConfiguration(messagesFile);

        delimiter = ChatColor.translateAlternateColorCodes('&', messages.getString("PlayerNameDelimiter"));
    }

    /**
     * 获取配置文件节点对于的字符串
     *
     * @param node 配置文件节点
     * @return 消息实例
     */
    public static Messages get(String node) {
        return new Messages(ChatColor.translateAlternateColorCodes('&', messages.getString(node)));
    }

    public static Messages set(String msg) {
        return new Messages(ChatColor.translateAlternateColorCodes('&', msg));
    }

    private Messages() {
    }

    private Messages(String msg) {
        message = msg;
    }

    public Messages parse(OfflinePlayer player) {
        message = message.replace("%player%", player.getName());
        return this;
    }

    public Messages parse(LonghupanGame game) {
        message = message

                .replace("%owner%", game.getOwner().getName()).replace("%money%", Integer.toString(game.getMoney()))
                .replace("%playernum%", Integer.toString(game.getPlayerNumber()));

        parse(game.getGameSettings());
        parse("%players%", game.getAllPlayers());

        return this;
    }

    public Messages parse(GameSettings GameSettings) {
        message = message

                .replace("%currency%", GameSettings.getiMoney().getNmae())
                .replace("%minmoney%", Integer.toString(GameSettings.getMinMoney()))
                .replace("%maxmoney%", Integer.toString(GameSettings.getMaxMoney()))
                .replace("%minplayer%", Integer.toString(GameSettings.getMinPlayer()))
                .replace("%maxplayer%", Integer.toString(GameSettings.getMaxPlayer()))
                .replace("%losernum%", Integer.toString(GameSettings.getLoserNum()))
                .replace("%delay%", Integer.toString(GameSettings.getDelay()))
                .replace("%rate%", (GameSettings.getRate() * 100) + "%");

        return this;
    }

    public Messages parse(LonghupanEndEvent e) {
        String a = Integer.toString(e.getAllMoney());
        String b = Integer.toString(e.getAllMoney() / e.getWinners().size());
        parse("%allmoney%", a);
        parse("%newmoney%", b);
        parse(e.getOwner(), e.getGame());
        parse("%winners%", e.getWinners());
        parse("%losers%", e.getLosers());
        return this;
    }

    public Messages parse(OfflinePlayer player, GameSettings gameSettings) {
        parse(player).parse(gameSettings);
        return this;
    }

    public Messages parse(OfflinePlayer player, LonghupanGame game) {
        parse(player).parse(game);
        return this;
    }

    public Messages parse(String oldString, String newString) {
        message = message.replace(oldString, newString);
        return this;
    }

    public Messages parse(String oldString, List<OfflinePlayer> players) {
        if (message.contains(oldString)) {
            StringBuilder string = new StringBuilder();
            for (OfflinePlayer player : players) {
                string.append(delimiter);
                string.append(player.getName());
            }

            if (string.length() > 0) {
                string.delete(0, delimiter.length());
            }
            message = message.replace(oldString, string.toString());
        }
        return this;
    }

    public void sendRawMessage(List<OfflinePlayer> players) {
        for (OfflinePlayer player : players) {
            if (player.getPlayer() != null) {
                Component component = MiniMessage.miniMessage().deserialize(message);
                Main.getBukkitAudiences().player(player.getPlayer()).sendMessage(component);
            }
        }
    }

    public void sendMessage(List<OfflinePlayer> players) {
        if (!message.isEmpty())
            for (OfflinePlayer player : players) {
                if (player.getPlayer() != null) {
                    Component component = MiniMessage.miniMessage().deserialize(message);
                    Main.getBukkitAudiences().player(player.getPlayer()).sendMessage(component);
                }
            }
    }

    public void sendMessage(OfflinePlayer player) {
        if (!message.isEmpty())
            if (player.getPlayer() != null) {
                Component component = MiniMessage.miniMessage().deserialize(message);
                Main.getBukkitAudiences().player(player.getPlayer()).sendMessage(component);
            }
    }

    public void sendMessage(CommandSender sender) {
        if (!message.isEmpty())
            sender.sendMessage(message);
    }

    public void broadcastMessage() {
        if (!message.isEmpty())
            for (Player player : Bukkit.getOnlinePlayers()) {
                parse(player);
                player.sendMessage(message);
            }
    }

    public void dispatchCommand(ConsoleCommandSender consoleSender) {
        Bukkit.dispatchCommand(consoleSender, message);
    }
}