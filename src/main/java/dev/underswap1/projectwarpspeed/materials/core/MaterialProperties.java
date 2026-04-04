package dev.underswap1.projectwarpspeed.materials.core;

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
            Map.entry("Ba", 215.0),
            Map.entry("La", 187.0),
            Map.entry("Ce", 182.0),
            Map.entry("Pr", 182.0),
            Map.entry("Nd", 181.0),
            Map.entry("Pm", 183.0),
            Map.entry("Sm", 180.0),
            Map.entry("Eu", 199.0),
            Map.entry("Gd", 180.0),
            Map.entry("Tb", 177.0),
            Map.entry("Dy", 178.0),
            Map.entry("Ho", 176.0),
            Map.entry("Er", 176.0),
            Map.entry("Tm", 176.0),
            Map.entry("Yb", 173.0),
            Map.entry("Lu", 174.0),
            Map.entry("Hf", 159.0),
            Map.entry("Ta", 146.0),
            Map.entry("W", 139.0),
            Map.entry("Re", 137.0),
            Map.entry("Os", 135.0),
            Map.entry("Ir", 136.0),
            Map.entry("Pt", 139.0),
            Map.entry("Au", 144.0),
            Map.entry("Hg", 151.0),
            Map.entry("Tl", 170.0),
            Map.entry("Pb", 175.0),
            Map.entry("Bi", 160.0),
            Map.entry("Po", 190.0),
            Map.entry("At", 150.0),
            Map.entry("Rn", 120.0),
            Map.entry("Fr", 348.0),
            Map.entry("Ra", 222.0),
            Map.entry("Ac", 188.0),
            Map.entry("Th", 179.0),
            Map.entry("Pa", 164.0),
            Map.entry("U", 175.0),
            Map.entry("Np", 175.0),
            Map.entry("Pu", 175.0),
            Map.entry("Am", 173.0),
            Map.entry("Cm", 173.0)
    );

    static final Map<String, Double> ELECTRONEGATIVITY = Map.<String, Double>ofEntries(
            Map.entry("H", 2.20),
            Map.entry("He", 0.00),
            Map.entry("Li", 0.98),
            Map.entry("Be", 1.57),
            Map.entry("B", 2.04),
            Map.entry("C", 2.55),
            Map.entry("N", 3.04),
            Map.entry("O", 3.44),
            Map.entry("F", 3.98),
            Map.entry("Ne", 0.00),
            Map.entry("Na", 0.93),
            Map.entry("Mg", 1.31),
            Map.entry("Al", 1.61),
            Map.entry("Si", 1.90),
            Map.entry("P", 2.19),
            Map.entry("S", 2.58),
            Map.entry("Cl", 3.16),
            Map.entry("Ar", 0.00),
            Map.entry("K", 0.82),
            Map.entry("Ca", 1.00),
            Map.entry("Sc", 1.36),
            Map.entry("Ti", 1.54),
            Map.entry("V", 1.63),
            Map.entry("Cr", 1.66),
            Map.entry("Mn", 1.55),
            Map.entry("Fe", 1.83),
            Map.entry("Co", 1.88),
            Map.entry("Ni", 1.91),
            Map.entry("Cu", 1.90),
            Map.entry("Zn", 1.65),
            Map.entry("Ga", 1.81),
            Map.entry("Ge", 2.01),
            Map.entry("As", 2.18),
            Map.entry("Se", 2.55),
            Map.entry("Br", 2.96),
            Map.entry("Kr", 3.00),
            Map.entry("Rb", 0.82),
            Map.entry("Sr", 0.95),
            Map.entry("Y", 1.22),
            Map.entry("Zr", 1.33),
            Map.entry("Nb", 1.60),
            Map.entry("Mo", 2.16),
            Map.entry("Tc", 1.90),
            Map.entry("Ru", 2.20),
            Map.entry("Rh", 2.28),
            Map.entry("Pd", 2.20),
            Map.entry("Ag", 1.93),
            Map.entry("Cd", 1.69),
            Map.entry("In", 1.78),
            Map.entry("Sn", 1.96),
            Map.entry("Sb", 2.05),
            Map.entry("Te", 2.10),
            Map.entry("I", 2.66),
            Map.entry("Xe", 2.60),
            Map.entry("Cs", 0.79),
            Map.entry("Ba", 0.89),
            Map.entry("La", 1.10),
            Map.entry("Ce", 1.12),
            Map.entry("Pr", 1.13),
            Map.entry("Nd", 1.14),
            Map.entry("Pm", 1.13),
            Map.entry("Sm", 1.17),
            Map.entry("Eu", 1.20),
            Map.entry("Gd", 1.20),
            Map.entry("Tb", 1.10),
            Map.entry("Dy", 1.22),
            Map.entry("Ho", 1.23),
            Map.entry("Er", 1.24),
            Map.entry("Tm", 1.25),
            Map.entry("Yb", 1.10),
            Map.entry("Lu", 1.27),
            Map.entry("Hf", 1.30),
            Map.entry("Ta", 1.50),
            Map.entry("W", 2.36),
            Map.entry("Re", 1.90),
            Map.entry("Os", 2.20),
            Map.entry("Ir", 2.20),
            Map.entry("Pt", 2.28),
            Map.entry("Au", 2.54),
            Map.entry("Hg", 2.00),
            Map.entry("Tl", 1.62),
            Map.entry("Pb", 2.33),
            Map.entry("Bi", 2.02),
            Map.entry("Po", 2.0),
            Map.entry("At", 2.2),
            Map.entry("Rn", 0.0),
            Map.entry("Fr", 0.7),
            Map.entry("Ra", 0.9),
            Map.entry("Ac", 1.1),
            Map.entry("Th", 1.30),
            Map.entry("Pa", 1.5),
            Map.entry("U", 1.38),
            Map.entry("Np", 1.36),
            Map.entry("Pu", 1.28),
            Map.entry("Am", 1.13),
            Map.entry("Cm", 1.28)
    );

    static final Map<String, Integer> VALENCE_ELECTRONS = Map.<String, Integer>ofEntries(
            Map.entry("H", 1),
            Map.entry("He", 2),
            Map.entry("Li", 1),
            Map.entry("Be", 2),
            Map.entry("B", 3),
            Map.entry("C", 4),
            Map.entry("N", 5),
            Map.entry("O", 6),
            Map.entry("F", 7),
            Map.entry("Ne", 8),
            Map.entry("Na", 1),
            Map.entry("Mg", 2),
            Map.entry("Al", 3),
            Map.entry("Si", 4),
            Map.entry("P", 5),
            Map.entry("S", 6),
            Map.entry("Cl", 7),
            Map.entry("Ar", 8),
            Map.entry("K", 1),
            Map.entry("Ca", 2),
            Map.entry("Sc", 3),
            Map.entry("Ti", 4),
            Map.entry("V", 5),
            Map.entry("Cr", 6),
            Map.entry("Mn", 7),
            Map.entry("Fe", 8),
            Map.entry("Co", 9),
            Map.entry("Ni", 10),
            Map.entry("Cu", 11),
            Map.entry("Zn", 12),
            Map.entry("Ga", 3),
            Map.entry("Ge", 4),
            Map.entry("As", 5),
            Map.entry("Se", 6),
            Map.entry("Br", 7),
            Map.entry("Kr", 8),
            Map.entry("Rb", 1),
            Map.entry("Sr", 2),
            Map.entry("Y", 3),
            Map.entry("Zr", 4),
            Map.entry("Nb", 5),
            Map.entry("Mo", 6),
            Map.entry("Tc", 7),
            Map.entry("Ru", 8),
            Map.entry("Rh", 9),
            Map.entry("Pd", 10),
            Map.entry("Ag", 11),
            Map.entry("Cd", 12),
            Map.entry("In", 3),
            Map.entry("Sn", 4),
            Map.entry("Sb", 5),
            Map.entry("Te", 6),
            Map.entry("I", 7),
            Map.entry("Xe", 8),
            Map.entry("Cs", 1),
            Map.entry("Ba", 2),
            Map.entry("La", 3),
            Map.entry("Ce", 4),
            Map.entry("Pr", 5),
            Map.entry("Nd", 6),
            Map.entry("Pm", 5),
            Map.entry("Sm", 6),
            Map.entry("Eu", 2),
            Map.entry("Gd", 3),
            Map.entry("Tb", 4),
            Map.entry("Dy", 5),
            Map.entry("Ho", 6),
            Map.entry("Er", 6),
            Map.entry("Tm", 5),
            Map.entry("Yb", 2),
            Map.entry("Lu", 3),
            Map.entry("Hf", 4),
            Map.entry("Ta", 5),
            Map.entry("W", 6),
            Map.entry("Re", 7),
            Map.entry("Os", 8),
            Map.entry("Ir", 9),
            Map.entry("Pt", 10),
            Map.entry("Au", 11),
            Map.entry("Hg", 12),
            Map.entry("Tl", 3),
            Map.entry("Pb", 4),
            Map.entry("Bi", 5),
            Map.entry("Po", 6),
            Map.entry("At", 7),
            Map.entry("Rn", 8),
            Map.entry("Fr", 1),
            Map.entry("Ra", 2),
            Map.entry("Ac", 3),
            Map.entry("Th", 4),
            Map.entry("Pa", 5),
            Map.entry("U", 6),
            Map.entry("Np", 5),
            Map.entry("Pu", 6),
            Map.entry("Am", 5),
            Map.entry("Cm", 6)
    );

    public static final MaterialProperties DEFAULT_METAL = new MaterialProperties(
            "Iron",
            "Fe",
            "Transition Metal",
            "solid",
            26,
            55.845,
            1.0E7,
            7.874,
            1811,
            3134,
            449,
            80.2,
            11.8e-6,
            "ferromagnetic",
            4.0,
            200,
            0.29,
            400,
            0.15
    );
}