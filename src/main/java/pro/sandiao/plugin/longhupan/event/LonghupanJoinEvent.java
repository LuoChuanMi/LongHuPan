package pro.sandiao.plugin.longhupan.event;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import pro.sandiao.plugin.longhupan.game.LonghupanGame;

public class LonghupanJoinEvent extends Event {

	private static final HandlerList handlerList = new HandlerList();
	private OfflinePlayer player;
	private LonghupanGame game;

	public LonghupanJoinEvent(OfflinePlayer who, LonghupanGame game) {
        this.player = who;
        this.game = game;
    }
	
	public LonghupanGame getGame() {
		return game;
	}
	
	public OfflinePlayer getPlayer() {
		return player;
	}
	
	public static HandlerList getHandlerList() {
		return handlerList;
	}

	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}

}
