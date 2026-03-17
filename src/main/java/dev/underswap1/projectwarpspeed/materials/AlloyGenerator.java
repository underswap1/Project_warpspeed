package dev.underswap1.projectwarpspeed.materials;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.underswap1.projectwarpspeed.registry.MaterialRegistry;
import java.util.*;
import static dev.underswap1.projectwarpspeed.materials.MaterialProperties.*;
import com.google.gson.*;

@SuppressWarnings("NonAsciiCharacters")
public class AlloyGenerator {

    private static final double KV = 0.08; // density excess volume constant
    private static final double KN = 0.0006; // conductivity scattering
    private final JsonArray generatedAlloys;

    public AlloyGenerator() {
        this.generatedAlloys = new JsonArray();
    }

    public MaterialProperties generateAlloy(List<String> materialNames, List<Double> fractionList) {
        if (materialNames.size() != fractionList.size() || materialNames.isEmpty()) {
            System.out.println("Invalid alloy input.");
            return null;
        }

        MaterialProperties[] components = new MaterialProperties[materialNames.size()];
        double[] fractions = new double[fractionList.size()];

        for (int i = 0; i < materialNames.size(); i++) {
            MaterialProperties mat = MaterialRegistry.get(materialNames.get(i).toLowerCase());

            if (mat == null) {
                System.out.println("Material not found: " + materialNames.get(i));
                return null;
            }

            components[i] = mat;
            fractions[i] = fractionList.get(i);
        }

        JsonObject alloyJson = generateAlloy(components, fractions);

        MaterialProperties alloy = new MaterialProperties(
                alloyJson.get("name").getAsString(),
                alloyJson.get("symbol").getAsString(),
                "alloy",
                alloyJson.get("structure").getAsString(),
                0,
                0,
                alloyJson.get("electroconductivity").getAsDouble(),
                alloyJson.get("density20C").getAsDouble(),
                alloyJson.get("meltingPointK").getAsDouble(),
                alloyJson.get("boilingPointK").getAsDouble(),
                alloyJson.get("specificHeat").getAsDouble(),
                alloyJson.get("thermalConductivity").getAsDouble(),
                alloyJson.get("thermalExpansion").getAsDouble(),
                alloyJson.get("magneticType").getAsString(),
                alloyJson.get("hardness").getAsDouble(),
                alloyJson.get("youngsModulus").getAsDouble(),
                alloyJson.get("poissonsRatio").getAsDouble(),
                alloyJson.get("toughness").getAsDouble(),
                alloyJson.get("ductility").getAsDouble()
        );

        MaterialRegistry.register(alloy);

        String predefined = getPredefinedCombinationName(components);
        if (predefined != null) {
            MaterialRegistry.registerAlias(predefined.toLowerCase(), alloy);
        }

        return alloy;
    }

    private JsonObject generateAlloy(MaterialProperties[] components, double[] fractions) {
        JsonObject alloy = new JsonObject();
        JsonArray compsArray = new JsonArray();

        for (int i = 0; i < components.length; i++) {
            JsonObject compObj = new JsonObject();
            compObj.addProperty("material", components[i].name);
            compObj.addProperty("fraction", fractions[i]);
            compsArray.add(compObj);
        }

        alloy.add("components", compsArray);
        alloy.addProperty("name", autoName(components, fractions));

        StringBuilder symbolBuilder = new StringBuilder();
        MaterialProperties[] sorted = components.clone();
        Arrays.sort(sorted, Comparator.comparing(m -> m.symbol));
        for (MaterialProperties m : sorted) symbolBuilder.append(m.symbol);
        alloy.addProperty("symbol", symbolBuilder.toString());

        alloy.addProperty("structure", predictStructure(components));
        alloy.addProperty("density20C", calculateDensity(components, fractions));
        alloy.addProperty("electroconductivity", calculateConductivity(components, fractions));
        alloy.addProperty("thermalConductivity", calculateThermalConductivity(components, fractions));
        alloy.addProperty("specificHeat", calculateSpecificHeat(components, fractions));
        alloy.addProperty("meltingPointK", calculateMeltingPoint(components, fractions));
        alloy.addProperty("boilingPointK", calculateBoilingPoint(components, fractions));
        alloy.addProperty("youngsModulus", calculateYoungsModulus(components, fractions));
        alloy.addProperty("poissonsRatio", calculatePoissonsRatio(components, fractions));
        alloy.addProperty("hardness", calculateHardness(components, fractions));
        alloy.addProperty("toughness", calculateToughness(components, fractions));
        alloy.addProperty("ductility", calculateDuctility(components, fractions));
        alloy.addProperty("thermalExpansion", calculateThermalExpansion(components, fractions));
        alloy.addProperty("magneticType", determineMagnetic(components));

        generatedAlloys.add(alloy);

        return alloy;
    }

