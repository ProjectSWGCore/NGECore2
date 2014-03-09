from resources.common import RadialOptions
from services.sui.SUIWindow import Trigger
import sys

def createRadial(core, owner, target, radials):

	ghost = owner.getSlottedObject('ghost')
	if ghost is None:
		return
	print (owner.getTargetId())
	print (owner.getObjectId())
	if ghost.getSpouseName() is None:
		targetPlayer = core.objectService.getObject(owner.getTargetId())
		if targetPlayer is not None and targetPlayer.getSlottedObject('ghost') is not None:
			radials.add(RadialOptions(0, 69, 1, 'Propose Unity'))
	return
	
def handleSelection(core, owner, target, option):
	if option == 69 and target:
		tGhost = core.objectService.getObject(owner.getTargetId()).getSlottedObject('ghost')
		suiSvc = core.suiService
		targetWindow = suiSvc.createMessageBox(3, 'Proposal', owner.getCustomName() + ' would like to marry you. Would you like to marry?', tGhost, owner, 10)
		returnList = Vector()
		targetWindow.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleSUI)
		targetWindow.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, handleSUI)
		suiSvc.openSUIWindow(targetWindow)
		return
	return

def handleSUI(owner, window, eventType, returnList):
    if eventType == 0:
    	ghost = owner.getSlottedObject('ghost')
    	ghost.setSpouseName("Waverunner")
        return
    return