from resources.common import RadialOptions
from protocol.swg import ResourceListForSurveyMessage
from services.sui.SUIService import MessageBoxType
from services.sui.SUIWindow import Trigger
from java.util import Vector
import main.NGECore
import sys

def createRadial(core, owner, target, radials):
	#(byte parentId, short optionId, byte optionType, String description)
	radials.clear()
	radials.add(RadialOptions(0, 7, 0, 'Examine'))
	radials.add(RadialOptions(0, 216, 0, '@city/city:city_management'))
	radials.add(RadialOptions(0, 226, 0, '@city/city:city_info'))
	radials.add(RadialOptions(0, 227, 0, '@city/city:remove_trainers'))	
	radials.add(RadialOptions(2,  217, 0, '@city/city:city_name_new_t')) 
	radials.add(RadialOptions(2,  222, 0, '@city/city:city_register')) 
	radials.add(RadialOptions(2,  224, 0, '@city/city:unzone'))
	radials.add(RadialOptions(2,  218, 0, '@city/city:city_militia'))
	radials.add(RadialOptions(2,  219, 0, '@city/city:treasury_taxes'))
	radials.add(RadialOptions(2,  221, 0, '@city/city:treasury_withdraw'))
	radials.add(RadialOptions(2,  225, 0, '@city/city:city_specializations'))	
	radials.add(RadialOptions(2,  228, 0, '@city/city:align'))	
	radials.add(RadialOptions(3, 121, 0, '@city/city:non_citizen_city_status'))	
	radials.add(RadialOptions(3, 122, 0, '@city/city:rank_info_t'))
	radials.add(RadialOptions(3, 123, 0, '@city/city:citizen_list_t'))

	return
	
def handleSelection(core, owner, target, option):
	playerCity = core.playerCityService.getPlayerCity(owner)
	if playerCity==None:
		return
		
	if option == 217:
		if owner is not None:
			handleSetCityName(core, owner, target, option)
			return
	if option == 124:
		if owner is not None:
			core.housingService.createStatusSUIPage(owner,target)
			return
	if option == 129:
		if owner is not None:
			core.housingService.createPayMaintenanceSUIPage(owner,target)
			return
	if option == 50:
		if owner is not None:
			core.housingService.createRenameSUIPage(owner,target)
			return
	if option == 222:
		if owner is not None:
			handleRegisterMap(core, owner, target, option)
			return
	if option == 171:
		if owner is not None:
			core.housingService.handleListAllItems(owner,target)	
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
	if option == 121:
		if owner is not None:
			handleCityRankInfo(core, owner, target, option)
			return
	if option == 122:
		if owner is not None:
			handleCityRankInfo(core, owner, target, option)
			return
	if option == 123:
		if owner is not None:
			handleCitizenList(core, owner, target, option)
			return
	
def handleSetCityName(core, owner, target, option):	
	window = core.suiService.createInputBox(2,'@city/city:city_name_new_t','@city/city:city_name_new_d', owner, target, 0)
	returnList = Vector()
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, setnameCallBack)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setnameCallBack)	
	core.suiService.openSUIWindow(window);
	return
	
def setnameCallBack(owner, window, eventType, returnList):
	if returnList.size()==0:
		return
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)	
	playerCity.setCityName(returnList.get(0))
	owner.sendSystemMessage('@city/city:name_changed', 0)		
	return
	
	
def handleAdjustTaxes(core, owner, target, option):	
	
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	if playerCity.getRank()<2:
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
	if int(returnList.get(0))>2000 or int(returnList.get(0))<0:
		owner.sendSystemMessage('Income tax value is out of range of accepteable values.', 0)	
		handleSetIncomeTax(owner)
		return
		
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	playerCity.setIncomeTax(int(returnList.get(0)))
	owner.sendSystemMessage('@city/city:set_income_tax', 0)	
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
	if int(returnList.get(0))>20 or int(returnList.get(0))<0:
		owner.sendSystemMessage('Sales tax value is out of range of accepteable values.', 0)	
		handleSetSalesTax(owner)
		return
		
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	playerCity.setIncomeTax(int(returnList.get(0)))
	owner.sendSystemMessage('@city/city:set_sales_tax', 0)	
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
	if int(returnList.get(0))>50 or int(returnList.get(0))<0:
		owner.sendSystemMessage('Property tax value is out of range of accepteable values.', 0)	
		handleSetPropertyTax(owner)
		return
		
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	playerCity.setIncomeTax(int(returnList.get(0)))
	owner.sendSystemMessage('@city/city:set_property_tax', 0)	
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
	if int(returnList.get(0))>500 or int(returnList.get(0))<1:
		owner.sendSystemMessage('Travel tax value is out of range of accepteable values.', 0)	
		handleSetIncomeTax(owner)
		return
		
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	playerCity.setTravelTax(int(returnList.get(0)))
	owner.sendSystemMessage('@city/city:set_travel_fee', 0)	
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
	if int(returnList.get(0))>500 or int(returnList.get(0))<1:
		owner.sendSystemMessage('Garage tax value is out of range of accepteable values.', 0)	
		handleSetIncomeTax(owner)
		return
		
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	playerCity.setGarageTax(int(returnList.get(0)))
	owner.sendSystemMessage('@city/city:set_garage_tax', 0)	
	return
	
