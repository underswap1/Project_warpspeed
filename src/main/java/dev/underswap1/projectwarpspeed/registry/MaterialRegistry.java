package dev.underswap1.projectwarpspeed.registry;

import com.google.gson.*;
import dev.underswap1.projectwarpspeed.materials.MaterialProperties;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MaterialRegistry {

    // Map of material name -> properties
    public static final Map<String, MaterialProperties> MATERIALS = new HashMap<>();

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
        return MATERIALS.get(name);
    }
}