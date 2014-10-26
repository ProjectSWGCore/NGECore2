import sys
import engine.resources.scene.Point3D
import resources.common.Forager
import time


def setup():
	return
	
def run(core, actor, target, commandString):
	
	actor.sendSystemMessage('Function suspended until working as intended'),0)
	return
	
	#if actor.getSkills('Foraging')==0:
		#actor.sendSystemMessage('@skl_use:sys_forage_noskill'),0)
	
	if actor.getAttachment("LastForageTime"):
		if actor.getAttachment("LastForageTime")>int(round(time.time() * 1000))-5000:
			actor.sendSystemMessage('@skl_use:sys_forage_already',0)
			return
		
	actor.setAttachment("LastForageTime",int(round(time.time() * 1000)))
	
	if actor.getCombatFlag()!=0:
		actor.sendSystemMessage('@skl_use:sys_forage_combatfail',0)
		return
		
	if actor.getPosture()!=0:
		actor.sendSystemMessage('@skl_use:sys_forage_cant',0)	
		return
		
	if core.terrainService.isWater(actor.getPlanet(), actor.getPosition().x, actor.getPosition().z):
		actor.sendSystemMessage('You cannot forage while in water.',0)
		return
		
	if actor.getContainer() != None:
		actor.sendSystemMessage('@skl_use:sys_forage_inside',0)
		return
		
	if actor.getAction() < 200:
		actor.sendSystemMessage('@skl_use:sys_forage_attrib',0)
		return
	
	somepoint = engine.resources.scene.Point3D(0,0,0)
	if actor.getAttachment('PostForagePosition')==None:
		actor.setAttachment('PostForagePosition', somepoint)
	
	oldposition = actor.getAttachment('PostForagePosition')
	if actor.getPosition().getDistance2D(oldposition)<5:
				
		actor.sendSystemMessage("@skl_use:sys_forage_empty", 0);
		return
		
		
	actor.sendSystemMessage('@skl_use:sys_forage_start', 0)
	
	actor.setAttachment("PreForagePosition", actor.getPosition())
	actor.setCurrentAnimation('forage')
	time.sleep(1) # does NOT stall other python scripts, tested
	# Each command might be its own thread
	
	actor.setAttachment("PostForagePosition", actor.getPosition())
	
	if actor.getCombatFlag()!=0:
		actor.sendSystemMessage('@skl_use:sys_forage_combatfail',0)
		return
	
	oldposition = actor.getAttachment("PreForagePosition")
	if actor.getPosition().getDistance2D(oldposition)>2:
		actor.sendSystemMessage('@skl_use:sys_forage_movefail',0)
		return
		
	
	
	forager = resources.common.Forager()
	forager.handleForageResults(actor)
	
	return
	