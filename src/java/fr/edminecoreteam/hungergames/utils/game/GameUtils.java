package fr.edminecoreteam.hungergames.utils.game;

import fr.edminecoreteam.hungergames.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameUtils
{
    private static final Core core = Core.getInstance();
    private final Location spawn = new Location(Bukkit.getWorld(
            core.getConfig().getString("spawn.world")),
            (float) core.getConfig().getDouble("spawn.x"),
            (float) core.getConfig().getDouble("spawn.y"),
            (float) core.getConfig().getDouble("spawn.z"),
            (float) core.getConfig().getDouble("spawn.f"),
            (float) core.getConfig().getDouble("spawn.t"));

    private final Location center = new Location(Bukkit.getWorld("game"),
            (float) core.getConfig().getDouble("maps." + core.world + ".center.x"),
            (float) core.getConfig().getDouble("maps." + core.world + ".center.y"),
            (float) core.getConfig().getDouble("maps." + core.world + ".center.z"));



    public Location getSpawn() { return this.spawn; }
    public Location getCenter() { return this.center; }
    public Location getPlayerSpawn(String spawn)
    {
        Location loc = new Location(Bukkit.getWorld("game"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spawns." + spawn + ".x"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spawns." + spawn + ".y"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spawns." + spawn + ".z"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spawns." + spawn + ".f"),
            (float) core.getConfig().getDouble("maps." + core.world + ".spawns." + spawn + ".t"));
        return loc;
    }

    public String convertTime(int timeInSeconds)
    {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        return String.format("%02dm %02ds", minutes, seconds);
    }

    // Fonction pour placer des coffres de manière intelligente dans le rayon spécifié
    public void placeChests(Location center, int radius) {
        List<Location> chestLocations = new ArrayList<>();

        for (int x = center.getBlockX() - radius; x <= center.getBlockX() + radius; x++) {
            for (int z = center.getBlockZ() - radius; z <= center.getBlockZ() + radius; z++) {
                Location loc = new Location(center.getWorld(), x, center.getBlockY(), z);
                if (loc.distance(center) <= radius && loc.getBlock().getType() == Material.AIR) {
                    chestLocations.add(loc);
                }
            }
        }

        Collections.shuffle(chestLocations); // Mélanger la liste des emplacements des coffres

        // Placer des coffres aux emplacements sélectionnés
        Random random = new Random();
        int numChests = Math.min(chestLocations.size(), core.getConfig().getInt("chests.number"));
        for (int i = 0; i < numChests; i++) {
            Location loc = chestLocations.get(i);
            Block block = loc.getBlock();
            block.setType(Material.CHEST);

            // Optionnel : Remplir le coffre avec des objets aléatoires
            if (block.getState() instanceof Chest) {
                fillChestRandomly((Chest) block.getState());
            }
        }
    }

    public void placeChestsAroundLocation(Location center, int minRadius, int maxRadius, int numChests) {
        List<Location> possibleLocations = generatePossibleChestLocations(center, minRadius, maxRadius);

        // Mélanger la liste des emplacements possibles
        Collections.shuffle(possibleLocations);

        // Sélectionner aléatoirement un nombre spécifié d'emplacements parmi les emplacements possibles
        List<Location> selectedLocations = possibleLocations.subList(0, Math.min(numChests, possibleLocations.size()));

        // Placer des coffres aux emplacements sélectionnés
        for (Location loc : selectedLocations) {
            Block block = loc.getBlock();
            block.setType(Material.CHEST);
            System.out.println("CHEST placed on: X" + loc.getX() + ", Y" + loc.getY() + ", Z" + loc.getZ());
        }
    }

    // Méthode pour générer une liste d'emplacements possibles de coffres autour d'une location avec un rayon minimum et maximum
    private List<Location> generatePossibleChestLocations(Location center, int minRadius, int maxRadius) {
        List<Location> possibleLocations = new ArrayList<>();

        // Parcourir tous les blocs autour de la location centrale avec un rayon maximum
        for (int x = center.getBlockX() - maxRadius; x <= center.getBlockX() + maxRadius; x++) {
            for (int z = center.getBlockZ() - maxRadius; z <= center.getBlockZ() + maxRadius; z++) {
                for (int y = 0; y <= center.getWorld().getMaxHeight(); y++) {
                    Location loc = new Location(center.getWorld(), x, y, z);
                    double distance = loc.distance(center);
                    // Vérifier si la distance est comprise entre le rayon minimum et maximum
                    if (distance >= minRadius && distance <= maxRadius && isValidChestLocation(loc)) {
                        possibleLocations.add(loc);
                    }
                }
            }
        }

        return possibleLocations;
    }

    // Méthode pour vérifier si un emplacement est valide pour placer un coffre
    private boolean isValidChestLocation(Location loc) {
        Block block = loc.getBlock();
            // Vérifier si le bloc au-dessus est de l'air
        Block aboveBlock = loc.clone().add(0, 1, 0).getBlock();
        if (aboveBlock.getType() == Material.AIR)
        {
            return true;
        }
        return false;
    }

    // Fonction pour déclarer des items dans tous les coffres de la carte sans tenir compte du rayon
    public void fillChestsRandomly(Location center) {
    // Parcourir tous les blocs de la zone
    for (int x = center.getBlockX() - 15; x <= center.getBlockX() + 15; x++) {
        for (int y = center.getBlockY() - 15; y <= center.getBlockY() + 15; y++) {
            for (int z = center.getBlockZ() - 15; z <= center.getBlockZ() + 15; z++) {
                Location loc = new Location(center.getWorld(), x, y, z);
                Block block = loc.getBlock();
                // Vérifier si le bloc est un coffre
                if (block.getState() instanceof Chest) {
                    Chest chest = (Chest) block.getState();
                    fillChest(chest); // Remplir le coffre
                }
            }
        }
    }
}

    private void fillChest(Chest chest) {
        Random random = new Random();
        // Ajouter quelques items aléatoires dans le coffre
        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(possibleItems.length);
            Material item = possibleItems[randomIndex];
            int amount = random.nextInt(5) + 1; // Quantité aléatoire entre 1 et 5
            chest.getBlockInventory().addItem(new ItemStack(item, amount));
        }
    }

    private final Material[] possibleItems = {
            Material.WOOD_SWORD,
            Material.BOW,
            Material.LEATHER_HELMET,
            Material.LEATHER_CHESTPLATE,
            Material.LEATHER_LEGGINGS,
            Material.LEATHER_BOOTS,
            Material.BREAD,
            Material.COOKED_BEEF,
            Material.COOKED_CHICKEN,
            Material.GRILLED_PORK,
            Material.STONE_SWORD,
            Material.CHAINMAIL_CHESTPLATE,
            Material.CHAINMAIL_CHESTPLATE,
            Material.CHAINMAIL_CHESTPLATE
    };

    private void fillChestRandomly(Chest chest) {
        Random random = new Random();

        // Liste des items communs
        List<ItemStack> commonItems = generateCommonItems();
        // Liste des items rares
        List<ItemStack> rareItems = generateRareItems();

        // Placer les items communs
        for (int i = 0; i < 4; i++) { // Placer 4 items communs
            int randomIndex = random.nextInt(commonItems.size());
            chest.getBlockInventory().addItem(commonItems.get(randomIndex));
        }

        // Placer les items rares
        for (int i = 0; i < 2; i++) { // Placer 2 items rares
            int randomIndex = random.nextInt(rareItems.size());
            chest.getBlockInventory().addItem(rareItems.get(randomIndex));
        }
    }

    // Générer des items communs
    private List<ItemStack> generateCommonItems() {
        List<ItemStack> commonItems = new ArrayList<>();
        commonItems.add(new ItemStack(Material.STONE_SWORD));
        commonItems.add(new ItemStack(Material.BREAD, 3));
        commonItems.add(new ItemStack(Material.COOKED_BEEF, 3));
        // Ajoutez d'autres items communs ici
        return commonItems;
    }

    // Générer des items rares
    private List<ItemStack> generateRareItems() {
        List<ItemStack> rareItems = new ArrayList<>();
        rareItems.add(new ItemStack(Material.IRON_SWORD));
        rareItems.add(new ItemStack(Material.GOLDEN_APPLE));
        // Ajoutez d'autres items rares ici
        return rareItems;
    }
}
