from resources.common import RadialOptions
from services.sui.SUIService import MessageBoxType
from services.sui.SUIWindow import Trigger
from java.util import Vector
from java.util import TreeMap
from java.util import Map
from java.lang import System
from java.lang import Long
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
		core.playerCityService.handleViewStandings(owner, city)
	if option == 226:
		core.playerCityService.handlePromptVote(owner, city)
	if option == 227 and city.isCandidate(owner.getObjectID()):
		handleUnregister(core, owner)
	if option == 227 and not city.isCandidate(owner.getObjectID()):
		handleRegister(core, owner)

	return
		
def handlePromptVote(core, owner):
	return

def handleUnregister(core, owner):
	city = core.playerCityService.getCityObjectIsIn(owner)
	if not city or not city.isCitizen(owner.getObjectID()):
		return
	if city.isElectionLocked():
		owner.sendSystemMessage('@city/city:registration_locked', 0)
		return
	if city.isCandidate(owner.getObjectID()):
		city.getElectionList().remove(Long(owner.getObjectID()))
		owner.sendSystemMessage('@city/city:unregistered_race', 0)
		city.sendCandidateUnregisteredMail(owner)
	return

def handleRegister(core, owner):
	city = core.playerCityService.getCityObjectIsIn(owner)
	if not city:
		return
	if not city.isCitizen(owner.getObjectID()):
		owner.sendSystemMessage('@city/city:register_noncitizen', 0)
		return
	if city.isElectionLocked():
		owner.sendSystemMessage('@city/city:registration_locked', 0)
		return
	if owner.getAttachment('registerElectionCooldown') and owner.getAttachment('registerElectionCooldown') > System.currentTimeMillis():
		owner.sendSystemMessage('@city/city:register_timestamp', 0)
		return		
	city.getElectionList().put(Long(owner.getObjectID()), 0)
	owner.sendSystemMessage('@city/city:register_congrats', 0)	
	owner.setAttachment('registerElectionCooldown', System.currentTimeMillis() + 86400000)
	city.sendCandidateRegisteredMail(owner)
	return
	