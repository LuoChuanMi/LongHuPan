package pro.sandiao.plugin.longhupan.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import pro.sandiao.plugin.longhupan.game.LonghupanGame;

public class LonghupanCountdownEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
	private LonghupanGame game;
	private int countdown;

    public LonghupanCountdownEvent(LonghupanGame game, int countdown) {
		this.game = game;
		this.countdown = countdown;
	}
	
	public LonghupanGame getGame() {
		return game;
	}
	
	public int getCountdown() {
		return countdown;
	}
	
	public static HandlerList getHandlerList() {
		return handlerList;
	}

	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}

}
