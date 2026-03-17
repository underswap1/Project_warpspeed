package dev.underswap1.projectwarpspeed.registry;

import com.google.gson.*;
import dev.underswap1.projectwarpspeed.materials.MaterialProperties;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class MaterialRegistry {

    // Map of material name -> properties
    public static final Map<String, MaterialProperties> MATERIALS = new HashMap<>();
    public static final Map<String, MaterialProperties> ALIASES = new HashMap<>();

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

    public static MaterialProperties get(String name) {
        if (name == null) return null;
        MaterialProperties mat = MATERIALS.get(name.toLowerCase());
        if (mat != null) return mat;
        return ALIASES.get(name.toLowerCase());
    }

    public static void register(MaterialProperties mat) {
        if (mat != null && mat.name != null) {
            MATERIALS.put(mat.name.toLowerCase(), mat);
        }
    }

    public static void registerAlias(String alias, MaterialProperties mat) {
        if (!ALIASES.containsKey(alias.toLowerCase())) {
            ALIASES.put(alias.toLowerCase(), mat);
        }
    }

    /** Returns all registered material names, including aliases, for tab completion */
    public static Set<String> getAllMaterialNames() {
        Set<String> names = new HashSet<>(MATERIALS.keySet());
        names.addAll(ALIASES.keySet());
        return names;
    }
}