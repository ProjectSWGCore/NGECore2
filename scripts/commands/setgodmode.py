import sys
from engine.resources.scene import Point3D
from protocol.swg import CommPlayerMessage
from protocol.swg.objectControllerObjects import ShowLootBox
from protocol.swg import ObjControllerMessage
from engine.resources.objects import SWGObject
from jarray import array

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
	
	if not command:
		return
	
	if command == 'giveExperience' and arg1:
		core.playerService.giveExperience(actor, int(arg1))
	
	elif command == 'setSpeed' and arg1:
		actor.sendSystemMessage('Your speed was ' + str(actor.getSpeedMultiplierBase()) + '. Don\'t forget to set this back or it\'ll permanently imbalance your speed. Default without buffs or mods is 1.', 2)
		actor.setSpeedMultiplierBase(float(arg1))
		actor.sendSystemMessage('Your new speed is ' + str(actor.getSpeedMultiplierBase()) + '.', 2)
	
	elif command == 'teleport' and arg2 and arg3 and arg4:
		position = Point3D(float(arg2), float(arg3), float(arg4))
		core.simulationService.transferToPlanet(actor, core.terrainService.getPlanetByName(arg1), position, actor.getOrientation(), None)
		
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
	
	elif command == 'rewardMe':
		testObject = core.objectService.createObject('object/weapon/ranged/rifle/shared_rifle_t21.iff', actor.getPlanet())
		testObject.setCustomName('Crush4r')
		testObject.setStringAttribute('crafter', 'Wavescrub')
		dGun = core.objectService.createObject('object/weapon/ranged/rifle/shared_rifle_tc22_blaster.iff', actor.getPlanet())
		dGun.setCustomName('Supertoms Gun')
		dGun.setStringAttribute('crafter', 'Wavescrub')
		core.playerService.giveItems(actor, testObject, dGun)
		return
	
	elif command == 'comm':
		comm = CommPlayerMessage(actor.getObjectId())
		actor.getClient().getSession().write(comm.serialize())
	
	elif command == 'id':
		actor.sendSystemMessage('Your id is: ' + str(actor.getObjectId()), 0)
	return
