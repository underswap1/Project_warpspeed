package dev.underswap1.projectwarpspeed.materials;

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

    /**
     * Returns the density at the given temperature (°C)
     */
    public double getDensity(double temperatureC) {
        return density20C / (1 + thermalExpansion * (temperatureC - 20));
    }
}