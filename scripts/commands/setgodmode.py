import sys
from engine.resources.scene import Point3D
from protocol.swg import ObjControllerMessage
from protocol.swg import UnknownAbilityPacket
from engine.resources.objects import SWGObject
from java.awt.datatransfer import StringSelection
from java.awt.datatransfer import Clipboard
from java.awt import Toolkit
from resources.datatables import GcwRank
from services.gcw import GCWService


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
	
	if actor.getClient().isGM() is False:
		return
	
	if playerObject.getGodLevel() == 0:
		actor.addAbility("admin")
		playerObject.setGodLevel(50)
	
	if command == 'giveExperience' and arg1:
		core.playerService.giveExperience(actor, int(arg1))
		
	if command == 'level' and arg1:
		core.playerService.grantLevel(actor, int(arg1))
	
	elif command == 'setSpeed' and arg1:
		#actor.sendSystemMessage("Your speed was " + str(actor.getSpeedMultiplierBase()) + ". Don\'t forget to set this back or it\'ll permanently imbalance your speed. Default without buffs or mods is 1.", 2)
		actor.setSpeedMultiplierBase(float(arg1))
		actor.sendSystemMessage('Your new speed is ' + arg1 + '.', 2)
	
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
		actor.sendSystemMessage('You have learned ' + arg1 + '.', 0)
	
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
		else:
			actor.setInStealth(True)
			actor.setRadarVisible(False)
	
	elif command == 'holoEmote' and arg1:
		playerObject.setHoloEmote('holoemote_' + arg1)
		playerObject.setHoloEmoteUses(20)
		actor.sendSystemMessage('Holo-Emote Generator set to ' + 'holoemote_' + arg1, 0)
	
	elif command == 'off':	
		if playerObject.getGodLevel > 0:
			actor.removeAbility("admin")
			playerObject.setGodLevel(0)
	
	elif command == 'setBounty':
		if actor.getFaction() == "":
			actor.sendSystemMessage('You must be aligned in order to place a bounty on yourself.', 0)
			return
		core.playerService.sendSetBountyWindow(actor, actor)
		return
		
	elif command == 'genloot':
		object = core.lootService.generateLootItem(actor, arg1)
		
		inventory = actor.getSlottedObject('inventory')
		
		if inventory:
			inventory.add(object)
			
	elif command == 'spawnobj':
		pos = actor.getPosition()
		core.staticService.spawnObject(arg1, actor.getPlanet().getName(), 0, pos.x, pos.y, pos.z, 0, 0)
		
	elif command == 'showpos':
		pos = actor.getPosition()
		ori = actor.getOrientation()
		cellid = 0
		planetName = actor.getPlanet().getName()
		
		actor.sendSystemMessage('Position.x : %s' % pos.x, 0)
		print ('Position.x : %s' % pos.x, 0)
		actor.sendSystemMessage('Position.y : %s' % pos.y, 0)
		print ('Position.y : %s' % pos.y, 0)
		actor.sendSystemMessage('Position.z : %s' % pos.z, 0)
		print ('Position.z : %s' % pos.z, 0)
		actor.sendSystemMessage('Orientation.w : %s' % ori.w, 0)
		print ('Orientation.x : %s' % ori.w, 0)
		actor.sendSystemMessage('Orientation.x : %s' % ori.x, 0)
		print ('Orientation.w : %s' % ori.x, 0)
		actor.sendSystemMessage('Orientation.y : %s' % ori.y, 0)
		print ('Orientation.y : %s' % ori.y, 0)
		actor.sendSystemMessage('Orientation.z : %s' % ori.z, 0)
		print ('Orientation.z : %s' % ori.z, 0)
		if (actor.getContainer()):
			building_id = actor.getGrandparent().getObjectID()
			cid = actor.getContainer().getCellNumber()
			cellid = cid
			actor.sendSystemMessage('Cell ID : %s' % cid, 0)
			actor.sendSystemMessage('Building ID : %s' % building_id, 0)
			print('Cell ID : %s' % cid, 0)
			print('Building ID : %s' % building_id, 0)
		if cellid == 0:			
			str = "<OBJECTNAME> = stcSvc.spawnObject('<MOBILENAME>', '" + planetName + "', long(%s" % cellid + "), float(%.4f" % pos.x + "), float(%.4f" % pos.y + "), float(%.4f" % pos.z + "), float(%.4f" % ori.w + "), float(%.4f" % ori.x + "), float(%.4f" % ori.y + "), float(%.4f" % ori.z + "))"
		elif cellid != 0:
			str = "\tbuilding =  core.objectService.getObject(long(%s" % building_id + "))\n\t<OBJECTNAME> = stcSvc.spawnObject('<MOBILENAME>', '" + planetName + "', building.getCellByCellNumber(long(%s" % cellid + ")), float(%.4f" % pos.x + "), float(%.4f" % pos.y + "), float(%.4f" % pos.z + "), float(%.4f" % ori.w + "), float(%.4f" % ori.x + "), float(%.4f" % ori.y + "), float(%.4f" % ori.z + "))"
		toolkit = Toolkit.getDefaultToolkit()
		clipboard = toolkit.getSystemClipboard()
		clipboard.setContents(StringSelection(str), None)
	
	elif command == 'patrolpoint':
		pos = actor.getPosition()					
		str = "patrolpoints.add(Point3D(float(%.2f" % pos.x + "), float(%.2f" % pos.y + "), float(%.2f" % pos.z + ")))"
		toolkit = Toolkit.getDefaultToolkit()
		clipboard = toolkit.getSystemClipboard()
		clipboard.setContents(StringSelection(str), None)
		actor.sendSystemMessage('Patrolpoint copied to clipboard', 0)
		
	elif command == 'jesus':		
		actor.setHealth(actor.getMaxHealth())
		actor.setAction(actor.getMaxAction())
		actor.setPosture(0)
		actor.setSpeedMultiplierBase(1)
		actor.setTurnRadius(1)
	
	elif command == 'unknownAbilityPacket' and arg1:
		packet = UnknownAbilityPacket(arg1)
		actor.getClient().getSession().write(packet.serialize())
		actor.sendSystemMessage('Sent UnknownAbilityPacket for ability ' + arg1, 0)
		return
		
	elif command == 'rank' and arg1:
		playerObject.setCurrentRank(int(arg1))
		print (playerObject.getCurrentRank())
		return
	
	elif command == 'addToFrogAdminList':
		frogBuilding = core.objectService.getObject(core.devService.getFrogBuildingId())
		
		if frogBuilding is None:
			return
		
		frogBuilding.addPlayerToAdminList(None, actor.getObjectID(), actor.getCustomName().split(' ')[0])
		actor.sendSystemMessage('You were added to the Admin list for the Frog Building.', 0)
		return
	return

	