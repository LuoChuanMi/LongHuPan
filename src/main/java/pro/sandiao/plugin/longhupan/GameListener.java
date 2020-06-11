package pro.sandiao.plugin.longhupan;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import pro.sandiao.plugin.longhupan.event.LonghupanCountdownEvent;
import pro.sandiao.plugin.longhupan.event.LonghupanEndEvent;
import pro.sandiao.plugin.longhupan.event.LonghupanJoinEvent;
import pro.sandiao.plugin.longhupan.event.LonghupanStartEvent;
import pro.sandiao.plugin.longhupan.utils.Messages;

public class GameListener implements Listener {

    @EventHandler
    public void onGameStart(LonghupanStartEvent e) {
        Messages.get("StratGameToPlayers").parse(e.getOwner() ,e.getGame()).sendMessage(e.getGame().getAllPlayers());
        Messages.get("StratGameToBroadcast").parse(e.getGame()).broadcastMessage();

        e.getGame().getGameSettings().getEventCommand().dispatchStartCommand(e);
    }

    @EventHandler
    public void onGameEnd(LonghupanEndEvent e) {
        switch (e.getCloseCode()) {
            case 0:
                Messages.get("EndGameToPlayer").parse(e).sendMessage(e.getOwner());
                Messages.get("EndGameToPlayers").parse(e).sendMessage(e.getGame().getAllPlayers());
                Messages.get("EndGameToWinners").parse(e).sendMessage(e.getWinners());
                Messages.get("EndGameToLosers").parse(e).sendMessage(e.getLosers());
                Messages.get("EndGameToBroadcast").parse(e).broadcastMessage();
            break;
            case 1:
                Messages.get("CloseGameToPlayer").parse(e).sendMessage(e.getOwner());
                Messages.get("CloseGameToPlayers").parse(e).sendMessage(e.getGame().getAllPlayers());
                Messages.get("CloseGameToBroadcast").parse(e).broadcastMessage();
            break;
            case 501:
                Messages.get("EndGameOfPeopleLessToPlayer").parse(e).sendMessage(e.getOwner());
                Messages.get("EndGameOfPeopleLessToPlayers").parse(e).sendMessage(e.getGame().getAllPlayers());
                Messages.get("EndGameOfPeopleLessToBroadcast").parse(e).broadcastMessage();
            break;
            default:
                break;
        }

        e.getGame().getGameSettings().getEventCommand().dispatchEndCommand(e);
    }

    @EventHandler
    public void onGameJoin(LonghupanJoinEvent e) {
        Messages.get("JoinGameToPlayer").parse(e.getPlayer(), e.getGame()).sendMessage(e.getPlayer());
        Messages.get("JoinGameToPlayers").parse(e.getPlayer(), e.getGame()).sendMessage(e.getGame().getAllPlayers());
        Messages.get("JoinGameToBroadcast").parse(e.getPlayer(), e.getGame()).broadcastMessage();

        e.getGame().getGameSettings().getEventCommand().dispatchJoinCommand(e);
    }

    @EventHandler
    public void onGameCountdown(LonghupanCountdownEvent e) {
        e.getGame().getGameSettings().getCdmsg().sendMessage(e);
    }
}