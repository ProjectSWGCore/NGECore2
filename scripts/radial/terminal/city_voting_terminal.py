from resources.common import RadialOptions
from services.sui.SUIService import MessageBoxType
from services.sui.SUIWindow import Trigger
from java.util import Vector
import sys

def createRadial(core, owner, target, radials):
	radials.clear()
	city = core.playerCityService.getCityObjectIsIn(owner)
	if not city:
		return
	radials.add(RadialOptions(0, 7, 0, 'Examine'))
	radials.add(RadialOptions(0, 224, 0, '@city/city:mayoral_race'))
	radials.add(RadialOptions(2, 225, 0, '@city/city:mayoral_standings'))
	radials.add(RadialOptions(2, 226, 0, '@city/city:mayoral_vote'))
	if city.isCandidate(owner.getObjectID()):
		radials.add(RadialOptions(2, 227, 0, '@city/city:mayoral_unregister'))
	else:
		radials.add(RadialOptions(2, 227, 0, '@city/city:mayoral_register'))
	
	
	return
	
def handleSelection(core, owner, target, option):

	city = core.playerCityService.getCityObjectIsIn(owner)
	if not city:
		return
	if option == 224 or option == 225:
		handleViewStandings(core, owner)
	if option == 226:
		handlePromptVote(core, owner)
	if option == 227 and city.isCandidate(owner.getObjectID()):
		handleUnregister(core, owner)
	if option == 227 and not city.isCandidate(owner.getObjectID()):
		handleRegister(core, owner)

	return
	
def handleViewStandings(core, owner):
	return
	
def handlePromptVote(core, owner):
	return

def handleUnregister(core, owner):
	return

def handleRegister(core, owner):
	return
	