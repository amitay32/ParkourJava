package me.amitay.Parkour.Utils;

import me.amitay.Parkour.Parkour;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class LoadItems {
    private Parkour pl;
    public ItemStack teams, solo, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, s1, s2, s3, s4, s5, s6, s7, s8, s9, s10;

    public LoadItems(Parkour pl) {
        this.pl = pl;
    }

    public void loadItems(){
        teams = new ItemBuilder(
                Material.WOOL, 1)
                .setDyeColor(DyeColor.GREEN)
                .setName(ChatColor.YELLOW + "Parkour teams").addLoreLine(ChatColor.YELLOW + "Click here to play parkour teams with your party member").toItemStack();
        solo = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.BLUE).setName(ChatColor.YELLOW + "Parkour solo").addLoreLine(ChatColor.YELLOW + "Click here to play parkour alone").toItemStack();

        t1 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.GREEN).setName(ChatColor.YELLOW + "Level 1 -> EASY").addLoreLine(ChatColor.YELLOW + "Click here to play level 1")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t2 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.GREEN).setName(ChatColor.YELLOW + "Level 2 -> EASY").addLoreLine(ChatColor.YELLOW + "Click here to play level 2")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t3 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.GREEN).setName(ChatColor.YELLOW + "Level 3 -> EASY").addLoreLine(ChatColor.YELLOW + "Click here to play level 3")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t4 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.YELLOW).setName(ChatColor.YELLOW + "Level 4 -> MEDIUM").addLoreLine(ChatColor.YELLOW + "Click here to play level 4")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t5 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.YELLOW).setName(ChatColor.YELLOW + "Level 5 -> MEDIUM").addLoreLine(ChatColor.YELLOW + "Click here to play level 5")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t6 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.YELLOW).setName(ChatColor.YELLOW + "Level 6 -> MEDIUM").addLoreLine(ChatColor.YELLOW + "Click here to play level 6")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t7 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.ORANGE).setName(ChatColor.YELLOW + "Level 7 -> HARD").addLoreLine(ChatColor.YELLOW + "Click here to play level 7")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t8 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.ORANGE).setName(ChatColor.YELLOW + "Level 8 -> HARD").addLoreLine(ChatColor.YELLOW + "Click here to play level 8")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t9 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.ORANGE).setName(ChatColor.YELLOW + "Level 9 -> HARD").addLoreLine(ChatColor.YELLOW + "Click here to play level 9")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        t10 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.RED).setName(ChatColor.YELLOW + "Level 10 -> REALLY HARD").addLoreLine(ChatColor.YELLOW + "Click here to play level 10")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();

        s1 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.GREEN).setName(ChatColor.YELLOW + "Level 1 -> EASY").addLoreLine(ChatColor.YELLOW + "Click here to play level 1")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s2 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.GREEN).setName(ChatColor.YELLOW + "Level 2 -> EASY").addLoreLine(ChatColor.YELLOW + "Click here to play level 2")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s3 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.GREEN).setName(ChatColor.YELLOW + "Level 3 -> EASY").addLoreLine(ChatColor.YELLOW + "Click here to play level 3")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s4 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.YELLOW).setName(ChatColor.YELLOW + "Level 4 -> MEDIUM").addLoreLine(ChatColor.YELLOW + "Click here to play level 4")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s5 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.YELLOW).setName(ChatColor.YELLOW + "Level 5 -> MEDIUM").addLoreLine(ChatColor.YELLOW + "Click here to play level 5")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s6 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.YELLOW).setName(ChatColor.YELLOW + "Level 6 -> MEDIUM").addLoreLine(ChatColor.YELLOW + "Click here to play level 6")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s7 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.ORANGE).setName(ChatColor.YELLOW + "Level 7 -> HARD").addLoreLine(ChatColor.YELLOW + "Click here to play level 7")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s8 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.ORANGE).setName(ChatColor.YELLOW + "Level 8 -> HARD").addLoreLine(ChatColor.YELLOW + "Click here to play level 8")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s9 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.ORANGE).setName(ChatColor.YELLOW + "Level 9 -> HARD").addLoreLine(ChatColor.YELLOW + "Click here to play level 9")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
        s10 = new ItemBuilder(Material.WOOL, 1).setDyeColor(DyeColor.RED).setName(ChatColor.YELLOW + "Level 10 -> REALLY HARD").addLoreLine(ChatColor.YELLOW + "Click here to play level 10")
                .addLoreLine(" ").addLoreLine(ChatColor.YELLOW + "Rewards for first completion: 10000 tokens, 100000 Money").addLoreLine(ChatColor.YELLOW + "Rewards for every other completion: 10000 tokens, 100000 Money").toItemStack();
    }

}
