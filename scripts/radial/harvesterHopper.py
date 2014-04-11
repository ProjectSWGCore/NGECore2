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
	radials.add(RadialOptions(0, 7, 0, 'Examine'))
	radials.add(RadialOptions(0, 78, 0, '@player_structure:management'))	
	radials.add(RadialOptions(2, 118, 0, '@harvester:manage'))  
	return
	
def handleSelection(core, owner, target, option):
	if option == 118 and target:
		if owner is not None:	
			core.harvesterService.handleOperateMachinery(owner,target)
			return
