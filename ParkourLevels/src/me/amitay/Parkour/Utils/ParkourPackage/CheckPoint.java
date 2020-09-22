package me.amitay.Parkour.Utils.ParkourPackage;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckPoint {
    private Map<Integer, Location> checkPoints;


    public CheckPoint(Map<Integer, Location> checkPoints) {
        this.checkPoints = checkPoints;
    }

    public Location getLocationByStage(int stage) {
        return checkPoints.get(stage);
    }

}
