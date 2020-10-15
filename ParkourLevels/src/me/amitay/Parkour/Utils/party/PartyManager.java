package me.amitay.parkour.utils.party;

import me.amitay.parkour.Parkour;
import me.amitay.parkour.utils.Utils;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PartyManager {

    private ArrayList<Party> parties = new ArrayList<>();
    private Parkour pl;
    public PartyManager(Parkour pl){
        this.pl = pl;
    }

    public void createParty(Player leader, Parkour pl) {
        parties.add(new Party(leader, pl));
        leader.sendMessage(Utils.getFormattedText("&eYou have successfully created a party."));
    }

    public boolean isLeader(Player player) {
        for (Party party : parties)
            if (party
                    .leader
                    .equals
                    (player))
                return true;
        return false;
    }

    public void addPlayer(Player inviter, Player player) {
        Party party = getPlayerParty(inviter);
        party.addmember(player);
        return;
    }

    public boolean isInParty(Player player) {
        for (Party party : parties) {
            if (party.containsPlayer(player))
                return true;
        }
        return false;
    }

    public void leaveParty(Player player) {
        //check if not in game
        Party party = getPlayerParty(player);
        if (party == null) {
            player.sendMessage(Utils.getFormattedText("&cYou are not in a party"));
            return;
        }
        party.deleteParty();
        parties.remove(party);
        player.sendMessage(Utils.getFormattedText("&eYou have successfully left the party"));
        return;
        //if in game handle
        //handle quit event
    }

    public void getPartyMembers(Player player) {
        Party party = getPlayerParty(player);
        if (party == null) {
            player.sendMessage(Utils.getFormattedText("&cYou are not in a party"));
            return;
        }
        if (party.hasMember()) {
            player.sendMessage(Utils.getFormattedText("&eThe current members of the party are: " + party.getLeaderName() + ", " + party.getMemberName()));
            return;
        } else {
            player.sendMessage(Utils.getFormattedText("&eYou are the only member in your party"));
            return;
        }
    }

    public Party getPlayerParty(Player player) {
        for (Party party : parties)
            if (party.containsPlayer(player))
                return party;
        return null;
    }

    public void promoteMember(Player p, Player oldleader) {
        if (isInParty(oldleader)) {
            Party party = getPlayerParty(oldleader);
            if (party.hasMember()) {
                if (!isLeader(p)) {
                    party.setmember(party.leader);
                    party.leader = p;
                    party.loadPlayerStats(p, 'l');
                    party.loadPlayerStats(oldleader, 'm');
                    oldleader.sendMessage(Utils.getFormattedText("&eSuccessfully transfered the leadership of the party"));
                    p.sendMessage(Utils.getFormattedText("&eYou are the new leader of the party"));
                } else {
                    p.sendMessage(Utils.getFormattedText("&cOnly the leader can execute these commands"));
                }
            } else {
                oldleader.sendMessage(Utils.getFormattedText("&cThat member is not in your party"));
                return;
            }
        } else {
            p.sendMessage(Utils.getFormattedText("&cYou are not in a party"));
            return;
        }
    }
    public ArrayList<Party> getParties() {
        return parties;
    }
}

