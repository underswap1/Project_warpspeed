package dev.underswap1.projectwarpspeed.materials;

import java.util.Map;

@SuppressWarnings("unused")
public class MaterialProperties {

    public final String name;
    public final String symbol;
    public final String category;
    public final String phase_stp;              // state at standard pressure and temp
    public final int atomic_number;
    public final double atomic_mass;            // g/mol
    public double electroconductivity;

    public double density20C;                   // g/cm³ at 20°C
    public double meltingPointK;                // K
    public double boilingPointK;                // K
    public double specificHeat;                 // J/(kg*K)
    public double thermalConductivity;          // W/(m*K)
    public double thermalExpansion;             // 1/K

    public final String magneticType;           // ferromagnetic, paramagnetic, diamagnetic

    public double hardness;                     // Mohs
    public double youngsModulus;                // GPa (stress)
    public double poissonsRatio;                // (stress)
    public double toughness;                    // J/m²
    public double ductility;                     // % elongated

    /**
     * Full constructor with all fields
     */
    public MaterialProperties(
            String name,
            String symbol,
            String category,
            String phase_stp,
            int atomic_number,
            double atomic_mass,
            double electroconductivity,
            double density20C,
            double meltingPointK,
            double boilingPointK,
            double specificHeat,
            double thermalConductivity,
            double thermalExpansion,
            String magneticType,
            double hardness,
            double youngsModulus,
            double poissonsRatio,
            double toughness,
            double ductility
    ) {
        this.name = name;
        this.symbol = symbol;
        this.category = category;
        this.phase_stp = phase_stp;
        this.atomic_number = atomic_number;
        this.atomic_mass = atomic_mass;
        this.electroconductivity = electroconductivity;
        this.density20C = density20C;
        this.meltingPointK = meltingPointK;
        this.boilingPointK = boilingPointK;
        this.specificHeat = specificHeat;
        this.thermalConductivity = thermalConductivity;
        this.thermalExpansion = thermalExpansion;
        this.magneticType = magneticType;
        this.hardness = hardness;
        this.youngsModulus = youngsModulus;
        this.poissonsRatio = poissonsRatio;
        this.toughness = toughness;
        this.ductility = ductility;
    }

    /**
     * Copy constructor (needed for block-specific overrides)
     */
    public MaterialProperties(MaterialProperties other) {
        this.name = other.name;
        this.symbol = other.symbol;
        this.category = other.category;
        this.phase_stp = other.phase_stp;
        this.atomic_number = other.atomic_number;
        this.atomic_mass = other.atomic_mass;
        this.electroconductivity = other.electroconductivity;
        this.density20C = other.density20C;
        this.meltingPointK = other.meltingPointK;
        this.boilingPointK = other.boilingPointK;
        this.specificHeat = other.specificHeat;
        this.thermalConductivity = other.thermalConductivity;
        this.thermalExpansion = other.thermalExpansion;
        this.magneticType = other.magneticType;
        this.hardness = other.hardness;
        this.youngsModulus = other.youngsModulus;
        this.poissonsRatio = other.poissonsRatio;
        this.toughness = other.toughness;
        this.ductility = other.ductility;
    }
    // calculates temperature at a given temperature of a material
    public double getDensity(double temperatureC) {
        return density20C / (1 + thermalExpansion * (temperatureC - 20));
    }
    //
    static final Map<String, Double> ATOMIC_RADIUS_PM = Map.<String, Double>ofEntries(
            Map.entry("H", 53.0),
            Map.entry("He", 31.0),
            Map.entry("Li", 167.0),
            Map.entry("Be", 112.0),
            Map.entry("B", 87.0),
            Map.entry("C", 67.0),
            Map.entry("N", 56.0),
            Map.entry("O", 48.0),
            Map.entry("F", 42.0),
            Map.entry("Ne", 38.0),
            Map.entry("Na", 190.0),
            Map.entry("Mg", 145.0),
            Map.entry("Al", 118.0),
            Map.entry("Si", 111.0),
            Map.entry("P", 98.0),
            Map.entry("S", 88.0),
            Map.entry("Cl", 79.0),
            Map.entry("Ar", 71.0),
            Map.entry("K", 243.0),
            Map.entry("Ca", 194.0),
            Map.entry("Sc", 184.0),
            Map.entry("Ti", 176.0),
            Map.entry("V", 171.0),
            Map.entry("Cr", 166.0),
            Map.entry("Mn", 161.0),
            Map.entry("Fe", 156.0),
            Map.entry("Co", 152.0),
            Map.entry("Ni", 149.0),
            Map.entry("Cu", 145.0),
            Map.entry("Zn", 142.0),
            Map.entry("Ga", 136.0),
            Map.entry("Ge", 125.0),
            Map.entry("As", 114.0),
            Map.entry("Se", 103.0),
            Map.entry("Br", 94.0),
            Map.entry("Kr", 88.0),
            Map.entry("Rb", 265.0),
            Map.entry("Sr", 215.0),
            Map.entry("Y", 212.0),
            Map.entry("Zr", 206.0),
            Map.entry("Nb", 198.0),
            Map.entry("Mo", 190.0),
            Map.entry("Tc", 183.0),
            Map.entry("Ru", 178.0),
            Map.entry("Rh", 173.0),
            Map.entry("Pd", 169.0),
            Map.entry("Ag", 165.0),
            Map.entry("Cd", 161.0),
            Map.entry("In", 156.0),
            Map.entry("Sn", 145.0),
            Map.entry("Sb", 133.0),
            Map.entry("Te", 123.0),
            Map.entry("I", 115.0),
            Map.entry("Xe", 108.0),
            Map.entry("Cs", 260.0),
            Map.entry("Ba", 215.0)
    );
}