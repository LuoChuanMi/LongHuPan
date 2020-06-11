package pro.sandiao.plugin.longhupan.event;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import pro.sandiao.plugin.longhupan.game.LonghupanGame;

public class LonghupanStartEvent extends Event {

	private static final HandlerList handlerList = new HandlerList();
	private OfflinePlayer owner;
    private LonghupanGame game;

    public LonghupanStartEvent(OfflinePlayer who, LonghupanGame game) {
        this.owner = who;
        this.game = game;
    }
	
	public LonghupanGame getGame() {
		return game;
	}
	
	public OfflinePlayer getOwner() {
		return owner;
	}
	
	public static HandlerList getHandlerList() {
		return handlerList;
	}

	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}

}
