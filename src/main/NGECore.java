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
package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IoSession;

import net.engio.mbassy.bus.config.BusConfiguration;
import resources.common.BountyListItem;
import resources.common.ThreadMonitor;
import resources.datatables.GalaxyStatus;
import resources.objects.creature.CreatureObject;
import resources.objects.guild.GuildObject;
import resources.objects.resource.GalacticResource;
import services.AttributeService;
import services.BuffService;
import services.CharacterService;
import services.ConnectionService;
import services.ConversationService;
import services.EntertainmentService;
import services.GroupService;
import services.housing.HousingService;
import services.AdminService;
import services.BrowserService;
import services.InstanceService;
import services.LoginService;
import services.LootService;
import services.PlayerService;
import services.ScriptService;
import services.SimulationService;
import services.SkillModService;
import services.SkillService;
import services.StaticService;
import services.SurveyService;
import services.TerrainService;
import services.WeatherService;
import services.ai.AIService;
import services.bazaar.AuctionItem;
import services.bazaar.BazaarService;
import services.chat.ChatRoom;
import services.chat.ChatService;
import services.chat.Mail;
import services.collections.CollectionService;
import services.combat.CombatService;
import services.command.CombatCommand;
import services.command.CommandService;
import services.equipment.EquipmentService;
import services.gcw.FactionService;
import services.gcw.GCWService;
import services.gcw.InvasionService;
import services.GuildService;
import services.LoginService;
import services.map.MapService;
import services.mission.MissionService;
import services.object.DuplicateId;
import services.object.ObjectId;
import services.object.ObjectService;
import services.object.UpdateService;
import services.pet.MountService;
import services.pet.PetService;
import services.playercities.PlayerCity;
import services.playercities.PlayerCityService;
import services.quest.QuestService;
import services.resources.HarvesterService;
import services.resources.ResourceService;
import services.retro.RetroService;
import services.reverseengineering.ReverseEngineeringService;
import services.spawn.SpawnService;
import services.sui.SUIService;
import services.trade.TradeService;
import services.travel.TravelService;
import tools.CharonPacketLogger;
import tools.DevLogQueuer;
//import services.BattlefieldService;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.CrcStringTableVisitor;
import engine.clientdata.visitors.DatatableVisitor;
import engine.clientdata.visitors.LevelOfDetailVisitor;
import engine.clientdata.visitors.MeshVisitor;
import engine.clientdata.visitors.PortalVisitor;
import engine.clientdata.visitors.TerrainVisitor;
import engine.clientdata.visitors.WorldSnapshotVisitor;
import engine.clientdata.visitors.WorldSnapshotVisitor.SnapshotChunk;
import engine.clients.Client;
import engine.resources.common.CRC;
import engine.resources.common.PHPBB3Auth;
import engine.resources.config.Config;
import engine.resources.config.DefaultConfig;
import engine.resources.container.Traverser;
import engine.resources.database.DatabaseConnection;
import engine.resources.database.ODBCursor;
import engine.resources.database.ObjectDatabase;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.service.InteractiveJythonAcceptor;
import engine.resources.service.NetworkDispatch;
import engine.resources.service.UncaughtExceptionLogger;
import engine.servers.InteractiveJythonServer;
import engine.servers.MINAServer;
import engine.servers.PingServer;


@SuppressWarnings("unused")
public class NGECore {
	
	private static boolean logUnhandledExceptions = true;
	
	public static boolean didServerCrash = false;
	
	private static NGECore instance;
	
	private Config config = null;
	private Config options = null;
	private String motd = "";
	private volatile boolean isShuttingDown = false;
	private long galacticTime = System.currentTimeMillis();
	private int galaxyStatus = -1;
	
	private ConcurrentHashMap<IoSession, Client> clients = new ConcurrentHashMap<IoSession, Client>();
	
	// Database
	
	private DatabaseConnection databaseConnection = null;
	private DatabaseConnection databaseConnection2 = null;
		
