package pro.sandiao.plugin.longhupan.event;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import pro.sandiao.plugin.longhupan.game.LonghupanGame;

public class LonghupanEndEvent extends Event {

	private static final HandlerList handlerList = new HandlerList();
	private OfflinePlayer owner;
	private LonghupanGame game;
	private int closeCode;
	private int allMoney;
	private List<OfflinePlayer> winners, losers;

	public LonghupanEndEvent(OfflinePlayer who, LonghupanGame game, int closeCode, int allMoney, List<OfflinePlayer> winners, List<OfflinePlayer> losers) {
        this.owner = who;
		this.game = game;
		this.closeCode = closeCode;
		this.allMoney = allMoney;
		this.winners = winners;
		this.losers = losers;
    }
	
	public LonghupanGame getGame() {
		return game;
	}
	
	public OfflinePlayer getOwner() {
		return owner;
	}

	public int getCloseCode() {
		return closeCode;
	}

	public int getAllMoney() {
		return allMoney;
	}

	public List<OfflinePlayer> getWinners() {
		return winners;
	}

	public List<OfflinePlayer> getLosers() {
		return losers;
	}
	
	public static HandlerList getHandlerList() {
		return handlerList;
	}

	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}

}
