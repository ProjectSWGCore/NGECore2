from resources.common import RadialOptions
from resources.common import OutOfBand
from protocol.swg import ResourceListForSurveyMessage
from services.sui.SUIService import MessageBoxType
from services.sui.SUIWindow import Trigger
from java.util import Vector
from java.lang import System
from java.lang import Long
from java.util import Map
from java.util import TreeMap
import main.NGECore
import sys
import math

def createRadial(core, owner, target, radials):
	#(byte parentId, short optionId, byte optionType, String description)
	radials.clear()
	city = core.playerCityService.getCityObjectIsIn(owner)
	if not city:
		return
	radials.add(RadialOptions(0, 7, 0, 'Examine'))
	radials.add(RadialOptions(0, 211, 0, '@city/city:city_info'))
	if city.getMayorID() == owner.getObjectID():
		radials.add(RadialOptions(0, 216, 0, '@city/city:city_management'))
	
	radials.add(RadialOptions(2, 212, 0, '@city/city:city_status'))	
	radials.add(RadialOptions(2, 213, 0, '@city/city:citizen_list_t'))
	radials.add(RadialOptions(2, 214, 0, '@city/city:city_structures'))
	radials.add(RadialOptions(2, 223, 0, '@city/city:rank_info_t'))
	radials.add(RadialOptions(2, 224, 0, '@city/city:city_maint'))
	radials.add(RadialOptions(2, 215, 0, '@city/city:treasury_status'))
	radials.add(RadialOptions(2, 220, 0, '@city/city:treasury_deposit'))
	
	if owner.getPlayerObject().getCitizenship() != 3 and core.playerCityService.getPlayerCity(owner) == city:
		radials.add(RadialOptions(2, 230, 0, '@city/city:revoke_citizenship'))
	
	if owner.getClient().isGM():
		radials.add(RadialOptions(2, 231, 0, 'Add 10 citizens'))
		radials.add(RadialOptions(2, 232, 0, 'Deduct 10 citizens'))

	if city.getMayorID() != owner.getObjectID():
		return
		
	#radials.add(RadialOptions(0, 227, 0, '@city/city:remove_trainers'))	not needed for pswg
	radials.add(RadialOptions(3,  217, 0, '@city/city:city_name_new_t')) 
	if city.isRegistered():
		radials.add(RadialOptions(3,  222, 0, '@city/city:city_unregister')) 
	else:
		radials.add(RadialOptions(3,  222, 0, '@city/city:city_register')) 
	if city.isZoningEnabled():
		radials.add(RadialOptions(3,  226, 0, '@city/city:unzone'))
	else:
		radials.add(RadialOptions(3,  226, 0, '@city/city:zone'))
	
	radials.add(RadialOptions(3,  218, 0, '@city/city:city_militia'))
	radials.add(RadialOptions(3,  219, 0, '@city/city:treasury_taxes'))
	radials.add(RadialOptions(3,  221, 0, '@city/city:treasury_withdraw'))
	radials.add(RadialOptions(3,  225, 0, '@city/city:city_specializations'))	
	radials.add(RadialOptions(3,  228, 0, '@city/city:align'))	

	return

	
def handleSelection(core, owner, target, option):
	playerCity = core.playerCityService.getCityObjectIsIn(owner)
	if playerCity == None:
		return
		
	if option == 217:
		if owner is not None:
			handleSetCityName(core, owner, target, option)
			return
	if option == 222:
		if owner is not None:
			if playerCity.getMayorID() == owner.getObjectID():
				if playerCity.isRegistered():
					handleUnregisterMap(core, owner, target, option)
				else:
					handleRegisterMap(core, owner, target, option)
			return
	if option == 219:
		if owner is not None:
			handleAdjustTaxes(core, owner, target, option)
			return
	if option == 225:
		if owner is not None:
			handleSpecialization(core, owner, target, option)
			return
	if option == 221:
		if owner is not None:
			handleWithdrawal(core, owner, target, option)
			return
	if option == 212:
		if owner is not None:
			handleCityInfo(core, owner, target, option)
			return
	if option == 223:
		if owner is not None:
			handleCityRankInfo(core, owner, target, option)
			return
	if option == 213:
		if owner is not None:
			handleCitizenList(core, owner, target, option)
			return
	if option == 231:
		if owner is not None:
			handle10Add(core, owner, target, option)
			return
	if option == 232:
		if owner is not None:
			handle10Deduct(core, owner, target, option)
			return
	if option == 220:
		if owner is not None:
			handleDeposit(core, owner, target, option)
			return
	if option == 226:
		if owner is not None:
			handleToggleZoning(core, owner, target, option)
			return
	if option == 214:
		if owner is not None:
			handleStructureReport(core, owner, target, option)
			return
	if option == 215:
		if owner is not None:
			handleTreasuryReport(core, owner, target, option)
			return
	if option == 218:
		if owner is not None:
			handleManageMilitia(core, owner, target, option)
			return
	if option == 230:
		if owner is not None:
			handleRevokeCitizenship(core, owner, target, option)
			return
	if option == 224:
		if owner is not None:
			handleMaintenanceReport(core, owner, target, option)
			return
		
