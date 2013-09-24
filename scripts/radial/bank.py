from resources.common import RadialOptions
from services.sui import SUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
import sys

def createRadial(core, owner, target, radials):
	radials.clear()
	bank = owner.getSlottedObject('bank')
	if bank:
		radials.add(RadialOptions(0, 21, 1, ''))
		radials.add(RadialOptions(0, 7, 1, ''))
		radials.add(RadialOptions(1, RadialOptions.bankTransfer, 3, '@sui:bank_credits'))
		radials.add(RadialOptions(1, RadialOptions.bankitems, 3, '@sui:bank_items'))	
		if owner.getBankCredits() > 0:
			radials.add(RadialOptions(1, RadialOptions.bankWithdrawAll, 3, '@sui:bank_withdrawall'))
		if owner.getCashCredits() > 0:
			radials.add(RadialOptions(1, RadialOptions.bankDepositAll, 3, '@sui:bank_depositall'))
	
	return
	
def handleSelection(core, owner, target, option):
		
	if option == RadialOptions.bankitems:
		bank = owner.getSlottedObject('bank')
		if bank:
			core.simulationService.openContainer(owner, bank)
	if option == RadialOptions.bankTransfer or option == 21:
		suiSvc = core.suiService
		suiWindow = suiSvc.createSUIWindow('Script.transfer', owner, target, 10)
		suiWindow.setProperty('transaction.txtInputFrom:Text', 'From')
		suiWindow.setProperty('transaction.txtInputFrom:Text', 'From')
		suiWindow.setProperty('bg.caption.lblTitle:Text', '@base_player:bank_title')
		suiWindow.setProperty('Prompt.lblPrompt:Text', '@base_player:bank_prompt')
		suiWindow.setProperty('transaction.txtInputTo:Text', 'To')	
		suiWindow.setProperty('transaction.lblFrom:Text', 'Cash')
		suiWindow.setProperty('transaction.lblTo:Text', 'Bank')	
		suiWindow.setProperty('transaction.lblStartingFrom:Text', str(owner.getCashCredits()))
		suiWindow.setProperty('transaction.lblStartingTo:Text', str(owner.getBankCredits()))
		suiWindow.setProperty('transaction.txtInputFrom:Text', str(owner.getCashCredits()))
		suiWindow.setProperty('transaction.txtInputTo:Text', str(owner.getBankCredits()))
		suiWindow.setProperty('transaction:ConversionRatioFrom', '1')
		suiWindow.setProperty('transaction:ConversionRatioTo', '1')
		returnParams = Vector()
		returnParams.add('transaction.txtInputFrom:Text')
		returnParams.add('transaction.txtInputTo:Text')
		suiWindow.addHandler(0, '', Trigger.TRIGGER_OK, returnParams, handleTransfer)
		suiWindow.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnParams, handleTransfer)

		suiSvc.openSUIWindow(suiWindow)
	if option == RadialOptions.bankWithdrawAll:
		withdrawAmount = owner.getBankCredits()
		owner.setCashCredits(withdrawAmount + owner.getCashCredits())
		owner.setBankCredits(0)
		owner.sendSystemMessage('You successfully withdraw ' + str(withdrawAmount) + ' credits from your account.', 0)
	if option == RadialOptions.bankDepositAll:
		depositAmount = owner.getCashCredits()
		owner.setBankCredits(depositAmount + owner.getBankCredits())
		owner.setCashCredits(0)
		owner.sendSystemMessage('You successfully deposit ' + str(depositAmount) + ' credits to your account.', 0)

	return
	
def handleTransfer(owner, window, eventType, returnList):

	if eventType == 0 and len(returnList) == 2:
		cashCredits = int(returnList.get(0))
		bankCredits = int(returnList.get(1))

		if bankCredits + cashCredits == owner.getCashCredits() + owner.getBankCredits():
			owner.setCashCredits(cashCredits)
			owner.setBankCredits(bankCredits)
			owner.sendSystemMessage('@base_player:bank_success', 0)
	return
	