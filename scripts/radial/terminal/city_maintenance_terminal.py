from resources.common import RadialOptions
from protocol.swg import ResourceListForSurveyMessage
from services.sui.SUIService import MessageBoxType
from services.sui.SUIWindow import Trigger
from java.util import Vector
import sys

def createRadial(core, owner, target, radials):
	#(byte parentId, short optionId, byte optionType, String description)
	radials.clear()
	radials.add(RadialOptions(0, 7, 0, 'Examine'))
	radials.add(RadialOptions(0, 78, 0, '@city/city:city_management @player_structure:management'))
	radials.add(RadialOptions(0, 117, 0, '@city/city:city_info @player_structure:permissions'))	
	radials.add(RadialOptions(2,  50, 0, '@city/city:city_name_new_t')) 
	radials.add(RadialOptions(2,  127, 0, '@city/city:city_register')) 
	radials.add(RadialOptions(2,  125, 0, '@city/city:unzone'))
	radials.add(RadialOptions(2,  171, 0, '@city/city:city_militia'))
	radials.add(RadialOptions(2,  173, 0, '@city/city:treasury_taxes'))
	radials.add(RadialOptions(2,  174, 0, '@city/city:treasury_withdraw'))
	radials.add(RadialOptions(2,  175, 0, '@city/city:city_specializations'))	
	
	radials.add(RadialOptions(3, 121, 0, '@player_structure:permission_enter'))	
	radials.add(RadialOptions(3, 123, 0, '@player_structure:permission_banned'))

	return
	
def handleSelection(core, owner, target, option):
	
	if option == 128:
		if owner is not None:
			core.housingService.createDestroySUIPage(owner,target)
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
	if option == 127:
		if owner is not None:
			core.housingService.declareResidency(owner,target)
			return
	if option == 171:
		if owner is not None:
			core.housingService.handleListAllItems(owner,target)	
			return
	if option == 175:
		if owner is not None:
			core.housingService.handleDeleteAllItems(owner,target)	
			return
	if option == 173:
		if owner is not None:
			core.housingService.handleFindLostItems(owner,target)	
			return
	if option == 174:
		if owner is not None:
			core.housingService.handleSearchForItems(owner,target)	
			return
	if option == 121:
		if owner is not None:
			core.housingService.handlePermissionEntry(owner,target)
			return
	if option == 123:
		if owner is not None:
			core.housingService.handlePermissionBan(owner,target)	
			return
	
	