def handleMaintenanceReport(core, owner, target, option):
	playerCity = core.playerCityService.getPlayerCity(owner)
	if not playerCity:
		return
	timeUntilUpdate = playerCity.getNextCityUpdate() - System.currentTimeMillis()
	days = math.floor(timeUntilUpdate / 86400 * 1000)
	timeUntilUpdate -= days * 86400 * 1000
	hours = math.floor(timeUntilUpdate / 3600 * 1000)
	timeUntilUpdate -= hours * 3600 * 1000
	minutes = math.floor(timeUntilUpdate / 60 * 1000)
	timeUntilUpdate -= minutes * 60 * 1000

	timeStr = ''
	
	if days > 0:
		timeStr += str(days) + ' day'
		if days > 1:
			timeStr += 's'
		if hours > 0 or minutes > 0 or timeUntilUpdate > 0:
			timeStr += ', '
	if hours > 0:
		timeStr += str(hours) + ' hour'
		if hours > 1:
			timeStr += 's'
		if minutes > 0 or timeUntilUpdate > 0:
			timeStr += ', '
	if minutes > 0:
		timeStr += str(minutes) + ' minute'
		if minutes > 1:
			timeStr += 's'
		if timeUntilUpdate > 0:
			timeStr += ', '
	if timeUntilUpdate > 0:
		timeStr += str(timeUntilUpdate) + ' second'
		if timeUntilUpdate > 1:
			timeStr += 's'
	
	totalMaintenance = 0
	menuItems = TreeMap()
	menuItems.put(Long(0), 'Next City Update: ' + timeStr)
	for structureId in playerCity.getPlacedStructures():
		structure = core.objectService.getObject(structureId)
		if not structure or not structure.getAttachment('isCivicStructure'):
			continue
		maintenance = structure.getBMR()
		if 'cityhall' in structure.getTemplate() and playerCity.isRegistered():
			maintenance += 5000
		totalMaintenance += maintenance
		menuItems.put(Long(structureId), structure.getLookAtText() + ' : ' + str(maintenance))
	
	if playerCity.getSpecialisationStfValue() != '':
		specMaintenance = playerCity.getSpecializationMaintenance()
		totalMaintenance += specMaintenance
		menuItems.put(Long(2), playerCity.getSpecialisationStfValue() + ' : ' + str(specMaintenance))
		
	
	menuItems.put(Long(3), 'Total: ' + str(totalMaintenance))
	window = core.suiService.createListBox(1, '@city/city:maint_info_t', '@city/city:maint_info_d', menuItems, owner, None, 0)
	core.suiService.openSUIWindow(window)

	return
			
def handleRevokeCitizenship(core, owner, target, option):
	playerCity = core.playerCityService.getPlayerCity(owner)
	if not playerCity or not playerCity.isCitizen(owner.getObjectID()) or playerCity.getMayorID() == owner.getObjectID():
		return
	window = core.suiService.createMessageBox(2, 'revoke_cit_t', 'revoke_cit_d', owner, None, 0)
	returnList = Vector()
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, revokeCitizenship)
	core.suiService.openSUIWindow(suiWindow)	
	return
	
def revokeCitizenship(owner, window, eventType, returnList):
	core = main.NGECore.getInstance()
	playerCity = core.playerCityService.getPlayerCity(owner)
	if not playerCity or not playerCity.isCitizen(owner.getObjectID()) or playerCity.getMayorID() == owner.getObjectID():
		return
	playerCity.removeCitizen(owner.getObjectID())
	owner.getPlayerObject().setHome('')
	owner.getPlayerObject().setCitizenship(0)
	owner.setAttachment('residentCity', None)
	owner.sendSystemMessage('You have successfully revoked your citizenship.', 0)	
	owner.sendSystemMessage('@city/city:revoke_citizenship_warning', 0)
	return
	
def handleManageMilitia(core, owner, target, option):
	playerCity = core.playerCityService.getPlayerCity(owner)
	if not playerCity:
		return
	menuItems = TreeMap()
	menuItems.put(Long(0), '@city/city:militia_new_t')
	for militiaId in playerCity.getMilitiaList():
		militia = core.objectService.getObject(militiaId)
		if not militia:
			militia = core.objectService.getCreatureFromDB(militiaId)
		if militia:
			menuItems.put(Long(militiaId), militia.getCustomName())
			
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')
	window = core.suiService.createListBox(1, '@city/city:militia_t', '@city/city:militia_d', menuItems, owner, None, 0)
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleMilitiaListBox)
	core.suiService.openSUIWindow(window)
	
	return
	
