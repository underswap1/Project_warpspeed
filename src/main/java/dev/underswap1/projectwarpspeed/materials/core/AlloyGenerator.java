package dev.underswap1.projectwarpspeed.materials.core;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.underswap1.projectwarpspeed.registry.MaterialRegistry;

import java.util.*;

import static dev.underswap1.projectwarpspeed.materials.core.MaterialProperties.*;

public class AlloyGenerator {

    private static final double KV = 0.08; // density excess volume constant
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

        alloy.addProperty("structure", predictStructure(components, fractions));
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
        alloy.addProperty("magneticType", determineMagnetic(components, fractions));

        generatedAlloys.add(alloy);

        return alloy;
    }

    private String autoName(MaterialProperties[] components, double[] fractions) {
        int n = components.length;
        MaterialProperties[] comps = components.clone();
        double[] fracs = fractions.clone();

        for (int i = 0; i < n -1; i++) {
            for (int j = i + 1; j < n; j++) {
                String ni = comps[i].name == null ? "" : comps[i].name;
                String nj = comps[j].name == null ? "" : comps[j].name;

                if (ni.compareTo(nj) > 0) {
                    MaterialProperties tempM = comps[i];
                    comps[i] = comps[j];
                    comps[j] = tempM;

                    double tempF = fracs[i];
                    fracs[i] = fracs[j];
                    fracs[j] = tempF;
                }
            }
        }

        String predefined = getPredefinedCombinationName(comps.clone());
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

    // ---------------- Predefined Alloys ----------------
    private String getPredefinedCombinationName(MaterialProperties[] components) {
        Set<String> names = new HashSet<>();
        for (MaterialProperties m : components) names.add(m.name.toLowerCase());

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

    // ---------------- Structure Prediction ----------------
    private String predictStructure(MaterialProperties[] c, double[] f) {
        double mismatch = calculateAtomicMismatch(c, f);
        double vec = calculateVEC(c, f);
        double deltaEN = calculateElectronegativitySpread(c, f);
        double entropy = calculateMixingEntropy(f);
        double hmix = calculateMixingEnthalpy(c, f);
        double tm = calculateAverageMeltingPoint(c, f);

        double omega = (tm * entropy) / Math.max(Math.abs(hmix), 1e-6);

        if (hmix < -4 && deltaEN > 0.12 && mismatch > 0.04) {
            return "fcc + ordered phase";
        }

        if (entropy > 11.5 && mismatch < 0.08 && hmix > -12) {
            return "high entropy solid solution";
        }

        if (hmix < -18 && omega < 1.1) {
            return "ordered intermetallic";
        }

        if (omega >= 1.1 && mismatch < 0.066) {
            if (vec >= 8.0) return "fcc solid solution";
            if (vec < 6.87) return "bcc solid solution";
            return "fcc + bcc mixed solid solution";
        }

        if (mismatch < 0.12 && deltaEN < 0.22) {
            return "distorted lattice";
        }

        if (hmix < -8) {
            return "partially ordered intermetallic";
        }

        return "intermetallic";
    }

    // ---------------- Helpers ----------------
    private double calculateAtomicMismatch(MaterialProperties[] c, double[] f) {
        double avgR = 0;
        for (int i = 0; i < c.length; i++) avgR += f[i] * getAtomicRadius(c[i]);
        double sum = 0;
        for (int i = 0; i < c.length; i++) {
            double ri = getAtomicRadius(c[i]);
            sum += f[i] * Math.pow(1 - ri / avgR, 2);
        }
        return Math.sqrt(sum);
    }

    private double calculateVEC(MaterialProperties[] c, double[] f) {
        double vec = 0;
        for (int i = 0; i < c.length; i++) vec += f[i] * getValenceElectrons(c[i]);
        return vec;
    }

    private double calculateElectronegativitySpread(MaterialProperties[] c, double[] f) {
        double avgEN = 0;
        for (int i = 0; i < c.length; i++) avgEN += f[i] * getElectronegativity(c[i]);
        double deltaEN = 0;
        for (int i = 0; i < c.length; i++) deltaEN += f[i] * Math.pow(getElectronegativity(c[i]) - avgEN, 2);
        return Math.sqrt(deltaEN);
    }

    private double calculateAvgMismatch(MaterialProperties[] c, double[] f){
        int n = c.length;
        if (n < 2) return 0;

        double avgMismatch = 0;
        double pairWeightSum = 0;
        double[] radii = new double[n];
        for (int i = 0; i < n; i++) radii[i] = getAtomicRadius(c[i]);

        for (int i = 0; i < n; i++) {
            for (int j= i + 1; j < n; j++) {
                double eps = Math.abs(radii[i] - radii[j]) / ((radii[i] + radii[j]) / 2.0);
                double weight = f[i] * f[j];
                avgMismatch += eps * weight;
                pairWeightSum += weight;
            }
        }
            if (pairWeightSum > 0) avgMismatch /=pairWeightSum;
            return Math.min(avgMismatch, 0.9);
    }

    private double calculateMixingEnthalpy(MaterialProperties[] c, double[] f) {
        int n = c.length;
        double h = 0;

        for (int i =0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double hij = getPairMixingEnthalpy(c[i].symbol, c[j].symbol);
                double eps = Math.abs(getAtomicRadius(c[i]) - getAtomicRadius(c[j])) / ((getAtomicRadius(c[i]) + getAtomicRadius(c[j])) / 2.0);
                double dEN = Math.abs(getElectronegativity(c[i]) - getElectronegativity(c[j]));
                double electronic = Math.abs(getValenceElectrons(c[i]) - getValenceElectrons(c[j]));

                h += 4.0 * hij * f[i] * f[j] * (1 + 0.28 * eps + 0.16 * dEN + 0.05 * electronic);
            }
        }
        return h;
    }

    private double calculateMassMismatch(MaterialProperties[] c, double[] f) {
        double avgMass = 0;

        for (int i = 0; i < c.length; i++) {
            avgMass += f[i] * c[i].atomic_mass;
        }

        double sum = 0;

        for (int i = 0; i < c.length; i++) {
            sum += f[i] * Math.pow(1 - c[i].atomic_mass / avgMass, 2);
        }

        return Math.sqrt(sum);
    }

    private double calculateAverageMeltingPoint(MaterialProperties[] c, double[ ] f) {
        double tm = 0;
        for (int i = 0; i < c.length; i++) {
            tm += f[i] * c[i].meltingPointK;
        }
        return tm;
    }

    private double calculateMixingEntropy(double[] f) {
        double R = 8.314;
        double s = 0;

        for (double x : f) {
            if (x > 0) s += x * Math.log(x);
        }

        return -R * s;
    }

    // ---------------- Properties ----------------
    public double calculateDensity(MaterialProperties[] c, double[] f) {
        int n = c.length;
        double invDensity = 0;
        double[] radii = new double[n];
        for (int i = 0; i < n; i++) {
            invDensity += f[i] / c[i].density20C;
            radii[i] = getAtomicRadius(c[i]);
        }

        double pairWeightSum = 0;
        double mismatchSum = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double eps = Math.abs(radii[i] - radii[j]) / ((radii[i] + radii[j]) / 2.0);
                double weight = f[i] * f[j];
                mismatchSum += eps * weight;
                pairWeightSum += weight;
            }
        }

        if (pairWeightSum > 0) mismatchSum /= pairWeightSum;

        double hmix = calculateMixingEnthalpy(c, f);
        double correction = 1 + KV * mismatchSum - 0.002 * hmix;

        return 1.0 / (invDensity * correction);
    }

    public double calculateConductivity(MaterialProperties[] c, double[] f) {
        int n = c.length;
        double rho = 0;

        for (int i = 0; i < n; i++) {
            double sigma_i = c[i].electroconductivity;
            if (sigma_i <= 0) sigma_i = 1e6;
            rho += f[i] / sigma_i;
        }

        double scattering = 0;
        double pairWeightSum = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double weight = f[i] * f[j];
                double dZ = Math.abs(getValenceElectrons(c[i]) - getValenceElectrons(c[j]));
                double eps = Math.abs(getAtomicRadius(c[i]) - getAtomicRadius(c[j]))
                        / ((getAtomicRadius(c[i]) + getAtomicRadius(c[j])) / 2.0);
                double dEN = Math.abs(getElectronegativity(c[i]) - getElectronegativity(c[j]));

                scattering += weight * (0.50 * dZ + 0.35 * eps + 0.25 * dEN);
                pairWeightSum += weight;
            }
        }
        if (predictStructure(c, f).contains("ordered")) {
            scattering *= 0.55;
        }

        if (pairWeightSum > 0) scattering /= pairWeightSum;
        rho *= (1 + 3.2 * scattering + 0.9 * calculateMassMismatch(c, f));
        return 1.0 / rho;
    }

    public double calculateThermalConductivity(MaterialProperties[] c, double[] f) {
        int n = c.length;
        double scatteringFactor = 0;
        double pairWeightSum = 0;
        double invK = 0;

        for (int i = 0; i < n; i++) {
            invK += f[i] / c[i].thermalConductivity;
        }
        double k = 1.0 / invK;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double weight = f[i] * f[j];

                double eps = Math.abs(getAtomicRadius(c[i]) - getAtomicRadius(c[j]))
                        / ((getAtomicRadius(c[i]) + getAtomicRadius(c[j])) / 2.0);

                double massFactor = Math.abs(c[i].atomic_mass - c[j].atomic_mass)
                        / ((c[i].atomic_mass + c[j].atomic_mass) / 2.0);

                double dEN = Math.abs(getElectronegativity(c[i]) - getElectronegativity(c[j]));
                double dZ = Math.abs(getValenceElectrons(c[i]) - getValenceElectrons(c[j]));

                scatteringFactor += weight * (0.5 * eps + 0.3 * massFactor + 0.2 * dEN + 0.1 * dZ);

                pairWeightSum += weight;
            }
        }

        if (pairWeightSum > 0) scatteringFactor /= pairWeightSum;

        double massFactor = calculateMassMismatch(c, f);
        double structureFactor = predictStructure(c, f).contains("ordered") ? 1.15 : 1.0;

        return k * structureFactor * Math.exp(-(scatteringFactor + 0.28 * massFactor));
    }

    public double calculateSpecificHeat(MaterialProperties[] c, double[] f) {
        int n = c.length;
        double Cp = 0;
        double excess = 0;
        double pairWeightSum = 0;

        for (int i = 0; i < n; i++) {
            Cp += f[i] * c[i].specificHeat;
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double weight = f[i] * f[j];
                double eps = Math.abs(getAtomicRadius(c[i]) - getAtomicRadius(c[j]))
                        / ((getAtomicRadius(c[i]) + getAtomicRadius(c[j])) / 2.0);
                double massFactor = Math.abs(c[i].atomic_mass - c[j].atomic_mass)
                        / ((c[i].atomic_mass + c[j].atomic_mass) / 2.0);
                excess += weight * (0.5 * eps + 0.3 * massFactor);
                pairWeightSum += weight;
            }
        }

        if (pairWeightSum > 0) excess /= pairWeightSum;

        return Cp * (1 + excess);
    }

    public double calculateMeltingPoint(MaterialProperties[] c, double[] f) {
        int n = c.length;
        double Tm = 0;
        double deltaWeightSum = 0;
        double delta = 0;
        double deltaEN_sum = 0;

        double[] radii = new double[n];
        for (int i = 0; i < n; i++) {
            Tm += f[i] * c[i].meltingPointK;
            radii[i] = getAtomicRadius(c[i]);
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double weight = f[i] * f[j];
                delta += weight * Math.abs(c[i].meltingPointK - c[j].meltingPointK);
                deltaEN_sum += weight * Math.abs(getElectronegativity(c[i]) - getElectronegativity(c[j]));
                deltaWeightSum += weight;
            }
        }

        if (deltaWeightSum > 0) deltaEN_sum /= deltaWeightSum;

        double mismatchFactor = calculateAvgMismatch(c, f);
        double hmix = calculateMixingEnthalpy(c, f);

        return Tm - 0.30 * delta + 35 * deltaEN_sum - 45 * mismatchFactor - 1.2 * hmix;
    }

    public double calculateBoilingPoint(MaterialProperties[] c, double[] f) {
        int n = c.length;
        double Tb = 0;
        double delta = 0;
        double[] radii = new double[n];

        for (int i = 0; i < n; i++) {
            Tb += f[i] * c[i].boilingPointK;
            radii[i] = getAtomicRadius(c[i]);
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double weight = f[i] * f[j];
                double eps = Math.abs(radii[i] - radii[j]) / ((radii[i] + radii[j]) / 2.0);
                delta += weight * Math.abs(c[i].boilingPointK - c[j].boilingPointK) * (1 + eps);
            }
        }

        return Tb - 0.1 * delta;
    }

    public double calculateYoungsModulus(MaterialProperties[] c, double[] f) {
        int n = c.length;
        double voigt = 0;
        double reuss = 0;
        double correction = 0;
        double pairWeightSum = 0;

        for (int i = 0; i < n; i++) {
            voigt += f[i] * c[i].youngsModulus;
            reuss += f[i] / c[i].youngsModulus;
        }

        double E = 0.5 * (voigt + 1.0 / reuss);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double weight = f[i] * f[j];
                double eps = Math.abs(getAtomicRadius(c[i]) - getAtomicRadius(c[j]))
                        / ((getAtomicRadius(c[i]) + getAtomicRadius(c[j])) / 2.0);
                double dEN = Math.abs(getElectronegativity(c[i]) - getElectronegativity(c[j]));
                correction += weight * (0.7 * eps + 0.3 * dEN);
                pairWeightSum += weight;
            }
        }

        if (pairWeightSum > 0) correction /= pairWeightSum;

        double hmix = calculateMixingEnthalpy(c, f);
        double softening = Math.min(0.35, Math.max(0, -hmix / 40.0));

        return E * (1.0 + 0.06 * correction - 0.12 * softening);
    }

    public double calculatePoissonsRatio(MaterialProperties[] c, double[] f) {
        double v = 0;
        for (int i = 0; i < c.length; i++) {
            v += f[i] * c[i].poissonsRatio;
        }
        double mismatch = calculateAvgMismatch(c, f);
        return Math.max(0.18, Math.min(0.42, v * (1 - 0.03 * mismatch)));
    }

    public double calculateHardness(MaterialProperties[] c, double[] f) {
        int n = c.length;
        double baseH = 0;
        double strengthening = 0;
        double pairWeightSum = 0;

        for (int i = 0; i < n; i++) {
            baseH += f[i] * c[i].hardness;
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double weight = f[i] * f[j];
                double eps = Math.abs(getAtomicRadius(c[i]) - getAtomicRadius(c[j]))
                        / ((getAtomicRadius(c[i]) + getAtomicRadius(c[j])) / 2.0);
                double dEN = Math.abs(getElectronegativity(c[i]) - getElectronegativity(c[j]));
                double dZ = Math.abs(getValenceElectrons(c[i]) - getValenceElectrons(c[j]));
                strengthening += weight * (10 * eps + 4 * dEN + 2 * dZ + 4.0 * Math.abs(getPairMixingEnthalpy(c[i].symbol, c[j].symbol)) / 30.0);
                pairWeightSum += weight;
            }
        }

        if (pairWeightSum > 0) strengthening /= pairWeightSum;
        strengthening = 6.2 * Math.pow(strengthening, 0.62);
        double E = calculateYoungsModulus(c, f);

        return (baseH + Math.min(strengthening, 1.4 * baseH)) * (1 + 0.0015 * E);
    }

    public double calculateToughness(MaterialProperties[] c, double[] f) {
        double baseT = 0;
        for (int i = 0; i < c.length; i++) baseT +=f[i] * c[i].toughness;

        double avgMismatch = calculateAvgMismatch(c, f);
        double phasePenalty = predictStructure(c, f).contains("ordered") ? 0.82 : 1.0;

        return baseT * Math.exp(-1.3 * avgMismatch) * phasePenalty * (1 + 0.12 * calculateYoungsModulus(c, f) / 100);
    }

    public double calculateDuctility(MaterialProperties[] c, double[] f) {
        double baseD = 0;
        for (int i = 0; i < c.length; i++) baseD +=f[i] * c[i].ductility;

        double avgMismatch = calculateAvgMismatch(c, f);
        double phaseFactor = predictStructure(c, f).contains("fcc") ? 1.10 : 0.92;
        return baseD * Math.exp(-2.5 * avgMismatch) * phaseFactor;
    }

    public double calculateThermalExpansion(MaterialProperties[] c, double[] f) {
        double baseAlpha = 0;
        for (int i = 0; i < c.length; i++) {
            baseAlpha += f[i] * c[i].thermalExpansion;
        }
        double avgMismatch = calculateAvgMismatch(c, f);
        double dEN = calculateElectronegativitySpread(c, f);
        double phaseFactor = predictStructure(c, f).contains("ordered") ? 0.88 : 1.0;

        return baseAlpha * phaseFactor * (1 + 0.35 * avgMismatch + 0.08 * dEN);
    }

    private String determineMagnetic(MaterialProperties[] c, double[] f) {
        double ferroFraction = 0;
        double suppressor = 0;

        for (int i = 0; i < c.length; i++) {
            if (c[i].symbol.equals("Cr") || c[i].symbol.equals("Mn")) {
                suppressor += f[i];
            }
        }

        for (int i = 0; i < c.length; i++) {
            if (c[i].symbol.equals("Fe") || c[i].symbol.equals("Co") || c[i].symbol.equals("Ni")) {
                ferroFraction += f[i];
            }
        }

        ferroFraction *= (1 - 0.7 * suppressor);

        if (predictStructure(c, f).contains("intermetallic")) {
            return "paramagnetic";
        }

        if (ferroFraction > 0.55) return "ferromagnetic";
        if (ferroFraction > 0.15) return "weakly ferromagnetic";
        return "unknown";
    }

    private int getValenceElectrons(MaterialProperties m) {
        if (!VALENCE_ELECTRONS.containsKey(m.symbol)) throw new IllegalStateException("Valence electrons missing for: " + m.symbol);
        return VALENCE_ELECTRONS.get(m.symbol);
    }

    private double getAtomicRadius(MaterialProperties m) {
        if (!ATOMIC_RADIUS_PM.containsKey(m.symbol)) throw new IllegalStateException("Atomic radius missing for: " + m.symbol);
        return ATOMIC_RADIUS_PM.get(m.symbol);
    }

    private double getElectronegativity(MaterialProperties m) {
        if (!ELECTRONEGATIVITY.containsKey(m.symbol)) throw new IllegalStateException("Electronegativity missing for: " + m.symbol);
        return ELECTRONEGATIVITY.get(m.symbol);
    }

    private double getPairMixingEnthalpy(String a, String b) {
        String key = a.compareTo(b) < 0 ? a + "-" + b : b + "-" + a;

        return switch (key) {
            case "Cu-Ni" -> 4;
            case "Cu-Zn", "Cr-Ni" -> -7;
            case "Ni-Ti" -> -35;
            case "Fe-Al" -> -11;
            case "Ni-Al" -> -22;
            case "Ti-Al" -> -30;
            case "Nb-Ti" -> 2;
            case "Mo-Ta", "W-Ta" -> 1;
            case "Co-Cr" -> -4;
            case "Fe-Cr" -> -1;
            case "Cu-Al" -> -14;
            case "Fe-Ni", "Ti-V" -> -2;
            case "Mn-Ni" -> -8;
            case "Nb-Mo" -> 0;
            case "Al-Co" -> -19;
            case "Al-Cr" -> -10;
            case "Ag-Cu" -> 5;
            default -> {
                double en1 = ELECTRONEGATIVITY.getOrDefault(a, 1.8);
                double en2 = ELECTRONEGATIVITY.getOrDefault(b, 1.8);

                double r1 = ATOMIC_RADIUS_PM.getOrDefault(a, 140.0);
                double r2 = ATOMIC_RADIUS_PM.getOrDefault(b, 140.0);

                int z1 = VALENCE_ELECTRONS.getOrDefault(a, 4);
                int z2 = VALENCE_ELECTRONS.getOrDefault(b, 4);

                double dEN = Math.abs(en1 - en2);
                double dR = Math.abs(r1 - r2) / ((r1 + r2) / 2.0);
                double dZ = Math.abs(z1 - z2);

                double chemical = -9.5 * dEN;
                double strain = -11.0 * dR;
                double electronic = -1.8 * dZ;

                yield chemical + strain + electronic;
            }
        };
    }

    public JsonArray getGeneratedAlloys() {
        return generatedAlloys;
    }
}