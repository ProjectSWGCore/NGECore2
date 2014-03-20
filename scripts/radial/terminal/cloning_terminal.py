from resources.common import RadialOptions
from services.sui.SUIService import MessageBoxType
from services.sui.SUIWindow import Trigger
from java.util import Vector
import sys

def createRadial(core, owner, target, radials):
	return
	
def handleSelection(core, owner, target, option):

	if option == 21 and target:
		suiSvc = core.suiService
		suiWindow = suiSvc.createMessageBox(MessageBoxType.MESSAGE_BOX_OK_CANCEL, '@base_player:clone_confirm_title', '@base_player:clone_confirm_prompt', owner, target, 15)
		returnParams = Vector()
		returnParams.add('btnOk:Text')
		returnParams.add('btnCancel:Text')
		suiWindow.addHandler(0, '', Trigger.TRIGGER_OK, returnParams, handleSUI)
		suiWindow.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnParams, handleSUI)
		suiSvc.openSUIWindow(suiWindow)

	return
	
def handleSUI(owner, window, eventType, returnList):

	if eventType == 0:
		cash = owner.getCashCredits()
		bank = owner.getBankCredits()
		if bank < 1000 and cash < 1000:
			owner.sendSystemMessage('You lack the credits required to cover the cost of cloning.', 0)
			return
		elif bank > 1000:
			owner.setBankCredits(bank - 1000)
		elif cash > 1000 and bank < 1000:
			owner.setCashCredits(cash - 1000)
		
		owner.setAttachment('preDesignatedCloner', window.getRangeObject().getGrandparent().getObjectID())
		owner.sendSystemMessage('@base_player:clone_success', 0)
		

	return

	