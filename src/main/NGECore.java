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

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.service.IoHandler;

import protocol.swg.ChatSystemMessage;
import net.engio.mbassy.bus.config.BusConfiguration;
import resources.common.RadialOptions;
import resources.common.ThreadMonitor;
import resources.objects.creature.CreatureObject;
import services.AttributeService;
import services.BuffService;
import services.CharacterService;
import services.ConnectionService;
import services.EntertainmentService;
import services.EquipmentService;
import services.GroupService;
import services.LoginService;
import services.MissionService;
import services.PlayerService;
import services.ScriptService;
import services.SimulationService;
import services.SkillModService;
import services.SkillService;
import services.StaticService;
import services.TerrainService;
import services.WeatherService;
import services.ai.AIService;
import services.chat.ChatService;
import services.collections.CollectionService;
import services.combat.CombatService;
import services.command.CombatCommand;
import services.command.CommandService;
import services.gcw.FactionService;
import services.gcw.GCWService;
import services.guild.GuildService;
import services.LoginService;
import services.map.MapService;
import services.object.ObjectService;
import services.object.UpdateService;
import services.spawn.SpawnService;
import services.sui.SUIService;
import services.trade.TradeService;
import services.travel.TravelService;
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
import engine.resources.database.DatabaseConnection;
import engine.resources.database.ObjectDatabase;
import engine.resources.objects.SWGObject;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.service.InteractiveJythonAcceptor;
import engine.resources.service.NetworkDispatch;
import engine.servers.InteractiveJythonServer;
import engine.servers.MINAServer;
import engine.servers.PingServer;

@SuppressWarnings("unused")

public class NGECore {
	
	public static boolean didServerCrash = false;

	private static NGECore instance;
	
	private Config config = null;

	private volatile boolean isShuttingDown = false;
	
	private ConcurrentHashMap<Integer, Client> clients = new ConcurrentHashMap<Integer, Client>();
	
	// Database
	
	private DatabaseConnection databaseConnection = null;
	private DatabaseConnection databaseConnection2 = null;
		
	// Services
	public LoginService loginService;
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
	//public MissionService missionService;
	
	// Login Server
	public NetworkDispatch loginDispatch;
	private MINAServer loginServer;
	
	// Zone Server
	public NetworkDispatch zoneDispatch;
	private MINAServer zoneServer;
	
	// Interactive Jython Console
	public InteractiveJythonAcceptor jythonAcceptor;
	private InteractiveJythonServer jythonServer;

	private ObjectDatabase creatureODB;
	private ObjectDatabase mailODB;
	private ObjectDatabase guildODB;
	
	private BusConfiguration eventBusConfig = BusConfiguration.Default(1, new ThreadPoolExecutor(1, 4, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>()));

