from resources.common import RadialOptions
from services.sui.SUIService import MessageBoxType
from services.sui.SUIWindow import Trigger
from java.util import Vector
import sys

def createRadial(core, owner, target, radials):
	return
	
def handleSelection(core, owner, target, option):

	if option == 21 and target:
		# REALLY Dirty hack until this can be resolved SWG way !!
		stra = "Will you pay the %DI credits to clone yourself here?"
		level = int(owner.getLevel())
		
		if level <= 10:
			cloningFee = 100
		elif level >= 11:
			cloningFee = (((owner.getLevel() + 17) * owner.getLevel())/2)
		elif level == 90:
			cloningFee = 5000
	
		suiSvc = core.suiService
		suiWindow = suiSvc.createMessageBox(MessageBoxType.MESSAGE_BOX_OK_CANCEL, '@base_player:clone_confirm_title', stra.replace ("%DI" , str(cloningFee)), owner, target, 15)
		returnParams = Vector()
		returnParams.add('btnOk:Text')
		returnParams.add('btnCancel:Text')
		suiWindow.addHandler(0, '', Trigger.TRIGGER_OK, returnParams, handleSUI)
		suiWindow.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnParams, handleSUI)
		suiSvc.openSUIWindow(suiWindow)

	return
	
def handleSUI(owner, window, eventType, returnList):

	if eventType == 0:
		level = int(owner.getLevel())
		if level <= 10:
			cloningFee = 100
		elif level >= 11:
			cloningFee = (((owner.getLevel() + 17) * owner.getLevel())/2)
		elif level == 90:
			cloningFee = 5000
		
		cash = owner.getCashCredits()
		bank = owner.getBankCredits()
		
		if bank < cloningFee and cash < cloningFee:
			owner.sendSystemMessage('You lack the credits required to cover the cost of cloning.', 0)
			return
		elif bank >= cloningFee:
			owner.setBankCredits(bank - cloningFee)
		elif cash >= cloningFee and bank < cloningFee:
			owner.setCashCredits(cash - cloningFee)
		
		owner.getPlayerObject().setBindLocation(window.getRangeObject().getGrandparent().getObjectID())
		owner.sendSystemMessage('@base_player:clone_success', 0)
		

	return

	