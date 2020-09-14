package me.amitay.Parkour.Utils.ParkourPackage;

import me.amitay.Parkour.Parkour;
import org.bukkit.Location;

import java.util.ArrayList;

public class ParkourStage {
    private Parkour pl;
    private int stage;
    private Location startPoint, endPoint;
    private Long timeToComplete;
    private String mode;
    private ArrayList<Location> locations = new ArrayList<>();

    public ParkourStage(int stage, String mode, Parkour pl){
        this.stage = stage;
        this.mode = mode;
        this.pl = pl;
    }

    public int getStage() {
        return stage;
    }
    public Location getStartPoint(){
        if (pl.getConfig().get("Parkour.Levels.Solo.1.StartPoint") == null)
            return null;
        return (Location) pl.getConfig().get("Parkour.Levels." + mode + "." + stage + ".StartPoint");
    }
    public Location getEndPoint(){
        if (pl.getConfig().get("Parkour.Levels." + mode + "." + stage + ".EndPoint") == null){
            return null;
        } else
            return (Location) pl.getConfig().get("Parkour.Levels." + mode + "." + stage + ".EndPoint");
    }
    public String getMode(){
        return mode;
    }
    public ArrayList<Location> getLocations() {
        return locations;
    }


}
