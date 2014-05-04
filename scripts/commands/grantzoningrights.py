import sys
from java.lang import System
from resources.common import OutOfBand
from resources.common import ProsePackage
import main.NGECore

def setup():
    return
    
def run(core, actor, target, commandString):

	if target == None:
		return
	
	#if !actor.hasSkill('Politician'):
		#You must be a Politician to enable city zoning.
		#actor.sendSystemMessage('@city/city:zoning_skill', 0)
		#return
	
	thisCity = main.NGECore.getInstance().playerCityService.getCityObjectIsIn(actor)
	
	if thisCity == None:
		#not_in_city_limits
		actor.sendSystemMessage('@city/city:not_in_city_limits', 0)
		return
		
	if thisCity.getMayorID()!=actor.getObjectID() and not thisCity.isMilitiaMember(actor.getObjectID()):	
		#You must be the mayor of the city or a member of the city militia to grant zoning rights.	
		actor.sendSystemMessage('@city/city:grant_rights_fail', 0)
		actor.sendSystemMessage('thisCity.getMayorID() %s' % thisCity.getMayorID(), 0)
		actor.sendSystemMessage('actor.getObjectID() %s' % actor.getObjectID(), 0)
		return
		
	#You have granted %TO zoning rights for 24 hours.
	actor.sendSystemMessage(OutOfBand.ProsePackage("@city/city:rights_granted_self", "TO", target.getCustomName()), 0)
	target.setAttachment('Has24HZoningFor',thisCity.getCityID())
	target.setAttachment('Has24HZoningUntil',System.currentTimeMillis()+60*60*24)
	target.sendSystemMessage(OutOfBand.ProsePackage("@city/city:rights_granted", "TO", thisCity.getCityName()), 0)
		
	return