package services.object;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import main.NGECore;

import org.apache.mina.core.buffer.IoBuffer;

import protocol.swg.ChatSystemMessage;
import protocol.swg.ObjControllerMessage;
import protocol.swg.UpdatePVPStatusMessage;
import protocol.swg.UpdatePostureMessage;
import protocol.swg.objectControllerObjects.Posture;







import com.sleepycat.je.Environment;
import com.sleepycat.je.Transaction;
import com.sleepycat.persist.model.Entity;
import com.sleepycat.persist.model.NotPersistent;

import engine.clients.Client;
import engine.resources.objects.Buff;
import engine.resources.objects.IPersistent;
import engine.resources.objects.MissionCriticalObject;
import engine.resources.objects.SWGObject;
import engine.resources.objects.SkillMod;
import engine.resources.scene.Planet;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import resources.objects.creature.CreatureObject;
import resources.objects.tangible.TangibleObject;
import resources.objects.weapon.WeaponObject;



import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

import java.util.Map;

import main.NGECore;
import engine.resources.scene.Point3D;
import engine.resources.scene.Quaternion;
import engine.resources.service.INetworkRemoteEvent;

public class NPCService {
private NGECore  core; 
	
	public CreatureObject createNpc(String template, String planetName, long cellId, float x, float y, float z, float qY, float qW) { 
		return (CreatureObject) core.objectService.createObject("object/creature/npc/base/shared_tusken_raider_base_male.iff",0,core.terrainService.getPlanetByName("tatooine"), new Point3D(4160,0,-4780), new Quaternion (0,0,0,0));
		
	}
	
	public NPCService(final NGECore core) { 
		this.core = core; 
		createNpc(); 
	}

	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0,
			Map<Integer, INetworkRemoteEvent> arg1) {
		
		
	}

	public void shutdown() {
		
		
}
}