def handleMilitiaListBox(owner, window, eventType, returnList):
	core = main.NGECore.getInstance()
	playerCity = core.playerCityService.getPlayerCity(owner)
	if not playerCity:
		return
	index = int(returnList.get(0))
	id = window.getObjectIdByIndex(index)
	if id == 0:
		handleAddMiltiaPrompt(core, owner)
	else:
		core.playerCityService.handleRemoveMilitia(owner, id)
	return
		
def	handleAddMiltiaPrompt(core, owner):
	playerCity = core.playerCityService.getPlayerCity(owner)
	if not playerCity:
		return
	window = core.suiService.createInputBox(2, '@city/city:militia_new_t', '@city/city:militia_new_d', owner, None, 0)
	returnList = Vector()
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleAddMiltia)
	core.suiService.openSUIWindow(window)	
	return
	
def handleAddMiltia(owner, window, eventType, returnList):
	core = main.NGECore.getInstance()
	playerCity = core.playerCityService.getPlayerCity(owner)
	if not playerCity:
		return
	name = returnList.get(0)
	militiaId = core.characterService.getPlayerOID(name)
	if militiaId == 0:
		owner.sendSystemMessage('@city/city:cannot_find_citizen', 0)
		return
	if not playerCity.isCitizen(militiaId):
		owner.sendSystemMessage('@city/city:not_citizen', 0)
		return
	if playerCity.isMilitiaMember(militiaId) or militiaId == playerCity.getMayorID():
		owner.sendSystemMessage('That player is already a member of the city militia.', 0)
		return
	militia = core.objectService.getObject(militiaId)
	persist = False
	if not militia:
		militia = core.objectService.getCreatureFromDB(militiaId)
		persist = True
	if not militia:
		owner.sendSystemMessage('@city/city:cannot_find_citizen', 0)
		return
	playerCity.addMilitia(militiaId)
	if militia.getClient():
		militia.sendSystemMessage('@city/city:added_militia_target', 0)
	owner.sendSystemMessage('@city/city:added_militia', 0)
	militia.getPlayerObject().setCitizenship(2)
	if persist:
		core.objectService.persistObject(militiaId, militia, core.getSWGObjectODB())
	return
	
def handleTreasuryReport(core, owner, target, option):
	playerCity = core.playerCityService.getPlayerCity(owner)
	if not playerCity:
		return
	menuItems = TreeMap()
	menuItems.put(Long(1), '@city/city:treasury ' + str(playerCity.getCityTreasury()))
	window = core.suiService.createListBox(1, '@city/city:treasury_balance_t', '@city/city:treasury_balance_d', menuItems, owner, None, 0)
	core.suiService.openSUIWindow(window)
	return
	
def handleStructureReport(core, owner, target, option):
	playerCity = core.playerCityService.getPlayerCity(owner)
	if not playerCity:
		return
	menuItems = TreeMap()
	i = 0
	for structureId in playerCity.getPlacedStructures():
		structure = core.objectService.getObject(structureId)
		if structure and structure.getAttachment('isCivicStructure') is not None and structure.getAttachment('isCivicStructure') == True:
			i += 1
			menuItems.put(Long(i), structure.getLookAtText() + ' - Condition : ' + str((structure.getMaximumCondition() - structure.getConditionDamage()) / structure.getMaximumCondition() * 100) + '%')
	window = core.suiService.createListBox(1, '@city/city:structure_list_t', '@city/city:structure_list_d', menuItems, owner, None, 0)
	core.suiService.openSUIWindow(window)
	return


def handleDeposit(core, owner, target, option):	
	playerCity = core.playerCityService.getPlayerCity(owner)
	suiSvc = core.suiService
	suiWindow = suiSvc.createSUIWindow('Script.transfer', owner, target, 10)
	suiWindow.setProperty('transaction.txtInputFrom:Text', '@city/city:total_funds')
	suiWindow.setProperty('bg.caption.lblTitle:Text', '@city/city:treasury_deposit')
	suiWindow.setProperty('Prompt.lblPrompt:Text', '@city/city:treasury_deposit_d')
	suiWindow.setProperty('transaction.lblFrom:Text', '@city/city:total_funds')
	suiWindow.setProperty('transaction.lblTo:Text', '@city/city:treasury')	
	suiWindow.setProperty('transaction.lblStartingFrom:Text', str(owner.getCashCredits()))
	suiWindow.setProperty('transaction.lblStartingTo:Text', '')
	suiWindow.setProperty('transaction.txtInputFrom:Text', str(owner.getCashCredits()))
	suiWindow.setProperty('transaction.txtInputTo:Text', '')
	suiWindow.setProperty('transaction:ConversionRatioFrom', '1')
	suiWindow.setProperty('transaction:ConversionRatioTo', '1')
	returnParams = Vector()
	returnParams.add('transaction.txtInputFrom:Text')
	returnParams.add('transaction.txtInputTo:Text')
	suiWindow.addHandler(0, '', Trigger.TRIGGER_OK, returnParams, handleDepositSUI)

	suiSvc.openSUIWindow(suiWindow)
	
	return
	
