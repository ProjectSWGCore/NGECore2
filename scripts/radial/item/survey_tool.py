from resources.common import RadialOptions
from protocol.swg import ResourceListForSurveyMessage
from services.sui.SUIService import MessageBoxType
from services.SurveyService import createSurveyRangeSUIWindow
from services.sui.SUIWindow import Trigger
from java.util import Vector
from org.python.core.util import StringUtil
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
			generalType = None
			toolName = target.getDetailName();
			if toolName is 'survey_tool_mineral':
				generalType = 0
			elif toolName is 'survey_tool_inorganic':
				generalType = 1
			elif toolName is 'survey_tool_organic' or toolName is 'survey_tool_lumber':
				generalType = 2
			elif toolName is 'survey_tool_gas':
				generalType = 3
			elif toolName is 'survey_tool_moisture':
				generalType = 4
			elif toolName is 'survey_tool_solar':
				generalType = 5
			elif toolName is 'survey_tool_wind':
				generalType = 6
			elif toolName is 'survey_tool_liquid':
				generalType = 7
			
			rlfsm = ResourceListForSurveyMessage(owner.getObjectId(), core.resourceService.getSpawnedResourcesByPlanetAndType(owner.getPlanetId(), StringUtil.toBytes(str(generalType))[0]))
			owner.getClient().getSession().write(rlfsm.serialize())	
			return
	if option == 132:
		if owner is not None:
			surSvc = core.surveyService
			surSvc.createSurveyRangeSUIWindow(owner, target)
			return