def handleWithdrawal(core, owner, target, option):	

	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	window = core.suiService.createSUIWindow("Script.transfer", owner, target, 10)
	
	window.setProperty("bg.caption.lblTitle:Text", "@city/city:treasury_withdraw_subject")
	window.setProperty("Prompt.lblPrompt:Text", "@city/city:treasury_withdraw_prompt" + "\n \n @city/city:treasury_status : %s" % playerCity.getCityTreasury())	
			
	window.setProperty("msgPayMaintenance", "transaction.txtInputFrom");
	
	window.setProperty("transaction.lblFrom:Text", "@city/city:treasury_balance_t");
	window.setProperty("transaction.lblTo:Text", "@city/city:treasury_withdraw");		
	window.setProperty("transaction.lblFrom", "@city/city:treasury_balance_t");
	window.setProperty("transaction.lblTo", "@city/city:treasury_withdraw");	
			
	window.setProperty("transaction.lblStartingFrom:Text", '%s' % playerCity.getCityTreasury());
	window.setProperty("transaction.lblStartingTo:Text", '0');
			
	window.setProperty("transaction:InputFrom", "555555");
	window.setProperty("transaction:InputTo", "666666");
	
	window.setProperty("transaction:txtInputFrom", '%s' % playerCity.getCityTreasury());
	window.setProperty("transaction:txtInputTo", "1");
	window.setProperty("transaction.txtInputFrom:Text", '%s' % playerCity.getCityTreasury());
	window.setProperty("transaction.txtInputTo:Text", "" + "0");
	
	window.setProperty("transaction.ConversionRatioFrom", "1");
	window.setProperty("transaction.ConversionRatioTo", "0");
		
	window.setProperty("btnOk:visible", "True");
	window.setProperty("btnCancel:visible", "True");
	window.setProperty("btnOk:Text", "@ok");
	window.setProperty("btnCancel:Text", "@cancel");				

	returnList = Vector()
	returnList.add('transaction.txtInputFrom:Text')
	returnList.add('ransaction.txtInputTo:Text')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, setWithdrawalCallBack)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setWithdrawalCallBack)	
	core.suiService.openSUIWindow(window);
	return
	
def setWithdrawalCallBack(owner, window, eventType, returnList):
	if returnList.size()==0:
		return
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)	
	playerCity.setCityName(returnList.get(0))
	owner.sendSystemMessage('@city/city:name_changed', 0)		
	return
	
def handleCitizenList(core, owner, target, option):	
	window = core.suiService.createSUIWindow('Script.listBox', owner, target, 0);
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:citizen_list_t')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:citizen_list_prompt')	
	
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)	
	index = 1;
	for citizen in playerCity.getCitizens():
		citizenObject = core.objectService.getObject(citizen)
		window.addListBoxMenuItem(citizenObject.getCustomName(),index)
		index += 1
	
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)	
	returnList = Vector()
	returnList.add('txtInput:LocalText')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, setCitizenListCallBack)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setCitizenListCallBack)	
	core.suiService.openSUIWindow(window);
	return
	
def setCitizenListCallBack(owner, window, eventType, returnList):
	#handle waypoint creation to citizen's house	
	return
	
def handleCityRankInfo(core, owner, target, option):	
	window = core.suiService.createSUIWindow('Script.listBox', owner, target, 0);
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:rank_info_t')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:rank_info_d')	
	
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)	
	
	window.addListBoxMenuItem('City : ' + playerCity.getCityName(),0)
	window.addListBoxMenuItem('@city/city:radius_prompt : %s' % playerCity.getCityRadius(),1)
	window.addListBoxMenuItem('@city/city:city_rank_prompt : @city/city:rank' + str(playerCity.getRank()),2)
	window.addListBoxMenuItem('@city/city:reg_citizen_prompt : %s' % len(playerCity.getCitizens()),3)
	if playerCity.getRank()>=3 and playerCity.getSpecialization()>-1:
		window.addListBoxMenuItem('@city/city:specialization_prompt : @city/city:' + playerCity.getSpecializationSTFNamesAsList().get(playerCity.getSpecialization()),4)
		
	
	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	window.setProperty('btnCancel:Text', '@cancel')
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)	
	returnList = Vector()
	returnList.add('txtInput:LocalText')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, setCityInfoCallBack)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setCitizenListCallBack)	
	core.suiService.openSUIWindow(window);
	return
	
def setCityInfoCallBack(owner, window, eventType, returnList):
	#handle waypoint creation to citizen's house	
	return
	
def handleRegisterMap(core, owner, target, option):	
	
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	if playerCity.getRank()<3:
		owner.sendSystemMessage('@city/city:cant_register_rank', 0)
		return
		
	if owner.getObjectId()!=playerCity.getMayorID():
		owner.sendSystemMessage('@city/city:cant_register', 0)
		return
	
	# now register it somehow
	
	return
	
def setRegisterMapCallBack(owner, window, eventType, returnList):
	if returnList.size()==0:
		return
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)	
	playerCity.setCityName(returnList.get(0))
	owner.sendSystemMessage('@city/city:name_changed', 0)		
	return
	
def handleSpecialization(core, owner, target, option):	
	
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	if playerCity.getRank()<3:
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
	window.addListBoxMenuItem('@city/city:city_spec_master_manufacturing',9)
	window.addListBoxMenuItem('@city/city:city_spec_master_healing',10)
	window.addListBoxMenuItem('@city/city:city_spec_stronghold',11)

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
	playerCity.setSpecialisation(chosenSpec)
	return