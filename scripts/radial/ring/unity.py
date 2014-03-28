from resources.common import RadialOptions
from services.sui.SUIWindow import Trigger
from java.util import Vector
from main import NGECore
from engine.resources.objects import SWGObject
import sys

def createRadial(core, owner, target, radials):

	ghost = owner.getSlottedObject('ghost')
	if ghost is None:
		return

	if ghost.getSpouseName() is None or ghost.getSpouseName() is "":
		targetPlayer = core.objectService.getObject(owner.getTargetId())
		if targetPlayer is not None and targetPlayer.getSlottedObject('ghost') is not None:
			if targetPlayer.getObjectID() == owner.getObjectID():
				return
			radials.add(RadialOptions(0, 69, 3, '@unity:mnu_propose'))
			return
	else:
		radials.add(RadialOptions(0, 70, 3, '@unity:mnu_divorce'))
		return
	return
	
def handleSelection(core, owner, target, option):
	if option == 69 and target:
		player = core.objectService.getObject(owner.getTargetId())
		tGhost = player.getSlottedObject('ghost')
		suiSvc = core.suiService
		
		if tGhost.getPosition().getDistance2D(owner.getWorldPosition()) < 50:
			owner.sendSystemMessage('@unity:out_of_range', 0)
			return
		
		if tGhost.getSpouseName() is not None:
			owner.sendSystemMessage('@unity:target_married', 0)
			return
		
		if player.getAttachment("proposer") is not None:
			owner.sendSystemMessage('@unity:target_proposed', 0)
			return
		
		else:
			
			owner.sendSystemMessage('You propose unity to ' + player.getCustomName() + '.', 0)
			targetWindow = suiSvc.createMessageBox(3, '@unity:accept_title', owner.getCustomName() + ' is proposing unity to you. Do you wish to accept?', tGhost.getContainer(), owner, 15)
			returnList = Vector()

			targetWindow.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handlePropose)
			targetWindow.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, handlePropose)
			player.setAttachment("proposer", owner.getCustomName())
			suiSvc.openSUIWindow(targetWindow)

			target.setAttachment("unity", True)
			core.equipmentService.equip(owner, target)
			
			core.commandService.callCommand(owner, 'kneel', None, '')
			return
		return
	
	if option == 70:
		married = owner.getSlottedObject('ghost').getSpouseName()
		myGhost = owner.getSlottedObject('ghost')
		otherPlayer = NGECore.getInstance().objectService.getObjectByCustomName(married)
		
		if otherPlayer is None:
			return
		ghost = otherPlayer.getSlottedObject('ghost')
		
		if ghost is None:
			return
		
		owner.sendSystemMessage('Your union with ' + married + ' has ended.', 0)
		otherPlayer.sendSystemMessage('Your union with ' + owner.getCustomName() + ' has ended.', 0)
		myGhost.setSpouseName(None)
		ghost.setSpouseName(None)
		
		target.setAttachment("unity", None)
		#TODO: Divorce offline players
		return
	return

def handlePropose(owner, window, eventType, returnList):
	core = NGECore.getInstance()
	
	if owner is None:
		return
	
	if eventType == 0:
		proposer = core.objectService.getObjectByCustomName(owner.getAttachment("proposer"))
		if proposer is None:
			return

		core.playerService.performUnity(owner, proposer)
		return
	
	if eventType == 1:
		proposer = core.objectService.getObjectByCustomName(owner.getAttachment("proposer"))
		proposer.sendSystemMessage('@unity:declined', 0)
		for SWGObject in proposer.getEquipmentList():
			if SWGObject.getAttachment("unity") is not None:
				SWGObject.setAttachment("unity", None)
				break

		owner.setAttachment("proposer", None)
		return
	return
