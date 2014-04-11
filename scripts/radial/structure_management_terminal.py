from resources.common import RadialOptions
from protocol.swg import ResourceListForSurveyMessage
from services.sui.SUIService import MessageBoxType
from services.SurveyService import createSurveyRangeSUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
import sys

def createRadial(core, owner, target, radials):
	#(byte parentId, short optionId, byte optionType, String description)
	radials.clear()
	owner.sendSystemMessage('target template ' + target.getTemplate(),1)
	radials.add(RadialOptions(0, 7, 0, 'Examine'))
	radials.add(RadialOptions(0, 78, 0, '@player_structure:management'))
	radials.add(RadialOptions(0, 117, 0, '@player_structure:permissions'))	
	radials.add(RadialOptions(2, 118, 0, '@harvester:manage')) 
	radials.add(RadialOptions(2, 128, 0, '@player_structure:permission_destroy')) 
	radials.add(RadialOptions(2, 124, 0, '@player_structure:management_status')) 	
	radials.add(RadialOptions(2, 129, 0, '@player_structure:management_pay')) 
	radials.add(RadialOptions(2,  50, 0, '@base_player:set_name')) 
	radials.add(RadialOptions(2,  51, 0, '@player_structure:management_power')) 
	radials.add(RadialOptions(3, 121, 0, '@player_structure:permission_admin'))	
	radials.add(RadialOptions(3, 123, 0, '@player_structure:permissions'))

	return
	
def handleSelection(core, owner, target, option):
	if option == 118 and target:
		if owner is not None:	
			core.housingService.handleOperateMachinery(owner,target)
			return
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
	if option == 51:
		if owner is not None:
			core.housingService.handleDepositPower(owner,target)
			return
	if option == 121:
		if owner is not None:
			core.housingService.handlePermissionAdmin(owner,target)
			return
	if option == 123:
		if owner is not None:
			core.housingService.handlePermissionHopper(owner,target)	
			return
