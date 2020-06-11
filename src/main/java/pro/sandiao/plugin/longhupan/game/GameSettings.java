package pro.sandiao.plugin.longhupan.game;

import java.util.List;

import pro.sandiao.plugin.longhupan.money.IMoney;
import pro.sandiao.plugin.longhupan.utils.CdMessage;
import pro.sandiao.plugin.longhupan.utils.EventCommand;

public class GameSettings {
    private IMoney iMoney;
    private int minMoney;
    private int maxMoney;
    private int minPlayer;
    private int maxPlayer;
    private int loserNum;
    private double rate;
    private int delay;
    private CdMessage cdmsg;
    private EventCommand ecmd;

    public GameSettings(IMoney iMoney, int minMoney, int maxMoney, int minPlayer, int maxPlayer, int loserNum, double rate, int delay, List<String> cdmsg, EventCommand ecmd){
        this.iMoney = iMoney;
        this.minMoney = minMoney;
        this.maxMoney = maxMoney;
        this.minPlayer = minPlayer;
        this.maxPlayer = maxPlayer;
        this.loserNum = loserNum;
        this.rate = rate;
        this.delay = delay;
        this.cdmsg = new CdMessage(cdmsg);
        this.ecmd = ecmd;
    }

    public IMoney getiMoney() {
        return iMoney;
    }

    public int getMinMoney() {
        return minMoney;
    }

    public int getMaxMoney() {
        return maxMoney;
    }

    public int getMinPlayer() {
        return minPlayer;
    }

    public int getMaxPlayer() {
        return maxPlayer;
    }

    public int getLoserNum() {
        return loserNum;
    }

    public double getRate() {
        return rate;
    }

    public int getDelay() {
        return delay;
    }

    public CdMessage getCdmsg() {
        return cdmsg;
    }

    public EventCommand getEventCommand() {
        return ecmd;
    }
}