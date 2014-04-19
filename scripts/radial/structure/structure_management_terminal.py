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
	radials.add(RadialOptions(0, 78, 0, '@player_structure:management'))
	radials.add(RadialOptions(0, 117, 0, '@player_structure:permissions'))	
	radials.add(RadialOptions(2, 128, 0, '@player_structure:permission_destroy')) 
	radials.add(RadialOptions(2, 124, 0, '@player_structure:management_status')) 	
	radials.add(RadialOptions(2, 129, 0, '@player_structure:management_pay')) 
	radials.add(RadialOptions(2,  50, 0, '@base_player:set_name')) 
	radials.add(RadialOptions(2,  127, 0, '@player_structure:management_residence')) 
	radials.add(RadialOptions(2,  125, 0, '@player_structure:management_privacy : ' + core.housingService.fetchPrivacyString(target)))
	radials.add(RadialOptions(2,  171, 0, '@player_structure:find_items_find_all_house_items'))
	radials.add(RadialOptions(2,  173, 0, '@player_structure:move_first_item'))
	radials.add(RadialOptions(2,  174, 0, '@player_structure:find_items_search_for_house_items'))
	radials.add(RadialOptions(2,  175, 0, '@player_structure:delete_all_items_title'))	
	radials.add(RadialOptions(2,  172, 0, 'Pack Up This Building'))
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
	
	
