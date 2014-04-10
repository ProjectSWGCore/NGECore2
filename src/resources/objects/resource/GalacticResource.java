/*******************************************************************************
 * Copyright (c) 2013 <Project SWG>
 * 
 * This File is part of NGECore2.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Using NGEngine to work with NGECore2 is making a combined work based on NGEngine. 
 * Therefore all terms and conditions of the GNU Lesser General Public License cover the combination.
 ******************************************************************************/
package resources.objects.resource;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import main.NGECore;
import protocol.swg.SurveyMapUpdateMessage;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import resources.objects.waypoint.WaypointObject;

import com.sleepycat.je.Environment;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.NotPersistent;

import engine.clients.Client;
import engine.resources.objects.IPersistent;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;

/** 
 * @author Charon 
 */

@Entity(version=0)
public class GalacticResource extends SWGObject implements IPersistent {
	
	@NotPersistent
	private Transaction txn;
	
	private String name;
	private String fileName;
	private String category;
	private Long id; // use server highest
	
	private long spawnTime;
	private long vanishTime;

	
	@NotPersistent
	public static final int INT_COLDRESISTANCE     = 0;
	@NotPersistent
	public static final int INT_CONDUCTIVITY       = 1;
	@NotPersistent
	public static final int INT_DECAYRESISTANCE    = 2;
	@NotPersistent
	public static final int INT_HEATRESISTANCE     = 3;
	@NotPersistent
	public static final int INT_MALLEABILITY       = 4;
	@NotPersistent
	public static final int INT_SHOCKRESISTANCE    = 5;
	@NotPersistent
	public static final int INT_UNITTOUGHNESS      = 6;
	@NotPersistent
	public static final int INT_ENTANGLERESISTANCE = 7;
	@NotPersistent
	public static final int INT_POTENTIALENERGY    = 8;
	@NotPersistent
	public static final int INT_OVERALLQUALITY     = 9;
	@NotPersistent
	public static final int INT_FLAVOR             = 10;
	
	@NotPersistent
	public static final String[] statNamesLookup = {"Cold Resistance",
		                                            "Conductivity",
		                                            "Decay Resistance",
		                                            "Heat Resistance",
		                                            "Malleability",
		                                            "Shock Resistance",
		                                            "Unit Toughness",
		                                            "Entangle Resistance",
		                                            "Potential Energy",
		                                            "Overall Quality",
		                                            "Flavor"};
	

	private byte poolNumber;	
	private String stfName;
	private String iffName;
	private int type;
	private byte generalType;	
	private byte containerType;
	private String resourceClass;
	private String getresourceType;	
	
	//@NotPersistent
	Vector<PlanetDeposits> galacticDepositList = new Vector<PlanetDeposits>();
	
	//@NotPersistent
	Vector<Integer> spawnedPlanetIDs = new Vector<Integer>();

	short[] resourceStats = new short[11];
	
	@NotPersistent
	private static int RES_CAT_HI   = 1;  // Up to 99%, easy to find spots over 90%
	@NotPersistent
	private static int RES_CAT_MID  = 2;  // Up to 90%, often have to settle at around 80%
	@NotPersistent
	private static int RES_CAT_LO   = 3;  // Up to 70%, often have to settle around 60%
	@NotPersistent
	private static int RES_CAT_CRE  = 4;  // Creature resource
	
	@NotPersistent // Direct Persistence Layer entity reference restriction
	private ResourceRoot resourceRoot;
	
	private int resourceRootID; // This is for re-referencing the resourceRoot in the reloaded resourceRoot collection
								// due to the DPL entity reference restriction
	
	@NotPersistent
	private double minDist = 99999.0;
	
	
	public GalacticResource(){	
		super();
	}
	
	public GalacticResource(long objectID, Planet planet, Point3D position, Quaternion orientation, String template){
		super(objectID, planet, position, orientation, template);
		this.id = objectID;
	}

	public GalacticResource(long id){	
		this.id = id;
	}
	