	// Services
	public LoginService loginService;
	public RetroService retroService;
	public ConnectionService connectionService;
	public CommandService commandService;
	public CharacterService characterService;
	public FactionService factionService;
	public ObjectService objectService;
	public MapService mapService;
	public UpdateService updateService;
	public TerrainService terrainService;
	public SimulationService simulationService;
	public ScriptService scriptService;
	public ChatService chatService;
	public AttributeService attributeService;
	public SUIService suiService;
	public GuildService guildService;
	public GCWService gcwService;
	public TradeService tradeService;
	public CombatService combatService;
	public PlayerService playerService;
	public BuffService buffService;
	public StaticService staticService;
	public GroupService groupService;
	public SkillService skillService;
	public SkillModService skillModService;
	public EquipmentService equipmentService;
	public TravelService travelService;
	public CollectionService collectionService;
	public EntertainmentService entertainmentService;
	public WeatherService weatherService;
	public SpawnService spawnService;
	public AIService aiService;
	public MissionService missionService;
	public QuestService questService;
	public InstanceService instanceService;
	public SurveyService surveyService;
	public ResourceService resourceService;
	public ConversationService conversationService;
	public BazaarService bazaarService;
	public HousingService housingService;
	public LootService lootService;
	public HarvesterService harvesterService;
	public MountService mountService;
	public PlayerCityService playerCityService;
	public ReverseEngineeringService reverseEngineeringService;
	public PetService petService;
	public BrowserService browserService;
	//public BattlefieldService battlefieldService;
	public InvasionService invasionService;
	public AdminService adminService;
	
	// Login Server
	public NetworkDispatch loginDispatch;
	private MINAServer loginServer;
	
	// Zone Server
	public NetworkDispatch zoneDispatch;
	private MINAServer zoneServer;
	
	// Interactive Jython Console
	public InteractiveJythonAcceptor jythonAcceptor;
	private InteractiveJythonServer jythonServer;

	private ObjectDatabase mailODB;
	private ObjectDatabase guildODB;
	private ObjectDatabase objectIdODB;
	private ObjectDatabase duplicateIdODB;
	private ObjectDatabase chatRoomODB;
	
	private BusConfiguration eventBusConfig = BusConfiguration.Default(1, new ThreadPoolExecutor(1, 4, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>()));

	private ObjectDatabase auctionODB;
	private ObjectDatabase resourceHistoryODB;
	private ObjectDatabase swgObjectODB;
	private ObjectDatabase bountiesODB;
	private ObjectDatabase cityODB;
	
	public static boolean PACKET_DEBUG = false;
	
	public NGECore() {

		instance = this;
	}
	
