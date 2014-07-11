import sys
from java.lang import System
from resources.common import OutOfBand
from resources.common import ProsePackage
import main.NGECore

def setup():
    return
    
def run(core, actor, target, commandString):

	if not target or not target.getClient():
		return
		
	thisCity = core.playerCityService.getCityObjectIsIn(actor)
	
	if not thisCity:
		#not_in_city_limits
		actor.sendSystemMessage('@city/city:not_in_city_limits', 0)
		return
		
	if thisCity.getMayorID() != actor.getObjectID() and not thisCity.isMilitiaMember(actor.getObjectID()):	
		#You must be the mayor of the city or a member of the city militia to grant zoning rights.	
		actor.sendSystemMessage('@city/city:grant_rights_fail', 0)
		return
		
	if target.getObjectID() == thisCity.getMayorID():
		return
		
	if thisCity.hasZoningRights(target):
		thisCity.removeZoningRights(target)
		actor.sendSystemMessage('@city/city:rights_revoked', 0)
		target.sendSystemMessage('@city/city:rights_revoked_other', 0)
	else:
		thisCity.grantZoningRights(target)
		#You have granted %TO zoning rights for 24 hours.
		actor.sendSystemMessage(OutOfBand.ProsePackage("@city/city:rights_granted_self", "TO", target.getCustomName()), 0)
		target.sendSystemMessage(OutOfBand.ProsePackage("@city/city:rights_granted", "TO", thisCity.getCityName()), 0)
		
	return
	