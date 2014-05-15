from resources.common import RadialOptions
from services.sui.SUIService import MessageBoxType
import time
from services.sui.SUIWindow import Trigger
from java.util import Vector
import main.NGECore
import sys

def createRadial(core, owner, target, radials):	
	radials.clear()	
	radials.add(RadialOptions(0, 7, 0, 'Examine'))
	radials.add(RadialOptions(0, 15, 0, 'Destroy'))
	if target.getAttachment("tunerId") == None or target.getAttachment("tunerId") == 0: 
		if owner.getPlayerObject().getProfession()=='force_sensitive_1a':
			radials.add(RadialOptions(0, 19, 0, '@jedi_spam:tune_crystal'))
	return
	
def handleSelection(core, owner, target, option):
	if option == 19 and target:
		if owner is not None:	
			#owner.sendSystemMessage('You are not attuned enough with the force yet.',1)
			
			if target.getAttachment("tunerId") == None or target.getAttachment("tunerId") == 0:

				owner.setAttachment("TunableObject", target.getObjectID())
				handleSUIWindow(core, owner, target, option)
										
			else:
				owner.sendSystemMessage("You cannot tune this crystal.",1)
			return

def handleSUIWindow(core, owner, target, option):
	suiSvc = core.suiService
	suiWindow = suiSvc.createMessageBox(MessageBoxType.MESSAGE_BOX_OK_CANCEL, '@jedi_spam:confirm_tune_title', '@jedi_spam:confirm_tune_prompt', owner, target, 15)
	returnParams = Vector()
	returnParams.add('btnOk:Text')
	returnParams.add('btnCancel:Text')
	suiWindow.addHandler(0, '', Trigger.TRIGGER_OK, returnParams, handleSUI)
	suiWindow.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnParams, handleSUI)
	suiSvc.openSUIWindow(suiWindow)

def handleSUI(owner, window, eventType, returnList):

	if eventType == 0:					
		time.sleep(1)
		objectID = long(owner.getAttachment("TunableObject"))
		tunableObject = main.NGECore.getInstance().objectService.getObject(objectID)
		
		if tunableObject:
			tunableObject.getAttributes().put("@obj_attr_n:crystal_owner", owner.getCustomName())
			tunableObject.setAttachment("tunerId", int(owner.getObjectId()))
			
			if tunableObject.getAttributes().get("@obj_attr_n:color") is None:
				main.NGECore.getInstance().lootService.tuneProcess(tunableObject)
				time.sleep(0.5)
			
			tunableObject.setCustomName( "\\#00FF00" + tunableObject.getObjectName().getStfValue() + " (tuned)")
			owner.sendSystemMessage('@jedi_spam:crystal_tune_success',1)
	return	
	