    private String autoName(MaterialProperties[] components, double[] fractions) {
        int n = components.length;
        MaterialProperties[] comps = components.clone();
        double[] fracs = fractions.clone();

        Arrays.sort(comps, Comparator.comparing(m -> m.name));

        String predefined = getPredefinedCombinationName(comps);
        StringBuilder nameBuilder = new StringBuilder();

        if (predefined != null) {
            nameBuilder.append(predefined);
        } else {
            for (int i = 0; i < n; i++) {
                if (i > 0) nameBuilder.append("-");
                nameBuilder.append(comps[i].name);
            }
            nameBuilder.append(" Alloy");
        }

        nameBuilder.append(" ");
        for (int i = 0; i < n; i++) {
            if (i > 0) nameBuilder.append(", ");
            int percent = (int) Math.round(fracs[i] * 100);
            nameBuilder.append(comps[i].symbol).append(" ").append(percent).append("%");
        }

        StringBuilder symbolBuilder = new StringBuilder();
        for (int i = 0; i < n; i++) symbolBuilder.append(comps[i].symbol);
        nameBuilder.append(" (").append(symbolBuilder).append(")");

        return nameBuilder.toString();
    }

    private String getPredefinedCombinationName(MaterialProperties[] components) {
        Set<String> names = new HashSet<>();

        for (MaterialProperties m : components) {
            names.add(m.name.toLowerCase());
        }

        // ---------------- Copper alloys ----------------
        if (names.contains("copper") && names.contains("zinc") && names.size() == 2)
            return "Brass";

        if (names.contains("copper") && names.contains("tin") && names.size() == 2)
            return "Bronze";

        if (names.contains("copper") && names.contains("nickel") && names.size() == 2)
            return "Cupronickel";

        if (names.contains("copper") && names.contains("beryllium") && names.size() == 2)
            return "Beryllium Copper";

        if (names.contains("copper") && names.contains("aluminum"))
            return "Aluminum Bronze";

        if (names.contains("copper") && names.contains("silicon"))
            return "Silicon Bronze";

        if (names.contains("copper") && names.contains("silver"))
            return "Coin Silver";

        if (names.contains("copper") && names.contains("gold"))
            return "Tumbaga";

        if (names.contains("copper") && names.contains("tin") && names.contains("zinc"))
            return "Gunmetal";

        if (names.contains("copper") && names.contains("zinc") && names.contains("nickel"))
            return "Nickel Silver";

// ---------------- Iron alloys ----------------
        if (names.contains("iron") && names.contains("carbon") && names.size() == 2)
            return "Steel";

        if (names.contains("iron") && names.contains("chromium") && names.contains("nickel") && names.contains("molybdenum"))
            return "316 Stainless Steel";

        if (names.contains("iron") && names.contains("chromium") && names.contains("nickel"))
            return "Stainless Steel";

        if (names.contains("iron") && names.contains("chromium") && names.size() == 2)
            return "Chromium Steel";

        if (names.contains("iron") && names.contains("nickel") && names.size() == 2)
            return "Invar";

        if (names.contains("iron") && names.contains("manganese"))
            return "Hadfield Steel";

        if (names.contains("iron") && names.contains("silicon"))
            return "Silicon Steel";

        if (names.contains("iron") && names.contains("cobalt"))
            return "Kovar";

        if (names.contains("iron") && names.contains("tungsten"))
            return "High-Speed Steel";

        if (names.contains("iron") && names.contains("vanadium"))
            return "Vanadium Steel";

// ---------------- Aluminum alloys ----------------
        if (names.contains("aluminum") && names.contains("magnesium") && names.size() == 2)
            return "Magnalium";

        if (names.contains("aluminum") && names.contains("zinc") && names.size() == 2)
            return "Zinc-Aluminum Alloy";

        if (names.contains("aluminum") && names.contains("copper") && names.contains("magnesium"))
            return "Duralumin";

        if (names.contains("aluminum") && names.contains("silicon"))
            return "Silumin";

        if (names.contains("aluminum") && names.contains("manganese"))
            return "Manganal";

// ---------------- Nickel alloys ----------------
        if (names.contains("nickel") && names.contains("chromium") && names.contains("iron"))
            return "Inconel";

        if (names.contains("nickel") && names.contains("copper"))
            return "Monel";

        if (names.contains("nickel") && names.contains("chromium"))
            return "Nichrome";

        if (names.contains("nickel") && names.contains("cobalt"))
            return "Nickel-Cobalt Alloy";

// ---------------- Lead alloys ----------------
        if (names.contains("lead") && names.contains("tin"))
            return "Solder";

        if (names.contains("lead") && names.contains("antimony"))
            return "Type Metal";

// ---------------- Titanium alloys ----------------
        if (names.contains("titanium") && names.contains("aluminum") && names.contains("vanadium"))
            return "Ti-6Al-4V";

        if (names.contains("titanium") && names.contains("nickel"))
            return "Shape Memory Alloy";

        if (names.contains("titanium") && names.contains("molybdenum"))
            return "Beta Titanium";

// ---------------- Cobalt alloys ----------------
        if (names.contains("cobalt") && names.contains("chromium"))
            return "Stellite";

// ---------------- Precious metal alloys ----------------
        if (names.contains("gold") && names.contains("silver"))
            return "Electrum";

        if (names.contains("gold") && names.contains("copper"))
            return "Rose Gold";

        if (names.contains("silver") && names.contains("copper"))
            return "Sterling Silver";

        if (names.contains("gold") && names.contains("nickel"))
            return "White Gold";

        if (names.contains("gold") && names.contains("palladium"))
            return "Palladium White Gold";

// ---------------- Zinc alloys ----------------
        if (names.contains("zinc") && names.contains("aluminum") && names.contains("magnesium"))
            return "Zamak";

// ---------------- Magnesium alloys ----------------
        if (names.contains("magnesium") && names.contains("aluminum") && names.contains("zinc"))
            return "AZ Alloy";

        return null;
    }