def handleDepositSUI(owner, window, eventType, returnList):
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	if not playerCity:
		return
	amount = int(returnList.get(0))
	cash = owner.getCashCredits()
	deposit = cash - amount
	if amount > cash or deposit < 1:
		owner.sendSystemMessage('@city/city:positive_deposit', 0)
		return
	if playerCity.getCityTreasury() + deposit > 100000000:
		owner.sendSystemMessage('@city/city:positive_deposit', 0)
		return
	playerCity.addToTreasury(deposit)
	owner.deductCashCredits(deposit)
	owner.sendSystemMessage(OutOfBand.ProsePackage('@city/city:deposit_treasury', deposit), 0)
	playerCity.sendTreasuryDepositMail(owner, deposit)
	return
	
def	handleToggleZoning(core, owner, target, option):
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)	
	if not playerCity or playerCity.getMayorID() != owner.getObjectID():
		return
	
	playerCity.setZoningEnabled(not playerCity.isZoningEnabled())
	
	if playerCity.isZoningEnabled():
		owner.sendSystemMessage('@city/city:zoning_enabled', 0)	
	else:
		owner.sendSystemMessage('@city/city:zoning_disabled', 0)	
	
	return

	
def handle10Add(core, owner, target, option):	
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	playerCity.Add10MoreCitizens()
	return

def handle10Deduct(core, owner, target, option):	
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	playerCity.Deduct10Citizens()
	return
	
def	handleSetCityName(core, owner, target, option):
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)	
	if not playerCity:
		return
	if playerCity.getCityNameChangeCooldown() > System.currentTimeMillis():
		owner.sendSystemMessage('You may only change the city name once in 4 weeks.', 0)
		return
	window = core.suiService.createInputBox(2, '@city/city:city_name_new_t','@city/city:city_name_new_d', owner, None, 0)
	window.setProperty('txtInput:MaxLength', '40')
	returnList = Vector()
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, setNameCallBack)
	core.suiService.openSUIWindow(window)
	return

	
def setNameCallBack(owner, window, eventType, returnList):
	core = main.NGECore.getInstance()
	if returnList.size()==0:
		return
	name = returnList.get(0)
	if not core.characterService.checkName(name, owner.getClient(), True):
		owner.sendSystemMessage('@player_structure:not_valid_name', 0)
		return
	if not core.playerCityService.doesCityNameExist(name):
		owner.sendSystemMessage('@player_structure:cityname_not_unique', 0)
		return
		
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)	
	playerCity.setCityName(name)
	playerCity.setCityNameChangeCooldown(System.currentTimeMillis() + str(604800 * 4 * 1000))
	# TODO send mail
	owner.sendSystemMessage('@city/city:name_changed', 0)		
	return
	
	
def handleAdjustTaxes(core, owner, target, option):	
	
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	if playerCity.getRank() < 2:
		owner.sendSystemMessage('@city/city:no_rank_taxes', 0)
		return
	
	window = core.suiService.createSUIWindow('Script.listBox', owner, target, 0);
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:adjust_taxes_t')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:adjust_taxes_d')	

	window.addListBoxMenuItem('@city/city:set_tax_t_income',0)
	window.addListBoxMenuItem('@city/city:set_tax_t_property',1)
	window.addListBoxMenuItem('@city/city:set_tax_t_sales',2)
	window.addListBoxMenuItem('@city/city:set_tax_t_travel',3)
	window.addListBoxMenuItem('@city/city:set_tax_t_garage',4)

	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, setAdjustTaxesCallBack)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setSpecializationCancelCallBack)	
	core.suiService.openSUIWindow(window);
	return
	
def setAdjustTaxesCallBack(owner, window, eventType, returnList):
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		handleSetIncomeTax(owner)
		return
	if returnList.get(0)=='1':
		handleSetPropertyTax(owner)
		return
	if returnList.get(0)=='2':
		handleSetSalesTax(owner)
		return
	if returnList.get(0)=='3':
		handleSetTravelTax(owner)
		return
	if returnList.get(0)=='4':
		handleSetGarageTax(owner)
		return
	
	return
	
def handleSetIncomeTax(owner):

	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	window = main.NGECore.getInstance().suiService.createInputBox(2,'@city/city:set_tax_t_income','@city/city:set_tax_d_income \n \n @city/city:income_tax_prompt : %s' % playerCity.getIncomeTax(), owner, owner, 0)
	returnList = Vector()
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleSetIncomeTaxCallBack)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setSpecializationCancelCallBack)	
	main.NGECore.getInstance().suiService.openSUIWindow(window);
	return
	
def handleSetIncomeTaxCallBack(owner, window, eventType, returnList):
	tax = int(returnList.get(0))
	if tax > 2000 or tax < 0:
		owner.sendSystemMessage('Income tax value is out of range of accepteable values.', 0)	
		handleSetIncomeTax(owner)
		return
		
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	playerCity.setIncomeTax(tax)
	owner.sendSystemMessage(OutOfBand.ProsePackage('@city/city:set_income_tax', tax), 0)	
	return
	
