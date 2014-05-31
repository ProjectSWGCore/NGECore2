from resources.common import RadialOptions, ProsePackage, OutOfBand
from java.util import HashMap
from java.lang import Long
from services.sui.SUIService import ListBoxType
from services.sui.SUIService import InputBoxType
from services.sui.SUIWindow import Trigger
import sys

def createRadial(core, owner, target, radials):
	radials.clear()	
	radials.add(RadialOptions(0, 7, 1, ''))
	radials.add(RadialOptions(0, 11, 1, ''))
	if not long(target.getAttachment('vendorOwner')) == owner.getObjectID():
		return
	radials.add(RadialOptions(0, 55, 0, ''))
	radials.add(RadialOptions(0, 52, 0, ''))
	radials.add(RadialOptions(0, 112, 3, '@player_structure:vendor_control'))
	radials.add(RadialOptions(3, 56, 1, ''))
	radials.add(RadialOptions(3, 57, 1, ''))
	radials.add(RadialOptions(3, 58, 1, ''))
	radials.add(RadialOptions(3, 59, 1, ''))
	radials.add(RadialOptions(4, 53, 1, ''))
	radials.add(RadialOptions(4, 54, 1, ''))
	

	if target.getAttachment('initialized') == False:
		radials.add(RadialOptions(5, 118, 3, '@player_structure:vendor_init'))
	else:
		# soe reuses option 118
		radials.add(RadialOptions(5, 118, 3, '@player_structure:vendor_status'))
		radials.add(RadialOptions(5, 217, 3, '@player_structure:give_maintenance'))
		radials.add(RadialOptions(5, 115, 3, '@player_structure:take_maintenance'))
		if owner.getSkillModBase('private_register_vendor') >= 1:
			radials.add(RadialOptions(5, 116, 3, '@player_structure:register_vendor'))
		radials.add(RadialOptions(5, 205, 3, '@player_structure:enable_vendor_search'))
		radials.add(RadialOptions(5, 206, 3, '@player_structure:disable_vendor_search'))
		radials.add(RadialOptions(5, 121, 3, '@player_structure:customize_vendor'))
		
	radials.add(RadialOptions(5, 114, 3, '@player_structure:vendor_pack'))		
	radials.add(RadialOptions(5, 117, 3, '@player_structure:remove_vendor'))	
	
	return
	
def handleSelection(core, owner, target, option):

	if not target.getAttachment('vendorOwner') == owner.getObjectID():
		return
	
	if target.getAttachment('initialized') == False:
		if option == 118:
			owner.sendSystemMessage('@player_structure:vendor_initialized', 0)
			target.setAttachment('initialized', True)
			core.bazaarService.startVendorUpdateTask(owner, target)	
	else:
		if option == 118:
			suiOptions = HashMap()
			suiOptions.put(Long(1), 'Owner: ' + owner.getCustomName())
			suiOptions.put(Long(2), 'Maintenance Pool: ' + str(target.getAttachment('maintenanceAmount')) + ' cr')
			maintenanceRate = 15
			if target.getAttachment('onMap') is True:
				maintenanceRate += 6
			suiOptions.put(Long(3), 'Maintenance Rate: ' + str(maintenanceRate) + ' cr/hr')
			suiOptions.put(Long(4), 'Number of Items For Sale: ' + str(core.bazaarService.getNumberOfItemsForSale(target.getObjectID())))
			window = core.suiService.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, '@player_structure:vendor_status', 'Vendor Status', suiOptions, owner, None, 5)
			core.suiService.openSUIWindow(window)
		if option == 205:
			target.setAttachment('vendorSearchEnabled', True)
			owner.sendSystemMessage('@player_structure:vendor_search_enabled', 0)
		if option == 206:
			target.setAttachment('vendorSearchEnabled', False)
			owner.sendSystemMessage('@player_structure:vendor_search_disabled', 0)
		if option == 217:
			window = core.suiService.createInputBox(InputBoxType.INPUT_BOX_OK_CANCEL, '@player_structure:pay_vendor_t', '@player_structure:pay_vendor_d', owner, target, 5, handlePayMaintenance)
			core.suiService.openSUIWindow(window)
		if option == 115:
			window = core.suiService.createInputBox(InputBoxType.INPUT_BOX_OK_CANCEL, '@player_structure:withdraw_vendor_t', '@player_structure:withdraw_vendor_d', owner, target, 5, handleWithdrawMaintenance)
			core.suiService.openSUIWindow(window)
			


		
		

	return
	
def handlePayMaintenance(actor, window, eventType, returnList):

	value = int(returnList.get(0))
	
	if value > 100000:
			actor.sendSystemMessage('@player_structure:vendor_maint_invalid', 0)
			return
	if value <= 0:
			actor.sendSystemMessage('@player_structure:amt_greater_than_zero', 0)
			return
	if actor.getBankCredits() + actor.getCashCredits() <= value:
			actor.sendSystemMessage('@player_structure:vendor_maint_denied', 0)
			return
	
	if actor.getBankCredits() < value:
		actor.setCashCredits(actor.getCashCredits() - (value - actor.getBankCredits()))
		actor.setBankCredits(0)
	else:
		actor.setBankCredits(actor.getBankCredits() - value)
	
	maintAmount = window.getRangeObject().getAttachment('maintenanceAmount')
	maintAmount += value
	window.getRangeObject().setAttachment('maintenanceAmount', maintAmount)
	
	#actor.sendSystemMessage('@player_structure:vendor_maint_accepted', OutOfBand(ProsePackage(maintAmount)), 0)
	
	return
	
def handleWithdrawMaintenance(actor, window, eventType, returnList):

	value = int(returnList.get(0))
	maintAmount = window.getRangeObject().getAttachment('maintenanceAmount')

	if value > maintAmount:
		#actor.sendSystemMessage('@player_structure:vendor_withdraw_fail', OutOfBand(ProsePackage(value)), 0)
		return
	if value <= 0:
		actor.sendSystemMessage('@player_structure:amt_greater_than_zero', 0)
	
	maintAmount -= value
	window.getRangeObject().setAttachment('maintenanceAmount', maintAmount)
	#actor.sendSystemMessage('@player_structure:vendor_withdraw', OutOfBand(ProsePackage(value)), 0)
		
	return
	