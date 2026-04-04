package dev.underswap1.projectwarpspeed.registry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.underswap1.projectwarpspeed.machine.MachinePartType;
import dev.underswap1.projectwarpspeed.materials.MaterialPartItem;
import dev.underswap1.projectwarpspeed.materials.core.MaterialProperties;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class MaterialRegistry {

    // Map of material name -> properties
    public static final Map<String, MaterialProperties> MATERIALS = new HashMap<>();
    public static final Map<String, MaterialProperties> ALIASES = new HashMap<>();
    private static final Map<String, MaterialPartItem> PART_ITEMS = new LinkedHashMap<>();

    private static boolean itemsRegistered = false;

    @SuppressWarnings("CallToPrintStackTrace")
    public static void loadMaterials() {

        try (InputStream is = MaterialRegistry.class.getResourceAsStream(
                "/data/project_warpspeed/materials.json")) {

            if (is == null) {
                System.out.println("Failed to find materials.json!");
                return;
            }

            JsonObject json = new Gson().fromJson(new InputStreamReader(is), JsonObject.class);
            JsonArray materials = json.getAsJsonArray("materials");

            for (JsonElement e : materials) {
                MaterialProperties mat = new Gson().fromJson(e, MaterialProperties.class);
                MATERIALS.put(mat.name.toLowerCase(), mat);
            }

            System.out.println("Loaded " + MATERIALS.size() + " materials.");

        } catch (Exception e) {
            System.out.println("Failed to load materials: " + e);
            e.printStackTrace();
        }
    }

    public static void register(MaterialProperties mat) {
        if (mat == null || mat.name == null) return;
            String key = toKey(mat.name);
            MATERIALS.put(key, mat);
            if (itemsRegistered) {
                registerItemsForMaterial(key, mat);
        }
    }

    public static void registerAlias(String alias, MaterialProperties mat) {
        String key = toKey(alias);
        if (!ALIASES.containsKey(key)) {
            ALIASES.put(key, mat);
        }
    }

    public static MaterialProperties get(String name) {
        if (name == null) return null;
        MaterialProperties mat = MATERIALS.get(name.toLowerCase());
        if (mat != null) return mat;
        return ALIASES.get(name.toLowerCase());
    }

    /** Returns all registered material names, including aliases, for tab completion */
    public static Set<String> getAllMaterialNames() {
        Set<String> names = new HashSet<>(MATERIALS.keySet());
        names.addAll(ALIASES.keySet());
        return names;
    }

    public static void registerAllItems() {
        for (Map.Entry<String, MaterialProperties> entry : MATERIALS.entrySet()) {
            registerItemsForMaterial(entry.getKey(), entry.getValue());
        }
        itemsRegistered = true;
        System.out.println("Registered " + PART_ITEMS.size() + " material part items.");
    }

    private static void registerItemsForMaterial(String matKey, MaterialProperties mat) {

        if (mat.meltingPointK < 300) {
            System.out.println("Skipping part generation for " + mat.name + " (melting point too low)");
            return;
        }

        for (MachinePartType part : MachinePartType.values()) {
            String id = matKey + "_" + part.name().toLowerCase();
            if (PART_ITEMS.containsKey(id)) continue;

            MaterialPartItem item = Registry.register(
                    Registries.ITEM,
                    new Identifier("project_warpspeed", id),
                    new MaterialPartItem(mat, part)
            );
            PART_ITEMS.put(id, item);
        }
    }

    public static MaterialPartItem getPart(String materialName, MachinePartType part) {
        return PART_ITEMS.get(toKey(materialName) + "_" + part.name().toLowerCase());
    }

    public static Map<String, MaterialPartItem> getAllParts() {
        return Collections.unmodifiableMap(PART_ITEMS);
    }

    private static String toKey(String name) {
        return name.toLowerCase().replace(" ", "_").replace("-", "_");
    }
}