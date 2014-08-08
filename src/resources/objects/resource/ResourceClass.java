package resources.objects.resource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ResourceClass {
	IRON("Iron", 0),
	ALUMINUM("Aluminum", 0),
	STEEL("Steel", 0),
	COPPER("Copper", 0),
	ORE_EXTRUSIVE("Extrusive Ore", 0),
	ORE_INTRUSIVE("Intrusive Ore", 0),
	ORE_CARBONITE("Carbonite Ore", 0),
	ORE_CARBONATE("Carbonate Ore", 0),
	ORE_SILICLASTIC("Siliclastic Ore", 0),
	CRYSTALLINE_GEMSTONE("Crystalline Gemstone", 0),
	AMORPHOUS_GEMSTONE("Amorphous Gemstone", 0),
	NON_FERROUS("Non-Ferrous", 0),
	SEDIMENTARY_ORE("Sedimentary Ore", 0),
	IGNEOUS_ORE("Igneous Ore", 0),
	GEMSTONE("Gemstone", 0),
	TYPE_1_CRYSTAL_AMORPHOUS_GEM("Type 1 Crystal Amorphous Gem", 0),
	TYPE_2_CRYSTAL_AMORPHOUS_GEM("Type 2 Crystal Amorphous Gem", 0),
	TYPE_1_CRYSTALLINE_GEM("Type 1 Crystalline Gem", 0),
	TYPE_2_CRYSTALLINE_GEM("Type 2 Crystalline Gem", 0),
	RADIOACTIVE("Radioactive", 0),
	FERROUS_METAL("Ferrous Metal", 0),
	PETROCHEMICAL_SOLID("Petrochemical Solid", 1),
	PETROCHEMICAL_LIQUID("Petrochemical Liquid", 1),
	POLYMER("Polymer", 1),
	PETRO_FUEL("Petro Fuel", 1),
	FIBERPLAST("Fiberplast", 1),
	LIQUID_PETRO_FUEL("Liquid Petro Fuel", 1),
	SOLID_PETRO_FUEL("Solid Petro Fuel", 1),
	LUBRICATING_OIL("Lubricating Oil", 1),
	OIL("Oil", 1),
	VEGETABLE_FUNGI("Vegetable Fungi", 2),
	VEGETABLE_FRUITS("Vegetable Fruits", 2),
	VEGETABLE_BERRIES("Vegetable Berries", 2),
	CONIFER_WOOD("Conifer Wood", 2),
	EVERGREEN_WOOD("Evergreen Wood", 2),
	DECIDUOUS_WOOD("Deciduous Wood", 2),
	FRUIT("Fruit", 2),
	FLOWER_FRUIT("Flower Fruit", 2),
	DOMESTICATED_CORN("Domesticated Corn", 2),
	DOMESTICATED_OATS("Domesticated Oats", 2),
	DOMESTICATED_RICE("Domesticated Rice", 2),
	DOMESTICATED_WHEAT("Domesticated Wheat", 2),
	WILD_CORN("Wild Corn", 2),
	WILD_OATS("Wild Oats", 2),
	WILD_RICE("Wild Rice", 2),
	WILD_WHEAT("Wild Wheat", 2),
	VEGETABLE_BEANS("Vegetable Beans", 2),
	BERRY_FRUIT("Berry Fruit", 2),
	VEGETABLE_FUNGUS("Vegetable Fungus", 2),
	VEGETABLE_GREENS("Vegetable Greens", 2),
	VEGETABLE_TUBERS("Vegetable Tubers", 2),
	REACTIVE_GAS("Reactive Gas", 3),
	INERT_GAS("Inert Gas", 3),
	WATER_VAPOR("Water Vapor", 4),
	SOLAR_RENEWABLE_ENERGY("Solar Renewable Energy", 5),
	WIND_RENEWABLE_ENERGY("Wind Renewable Energy", 6),
	GEOTHERMAL_RENEWABLE_ENERGY("Geothermal Renewable Energy", 7),
	TIDAL_RENEWABLE_ENERGY("Tidal Renewable Energy", 8),
	HYDRON3_RENEWABLE_ENERGY("Hydron-3 Renewable Energy", 9),
	ANIMAL_BONES("Animal Bones", 10),
	AVIAN_BONES("Avian Bones", 10),
	HORN("Horn", 11),
	BRISTLEY_HIDE("Bristley Hide", 12),
	LEATHERY_HIDE("Leathery Hide", 12),
	SCALEY_HIDE("Scaley Hide", 12),
	WOOLY_HIDE("Wooly Hide", 12),
	AVIAN_MEAT("Avian Meat", 13),
	CARNIVORE_MEAT("Carnivore Meat", 13),
	DOMESTICATED_MEAT("Domesticated Meat", 13),
	HERBIVORE_MEAT("Herbivore Meat", 13),
	INSECT_MEAT("Insect Meat", 13),
	WILD_MEAT("Wild Meat", 13),
	CRUSTACEAN_MEAT("Crustacean Meat", 13),
	FISH_MEAT("Fish Meat", 13),
	MOLLUSK_MEAT("Mollusk Meat", 13),
	REPTILIAN_MEAT("Reptilian Meat", 13),
	EGG("Egg", 13),
	EGG_MEAT("Egg Meat", 13),
	DOMESTICATED_MILK("Domesticated Milk", 14),
	WILD_MILK("Wild Milk", 14),
	UNKNOWN("Unknown", 255);
	
	private static final Map<String, ResourceClass> CLASS_MAP = new ConcurrentHashMap<String, ResourceClass>();
	private String name;
	private int type;
	
	static {
		for (ResourceClass c : values())
			CLASS_MAP.put(c.getName(), c);
	}
	
	ResourceClass(String name, int type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public int getType() {
		return type;
	}
	
	public static final ResourceClass forString(String str) {
		ResourceClass c = CLASS_MAP.get(str);
		if (c == null)
			return UNKNOWN;
		return c;
	}
	
	public static final ResourceClass forEndOfString(String str) {
		ResourceClass ret = forString(str);
		if (ret == UNKNOWN) {
			for (ResourceClass c : values())
				if (str.toLowerCase().endsWith(c.getName().toLowerCase()))
					return c;
		}
		return ret;
	}
	
}
