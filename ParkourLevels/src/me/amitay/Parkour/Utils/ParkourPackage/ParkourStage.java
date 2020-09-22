package me.amitay.Parkour.Utils.ParkourPackage;

import me.amitay.Parkour.Parkour;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkourStage {
    private Parkour pl;
    private int stage, timeToComplete;
    private Location startPoint, endPoint;
    private String mode;
    private Map<World, Boolean> worlds = new HashMap<>();
    private Map<World, CheckPoint> checkPointMap = new HashMap<>();
    private List<String> commands = new ArrayList<>();
    private boolean isPlayable;

    public ParkourStage(int stage, String mode, Parkour pl, List<World> list, int timeToComplete, Map<Integer, Location> points, List<String> reward) {
        this.stage = stage;
        this.mode = mode;
        this.pl = pl;
        if (pl.getConfig().get("Parkour.Levels." + mode + "." + stage + ".StartPoint") != null
                && pl.getConfig().get("Parkour.Levels." + mode + "." + stage + ".EndPoint") != null) {
            startPoint = (Location) pl.getConfig().get("Parkour.Levels." + mode + "." + stage + ".StartPoint");
            endPoint = (Location) pl.getConfig().get("Parkour.Levels." + mode + "." + stage + ".EndPoint");
        }
        isPlayable = startPoint != null && endPoint != null;
        Map<Integer, Location> tempMap = new HashMap<>();
        for (World world : list) {
            worlds.put(world, false);
            for (Map.Entry<Integer, Location> entry : points.entrySet()){
                Location location = entry.getValue().clone();
                location.setWorld(world);
                tempMap.put(entry.getKey(), location);
            }
            checkPointMap.put(world, new CheckPoint(tempMap));
            tempMap.clear();
        }
        this.timeToComplete = timeToComplete;
        commands = reward;
    }

    public void executeCommands(String name){
        for (String key : commands)
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), key.replace("%player%", name));
    }
    public CheckPoint getCheckPoint(World world){
        return checkPointMap.get(world);
    }
    public int getStage() {
        return stage;
    }
    public boolean playable(){
        return isPlayable;
    }

    public Location getStartPoint(World world) {
        Location location = startPoint.clone();
        location.setWorld(world);
        return location;
    }

    public Location getEndPoint(World world) {
        Location location = startPoint.clone();
        location.setWorld(world);
        return location;
    }

    public World getEmptyWorld() {
        for (Map.Entry<World, Boolean> key : worlds.entrySet())
            if (key.getValue())
                return key.getKey();
        return null;
    }

    public String getMode() {
        return mode;
    }


}
