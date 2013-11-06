package services.travel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import resources.objects.creature.CreatureObject;

public class ShuttleObject extends CreatureObject {
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private boolean isInPort;
	private int nextShuttleTime;
	
	
	public ShuttleObject(long objectID, Planet planet, Point3D position, Quaternion orientation, String Template) {
		super(objectID, planet, position, orientation, Template);
		scheduleShuttle();
	}
	
	public ShuttleObject() {
		super();
	}
	
	public void scheduleShuttle() {
		final Runnable myRunnable = new Runnable() {
			@Override
			public void run() {
				if (isInPort) {
					setPosture((byte) 2);
					isInPort = false;
					nextShuttleTime = 60;
				} else {
					setPosture((byte) 0);
					isInPort = true;
					nextShuttleTime = 0;
				}
			}
		};
		
		scheduler.scheduleAtFixedRate(myRunnable, 60, 60, TimeUnit.SECONDS);
	}

	public enum ShuttleType {
		STARSHIP,
		CIVILIAN,
		THEED;
		
		@Override
		public String toString() {
			switch(this) {
			case CIVILIAN:
				return "object/creature/npc/theme_park/shared_player_shuttle.iff";
			case STARSHIP:
				return "object/creature/npc/theme_park/shared_player_transport.iff";
			case THEED:
				return "object/creature/npc/theme_park/shared_player_transport_theed_hangar.iff";
			default:
				return "object/creature/npc/theme_park/shared_player_shuttle.iff";
			
			}
		}
	}
	
	public ScheduledExecutorService getSchedule() {
		return scheduler;
	}
	
	public boolean isInPort() {
		return this.isInPort;
	}
	
	public int getNextShuttleTime() {
		return this.nextShuttleTime;
	}
}
