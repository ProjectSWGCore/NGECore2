from resources.common import RadialOptions
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

	return
	