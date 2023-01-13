package pro.sandiao.plugin.longhupan;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import pro.sandiao.plugin.longhupan.game.AllGame;
import pro.sandiao.plugin.longhupan.game.GameSettings;
import pro.sandiao.plugin.longhupan.utils.Messages;

public class Commands implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("dplan.use")) {
            Messages.get("NotHavePermissions").parse("%player%", sender.getName()).sendMessage(sender);
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")){
            if (sender.hasPermission("dplan.reload")){
                AllGame.loadGameSettings();
                Messages.init();
                Messages.get("ReloadComplete").parse("%player%", sender.getName()).sendMessage(sender);
                return true;
            }else{
                Messages.get("NotHavePermissions").parse("%player%", sender.getName()).sendMessage(sender);
                return true;
            }
        }

        if (!(sender instanceof Player)){
            Messages.get("NotPlayer").parse("%player%", sender.getName()).sendMessage(sender);
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 2 && args[0].equalsIgnoreCase("join")){
            Player owner = Bukkit.getPlayerExact(args[1]);
            if (owner != null){
                AllGame.joinGame(owner, player);
                return true;
            }else{
                Messages.get("NoPlayerFound").parse("%player%", args[1]).sendMessage(sender);
                return true;
            }
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("create")){
            GameSettings gameSettings = AllGame.getGmaeSettings(args[1]);
            if (gameSettings != null) {
                try {
                    int money = Integer.parseInt(args[2]);
                    AllGame.createGame(player, gameSettings, money);
                } catch (NumberFormatException e) {
                    Messages.get("NumberFormatError").parse(player).parse("%money%", args[2]).sendMessage(sender);
                }
                return true;
            }else{
                Messages.get("NotGameSettings").parse(player).parse("%currency%", args[1]).sendMessage(sender);
                return true;
            }
        }

        Messages.get("Help").parse("%player%", sender.getName()).sendMessage(sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1){
            List<String> complete = getTabComplete(args[0], "create", "join");
            if (sender.hasPermission("dplan.reload"))
            complete.addAll(getTabComplete(args[0], "reload"));
            return complete;
        }else if (args.length == 2){
            if (args[0].equalsIgnoreCase("create")){
                return getTabComplete(args[1], AllGame.allGameSettings.keySet().toArray(new String[0]));
            }else if (args[0].equalsIgnoreCase("join")){
                return getTabComplete(args[1], AllGame.allLonghupanGame.keySet().stream().map(OfflinePlayer::getName).toArray(String[]::new));
            }
        }
        return getTabComplete("空集合");
    }

    private List<String> getTabComplete(String cmd, String... args){
        List<String> complete = new ArrayList<>();
        for (String arg : args){
            if (arg.startsWith(cmd.toLowerCase())) complete.add(arg);
        }
        return complete;
    }
    
}