	private ObjectDatabase buildingODB;

	
	public NGECore() {
		
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
		
		setGalaxyStatus(1);
		creatureODB = new ObjectDatabase("creature", true, false, true);
		buildingODB = new ObjectDatabase("building", true, false, true);
		mailODB = new ObjectDatabase("mails", true, false, true);
		guildODB = new ObjectDatabase("guild", true, false, true);
		// Services
		loginService = new LoginService(this);
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
		//missionService = new MissionService(this);
		
		// Ping Server
		try {
			PingServer pingServer = new PingServer(config.getInt("PING.PORT"));
			pingServer.bind();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Login Server
		loginDispatch = new NetworkDispatch(this, false);
		loginDispatch.addService(loginService);
		
		loginServer = new MINAServer(loginDispatch, config.getInt("LOGIN.PORT"));
		loginServer.start();
		
		// Zone Server
		zoneDispatch = new NetworkDispatch(this, true);
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
		//zoneDispatch.addService(missionService);

		zoneServer = new MINAServer(zoneDispatch, config.getInt("ZONE.PORT"));
		zoneServer.start();
		staticService = new StaticService(this);
		
		// Planets
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
		spawnService = new SpawnService(this);
		terrainService.loadClientPois();
		// Travel Points
		travelService.loadTravelPoints();
		simulationService = new SimulationService(this);
		
		objectService.loadBuildings();
		terrainService.loadSnapShotObjects();
		simulationService.insertSnapShotObjects();
		
		// Zone services that need to be loaded after the above
		zoneDispatch.addService(simulationService);
		
		
		// Static Spawns
		staticService.spawnStatics();
		
		guildService = new GuildService(this);
		zoneDispatch.addService(guildService);
		
		gcwService = new GCWService(this);
		zoneDispatch.addService(gcwService);
		
		collectionService = new CollectionService(this);
		zoneDispatch.addService(collectionService);

		tradeService = new TradeService(this);
		zoneDispatch.addService(tradeService);
		
		zoneDispatch.addService(skillService);
		
		//travelService.startShuttleSchedule();
		
		weatherService = new WeatherService(this);
		weatherService.loadPlanetSettings();
		
	//	spawnService.loadLairTemplates();
	//	spawnService.loadLairGroups();
	//	spawnService.loadSpawnAreas();
		
		didServerCrash = false;
		System.out.println("Started Server.");
		setGalaxyStatus(2);
		
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
	
	public ObjectDatabase getCreatureODB() {
		return creatureODB;
	}
	
	public ObjectDatabase getMailODB() {
		return mailODB;
	}
	
	public ObjectDatabase getGuildODB() {
		return guildODB;
	}
	
	public ObjectDatabase getBuildingODB() {
		return buildingODB;
	}
	
	public int getActiveClients() {
		int connections = 0;
		for (Map.Entry<Integer, Client> c : clients.entrySet()) {
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
		for (Map.Entry<Integer, Client> c : clients.entrySet()) {
			if(c.getValue().getSession() != null) {
				if (c.getValue().getSession().isConnected() && c.getValue().getParent() != null) {
					connections++;
				}
			}
		}
		return connections;
	}

	
	public Client getClient(int connectionID) {
		return clients.get(connectionID);
	}
	
	public ConcurrentHashMap<Integer, Client> getActiveConnectionsMap() {
		return clients;
	}
	
	/*
	 * --------------- Other methods for services ---------------
	 */
	public void addClient(Integer connectionID, Client client) {
		clients.put(connectionID, client);
	}
	
	public void removeClient(Integer connectionID) {
		clients.remove(connectionID);
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
					simulationService.notifyAllClients(new ChatSystemMessage("The server will be shutting down soon. Please find a safe place to logout. (" + minutes + " minutes left)", (byte) 0 ).serialize());
					Thread.sleep(60000);
			}
			setGalaxyStatus(3);
			simulationService.notifyAllClients(new ChatSystemMessage("The server will be shutting down soon. Please find a safe place to logout. (" + 1 + " minutes left)", (byte) 0 ).serialize());
			Thread.sleep(30000);
			simulationService.notifyAllClients(new ChatSystemMessage("You will be disconnected in 30 seconds so the server can perform a final save before shutting down.  Please find a safe place to logout now.", (byte) 0 ).serialize());
			Thread.sleep(20000);
			simulationService.notifyAllClients(new ChatSystemMessage("You will be disconnected in 10 seconds so the server can perform a final save before shutting down.  Please find a safe place to logout now.", (byte) 0 ).serialize());
			Thread.sleep(10000);
			simulationService.notifyAllClients(new ChatSystemMessage("You will now be disconnected so the server can perform a final save before shutting down.", (byte) 0 ).serialize());
			
			synchronized(getActiveConnectionsMap()) {
				for(Client client : getActiveConnectionsMap().values()) {
					client.getSession().close(true);
				}
			}
			
			System.exit(0);
			
		} catch (InterruptedException e) {
				e.printStackTrace();
		}
		
		
		
	}
	
}