def handleSetSalesTax(owner):

	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	window = main.NGECore.getInstance().suiService.createInputBox(2,'@city/city:set_tax_t_sales','@city/city:set_tax_d_sales \n \n @city/city:sales_tax_prompt : %s' % playerCity.getSalesTax(), owner, owner, 0)
	returnList = Vector()
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleSetSalesTaxCallBack)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setSpecializationCancelCallBack)	
	main.NGECore.getInstance().suiService.openSUIWindow(window);
	return
	
def handleSetSalesTaxCallBack(owner, window, eventType, returnList):
	tax = int(returnList.get(0))
	if tax > 20 or tax < 0:
		owner.sendSystemMessage('Sales tax value is out of range of accepteable values.', 0)	
		handleSetSalesTax(owner)
		return
		
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	playerCity.setIncomeTax(tax)
	owner.sendSystemMessage(OutOfBand.ProsePackage('@city/city:set_sales_tax', tax), 0)	
	return
	
def handleSetPropertyTax(owner):

	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	window = main.NGECore.getInstance().suiService.createInputBox(2,'@city/city:set_tax_t_property','@city/city:set_tax_d_property \n \n @city/city:property_tax_prompt : %s' % playerCity.getPropertyTax(), owner, owner, 0)
	returnList = Vector()
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleSetPropertyTaxCallBack)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setSpecializationCancelCallBack)	
	main.NGECore.getInstance().suiService.openSUIWindow(window);
	return
	
def handleSetPropertyTaxCallBack(owner, window, eventType, returnList):
	tax = int(returnList.get(0))
	if tax > 50 or tax < 0:
		owner.sendSystemMessage('Property tax value is out of range of accepteable values.', 0)	
		handleSetPropertyTax(owner)
		return
		
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	playerCity.setIncomeTax(tax)
	owner.sendSystemMessage(OutOfBand.ProsePackage('@city/city:set_property_tax', tax), 0)	
	return
	
def handleSetTravelTax(owner):
	
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	if playerCity.hasShuttlePort()==0:
		owner.sendSystemMessage('@city/city:no_shuttleport', 0)	
		return
		
	window = main.NGECore.getInstance().suiService.createInputBox(2,'@city/city:set_tax_t_travel','@city/city:set_tax_d_travel \n \n @city/city:travel_tax_prompt : %s' % playerCity.getTravelTax(), owner, owner, 0)
	returnList = Vector()
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleSetTravelTaxCallBack)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setSpecializationCancelCallBack)	
	main.NGECore.getInstance().suiService.openSUIWindow(window);
	return
	
def handleSetTravelTaxCallBack(owner, window, eventType, returnList):
	tax = int(returnList.get(0))
	if tax > 500 or tax < 1:
		owner.sendSystemMessage('Travel tax value is out of range of accepteable values.', 0)	
		handleSetIncomeTax(owner)
		return
		
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	playerCity.setTravelTax(tax)
	owner.sendSystemMessage(OutOfBand.ProsePackage('@city/city:set_travel_fee', tax), 0)	
	return
	
def handleSetGarageTax(owner):

	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	window = main.NGECore.getInstance().suiService.createInputBox(2,'@city/city:set_tax_t_garage','@city/city:set_tax_d_garage \n\n @city/city:garage_tax : %s' % playerCity.getGarageTax(), owner, owner, 0)
	returnList = Vector()
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleSetGarageTaxCallBack)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setSpecializationCancelCallBack)	
	main.NGECore.getInstance().suiService.openSUIWindow(window);
	return
	
def handleSetGarageTaxCallBack(owner, window, eventType, returnList):
	tax = int(returnList.get(0))
	if tax > 500 or tax < 1:
		owner.sendSystemMessage('Garage tax value is out of range of accepteable values.', 0)	
		handleSetIncomeTax(owner)
		return
	
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	playerCity.setGarageTax(tax)
	owner.sendSystemMessage(OutOfBand.ProsePackage('@city/city:set_garage_tax', tax), 0)	
	return
	
def handleWithdrawal(core, owner, target, option):	

	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	if not playerCity:
		return
	if playerCity.getCityTreasuryWithdrawalCooldown() > System.currentTimeMillis():
		owner.sendSystemMessage('@city/city:withdraw_daily', 0)	
		return
	core.playerCityService.handleCityTreasuryWithdrawal(owner)

	
