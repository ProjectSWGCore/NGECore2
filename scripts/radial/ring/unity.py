from resources.common import RadialOptions
from services.sui.SUIWindow import Trigger
from java.util import Vector
from main import NGECore
import sys

def createRadial(core, owner, target, radials):

	ghost = owner.getSlottedObject('ghost')
	if ghost is None:
		return

	if ghost.getSpouseName() is None or "":
		targetPlayer = core.objectService.getObject(owner.getTargetId())
		if targetPlayer is not None and targetPlayer.getSlottedObject('ghost') is not None:
			radials.add(RadialOptions(0, 69, 1, '@unity:mnu_propose'))
	else:
		radials.add(RadialOptions(0, 70, 1, '@unity:mnu_divorce'))
		return
	return
	
def handleSelection(core, owner, target, option):
	if option == 69 and target:
		tGhost = core.objectService.getObject(owner.getTargetId()).getSlottedObject('ghost')
		suiSvc = core.suiService
		
		if tGhost.getSpouseName() is not None:
			owner.sendSystemMessage('@unity:target_married', 0)
			return
		
		if tGhost.getPosition().getDistance2D(owner.getWorldPosition()) < 50:
			owner.sendSystemMessage('@unity:out_of_range', 0)
			return
		
		else:
			targetWindow = suiSvc.createMessageBox(3, '@unity:accept_title', owner.getCustomName() + ' is proposing unity to you. Do you wish to accept?', tGhost.getContainer(), owner, 15)
			returnList = Vector()

			targetWindow.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleSUI)
			targetWindow.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, handleSUI)
			tGhost.getContainer().setAttachment("proposer", owner.getCustomName())
			suiSvc.openSUIWindow(targetWindow)

			target.setAttachment("unity", target)
			return
		return
	
	if option == 70:
		married = owner.getSlottedObject('ghost').getSpouseName()
		otherPlayer = NGECore.getInstance().objectService.getObjectByCustomName(married)
		
		if otherPlayer is None:
			return
		ghost = otherPlayer.getSlottedObject('ghost')
		
		if ghost is None:
			return
		
		owner.sendSystemMessage('Your union with ' + married + 'has ended.', 0)
		
		owner.setSpouseName("")
		otherPlayer.setSpouseName("")
		
		target.setAttachment("unity", None)
		return
	return

def handleSUI(owner, window, eventType, returnList):
	if eventType == 0:
		ghost = owner.getSlottedObject('ghost')
		if ghost is None:
			return
		
		print (owner.getAttachment("proposer"))
		core = NGECore.getInstance()
		proposer = core.objectService.getObjectByCustomName(owner.getAttachment("proposer"))
		if proposer is None:
			return
		print ('not none')
		pGhost = proposer.getSlottedObject('ghost')
		if pGhost is None:
			return
		
		ghost.setSpouseName(proposer.getCustomName())
		pGhost.setSpouseName(owner.getCustomName())
		
		owner.sendSystemMessage('Your union with ' + proposer.getCustomName() + ' is complete.', 0)
		proposer.sendSystemMessage('Your union with ' + owner.getCustomName() + ' is complete.', 0)
		owner.setAttachment("proposer", None)
		
		return
	if eventType == 1:
		proposer = core.objectService.getObjectByCustomName(owner.getAttachment("proposer"))
		proposer.sendSystemMessage('@unity:declined', 0)
		owner.setAttachment("proposer", None)
		return
	return