	public void start() {
		
		instance = this;
		
		final ThreadMonitor deadlockDetector = new ThreadMonitor();
		Thread deadlockMonitor = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					deadlockDetector.findDeadlock();
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		deadlockMonitor.start();
		config = new Config();
		config.setFilePath("nge.cfg");
		if (!(config.loadConfigFile())) {
			config = DefaultConfig.getConfig();
		}
		
		DevLogQueuer devLogQueuer = new DevLogQueuer();
		
		CharonPacketLogger packetLogger;
		if (PACKET_DEBUG)
			packetLogger = new CharonPacketLogger();
		
		options = new Config();
		options.setFilePath("options.cfg");
		if (!(options.loadConfigFile())) {
			System.err.println("Failed to load options.cfg!");
			options = DefaultConfig.getConfig();
		}

		if (options.getInt("CLEAN.ODB.FOLDERS") > 0){
			File baseFolder = new File("./odb");
			
			if (baseFolder.isDirectory()) {
				for (File odbFolder : baseFolder.listFiles()) {
					if (odbFolder.isDirectory()) {
						for (File file : odbFolder.listFiles()) {
							if (!file.isDirectory() && !file.getName().equals("placeholder.txt")) { file.delete(); }
						}
					}
				}
			}
			
			
			System.out.println("Cleaned ODB Folders.");
		}
		
		try(BufferedReader br = new BufferedReader(new FileReader("./motd.txt"))) {
			for(String line; (line = br.readLine()) != null;) {
				motd += line;
			}
		} catch (IOException e1) { e1.printStackTrace(); }

		// Database
		databaseConnection = new DatabaseConnection();
		databaseConnection.connect(config.getString("DB.URL"), config.getString("DB.NAME"), config.getString("DB.USER"), config.getString("DB.PASS"), "postgresql");

		String db2Url = config.getString("DB2.URL");
		if (db2Url == null || db2Url.matches("^\\s*$")) {
			databaseConnection2 = null;
		} else {
			databaseConnection2 = new DatabaseConnection();
			databaseConnection2.connect(config.getString("DB2.URL"), config.getString("DB2.NAME"), config.getString("DB2.USER"), config.getString("DB2.PASS"), "mysql");
		}
		
		setGalaxyStatus(GalaxyStatus.Loading);
		
		if (options.getInt("CLEAN.CHARACTERS.TABLE") > 0) {
			try { databaseConnection.preparedStatement("DELETE FROM characters").execute(); } 
			catch (SQLException e) {e.printStackTrace(); }
			System.out.println("Cleared characters table.");
		}
		
		swgObjectODB = new ObjectDatabase("swgobjects", true, true, true, SWGObject.class);
		mailODB = new ObjectDatabase("mails", true, true, true, Mail.class);
		guildODB = new ObjectDatabase("guild", true, true, true, GuildObject.class);
		objectIdODB = new ObjectDatabase("oids", true, true, true, ObjectId.class);
		duplicateIdODB = new ObjectDatabase("doids", true, true, true, DuplicateId.class);
		chatRoomODB = new ObjectDatabase("chatRooms", true, true, true, ChatRoom.class);
		resourceHistoryODB = new ObjectDatabase("resourcehistory", true, true, true, GalacticResource.class);
		auctionODB = new ObjectDatabase("auction", true, true, true, AuctionItem.class);
		bountiesODB = new ObjectDatabase("bounties", true, true, true, BountyListItem.class);
		cityODB = new ObjectDatabase("cities", true, true, true, PlayerCity.class);
		
		// Services
		loginService = new LoginService(this);
		retroService = new RetroService(this);
		connectionService = new ConnectionService(this);
		characterService = new CharacterService(this);
		mapService = new MapService(this);
		travelService = new TravelService(this);
		
		factionService = new FactionService(this);
		objectService = new ObjectService(this);
		terrainService = new TerrainService(this);
		updateService = new UpdateService(this);
		scriptService = new ScriptService(this);
		commandService = new CommandService(this);
		chatService = new ChatService(this);
		attributeService = new AttributeService(this);
		suiService = new SUIService(this);
		combatService = new CombatService(this);
		playerService = new PlayerService(this);
		buffService = new BuffService(this);
		groupService = new GroupService(this);
		skillService = new SkillService(this);
		skillModService = new SkillModService(this);
		equipmentService = new EquipmentService(this);
		entertainmentService = new EntertainmentService(this);
		conversationService = new ConversationService(this);
		bazaarService = new BazaarService(this);
		housingService = new HousingService(this);
		lootService = new LootService(this);
		harvesterService = new HarvesterService(this);
		mountService = new MountService(this);
		playerCityService = new PlayerCityService(this);
		staticService = new StaticService(this);
		reverseEngineeringService = new ReverseEngineeringService(this);
		petService = new PetService(this);
		
		if (options.getInt("LOAD.RESOURCE.SYSTEM") == 1) {
			surveyService = new SurveyService(this);
			resourceService = new ResourceService(this);
		}
		
		if (config.keyExists("JYTHONCONSOLE.PORT")) {
			int jythonPort = config.getInt("JYTHONCONSOLE.PORT");
			if (jythonPort > 0) {
				
				System.out.println("Starting InteractiveJythonServer on Port " + jythonPort);
				jythonAcceptor = new InteractiveJythonAcceptor();
				jythonServer = new InteractiveJythonServer((IoHandler) jythonAcceptor, jythonPort, this);
				jythonAcceptor.setServer(jythonServer);
				jythonServer.start();
			}
		}
		spawnService = new SpawnService(this);
		aiService = new AIService(this);
		missionService = new MissionService(this);
		questService = new QuestService(this);
		invasionService = new InvasionService(this);
		
		// Ping Server
		
		if(config.keyExists("PING.PORT"))
			if (config.getInt("PING.PORT") != 0) {
				try {
					PingServer pingServer = new PingServer(config.getInt("PING.PORT"));
					pingServer.bind();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else
			System.out.println("Warning: pingServer was not launched. Port equals 0.");
		else
			System.out.println("Warning: pingServer was not launched. Port not specified.");
		
		
		// Login Server
		loginDispatch = new NetworkDispatch(this, false);
		loginDispatch.addService(loginService);
				
		if(config.keyExists("LOGIN.PORT"))
			if (config.getInt("LOGIN.PORT") != 0) {
				loginServer = new MINAServer(loginDispatch, config.getInt("LOGIN.PORT"));
				loginServer.start();
			} else
			System.out.println("Warning: loginServer was not launched. Port equals 0.");
		else
			System.out.println("Warning: loginServer was not launched. Port not specified.");
		
		// Zone Server
		zoneDispatch = new NetworkDispatch(this, true);
		zoneDispatch.addService(retroService);
		zoneDispatch.addService(connectionService);
		zoneDispatch.addService(characterService);
		zoneDispatch.addService(factionService);
		zoneDispatch.addService(objectService);
		zoneDispatch.addService(commandService);
		zoneDispatch.addService(chatService);
		zoneDispatch.addService(suiService);
		zoneDispatch.addService(mapService);
		zoneDispatch.addService(travelService);
		zoneDispatch.addService(playerService);
		zoneDispatch.addService(buffService);
		zoneDispatch.addService(entertainmentService);
		zoneDispatch.addService(missionService);
		zoneDispatch.addService(questService);
		zoneDispatch.addService(bazaarService);
		zoneDispatch.addService(lootService);
		zoneDispatch.addService(mountService);
		zoneDispatch.addService(housingService);
		zoneDispatch.addService(playerCityService);
		zoneDispatch.addService(staticService);
		zoneDispatch.addService(reverseEngineeringService);
		zoneDispatch.addService(petService);
		zoneDispatch.addService(invasionService);
		
		if (options.getInt("LOAD.RESOURCE.SYSTEM") == 1) {
			zoneDispatch.addService(surveyService);
			zoneDispatch.addService(resourceService);
		}
			
		if(config.keyExists("ZONE.PORT"))
			if (config.getInt("ZONE.PORT") != 0) {
				zoneServer = new MINAServer(zoneDispatch, config.getInt("ZONE.PORT"));
				zoneServer.start();
			} else
			System.out.println("Warning: zoneServer was not launched. Port equals 0.");
		else
			System.out.println("Warning: zoneServer was not launched. Port not specified.");
		
		//Start terrainList
		// Original Planets
		terrainService.addPlanet(1, "tatooine", "terrain/tatooine.trn", true);
		terrainService.addPlanet(2, "naboo", "terrain/naboo.trn", true);
		terrainService.addPlanet(3, "corellia", "terrain/corellia.trn", true);
		terrainService.addPlanet(4, "rori", "terrain/rori.trn", true);
		terrainService.addPlanet(5, "lok", "terrain/lok.trn", true);
		terrainService.addPlanet(6, "dantooine", "terrain/dantooine.trn", true);
		terrainService.addPlanet(7, "talus", "terrain/talus.trn", true);
		terrainService.addPlanet(8, "yavin4", "terrain/yavin4.trn", true);
		terrainService.addPlanet(9, "endor", "terrain/endor.trn", true);
		terrainService.addPlanet(10, "dathomir", "terrain/dathomir.trn", true);
		terrainService.addPlanet(11, "mustafar", "terrain/mustafar.trn", true);
		terrainService.addPlanet(12, "kashyyyk_main", "terrain/kashyyyk_main.trn", true);
		//Dungeon Terrains
		// TODO: Fix BufferUnderFlow Errors on loaded of dungeon instances.
		terrainService.addPlanet(13, "kashyyyk_dead_forest", "terrain/kashyyyk_dead_forest.trn", false);
		terrainService.addPlanet(14, "kashyyyk_hunting", "terrain/kashyyyk_hunting.trn", false);
		terrainService.addPlanet(15, "kashyyyk_north_dungeons", "terrain/kashyyyk_north_dungeons.trn", false);
		terrainService.addPlanet(16, "kashyyyk_rryatt_trail", "terrain/kashyyyk_rryatt_trail.trn", false);
		terrainService.addPlanet(17, "kashyyyk_south_dungeons", "terrain/kashyyyk_south_dungeons.trn", false);
		terrainService.addPlanet(18, "adventure1", "terrain/adventure1.trn", false);
		terrainService.addPlanet(19, "adventure2", "terrain/adventure2.trn", false);
		// Tutorial Terrains
		terrainService.addPlanet(20, "dungeon1", "terrain/dungeon1.trn", true);
		terrainService.addPlanet(21, "tutorial", "terrain/tutorial.trn", false); // 21B droid scene
		//terrainService.addPlanet(22, "space_npe_falcon_3", "terrain/space_npe_falcon_3.trn", false);
		
		//Space Zones
		// NOTE: Commented out for now until space is implemented. No need to be loaded into memory when space is not implemented.
		/*terrainService.addPlanet(21, "space_corellia", "terrain/space_corellia.trn", true);
		terrainService.addPlanet(22, "space_corellia_2", "terrain/space_corellia_2.trn", true);
		terrainService.addPlanet(23, "space_dantooine", "terrain/space_dantooine.trn", true);
		terrainService.addPlanet(24, "space_dathomir", "terrain/space_dathomir.trn", true);
		terrainService.addPlanet(25, "space_endor", "terrain/space_endor.trn", true);
		terrainService.addPlanet(26, "space_env", "terrain/space_env.trn", true);
		terrainService.addPlanet(27, "space_halos", "terrain/space_halos.trn", true);
		terrainService.addPlanet(28, "space_heavy1", "terrain/space_heavy1.trn", true);
		terrainService.addPlanet(29, "space_kashyyyk", "terrain/space_kashyyyk.trn", true);
		terrainService.addPlanet(30, "space_light1", "terrain/space_light1.trn", true);
		terrainService.addPlanet(31, "space_lok", "terrain/space_lok.trn", true);
		terrainService.addPlanet(32, "space_naboo", "terrain/space_naboo.trn", true);
		terrainService.addPlanet(33, "space_naboo_2", "terrain/space_naboo_2.trn", true);
		terrainService.addPlanet(34, "space_nova_orion", "terrain/space_nova_orion.trn", true); 
		terrainService.addPlanet(35, "space_npe_falcon", "terrain/space_npe_falcon.trn", true); // Only space_npe_falce_3 is used
		terrainService.addPlanet(36, "space_npe_falcon_2", "terrain/space_npe_falcon_2.trn", true); // Only space_npe_falce_3 is used
		terrainService.addPlanet(37, "space_ord_mantell", "terrain/space_ord_mantell.trn", true);
		terrainService.addPlanet(38, "space_ord_mantell_2", "terrain/space_ord_mantell_2.trn", true);
		terrainService.addPlanet(39, "space_ord_mantell_3", "terrain/space_ord_mantell_3.trn", true);
		terrainService.addPlanet(40, "space_ord_mantell_4", "terrain/space_ord_mantell_4.trn", true);
		terrainService.addPlanet(41, "space_ord_mantell_5", "terrain/space_ord_mantell_5.trn", true);
		terrainService.addPlanet(42, "space_ord_mantell_6", "terrain/space_ord_mantell_6.trn", true);
		terrainService.addPlanet(43, "space_tatooine", "terrain/space_tatooine.trn", true);
		terrainService.addPlanet(44, "space_tatooine_2", "terrain/space_tatooine_2.trn", true);
		terrainService.addPlanet(45, "space_yavin4", "terrain/space_yavin4.trn", true);*/
		
		//end terrainList
		
		if (options.getInt("LOAD.RESOURCE.SYSTEM") > 0) {
			resourceService.loadResources();
		}
		
		chatService.loadChatRooms();
		
		spawnService = new SpawnService(this);
		terrainService.loadClientPois();
		// Travel Points
		travelService.loadTravelPoints();
		simulationService = new SimulationService(this);
		
		
		terrainService.loadSnapShotObjects();
		objectService.loadServerTemplates();		
		objectService.loadObjects();
		harvesterService.loadHarvesters();

		simulationService.insertSnapShotObjects();
		simulationService.insertPersistentBuildings();
		// Zone services that need to be loaded after the above
		zoneDispatch.addService(simulationService);
		
		guildService = new GuildService(this);
		zoneDispatch.addService(guildService);
		
		gcwService = new GCWService(this);
		zoneDispatch.addService(gcwService);
		
		collectionService = new CollectionService(this);
		zoneDispatch.addService(collectionService);

		tradeService = new TradeService(this);
		zoneDispatch.addService(tradeService);
		
		zoneDispatch.addService(skillService);
		
		instanceService = new InstanceService(this);
		zoneDispatch.addService(instanceService);
		
		weatherService = new WeatherService(this);
		weatherService.loadPlanetSettings();
		
		spawnService.loadMobileTemplates();
		spawnService.loadLairTemplates();
		spawnService.loadLairGroups();
		spawnService.loadDynamicGroups();;
		spawnService.loadSpawnAreas();
		
		staticService.spawnStatics();
		
		equipmentService.loadBonusSets();
		
		playerCityService.loadCityRankCaps();
		playerCityService.loadCities();
		
		retroService.run();
		
		browserService = new BrowserService(this);
		//battlefieldService = new BattlefieldService(this);
		adminService = new AdminService(this);
		
		didServerCrash = false;
		System.out.println("Started Server.");
		cleanupCreatureODB();
		setGalaxyStatus(GalaxyStatus.Online);
		
	}

	private void cleanupCreatureODB() {
		ODBCursor cursor = swgObjectODB.getCursor();
		
		List<CreatureObject> deletedObjects = new ArrayList<CreatureObject>();
		
		while(cursor.hasNext()) {
			Object next = cursor.next();
			if (next == null) continue;
			SWGObject creature = (SWGObject) next;
			if(!characterService.playerExists(creature.getObjectID()) && creature instanceof CreatureObject)
				deletedObjects.add((CreatureObject) creature);
		}
		cursor.close();
		
		for(CreatureObject creature : deletedObjects) {
			swgObjectODB.remove(creature.getObjectID());
		}
		
		System.out.println("Deleted " + deletedObjects.size() + " creatures.");
	}

	public void stop() {
		System.out.println("Stopping Servers and Connections.");
		databaseConnection.close();
		databaseConnection2.close();
	}
	
	public void cleanUp() {
		System.out.println("Cleaning Up...");
		long memoryUsed = Runtime.getRuntime().freeMemory();
		System.out.println("Using " + memoryUsed + " bytes of memory.");
		
		config = null;
		databaseConnection = null;
		databaseConnection2 = null;
		Runtime.getRuntime().gc();
		System.out.println("Cleaned Up " + (Runtime.getRuntime().freeMemory() - memoryUsed) + " bytes of memory.");
	}
	
	public void restart() {
		stop();
		cleanUp();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			
		}
		start();
	}
	
	public static void main(String[] args) {
		//With this class, we are overwriting the JVM's way of handling exceptions that are never caught. Very handy so no try/catch spam for every method.
		if (logUnhandledExceptions) Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionLogger("./logs/uncaught"));

		NGECore core = new NGECore();
		
		core.start();
		
		do {
			if (didServerCrash) {
				core.restart();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
			}
		} while (true);
		
	}
	