def handleCitizenList(core, owner, target, option):
	# wasnt there a new list layout in NGE ?
	window = core.suiService.createSUIWindow('Script.listBox', owner, target, 0);
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:citizen_list_t')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:citizen_list_prompt')	
	
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)	
	index = 1;
	for citizen in playerCity.getCitizens():
		citizenObject = core.objectService.getObject(citizen)
		if not citizenObject:
			continue
		name = citizenObject.getCustomName()
		if citizenObject.getPlayerObject().getCitizenship() == 2:
			name += ' (Militia)'
		window.addListBoxMenuItem(name,index)
		index += 1
	
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)	
	returnList = Vector()
	returnList.add('txtInput:LocalText')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, setCitizenListCallBack)
	core.suiService.openSUIWindow(window);
	return
	
def setCitizenListCallBack(owner, window, eventType, returnList):
	# todo add waypoint
	return
	
def handleCityInfo(core, owner, target, option):	
	window = core.suiService.createSUIWindow('Script.listBox', owner, None, 0);
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:city_info_t')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:city_info_d')	
	
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)	
	mayor = core.objectService.getObject(playerCity.getMayorID())
	if not mayor:
		mayor = core.objectService.getCreatureFromDB(playerCity.getMayorID())
	window.addListBoxMenuItem('@city/city:name_prompt : ' + playerCity.getCityName(),0)
	if mayor:
		window.addListBoxMenuItem('@city/city:mayor_prompt : ' + mayor.getCustomName(),5)
	window.addListBoxMenuItem('@city/city:location_prompt : ' + str(playerCity.getCityCenterPosition().x) + ' ' + str(playerCity.getCityCenterPosition().z),6)
	window.addListBoxMenuItem('@city/city:radius_prompt : %s' % playerCity.getCityRadius(),1)	
	window.addListBoxMenuItem('@city/city:reg_citizen_prompt : %s' % len(playerCity.getCitizens()),7)
	window.addListBoxMenuItem('@kb/kb_player_cities_n:civic_structures_n : %s' % playerCity.getCivicStructuresCount(),8)
	window.addListBoxMenuItem('@city/city:decorations : %s' % 0,9) # todo when decorations are implemented
	if playerCity.getRank() >= 3 and playerCity.getSpecialization() > -1:
		window.addListBoxMenuItem('@city/city:specialization_prompt : @city/city:' + playerCity.getSpecializationSTFNamesAsList().get(playerCity.getSpecialization()),4)
	
	window.addListBoxMenuItem('@city/city:city_rank_prompt : @city/city:rank' + str(playerCity.getRank()),2)
		
	# TODO add tax info
	
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	window.setProperty('btnCancel:Text', '@cancel')
	returnList = Vector()
	returnList.add('txtInput:LocalText')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, setCityInfoCallBack)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setCitizenListCallBack)	
	core.suiService.openSUIWindow(window);
	return
	
def setCityInfoCallBack(owner, window, eventType, returnList):
	#handle waypoint creation to citizen's house	
	return
	
def handleCityRankInfo(core, owner, target, option):	
	window = core.suiService.createSUIWindow('Script.listBox', owner, None, 0);
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:rank_info_t')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:rank_info_d')	
	
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)	
	rank = playerCity.getRank()
	nextRank = rank + 1
	if nextRank > 5:
		nextRank = 5
	window.addListBoxMenuItem('@city/city:city_rank_prompt : @city/city:rank' + str(playerCity.getRank()),1)
	window.addListBoxMenuItem('@city/city:reg_citizen_prompt : %s' % len(playerCity.getCitizens()),2)
	window.addListBoxMenuItem('@city/city:pop_req_current_rank : ' + str(playerCity.getReqCitizenCountForRank(rank)),3)
	window.addListBoxMenuItem('@city/city:pop_req_next_rank : ' + str(playerCity.getReqCitizenCountForRank(nextRank)),4)
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	window.setProperty('btnCancel:Text', '@cancel')
	core.suiService.openSUIWindow(window);

	return
	
def handleRegisterMap(core, owner, target, option):	
	
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	if playerCity.getRank()<3:
		owner.sendSystemMessage('@city/city:cant_register_rank', 0)
		return
		
	if owner.getObjectId()!=playerCity.getMayorID():
		owner.sendSystemMessage('@city/city:cant_register', 0)
		return
		
	if playerCity.isRegistered():
		return
	playerCity.setRegistered(True)
	core.mapService.addLocation(owner.getPlanet(), playerCity.getCityName(), playerCity.getCityCenterPosition().x, playerCity.getCityCenterPosition().z, 17, 0, 0)
	return
	
def handleUnregisterMap(core, owner, target, option):	
	
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	if playerCity.getRank()<3:
		owner.sendSystemMessage('@city/city:cant_register_rank', 0)
		return
		
	if owner.getObjectId()!=playerCity.getMayorID():
		owner.sendSystemMessage('@city/city:cant_register', 0)
		return
		
	if not playerCity.isRegistered():
		return
	playerCity.setRegistered(False)	
	core.mapService.removeLocation(owner.getPlanet(), playerCity.getCityCenterPosition().x, playerCity.getCityCenterPosition().z, 17)
	return

	
