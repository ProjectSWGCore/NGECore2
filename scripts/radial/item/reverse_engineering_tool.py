from resources.common import RadialOptions
from protocol.swg import ResourceListForSurveyMessage
from services.sui.SUIService import MessageBoxType
from services.SurveyService import createSurveyRangeSUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
import sys

def createRadial(core, owner, target, radials):
	radials.clear()
	radials.add(RadialOptions(0, 17, 1, 'Open'))
	radials.add(RadialOptions(0, 7, 2, 'Examine'))
	radials.add(RadialOptions(0, 15, 3, 'Destroy'))
	radials.add(RadialOptions(0, 14, 4, 'Drop'))
	radials.add(RadialOptions(0, 132, 5, 'Reverse Engineer'))	
	radials.add(RadialOptions(0, 133, 6, 'Create Skill Enhancing Attachment'))	
	radials.add(RadialOptions(0, 134, 7, 'Create Powerup'))
	return
	
def handleSelection(core, owner, target, option):
	if owner.getPlayerObject().getProfession()!='trader_0a' and owner.getPlayerObject().getProfession()!='trader_0b' and owner.getPlayerObject().getProfession()!='trader_0c' and owner.getPlayerObject().getProfession()!='trader_0d':
		owner.sendSystemMessage('@base_player:not_correct_skill', 0)
		return 
		
	if option == 21 and target:
		if owner is not None:	
			
			return
	if option == 132:
		if owner is not None:
			core.reverseEngineeringService.reverseEngineer(owner, target)
			return
			
	if option == 133:
		if owner is not None:
			core.reverseEngineeringService.createSEA(owner, target)
			return
			
	if option == 134:
		if owner is not None:
			core.reverseEngineeringService.createPowerup(owner, target)
			return
			
	return
