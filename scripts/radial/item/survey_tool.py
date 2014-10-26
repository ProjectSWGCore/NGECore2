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
			if 'survey_tool_mineral' in toolName:
				generalType = 0
			elif 'survey_tool_inorganic' in toolName:
				generalType = 1
			elif 'survey_tool_organic' in toolName or 'survey_tool_lumber' in toolName:
				generalType = 2
			elif 'survey_tool_gas' in toolName:
				generalType = 3
			elif 'survey_tool_moisture' in toolName:
				generalType = 4
			elif 'survey_tool_solar' in toolName:
				generalType = 5
			elif 'survey_tool_wind' in toolName:
				generalType = 6
			elif 'survey_tool_liquid' in toolName:
				generalType = 7
			
			rlfsm = ResourceListForSurveyMessage(owner.getObjectId(), core.resourceService.getSpawnedResourcesByPlanetAndType(owner.getPlanetId(), generalType))
			owner.getClient().getSession().write(rlfsm.serialize())	
			return
	if option == 132:
		if owner is not None:
			surSvc = core.surveyService
			surSvc.createSurveyRangeSUIWindow(owner, target)
			return