def handleSpecialization(core, owner, target, option):	
	
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	if playerCity.getRank() < 3:
		owner.sendSystemMessage('@city/city:no_rank_spec', 0)
		return

	window = core.suiService.createSUIWindow('Script.listBox', owner, target, 0);
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:city_specs_t')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:city_specs_d')	

	window.addListBoxMenuItem('@city/city:city_spec_cloning',0)
	window.addListBoxMenuItem('@city/city:city_spec_bm_incubator',1)
	window.addListBoxMenuItem('@city/city:city_spec_storyteller',2)
	window.addListBoxMenuItem('@city/city:city_spec_entertainer',3)
	window.addListBoxMenuItem('@city/city:city_spec_missions',4)
	window.addListBoxMenuItem('@city/city:city_spec_industry',5)
	window.addListBoxMenuItem('@city/city:city_spec_doctor',6)
	window.addListBoxMenuItem('@city/city:city_spec_research',7)
	window.addListBoxMenuItem('@city/city:city_spec_sample_rich',8)

	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, setSpecializationCallBack)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setSpecializationCancelCallBack)	
	core.suiService.openSUIWindow(window);
	return

def setSpecializationCancelCallBack(owner, window, eventType, returnList):
	return
	
def setSpecializationCallBack(owner, window, eventType, returnList):
		
	if returnList.size()==0:
		owner.sendSystemMessage('NULL', 0)	
		return
		
	if returnList.get(0)=='0':
		showCloning(owner)
		return
	if returnList.get(0)=='1':
		showDNA(owner)
		return
	if returnList.get(0)=='2':
		showEncore(owner)
		return
	if returnList.get(0)=='3':
		showEntertainment(owner)
		return
	if returnList.get(0)=='4':
		showImproved(owner)
		return
	if returnList.get(0)=='5':
		showManufacturing(owner)
		return
	if returnList.get(0)=='6':
		showMedical(owner)
		return
	if returnList.get(0)=='7':
		showResearch(owner)
		return
	if returnList.get(0)=='8':
		showSample(owner)
		return
	
	return
	
def showCloning(owner):
	window = main.NGECore.getInstance().suiService.createSUIWindow('Script.messageBox', owner, owner, 0)
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:city_spec_cloning')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:city_spec_cloning_d')
	window.setProperty("btnOk:visible", "True")
	window.setProperty("btnCancel:visible", "True")
	window.setProperty("btnOk:Text", "Select")
	window.setProperty("btnCancel:Text", "@cancel")	
	returnList = Vector()  
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleCloningCallback)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setSpecializationCancelCallBack)	
	main.NGECore.getInstance().suiService.openSUIWindow(window);
	return
	
def handleCloningCallback(owner, window, eventType, returnList):
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	owner.setAttachment('ChosenCitySpec',1)
	showSpecConfirmWindow(owner)
	return
	
def showDNA(owner):
	window = main.NGECore.getInstance().suiService.createSUIWindow('Script.messageBox', owner, owner, 0)
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:city_spec_bm_incubator')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:city_spec_bm_incubator_d')
	window.setProperty("btnOk:visible", "True")
	window.setProperty("btnCancel:visible", "True")
	window.setProperty("btnOk:Text", "Select")
	window.setProperty("btnCancel:Text", "@cancel")	
	returnList = Vector()  
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleDNACallback)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setSpecializationCancelCallBack)	
	main.NGECore.getInstance().suiService.openSUIWindow(window);
	return
	
def handleDNACallback(owner, window, eventType, returnList):
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	owner.setAttachment('ChosenCitySpec',2)
	showSpecConfirmWindow(owner)
	return
	
def showEncore(owner):
	window = main.NGECore.getInstance().suiService.createSUIWindow('Script.messageBox', owner, owner, 0)
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:city_spec_storyteller')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:city_spec_storyteller_d')
	window.setProperty("btnOk:visible", "True")
	window.setProperty("btnCancel:visible", "True")
	window.setProperty("btnOk:Text", "Select")
	window.setProperty("btnCancel:Text", "@cancel")	
	returnList = Vector()  
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleEncoreCallback)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setSpecializationCancelCallBack)	
	main.NGECore.getInstance().suiService.openSUIWindow(window);
	return
	
def handleEncoreCallback(owner, window, eventType, returnList):
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	owner.setAttachment('ChosenCitySpec',3)
	showSpecConfirmWindow(owner)
	return
	
	
def showEntertainment(owner):
	window = main.NGECore.getInstance().suiService.createSUIWindow('Script.messageBox', owner, owner, 0)
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:city_spec_entertainer')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:city_spec_entertainer_d')
	window.setProperty("btnOk:visible", "True")
	window.setProperty("btnCancel:visible", "True")
	window.setProperty("btnOk:Text", "Select")
	window.setProperty("btnCancel:Text", "@cancel")	
	returnList = Vector()  
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleEntertainmentCallback)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setSpecializationCancelCallBack)	
	main.NGECore.getInstance().suiService.openSUIWindow(window);
	return
	
