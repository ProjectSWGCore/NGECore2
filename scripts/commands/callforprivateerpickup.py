import sys
import time

def setup(core, actor, target, command):
	return
	
def run(core, actor, target, commandstring):

	stcSvc = core.staticService
	objSvc = core.objectService
	object = objSvc.createObject('object/tangible/terminal/shared_terminal_travel_instant_privateer.iff', actor.getPlanet(), (actor.getWorldPosition().x)+5, actor.getWorldPosition().z, actor.getWorldPosition().y)
	
	
	if actor.getAttachment("itv") is None:	
		if core.gcwService.isInPvpZone is True:
			actor.sendSystemMessage('@travel:no_pickup_in_pvp', 0)
			return
		
		if actor.getCombatFlag() == 1:
			actor.sendSystemMessage('@travel:in_combat', 0)
			return
		
		if core.terrainService.canBuildAtPosition(actor, actor.getWorldPosition().x, actor.getWorldPosition().z) is True:
			core.simulationService.add(object, (actor.getWorldPosition().x)+5, actor.getWorldPosition().z)
			actor.sendSystemMessage('@travel:calling_for_pickup', 0)
		else:
			actor.sendSystemMessage('@travel:invalid_pickup_loc', 0)
			return
				
		if core.simulationService.checkForObject(20, actor) is True:
			actor.sendSystemMessage('@travel:no_pickup_location', 0)
			return
		
		core.travelService.checkForItvTimedDespawn(actor, object)
		
		actor.setAttachment("itv", object.getObjectID())
		return
	elif actor.getAttachment("itv") is not None:
		actor.sendSystemMessage('@travel:pickup_craft_already_out', 0)
		return
				
	return