    private String predictStructure(MaterialProperties[] c) {
        double mismatch = 0;
        double pairs = 0;
        for (int i = 0; i < c.length; i++) {
            for (int j = i + 1; j < c.length; j++) {
                double r1 = getAtomicRadius(c[i]);
                double r2 = getAtomicRadius(c[j]);
                mismatch += Math.abs(r1 - r2) / ((r1 + r2) / 2.0);
                pairs++;
            }
        }
        double avgMismatch = mismatch / pairs;
        if (avgMismatch < 0.08) return c[0].phase_stp;
        else if (avgMismatch < 0.15) return "distorted lattice";
        else return "intermetallic";
    }

    // ------------------- Property calculations -------------------

    private double calculateDensity(MaterialProperties[] c, double[] f) {
        double V = 0;
        for (int i = 0; i < c.length; i++) V += f[i] / c[i].density20C;
        for (int i = 0; i < c.length; i++)
            for (int j = i + 1; j < c.length; j++) {
                double ε = Math.abs(getAtomicRadius(c[i]) - getAtomicRadius(c[j])) / ((getAtomicRadius(c[i]) + getAtomicRadius(c[j])) / 2.0);
                V -= KV * f[i] * f[j] * ε;
            }
        return 1.0 / V;
    }

    private double calculateConductivity(MaterialProperties[] c, double[] f) {
        double res = 0;
        for (int i = 0; i < c.length; i++) res += f[i] / c[i].electroconductivity;
        for (int i = 0; i < c.length; i++)
            for (int j = i + 1; j < c.length; j++) {
                double ΔZ = Math.abs(getValenceElectrons(c[i]) - getValenceElectrons(c[j]));
                res += KN * f[i] * f[j] * ΔZ * ΔZ;
            }
        return 1.0 / res;
    }

    private int getValenceElectrons(MaterialProperties m) {
        int Z = m.atomic_number;
        if (Z == 1) return 1;
        if (Z == 2) return 2;
        if (Z >= 3 && Z <= 10) return Z - 2;
        if (Z >= 11 && Z <= 18) return Z - 10;
        if (Z >= 19 && Z <= 36) return Z - 18;
        if (Z >= 37 && Z <= 54) return Z - 36;
        if (Z >= 55 && Z <= 86) return Z - 54;
        if (Z >= 87 && Z <= 118) return Z - 86;
        return Z % 8; // fallback
    }

    private double calculateThermalConductivity(MaterialProperties[] c, double[] f) {
        double k = 0, ΔEN = 0;
        for (int i = 0; i < c.length; i++) k += f[i] * c[i].thermalConductivity;
        for (int i = 0; i < c.length; i++)
            for (int j = i + 1; j < c.length; j++) ΔEN += Math.abs(getValenceElectrons(c[i]) - getValenceElectrons(c[j])) * f[i] * f[j];
        return k * Math.exp(-0.8 * ΔEN);
    }

    private double calculateSpecificHeat(MaterialProperties[] c, double[] f) {
        double Cp = 0, cFactor = 0;
        for (int i = 0; i < c.length; i++) Cp += f[i] * c[i].specificHeat;
        for (int i = 0; i < c.length; i++)
            for (int j = i + 1; j < c.length; j++) cFactor += f[i] * f[j];
        return Cp * (1 + 0.1 * cFactor);
    }

    private double calculateMeltingPoint(MaterialProperties[] c, double[] f) {
        double Tm = 0, delta = 0;
        for (int i = 0; i < c.length; i++) Tm += f[i] * c[i].meltingPointK;
        for (int i = 0; i < c.length; i++)
            for (int j = i + 1; j < c.length; j++) delta += f[i] * f[j] * Math.abs(c[i].meltingPointK - c[j].meltingPointK);
        return Tm - 0.2 * delta;
    }

