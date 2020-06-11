package pro.sandiao.plugin.longhupan.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import pro.sandiao.plugin.longhupan.event.LonghupanEndEvent;
import pro.sandiao.plugin.longhupan.event.LonghupanJoinEvent;
import pro.sandiao.plugin.longhupan.event.LonghupanStartEvent;

public class EventCommand {

    List<String> start, join, end;

	public EventCommand(String name, YamlConfiguration settings) {
        start = settings.getStringList(name + ".cmd.start");
        join = settings.getStringList(name + ".cmd.join");
        end = settings.getStringList(name + ".cmd.end");
    }

    public List<String> getStartEventCommand() {
        return start;
    }
    
    public List<String> getJoinEventCommand() {
        return join;
    }

    public List<String> getEndEventCommand() {
        return end;
    }

    public void dispatchStartCommand(LonghupanStartEvent e){
        for (String cmd : start)
            Messages.set(cmd).parse(e.getOwner(), e.getGame()).dispatchCommand(Bukkit.getConsoleSender());
    }

    public void dispatchJoinCommand(LonghupanJoinEvent e){
        for (String cmd : join)
            Messages.set(cmd).parse(e.getPlayer(), e.getGame()).dispatchCommand(Bukkit.getConsoleSender());
    }

    public void dispatchEndCommand(LonghupanEndEvent e){
        for (String cmd : end)
            Messages.set(cmd).parse(e).dispatchCommand(Bukkit.getConsoleSender());
    }
    
    
}