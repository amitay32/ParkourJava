package me.amitay.Parkour.Utils.ParkourPackage;

import org.bukkit.Location;

import java.util.Map;

public class CheckPoint {
    private Map<Integer, Location> checkPoints;


    public Map<Integer, Location> getCheckPoints() {
        return checkPoints;
    }

    public CheckPoint(Map<Integer, Location> checkPoints) {
        this.checkPoints = checkPoints;
    }

    public Location getLocationByStage(int stage) {
        return checkPoints.get(stage);
    }

}
