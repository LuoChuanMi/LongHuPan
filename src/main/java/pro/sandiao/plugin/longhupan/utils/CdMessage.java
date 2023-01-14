package pro.sandiao.plugin.longhupan.utils;

import pro.sandiao.plugin.longhupan.event.LonghupanCountdownEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CdMessage {
    Map<Integer, List<String>> cdmsg = new HashMap<>();

    public CdMessage(List<String> cdmsgs) {
        for (String cdmsg : cdmsgs) {
            String[] a = cdmsg.split(":");
            if (a.length < 2) continue;
            String b = cdmsg.substring(cdmsg.indexOf(":") + 1);
            try {
                int x = Integer.parseInt(a[0]);
                if (this.cdmsg.get(x) == null) {
                    List<String> newlist = new ArrayList<>();
                    newlist.add(b);
                    this.cdmsg.put(x, newlist);
                } else {
                    this.cdmsg.get(x).add(b);
                }
            } catch (NumberFormatException e) {
            }
        }
    }

    public List<String> get(int cd) {
        return cdmsg.get(cd);
    }

    public void sendMessage(LonghupanCountdownEvent e) {
        List<String> cdmsgs = get(e.getCountdown());
        if (cdmsgs != null) {
            for (String cdmsg : cdmsgs) {
                Messages.set(cdmsg).parse(e.getGame()).sendMessage(e.getGame().getAllPlayers());
            }
        }
    }
}