    private double calculateBoilingPoint(MaterialProperties[] c, double[] f) {
        double Tb = 0, delta = 0;
        for (int i = 0; i < c.length; i++) Tb += f[i] * c[i].boilingPointK;
        for (int i = 0; i < c.length; i++)
            for (int j = i + 1; j < c.length; j++) delta += f[i] * f[j] * Math.abs(c[i].boilingPointK - c[j].boilingPointK);
        return Tb - 0.1 * delta;
    }

    private double calculateYoungsModulus(MaterialProperties[] c, double[] f) {
        double invE = 0;
        for (int i = 0; i < c.length; i++) invE += f[i] / c[i].youngsModulus;
        for (int i = 0; i < c.length; i++)
            for (int j = i + 1; j < c.length; j++) invE += 0.02 * f[i] * f[j] * Math.abs(getAtomicRadius(c[i]) - getAtomicRadius(c[j]));
        return 1.0 / invE;
    }

    private double calculatePoissonsRatio(MaterialProperties[] c, double[] f) {
        double ν = 0;
        for (int i = 0; i < c.length; i++) ν += f[i] * c[i].poissonsRatio;
        for (int i = 0; i < c.length; i++)
            for (int j = i + 1; j < c.length; j++) ν += 0.01 * f[i] * f[j] * Math.abs(getAtomicRadius(c[i]) - getAtomicRadius(c[j]));
        return ν;
    }

    private double calculateHardness(MaterialProperties[] c, double[] f) {
        double H = 0, ε = 0, cFactor = 0;
        for (int i = 0; i < c.length; i++) H += f[i] * c[i].hardness;
        for (int i = 0; i < c.length; i++)
            for (int j = i + 1; j < c.length; j++) {
                ε += Math.abs(getAtomicRadius(c[i]) - getAtomicRadius(c[j])) / ((getAtomicRadius(c[i]) + getAtomicRadius(c[j])) / 2.0);
                cFactor += f[i] * f[j];
            }
        return H * (1 + 3 * Math.pow(ε, 1.5) * Math.sqrt(cFactor));
    }

    private double calculateToughness(MaterialProperties[] c, double[] f) {
        double T = 0, ε = 0, cFactor = 0;
        for (int i = 0; i < c.length; i++) T += f[i] * c[i].toughness;
        for (int i = 0; i < c.length; i++)
            for (int j = i + 1; j < c.length; j++) {
                ε += Math.abs(getAtomicRadius(c[i]) - getAtomicRadius(c[j])) / ((getAtomicRadius(c[i]) + getAtomicRadius(c[j])) / 2.0);
                cFactor += f[i] * f[j];
            }
        return T * (1 - 1.2 * cFactor * ε);
    }

    private double calculateDuctility(MaterialProperties[] c, double[] f) {
        double D = 0, ε = 0, cFactor = 0;
        for (int i = 0; i < c.length; i++) D += f[i] * c[i].ductility;
        for (int i = 0; i < c.length; i++)
            for (int j = i + 1; j < c.length; j++) {
                ε += Math.abs(getAtomicRadius(c[i]) - getAtomicRadius(c[j])) / ((getAtomicRadius(c[i]) + getAtomicRadius(c[j])) / 2.0);
                cFactor += f[i] * f[j];
            }
        return D * (1 - 1.5 * cFactor * ε);
    }

    private double calculateThermalExpansion(MaterialProperties[] c, double[] f) {
        double α = 0, ε = 0, cFactor = 0;
        for (int i = 0; i < c.length; i++) α += f[i] * c[i].thermalExpansion;
        for (int i = 0; i < c.length; i++)
            for (int j = i + 1; j < c.length; j++) {
                ε += Math.abs(getAtomicRadius(c[i]) - getAtomicRadius(c[j])) / ((getAtomicRadius(c[i]) + getAtomicRadius(c[j])) / 2.0);
                cFactor += f[i] * f[j];
            }
        return α * (1 + 2 * cFactor * ε);
    }

    private String determineMagnetic(MaterialProperties[] c) {
        boolean ferro = false, para = false;
        for (MaterialProperties m : c) {
            if (m.magneticType.equals("ferromagnetic")) ferro = true;
            else if (m.magneticType.equals("paramagnetic")) para = true;
        }
        if (ferro) return "ferromagnetic";
        else if (para) return "paramagnetic";
        else return "diamagnetic";
    }

    private double getAtomicRadius(MaterialProperties m) {
        return ATOMIC_RADIUS_PM.getOrDefault(m.symbol, 125.0) * 0.01;
    }

    public JsonArray getGeneratedAlloys() {
        return generatedAlloys;
    }
}