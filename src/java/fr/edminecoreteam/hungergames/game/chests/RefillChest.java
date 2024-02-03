package fr.edminecoreteam.hungergames.game.chests;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RefillChest
{
    public void fillAllChestsRandomly() {
        // Parcourir tous les chunks chargés dans le monde
        for (org.bukkit.World world : Bukkit.getWorlds()) {
            for (org.bukkit.Chunk chunk : world.getLoadedChunks()) {
                // Parcourir tous les blocs de chaque chunk
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        for (int y = 0; y < world.getMaxHeight(); y++) {
                            Block block = chunk.getBlock(x, y, z);
                            if (block.getState() instanceof Chest) {
                                Chest chest = (Chest) block.getState();
                                fillChestRandomly(chest); // Remplir le coffre
                            }
                        }
                    }
                }
            }
        }
    }

    private void fillChestRandomly(Chest chest) {

        Random random = new Random();


        Random randomCommon = new Random();
        int amountCommon = randomCommon.nextInt(7) + 1;

        Random randomRare = new Random();
        int amountRare = randomRare.nextInt(3) + 1;

        // Liste des items communs
        List<ItemStack> commonItems = generateCommonItems();
        // Liste des items rares
        List<ItemStack> rareItems = generateRareItems();

        // Placer les items communs
        for (int i = 0; i < amountCommon; i++)
        {
            Random randomSlot = new Random();
            int slot = randomSlot.nextInt(26) + 1;
            int randomIndex = random.nextInt(commonItems.size());
            chest.getBlockInventory().setItem(slot, commonItems.get(randomIndex));
        }

        // Placer les items rares
        for (int i = 0; i < amountRare; i++)
        {
            Random randomSlot = new Random();
            int slot = randomSlot.nextInt(26) + 1;
            int randomIndex = random.nextInt(rareItems.size());
            chest.getBlockInventory().setItem(slot, rareItems.get(randomIndex));
        }
    }

    // Générer des items communs
    private List<ItemStack> generateCommonItems() {
        List<ItemStack> commonItems = new ArrayList<>();
        commonItems.add(new ItemStack(Material.WOOD_SWORD));
        commonItems.add(new ItemStack(Material.WOOD_AXE));
        commonItems.add(new ItemStack(Material.STONE_SWORD));
        commonItems.add(new ItemStack(Material.STONE_AXE));
        commonItems.add(new ItemStack(Material.BREAD, 3));
        commonItems.add(new ItemStack(Material.COOKED_BEEF, 3));
        commonItems.add(new ItemStack(Material.ARROW, 3));
        commonItems.add(new ItemStack(Material.EXP_BOTTLE, 3));
        commonItems.add(new ItemStack(Material.STICK, 1));
        commonItems.add(new ItemStack(Material.BREAD, 3));
        commonItems.add(new ItemStack(Material.COOKED_CHICKEN, 2));
        commonItems.add(new ItemStack(Material.EGG, 6));

        commonItems.add(new ItemStack(Material.LEATHER_HELMET));
        commonItems.add(new ItemStack(Material.LEATHER_CHESTPLATE));
        commonItems.add(new ItemStack(Material.LEATHER_LEGGINGS));
        commonItems.add(new ItemStack(Material.LEATHER_BOOTS));

        commonItems.add(new ItemStack(Material.CHAINMAIL_HELMET));
        commonItems.add(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        commonItems.add(new ItemStack(Material.CHAINMAIL_LEGGINGS));
        commonItems.add(new ItemStack(Material.CHAINMAIL_BOOTS));
        // Ajoutez d'autres items communs ici
        return commonItems;
    }

    // Générer des items rares
    private List<ItemStack> generateRareItems() {
        List<ItemStack> rareItems = new ArrayList<>();
        rareItems.add(new ItemStack(Material.IRON_SWORD));
        rareItems.add(new ItemStack(Material.IRON_AXE));
        rareItems.add(new ItemStack(Material.GOLD_SWORD));
        rareItems.add(new ItemStack(Material.GOLD_AXE));

        rareItems.add(new ItemStack(Material.BOW));

        rareItems.add(new ItemStack(Material.GOLDEN_APPLE));

        rareItems.add(new ItemStack(Material.GOLD_INGOT));
        rareItems.add(new ItemStack(Material.IRON_INGOT));
        rareItems.add(new ItemStack(Material.LAPIS_ORE));

        rareItems.add(new ItemStack(Material.BREAD, 6));
        rareItems.add(new ItemStack(Material.COOKED_BEEF, 6));
        rareItems.add(new ItemStack(Material.ARROW, 6));
        rareItems.add(new ItemStack(Material.EXP_BOTTLE, 8));
        rareItems.add(new ItemStack(Material.STICK, 3));
        rareItems.add(new ItemStack(Material.BREAD, 6));
        rareItems.add(new ItemStack(Material.COOKED_CHICKEN, 8));
        rareItems.add(new ItemStack(Material.EGG, 16));

        rareItems.add(new ItemStack(Material.GOLD_HELMET));
        rareItems.add(new ItemStack(Material.GOLD_CHESTPLATE));
        rareItems.add(new ItemStack(Material.GOLD_LEGGINGS));
        rareItems.add(new ItemStack(Material.GOLD_BOOTS));

        rareItems.add(new ItemStack(Material.IRON_HELMET));
        rareItems.add(new ItemStack(Material.IRON_CHESTPLATE));
        rareItems.add(new ItemStack(Material.IRON_LEGGINGS));
        rareItems.add(new ItemStack(Material.IRON_BOOTS));
        // Ajoutez d'autres items rares ici
        return rareItems;
    }
}