	public void setGalaxyStatus(int statusId) {
		
		galaxyStatus = statusId;
		int galaxyId = config.getInt("GALAXY_ID");
		
		try {
			PreparedStatement ps = databaseConnection.preparedStatement("UPDATE \"connectionServers\" SET \"statusId\"=? WHERE \"galaxyId\"=?");
			ps.setInt(1, statusId);
			ps.setInt(2, galaxyId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public int getGalaxyStatus() {
		return galaxyStatus;
	}
	
	/*
	 * ---------- Getter methods for NGECore ----------
	 */
	public Config getConfig() {
		return config;
	}
	
	public String getGalaxyName() {
		return config.getString("GALAXY_NAME");
	}
	
	public DatabaseConnection getDatabase1() {
		return databaseConnection;
	}
	
	public DatabaseConnection getDatabase2() {
		return databaseConnection2;
	}
	
	public ObjectDatabase getSWGObjectODB() {
		return swgObjectODB;
	}
	
	public ObjectDatabase getMailODB() {
		return mailODB;
	}
	
	public ObjectDatabase getGuildODB() {
		return guildODB;
	}
		
	public ObjectDatabase getObjectIdODB() {
		return objectIdODB;
	}
	
	public ObjectDatabase getDuplicateIdODB() {
		return duplicateIdODB;
	}
	
	public ObjectDatabase getChatRoomODB() {
		return chatRoomODB;
	}
	
	public ObjectDatabase getBountiesODB() {
		return bountiesODB;
	}
	
	public ObjectDatabase getResourceHistoryODB() {
		return resourceHistoryODB;
	}
	
	public ObjectDatabase getAuctionODB() {
		return auctionODB;
	}

	public ObjectDatabase getCityODB() {
		return cityODB;
	}

	public int getActiveClients() {
		int connections = 0;
		for (Map.Entry<IoSession, Client> c : clients.entrySet()) {
			if(c.getValue().getSession() != null) {
				if (c.getValue().getSession().isConnected()) {
					connections++;
				}
			}
		}
		return connections;
	}
	
	public int getActiveZoneClients() {
		int connections = 0;
		for (Map.Entry<IoSession, Client> c : clients.entrySet()) {
			if(c.getValue().getSession() != null) {
				if (c.getValue().getSession().isConnected() && c.getValue().getParent() != null) {
					connections++;
				}
			}
		}
		return connections;
	}

	
	public Client getClient(IoSession session) {
		return clients.get(session);
	}
	
	public ConcurrentHashMap<IoSession, Client> getActiveConnectionsMap() {
		return clients;
	}
	
	/*
	 * --------------- Other methods for services ---------------
	 */
	public void addClient(IoSession session, Client client) {
		clients.put(session, client);
	}
	
	public void removeClient(IoSession session) {
		clients.remove(session);
	}
	
	// for python scripts
	public Thread getCurrentThread() {
		return Thread.currentThread();
	}
	
	public static NGECore getInstance() {
		return instance;
	}
	
	public BusConfiguration getEventBusConfig() {
		return eventBusConfig;
	}
	
	public void initiateShutdown() {
		if(isShuttingDown)
			return;
		try {
	
			for(int minutes = 15; minutes > 1; minutes--) {
					chatService.broadcastGalaxy("The server will be shutting down soon. Please find a safe place to logout. (" + minutes + " minutes left)");
					Thread.sleep(60000);
			}
			setGalaxyStatus(GalaxyStatus.Locked);
			chatService.broadcastGalaxy("The server will be shutting down soon. Please find a safe place to logout. (" + 1 + " minutes left)");
			Thread.sleep(30000);
			chatService.broadcastGalaxy("You will be disconnected in 30 seconds so the server can perform a final save before shutting down.  Please find a safe place to logout now.");
			Thread.sleep(20000);
			chatService.broadcastGalaxy("You will be disconnected in 10 seconds so the server can perform a final save before shutting down.  Please find a safe place to logout now.");
			Thread.sleep(10000);
			chatService.broadcastGalaxy("You will now be disconnected so the server can perform a final save before shutting down.");
			
			synchronized(getActiveConnectionsMap()) {
				for(Client client : getActiveConnectionsMap().values()) {
					client.getSession().close(true);
					connectionService.disconnect(client);
				}
			}
			
			System.exit(0);
			
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
	}
		
		public void initiateStop() {
			if(isShuttingDown)
				return;
			try {
				chatService.broadcastGalaxy("You will now be disconnected so the server can perform a final save before shutting down.");
				Thread.sleep(10000);
				synchronized(getActiveConnectionsMap()) {
					for(Client client : getActiveConnectionsMap().values()) {
						client.getSession().close(true);
						connectionService.disconnect(client);
					}
				}
				
				System.exit(0);
				
			} catch (InterruptedException e) {
					e.printStackTrace();
			}
		
	}

	public long getGalacticTime() {
		return System.currentTimeMillis() - galacticTime;
	}
	
	public void closeODBs() {
		swgObjectODB.close();
		mailODB.close();
		guildODB.close();
		chatRoomODB.close();
		resourceHistoryODB.close();
		objectIdODB.close();
		duplicateIdODB.close();
		auctionODB.close();
		cityODB.close();
	}

	public String getMotd() {
		return motd;
	}

	public void setMotd(String motd) {
		this.motd = motd;
	}
	
	public Vector<String> getExcludedDevelopers(){
		Vector<String> excludedDevelopers = new Vector<String>();
		//excludedDevelopers.add("Charon");
		// Feel free to add your OS user account name here to exclude yourself from loading buildouts and snapshots
		// without having to change options.cfg all the time
		return excludedDevelopers;
	}
	
	public Config getOptions() {
		return options;
	}
}

