package me.amitay.parkour.utils.party;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Invites {
    public Map<UUID, Long> timer = new HashMap<>(); //invitor

    public Invites (UUID uuid){
        timer.put(uuid, System.currentTimeMillis());
    }
}
