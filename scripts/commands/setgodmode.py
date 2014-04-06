import sys
from engine.resources.scene import Point3D
from protocol.swg import ObjControllerMessage
from engine.resources.objects import SWGObject

def setup():
    return
    
def run(core, actor, target, commandString):
	
	playerObject = actor.getSlottedObject('ghost')

	if not playerObject:
		return
		
	commandArgs = commandString.split(' ')
	command = commandArgs[0]
	if len(commandArgs) > 1:
		arg1 = commandArgs[1]
	if len(commandArgs) > 2:
		arg2 = commandArgs[2]
	if len(commandArgs) > 3:
		arg3 = commandArgs[3]
	if len(commandArgs) > 4:
		arg4 = commandArgs[4]
	if len(commandArgs) > 5:
		arg5 = commandArgs[5]
	
	if not command:
		return
	
	if command == 'giveExperience' and arg1:
		core.playerService.giveExperience(actor, int(arg1))
		
	if command == 'level' and arg1:
		core.playerService.grantLevel(actor, int(arg1))
	
	elif command == 'setSpeed' and arg1:
		actor.sendSystemMessage('Your speed was ' + str(actor.getSpeedMultiplierBase()) + '. Don\'t forget to set this back or it\'ll permanently imbalance your speed. Default without buffs or mods is 1.', 2)
		actor.setSpeedMultiplierBase(float(arg1))
		actor.sendSystemMessage('Your new speed is ' + str(actor.getSpeedMultiplierBase()) + '.', 2)
	
	elif command == 'teleport' and arg2 and arg3 and arg4:
		position = Point3D(float(arg2), float(arg3), float(arg4))
		core.simulationService.transferToPlanet(actor, core.terrainService.getPlanetByName(arg1), position, actor.getOrientation(), None)
	
	elif command == 'teleportplayer' and arg1 and arg2 and arg3 and arg4 and arg5:
		player = core.chatService.getObjectByFirstName(arg1)
		if player:
			position = Point3D(float(arg3), float(arg4), float(arg5))
			core.simulationService.transferToPlanet(player, core.terrainService.getPlanetByName(arg2), position, player.getOrientation(), None)
			
	
	elif command == 'credits' and arg1:
		actor.setCashCredits(actor.getCashCredits() + int(arg1))
		actor.sendSystemMessage('The Galactic Empire has transferred ' + arg1 + ' credits to you for your service.', 0)
		
	elif command == 'addability' and arg1:
		actor.addAbility(str(arg1))
		actor.sendSystemMessage('You have learned ' + arg1 + '')
	
	elif command == 'anim' and arg1:
		actor.doSkillAnimation(arg1)
		actor.sendSystemMessage('Performed ' + arg1 ,0)
	
	elif command == 'changeBio' and arg1:
		actor.getSlottedObject('ghost').setBiography(arg1)
		
	elif command == 'spawn' and arg1 and arg2:
		pos = actor.getWorldPosition()
		core.spawnService.spawnCreature(arg1, actor.getPlanet().getName(), 0, pos.x, pos.y, pos.z, 1, 0, 1, 0, int(arg2))
		
	elif command == 'instance' and arg1:
		core.instanceService.queue(arg1, actor)	
		
	elif command == 'action' and arg1:
		actor.setAction(int(arg1))
		
	elif command == 'health' and arg1:
		actor.setHealth(int(arg1))
		
	elif command == 'id':
		actor.sendSystemMessage('Your id is: ' + str(actor.getObjectId()), 0)
	
	elif command == 'cust' and arg1 and arg2 and arg3:
		obj = core.objectService.getObject(long(arg1))
		obj.setCustomizationVariable(str(arg2), int(arg3))
		
	elif command == 'buff' and arg1:
		core.buffService.addBuffToCreature(actor, str(arg1), actor)
	
	elif command == 'stealth':
		if (actor.isInStealth()):
			actor.setInStealth(False)
			actor.setRadarVisible(True)
			actor.sendSystemMessage('You are now visible to other players.', 0)
		else:
			actor.setInStealth(True)
			actor.setRadarVisible(False)
			actor.sendSystemMessage('You are now hidden from players. "Stealth Effect" is not implemented, however players still won\'t be able to see you. Type /setgodmode stealth again to be visible.', 0)
	
	elif command == 'holoEmote' and arg1:
		playerObject.setHoloEmote('holoemote_' + arg1)
		playerObject.setHoloEmoteUses(20)
		actor.sendSystemMessage('Holo-Emote Generator set to ' + 'holoemote_' + arg1, 0)
	return