def handleEntertainmentCallback(owner, window, eventType, returnList):
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	owner.setAttachment('ChosenCitySpec',4)
	showSpecConfirmWindow(owner)
	return
	
def showImproved(owner):
	window = main.NGECore.getInstance().suiService.createSUIWindow('Script.messageBox', owner, owner, 0)
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:city_spec_missions')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:city_spec_missions_d')
	window.setProperty("btnOk:visible", "True")
	window.setProperty("btnCancel:visible", "True")
	window.setProperty("btnOk:Text", "Select")
	window.setProperty("btnCancel:Text", "@cancel")	
	returnList = Vector()  
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleImprovedCallback)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setSpecializationCancelCallBack)	
	main.NGECore.getInstance().suiService.openSUIWindow(window);
	return
	
def handleImprovedCallback(owner, window, eventType, returnList):
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	owner.setAttachment('ChosenCitySpec',5)
	showSpecConfirmWindow(owner)
	return
	
def showManufacturing(owner):
	window = main.NGECore.getInstance().suiService.createSUIWindow('Script.messageBox', owner, owner, 0)
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:city_spec_industry')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:city_spec_industry_d')
	window.setProperty("btnOk:visible", "True")
	window.setProperty("btnCancel:visible", "True")
	window.setProperty("btnOk:Text", "Select")
	window.setProperty("btnCancel:Text", "@cancel")	
	returnList = Vector()  
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleManufacturingCallback)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setSpecializationCancelCallBack)	
	main.NGECore.getInstance().suiService.openSUIWindow(window);
	return
	
def handleManufacturingCallback(owner, window, eventType, returnList):
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	owner.setAttachment('ChosenCitySpec',6)
	showSpecConfirmWindow(owner)
	return
	
def showMedical(owner):
	window = main.NGECore.getInstance().suiService.createSUIWindow('Script.messageBox', owner, owner, 0)
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:city_spec_doctor')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:city_spec_doctor_d')
	window.setProperty("btnOk:visible", "True")
	window.setProperty("btnCancel:visible", "True")
	window.setProperty("btnOk:Text", "Select")
	window.setProperty("btnCancel:Text", "@cancel")	
	returnList = Vector()  
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleMedicalCallback)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setSpecializationCancelCallBack)	
	main.NGECore.getInstance().suiService.openSUIWindow(window);
	return
	
def handleMedicalCallback(owner, window, eventType, returnList):
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	owner.setAttachment('ChosenCitySpec',7)
	showSpecConfirmWindow(owner)
	return
	
def showResearch(owner):
	window = main.NGECore.getInstance().suiService.createSUIWindow('Script.messageBox', owner, owner, 0)
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:city_spec_research')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:city_spec_research_d')
	window.setProperty("btnOk:visible", "True")
	window.setProperty("btnCancel:visible", "True")
	window.setProperty("btnOk:Text", "Select")
	window.setProperty("btnCancel:Text", "@cancel")	
	returnList = Vector()  
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleResearchCallback)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setSpecializationCancelCallBack)	
	main.NGECore.getInstance().suiService.openSUIWindow(window);
	return
	
def handleResearchCallback(owner, window, eventType, returnList):
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	owner.setAttachment('ChosenCitySpec',8)
	showSpecConfirmWindow(owner)
	return
	
def showSample(owner):
	window = main.NGECore.getInstance().suiService.createSUIWindow('Script.messageBox', owner, owner, 0)
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:city_spec_sample_rich')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:city_spec_sample_rich_d')
	window.setProperty("btnOk:visible", "True")
	window.setProperty("btnCancel:visible", "True")
	window.setProperty("btnOk:Text", "Select")
	window.setProperty("btnCancel:Text", "@cancel")	
	returnList = Vector()  
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleSampleCallback)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setSpecializationCancelCallBack)	
	main.NGECore.getInstance().suiService.openSUIWindow(window);
	return
	
def handleSampleCallback(owner, window, eventType, returnList):
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	owner.setAttachment('ChosenCitySpec',9)
	showSpecConfirmWindow(owner)
	return

def showSpecConfirmWindow(owner):
	window = main.NGECore.getInstance().suiService.createSUIWindow('Script.messageBox', owner, owner, 0)
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:confirm_spec_t')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:confirm_spec_d')
	window.setProperty("btnOk:visible", "True")
	window.setProperty("btnCancel:visible", "True")
	window.setProperty("btnOk:Text", "Yes")
	window.setProperty("btnCancel:Text", "No")	
	returnList = Vector()  
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleSpecConfirmCallback)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, handleSpecConfirmCallback)	
	main.NGECore.getInstance().suiService.openSUIWindow(window);
	return
	
def handleSpecConfirmCallback(owner, window, eventType, returnList):
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	chosenSpec = owner.getAttachment('ChosenCitySpec')
	playerCity.setSpecialization(chosenSpec)
	return