	public GalacticResource(String name, String fileName, String category){
		this.name = name;
		this.fileName = fileName;
		this.category = category;
		this.type = type;
		long range = 1234567L;
		Random r = new Random();
		this.id = (long)(r.nextDouble()*range);
	}
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long Id){
		this.id = Id;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public String getCategory(){
		return category;
	}	
	
	public byte getPoolNumber(){
		return poolNumber;
	}	
	
	public void setPoolNumber(byte poolNumber){
		this.poolNumber = poolNumber;
	}	
	
	public byte getGeneralType(){
		return generalType;
	}	
	
	public void setGeneralType(byte generalType){
		this.generalType = generalType;
	}	
	
	public boolean isSpawnedOn(int planetId){
		if (spawnedPlanetIDs.contains(planetId))
				return true;
		return false;
	}
	
	public void initializeNewGalaxyResource(Vector<String> completeResourceNameHistory){
		galacticDepositList.clear();
		
		constructResourceName(completeResourceNameHistory);
		generateResourceType();
		generateResourceStats();
		generatePlanetarySpawns();
		generateSpawnTimes();
		setIffFileName(resourceRoot.getResourceFileName());
		this.category = resourceRoot.getResourceClass();
		this.setGeneralType(resourceRoot.getGeneralType());
		this.setResourceRootID(resourceRoot.getResourceRootID()); // FK
					//setResourceContainerTemplateID(resourceRoot.getTemplateID());
	}
	
	public void generateResourceType(){
//		this.setResourceType(template.getResourceType());
//		this.setResourceClass(template.getResourceClass());
	}
	
	public void generateResourceStats(){
		// Generate the stats randomly, considering the caps	
		for (int k=0;k<resourceRoot.getResourceMinCaps().length; k++) {
			short result = 0;
			short randomStatValue = 0;
			if ((resourceRoot.getResourceMaxCaps()[k]-resourceRoot.getResourceMinCaps()[k])>0)
				randomStatValue = (short) (resourceRoot.getResourceMinCaps()[k] + new Random().nextInt(resourceRoot.getResourceMaxCaps()[k]-resourceRoot.getResourceMinCaps()[k]));
			
			resourceStats[k] = randomStatValue;
		}	

	}
	
	//Better not to make this publically available
	private void setResourceStats(short[] statArray){
		this.resourceStats = statArray;
	}
	
	public short[] getResourceStats(){
		return this.resourceStats;
	}
	
	public void setResourceRoot(ResourceRoot root){
		this.resourceRoot = root;
	}
	
	public ResourceRoot getResourceRoot(){
		return this.resourceRoot;
	}
	
	public void setPlanetID(int planetID){
		spawnedPlanetIDs.add(planetID);
	}
	
	public void generateSpawnTimes(){
		long minimumTime = resourceRoot.getMinimalLifeTime();
		long maximumTime = resourceRoot.getMaximalLifeTime();
		long randomTime  =  new Random(maximumTime).nextLong();
		this.spawnTime = System.currentTimeMillis();
		this.vanishTime = spawnTime + minimumTime + randomTime;
	}
			
	public int[] generateRandomPlanetArray(){
		int planets  =  6 + new Random().nextInt(2);
		int[] outpool = new int[planets];
		int[] pool = new int[10]; // 10 Number of SWG planets
		for (int i = 0; i < 10; i++) pool[i] = i+1;
		for (int i = 0; i < planets; i++) outpool[i] = i+1;
		shuffleArray(pool);
		for (int i = 0; i < outpool.length; i++)
	    {
	      outpool[i]=pool[i];
	    }
        return outpool;
	}
		
	public void shuffleArray(int[] ar)
	  {
	    Random rnd = new Random();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      int a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	  }
	
	public void generatePlanetarySpawns(){
		// Generate a number of planets the resource will spawn on
		int[] randomPlanetArray = generateRandomPlanetArray();
		if (poolNumber!=4){
			for (int i = 0; i < randomPlanetArray.length; i++){
				if (!spawnedPlanetIDs.contains(i)){
					PlanetDeposits planetDeposits = new PlanetDeposits();
					planetDeposits.setPlanetId(randomPlanetArray[i]);
					spawnedPlanetIDs.add(randomPlanetArray[i]);
					planetDeposits.addDeposits(generateDepositSpawns());	
					galacticDepositList.add(planetDeposits);
				}
			}
		} else {
			PlanetDeposits planetDeposits = new PlanetDeposits();
			planetDeposits.setPlanetId(getAllSpawnedPlanetIds().get(0));
			planetDeposits.addDeposits(generateDepositSpawns());	
			galacticDepositList.add(planetDeposits);
		}
	}
	
	public Vector<ResourceDeposit> generateDepositSpawns(){
		Vector<ResourceDeposit> depositList = new Vector<ResourceDeposit>();
		
		// Calculate deposit positions
		Random generator = new Random();
		int depositQuantity = 8 + generator.nextInt(24);
		
		// datatables/clientregion -> cities
		
		for (int i = 0; i < depositQuantity; i++) {
			ResourceDeposit deposit = new ResourceDeposit();
			generator = new Random(); // Exclude mountains at the edge of the maps
			int spawnCoordsX = generator.nextInt(15000) - 7500;
			int spawnCoordsZ = generator.nextInt(15000) - 7500;
			// check maybe for: if (!core.terrainService.isWater(player.getPlanetId(), spawnCoordsX, spawnCoordsZ)) ?
			generator = new Random();
			int spawnConcentration = 30 + generator.nextInt(70);
			generator = new Random();
			int spawnRadius = 360 + generator.nextInt(1200-360);
			deposit.setSpawnRadius(spawnRadius);
			deposit.setSpawnConcentration(spawnConcentration);	
			//System.out.println("spawnConcentration " + spawnConcentration);
			deposit.setSpawnCoordinatesX(spawnCoordsX);
			deposit.setSpawnCoordinatesZ(spawnCoordsZ);
			depositList.add(deposit);			
		}
		return depositList;
	}
		
	public float deliverConcentrationForSurvey(int planetId, float coordsX, float coordsY) {
		float measuredOutput = 0;
		for (int i = 0; i < galacticDepositList.size(); i++) {
			PlanetDeposits deposits = galacticDepositList.get(i);
			if (deposits.getPlanetId()==planetId){
				List<ResourceDeposit> depositList = deposits.getDeposits();
				for (int j=0;j<depositList.size();j++){
					ResourceDeposit resourceDeposit = depositList.get(j);
					int spawnCoordsX = (int)resourceDeposit.getSpawnCoordinatesX();
					int spawnCoordsZ = (int)resourceDeposit.getSpawnCoordinatesZ();
					int spawnRadius  = (int)resourceDeposit.getSpawnRadius();			
					float deltaX = coordsX-spawnCoordsX;
					float deltaY = coordsY-spawnCoordsZ;
					float localConcentration=0; 
					double hypothen = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
					if (hypothen<minDist)
						minDist = hypothen;
					if (hypothen<spawnRadius) {
						double depositConcentrationFactor = 1.0f -(hypothen/spawnRadius);
						localConcentration = (float)(depositConcentrationFactor*resourceDeposit.getSpawnConcentration());						
						if (localConcentration>measuredOutput) {
							measuredOutput=localConcentration;
						}
					}
				}
			}			
		}
		
		if (measuredOutput<=10.0f)
			return 0;
		//measuredOutput=80.0f;
		return measuredOutput;
	}
	
	public Vector<ResourceConcentration> buildConcentrationsCollection(Point3D measurePos, GalacticResource resource, float radius,float differential, int planetId){
		float leftXCoordinate = measurePos.x - (0.5f*radius);
		float topZCoordinate  = measurePos.z - (0.5f*radius);
		float cursorX = leftXCoordinate;
		float cursorZ = topZCoordinate;
		int divisions=5;
		if (radius<=192.0f){
			divisions = 4;
		}
		if (radius<=64.0f){
			divisions = 3;
		}

		Vector<ResourceConcentration> concentrationsCollection = new Vector<ResourceConcentration>();
		for (int z=0;z<divisions;z++) {
			for (int x=0;x<divisions;x++) {
				ResourceConcentration concentration = new ResourceConcentration(cursorX,cursorZ,(resource.deliverConcentrationForSurvey(planetId, cursorX, cursorZ)));
				concentrationsCollection.add(concentration);
				cursorZ+=differential;
			}
			cursorZ=topZCoordinate;
			cursorX+=differential;
		}		
		return concentrationsCollection;	
	}
	
	public void constructSurveyMapMessage(CreatureObject crafter, Vector<ResourceConcentration> concentrationMap, float radius){
		
		int pointsAmount = 25;
		if (radius<=64.0f) {
			pointsAmount=9;
		} else if (radius<=128.0) {
			pointsAmount = 16;
		} else if (radius<=192.0f) {
			pointsAmount = 16;
		} else if (radius<=256.0f) {
			pointsAmount = 25;
		} else if (radius>=256.0f) {
			pointsAmount = 25;
		}
			
		SurveyMapUpdateMessage smuMsg = new SurveyMapUpdateMessage(concentrationMap, pointsAmount);
		crafter.getClient().getSession().write(smuMsg.serialize());	
		tools.CharonPacketUtils.printAnalysis(smuMsg.serialize());
		
		PlayerObject player = (PlayerObject) crafter.getSlottedObject("ghost");	
		if (smuMsg.getHighestConcentration() > 0.10f){
			WaypointObject lastSurveyWaypoint = player.getLastSurveyWaypoint();
			WaypointObject surveyWaypoint = null;
			if (lastSurveyWaypoint==null){
				surveyWaypoint = (WaypointObject) NGECore.getInstance().objectService.createObject("object/waypoint/shared_world_waypoint_blue.iff", crafter.getPlanet(), smuMsg.getHighestX(), 0 ,smuMsg.getHighestZ());
				surveyWaypoint.setName("Survey location");
				surveyWaypoint.setPlanetCRC(engine.resources.common.CRC.StringtoCRC(crafter.getPlanet().getName()));
				surveyWaypoint.setPosition(new Point3D(smuMsg.getHighestX(),0, smuMsg.getHighestZ()));	
				player.waypointAdd(surveyWaypoint);
				surveyWaypoint.setPosition(new Point3D(smuMsg.getHighestX(),0, smuMsg.getHighestZ()));
				surveyWaypoint.setActive(true);
				surveyWaypoint.setColor((byte)1);
				surveyWaypoint.setStringAttribute("", "");
				player.waypointAdd(surveyWaypoint);
				surveyWaypoint.setName("Survey location");
				surveyWaypoint.setPlanetCRC(engine.resources.common.CRC.StringtoCRC(crafter.getPlanet().getName()));							
				player.setLastSurveyWaypoint(surveyWaypoint);
			} else {
				surveyWaypoint = lastSurveyWaypoint;
				surveyWaypoint.setPosition(new Point3D(smuMsg.getHighestX(),0, smuMsg.getHighestZ()));	
				player.waypointUpdate(surveyWaypoint);	
			}		
			crafter.sendSystemMessage("@survey:survey_waypoint", (byte) 0);
		}
	}
	
	// Redundant just for Testing !!!
	public double getHelperMinDist(){
		return minDist;
	}

	public void constructResourceName(Vector<String> completeResourceNameHistory){
		// ToDo: This is where the database check for past names must be added
		boolean check=true;                  
        while(check)
        {
			this.name = NAME_SYLLABLE_1[(int) new Random().nextInt(NAME_SYLLABLE_1.length-1)] 
					  + NAME_SYLLABLE_2[(int) new Random().nextInt(NAME_SYLLABLE_2.length-1)] 
				      + NAME_SYLLABLE_3[(int) new Random().nextInt(NAME_SYLLABLE_3.length-1)];
			check = completeResourceNameHistory.contains(this.name);
        }
	}
	
	public Vector<PlanetDeposits> getGalacticDepositList(){
		return galacticDepositList;
	}
	
	public Vector<Integer> getAllSpawnedPlanetIds() {
		return spawnedPlanetIDs;
	}
	
	public void setIffFileName(String iffName) {
		this.iffName = iffName;
		this.fileName = iffName;
	}
	
	public String getIffFileName() {
		return this.fileName;
	}

//	protected String getStfFileName() {
//		return stfName;
//	}
//	
//	protected void setSffFileName(String stfName) {
//		this.stfName = iffName;
//	}
	
	public byte getContainerType() {
		return containerType;
	}

	public void setContainerType(byte containerType) {
		this.containerType = containerType;
	}
	
	public long getVanishTime() {
		return vanishTime;
	}

	public void setVanishTime(long vanishTime) {
		this.vanishTime = vanishTime;
	}
	
	@NotPersistent
	public final static String[] GENERAL_TYPES = {
		"resource_mineral",
		"resource_chemical",
		"resource_flora",
		"resource_gas",
		"resource_water",
		"resource_solar",
		"resource_wind",
		"resource_geothermal",
		"resource_tidal",
		"resource_hydron",
		"resource_bones",
		"resource_hide",
		"resource_meat",
		"resource_milk",
		"resource_JTL"
	};
	
	@NotPersistent
	public final static byte GENERAL_MINERAL  = 0;
	@NotPersistent
	public final static byte GENERAL_CHEMICAL = 1;
	@NotPersistent
	public final static byte GENERAL_FLORA    = 2;
	@NotPersistent
	public final static byte GENERAL_GAS      = 3;
	@NotPersistent
	public final static byte GENERAL_WATER    = 4;
	@NotPersistent
	public final static byte GENERAL_SOLAR    = 5;
	@NotPersistent
	public final static byte GENERAL_WIND     = 6;
	@NotPersistent
	public final static byte GENERAL_GEOTHERM = 7;
	@NotPersistent
	public final static byte GENERAL_TIDAL    = 8;
	@NotPersistent
	public final static byte GENERAL_HYDRON   = 9;
	@NotPersistent
	public final static byte GENERAL_BONES    = 10;
	@NotPersistent
	public final static byte GENERAL_HORN     = 11;
	@NotPersistent
	public final static byte GENERAL_HIDE     = 12;
	@NotPersistent
	public final static byte GENERAL_MEAT     = 13;
	@NotPersistent
	public final static byte GENERAL_MILK     = 14;
	@NotPersistent
	public final static byte GENERAL_JTL      = 15;
	
	@NotPersistent
	public final static String[] CONTAINER_TYPES = {
		"inorganic_minerals_small",
		"inorganic_chemicals_small",
		"organic_food_small",
		"inorganic_gas_small",
		"inorganic_water_small",
		"energy_liquid_small",
		"energy_liquid_small",
		"energy_liquid_small",
		"energy_liquid_small",
		"energy_liquid_small",
		"organic_structure_small",
		"organic_structure_small",
		"organic_hide_small",
		"organic_food_small",
		"organic_milk_small",
		"resource_JTL"
	};

	@NotPersistent
	private static String[] NAME_SYLLABLE_1 = {"Zi", "Ao", "Bo", "Gox",
		"Ru", "Su", "Tu", "Uu", "Vu", "Wu", "Xu", "Yu", "Zu",
		"Ie", "Ce", "Ia", "Tip", "Tri", "Rel", "Xio", "Che", "Fa", "Po",
		"Pae", "Doi", "Wa", "Rup", "Ovi", "Ove", "Tae", "Nao", "Lao",
		"Lin", "Lex", "Loex", "Paex", "Bib", "Jaw", "Xat", "Lape", "Su",
		"Sae", "Epo", "Vix", "Vu", "Ke", "Kel", "Koi", "Cix", "Pal", "Pla",
		"Sor", "Lak", "Lek", "Waf", "Weg", "Ga", "Gix", "Nai", "Su",
		"Aa", "Ba", "Ca", "Da", "Ea", "Fa", "Ga", "Ha", "Ia", "Ja", "Ka",
		"La", "Ma", "Na", "Oa", "Pa", "Qa", "Ra", "Sa", "Ta", "Ua", "Va",
		"Wa", "Xa", "Ya", "Za", "Ae", "Be", "Ce", "De", "Ee", "Fe", "Ge",
		"He", "Ie", "Je", "Ke", "Le", "Me", "Ne", "Oe", "Pe", "Qe", "Re",
		"Se", "Te", "Ue", "Ve", "We", "Xe", "Ye", "Ze", "Ai", "Bi", "Ci",
		"Di", "Ei", "Fi", "Gi", "Hi", "Ii", "Ji", "Ki", "Li", "Mi", "Ni",
		"Oi", "Pi", "Qi", "Ri", "Si", "Ti", "Ui", "Vi", "Wi", "Xi", "Yi",
		"Re", "Iso", "Nab", "Co", "Do", "Eo", "Fo", "Go", "Ho", "Io", "Jo",
		"Ko", "Lo", "Mo", "No", "Oo", "Po", "Qo", "Ro", "So", "To", "Uo",
		"Vo", "Wo", "Xo", "Yo", "Zo", "Au", "Bu", "Cu", "Du", "Eu", "Fu",
		"Or", "Ved", "Pos", "Xe", "Ex", "Sti", "Ir", "Ake", "Geh", "Fig",
		"Gu", "Hu", "Iu", "Ju", "Ku", "Lu", "Mu", "Nu", "Ou", "Pu", "Qu",
		"Rel", "Xio", "Ie", "Ce", "Ia", "Tip", "Tri", "Che", "Fa", "Po"};
	
	@NotPersistent
	private static String[] NAME_SYLLABLE_2 = {"gi", "hi", "ii", "qo", "ro", 
		"da", "ea", "fa", "ga", "ha", "ia", "ja", "ka", "la", "ma", "na",
		"oa", "pa", "qa", "ra", "sa", "ta", "ua", "va", "wa", "xa", "ya",
		"za", "ae", "be", "ce", "de", "ee", "fe", "ge", "he", "ie", "je",
		"ke", "le", "me", "ne", "oe", "pe", "qe", "re", "se", "te", "ue",
		"ve", "we", "xe", "ye", "ze", "ai", "bi", "ci", "di", "ei", "fi",
		"uu", "vu", "wu", "xu", "yu", "zu", "mi", "ni", "oi", "pi", "qi",
		"ri", "si", "ti", "ui", "vi", "wi", "xi", "yi", "zi", "ao", "bo",
		"co", "do", "eo", "fo", "go", "ho", "io", "jo", "ko", "lo", "mo",
		"no", "oo", "po", "so", "to", "uo", "vo", "wo", "xo", "mu", "nu",
		"yo", "zo", "au", "bu", "cu", "du", "eu", "fu", "gu", "hu", "iu",
		"aa", "ba", "ca", "ji", "ki", "li"};
	
	@NotPersistent
	private static String[] NAME_SYLLABLE_3 = {"fui", "gti", "hsi", "dwi", 		
		"mna", "nma", "ola", "pka", "qja", "ria", "sha", "tga", "ufa",		
		"eve", "fue", "gte", "hse", "ire", "jqe", "kpe", "loe", "mne",		
		"wde", "xce", "ybe", "zae", "azi", "byi", "cxi", "dwi", "evi",
		"iri", "jqi", "kpi", "loi", "mni", "nmi", "aza", "bya", "cxa",
		"oli", "pki", "qji", "rii", "shi", "tgi", "ufi", "vei", "wdi",
		"xci", "ybi", "zai", "azo", "byo", "cxo", "dwo", "evo", "fuo",
		"dwa", "eva", "fua", "gta", "hsa", "ira", "jqa", "kpa", "loa",
		"gto", "hso", "iro", "jqo", "kpo", "loo", "mno", "nmo", "olo",
		"pko", "qjo", "rio", "sho", "tgo", "ufo", "veo", "wdo", "xco",
		"ybo", "zao", "azu", "byu", "cxu", "dwu", "evu", "fuu", "gtu",
		"nme", "ole", "pke", "qje", "rie", "she", "tge", "ufe", "vee",
		"hsu", "iru", "jqu", "kpu", "lou", "mnu", "nmu", "olu", "pku",
		"vea", "wda", "xca", "yba", "zaa", "aze", "bye", "cxe", "dwe",
		"qju", "riu", "shu", "tgu", "ufu", "veu", "wdu", "xcu", "ybu",
		"zau"};

	
	@Override
	public void createTransaction(Environment env) { txn = env.beginTransaction(null, null);}


	@Override
	public Transaction getTransaction() { return txn; }

	@Override
	public void sendBaselines(Client arg0) {
	}

	public int getResourceRootID() {
		return resourceRootID;
	}

	public void setResourceRootID(int resourceRootID) {
		this.resourceRootID = resourceRootID;
	}

	public String getResourceClass() {
		return resourceClass;
	}

	public void setResourceClass(String resourceClass) {
		this.resourceClass = resourceClass;
	}

	public String getResourceType() {
		return getresourceType;
	}

	public void setResourceType(String getresourceType) {
		this.getresourceType = getresourceType;
	}
	
	public GalacticResource convertToHistoricResource(){
		GalacticResource historicResource = new GalacticResource();
		historicResource.setName(this.getName());
		historicResource.setResourceClass(this.getResourceClass());
		historicResource.setResourceStats(this.getResourceStats());		
		historicResource.setResourceType(this.getResourceType());
		historicResource.setGeneralType(this.getGeneralType());
		historicResource.setId(this.getId());
		historicResource.setIffFileName(this.getIffFileName());
		
		return historicResource;
	}
}
