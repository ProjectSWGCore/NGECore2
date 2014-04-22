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
	
	radials.add(RadialOptions(3, 121, 0, '@city/city:citizens'))	
	radials.add(RadialOptions(3, 123, 0, '@city/city:citizens'))

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
	if option == 175:
		if owner is not None:
			core.housingService.handleDeleteAllItems(owner,target)	
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
			handleCitizenList(core, owner, target, option)
			return
	if option == 123:
		if owner is not None:
			handleCitizenList(core, owner, target, option)
			return
	
def handleSetCityName(core, owner, target, option):	
	window = core.suiService.createInputBox(2,'@player_structure:structure_status','@player_structure:structure_name_prompt', owner, target, 0)
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
	
def handleTaxes(core, owner, target, option):	
	window = core.suiService.createInputBox(2,'@player_structure:structure_status','@player_structure:structure_name_prompt', owner, target, 0)
	returnList = Vector()
	returnList.add('txtInput:LocalText')
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, setnameCallBack)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setnameCallBack)	
	core.suiService.openSUIWindow(window);
	return
	
def setTaxesCallBack(owner, window, eventType, returnList):
	if returnList.size()==0:
		return
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)	
	playerCity.setCityName(returnList.get(0))
	owner.sendSystemMessage('@city/city:name_changed', 0)		
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
	
def handleRegisterMap(core, owner, target, option):	
	
	playerCity = main.NGECore.getInstance().playerCityService.getPlayerCity(owner)
	if playerCity.getCityRank()<3:
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
	window = core.suiService.createSUIWindow('Script.listBox', owner, target, 0);
	window.setProperty('bg.caption.lblTitle:Text', '@city/city:city_specs_t')
	window.setProperty('Prompt.lblPrompt:Text', '@city/city:city_specs_d')	

	window.addListBoxMenuItem('Cloning Lab',0)
	window.addListBoxMenuItem('DNA Laboratory',1)
	window.addListBoxMenuItem('Encore Performance',2)
	window.addListBoxMenuItem('Entertainment District',3)
	window.addListBoxMenuItem('Improved Job Market',4)
	window.addListBoxMenuItem('Manufacturing Center',5)
	window.addListBoxMenuItem('Medical Center',6)
	window.addListBoxMenuItem('Research Center',7)
	window.addListBoxMenuItem('Sample Rich',8)

	window.setProperty('btnOk:visible', 'True')
	window.setProperty('btnCancel:visible', 'True')
	window.setProperty('btnOk:Text', '@ok')
	window.setProperty('btnCancel:Text', '@cancel')
	
	returnList = Vector()
	returnList.add('List.lstList:SelectedRow')			
	window.addHandler(0, '', Trigger.TRIGGER_OK, returnList, setSpecializationCallBack)
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, setSpecializationCallBack)	
	core.suiService.openSUIWindow(window);
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
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, handleCloningCallback)	
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
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, handleDNACallback)	
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
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, handleEncoreCallback)	
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
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, handleEntertainmentCallback)	
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
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, handleImprovedCallback)	
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
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, handleManufacturingCallback)	
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
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, handleMedicalCallback)	
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
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, handleResearchCallback)	
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
	window.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnList, handleSampleCallback)	
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