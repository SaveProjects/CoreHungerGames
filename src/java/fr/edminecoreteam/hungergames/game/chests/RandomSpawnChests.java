package fr.edminecoreteam.hungergames.game.chests;

import fr.edminecoreteam.hungergames.Core;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomSpawnChests
{
    private final static Core core = Core.getInstance();

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
        }
    }

    private List<Location> generatePossibleChestLocations(Location center, int minRadius, int maxRadius) {
        List<Location> possibleLocations = new ArrayList<>();

        // Parcourir tous les blocs autour de la location centrale avec un rayon maximum
        for (int x = center.getBlockX() - maxRadius; x <= center.getBlockX() + maxRadius; x++) {
            for (int y = center.getBlockY() - maxRadius; y <= center.getBlockY() + maxRadius; y++) {
                for (int z = center.getBlockZ() - maxRadius; z <= center.getBlockZ() + maxRadius; z++) {
                    Location loc = new Location(center.getWorld(), x, y, z);
                    double distance = loc.distance(center);
                    // Vérifier si la distance est comprise entre le rayon minimum et maximum
                    if (distance >= minRadius && distance <= maxRadius && isValidChestLocation(loc) && isSafeDistance(loc, possibleLocations)) {
                        Location newloc = new Location(loc.getWorld(), loc.getX(), loc.getY() + 1, loc.getZ());
                        possibleLocations.add(newloc);
                    }
                }
            }
        }

        return possibleLocations;
    }

    private boolean isSafeDistance(Location loc, List<Location> placedLocations) {
        for (Location placedLoc : placedLocations) {
            if (loc.distanceSquared(placedLoc) < 600) { // 20 blocs de distance au carré
                return false; // Si la distance est inférieure à 20 blocs, l'emplacement n'est pas sûr
            }
        }
        return true; // Si la distance est supérieure ou égale à 20 blocs pour tous les coffres déjà placés, l'emplacement est sûr
    }

    // Méthode pour vérifier si un emplacement est valide pour placer un coffre
    private boolean isValidChestLocation(Location loc) {
        Block block = loc.getBlock();
        // Vérifier si le bloc est de l'herbe ou de la terre
        if (acceptSpawnBlocs().contains(block.getType())) {
            // Vérifier si le bloc au-dessus est de l'air
            Block aboveBlock = loc.clone().add(0, 1, 0).getBlock();
            return aboveBlock.getType() == Material.AIR;
        }
        return false;
    }

    private List<Material> acceptSpawnBlocs()
    {
        List<Material> list = new ArrayList<>();

        list.add(Material.GRASS);
        list.add(Material.DIRT);
        list.add(Material.STONE);
        list.add(Material.COBBLESTONE);
        list.add(Material.LOG);
        list.add(Material.LOG_2);
        list.add(Material.SAND);
        list.add(Material.GRAVEL);
        list.add(Material.SANDSTONE);
        list.add(Material.RED_SANDSTONE);
        list.add(Material.STAINED_CLAY);
        list.add(Material.QUARTZ_BLOCK);
        list.add(Material.ENDER_STONE);
        list.add(Material.NETHERRACK);
        list.add(Material.OBSIDIAN);
        list.add(Material.SOUL_SAND);
        list.add(Material.STAINED_CLAY);
        list.add(Material.SOUL_SAND);
        list.add(Material.SMOOTH_BRICK);
        list.add(Material.DOUBLE_STONE_SLAB2);

        /*list.add(Material.ACACIA_STAIRS);
        list.add(Material.BRICK_STAIRS);
        list.add(Material.SMOOTH_STAIRS);
        list.add(Material.SANDSTONE_STAIRS);
        list.add(Material.DARK_OAK_STAIRS);
        list.add(Material.BIRCH_WOOD_STAIRS);
        list.add(Material.COBBLESTONE_STAIRS);
        list.add(Material.SPRUCE_WOOD_STAIRS);
        list.add(Material.QUARTZ_STAIRS);
        list.add(Material.WOOD_STAIRS);
        list.add(Material.FENCE);
        list.add(Material.WOOD_STEP);
        list.add(Material.WOOD_DOUBLE_STEP);
        list.add(Material.STONE_SLAB2);
        list.add(Material.DOUBLE_STONE_SLAB2);
        list.add(Material.LEAVES);
        list.add(Material.LEAVES_2);
        list.add(Material.WATER);
        list.add(Material.LAVA);
        list.add(Material.CHEST);
        list.add(Material.ENDER_CHEST);
        list.add(Material.ENCHANTMENT_TABLE);
        list.add(Material.WORKBENCH);
        list.add(Material.ENDER_PORTAL_FRAME);
        list.add(Material.FURNACE);
        list.add(Material.AIR);
        list.add(Material.FLOWER_POT);
        list.add(Material.FLOWER_POT_ITEM);
        list.add(Material.LADDER);
        list.add(Material.YELLOW_FLOWER);
        list.add(Material.RED_ROSE);
        list.add(Material.LONG_GRASS);
        list.add(Material.CARPET);
        list.add(Material.TRAP_DOOR);
        list.add(Material.IRON_TRAPDOOR);
        list.add(Material.STAINED_GLASS_PANE);
        list.add(Material.GLASS);
        list.add(Material.WATER_LILY);*/


        return list;
    }
}
