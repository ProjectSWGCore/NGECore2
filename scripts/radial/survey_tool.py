from resources.common import RadialOptions
from protocol.swg import ResourceListForSurveyMessage
from services.sui.SUIService import MessageBoxType
from services.SurveyService import createSurveyRangeSUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
import sys

def createRadial(core, owner, target, radials):
	radials.clear()
	radials.add(RadialOptions(0, 21, 1, 'Use'))
	radials.add(RadialOptions(0, 7, 0, 'Examine'))
	radials.add(RadialOptions(0, 15, 0, 'Destroy'))
	radials.add(RadialOptions(0, 132, 1, 'Tool Options'))	
	return
	
def handleSelection(core, owner, target, option):
	if option == 21 and target:
		if owner is not None:	
			rlfsm = ResourceListForSurveyMessage(core,target,owner)
			owner.getClient().getSession().write(rlfsm.serialize())	
			return
	if option == 132:
		if owner is not None:
			surSvc = core.surveyService
			surSvc.createSurveyRangeSUIWindow(owner, target)
			return
