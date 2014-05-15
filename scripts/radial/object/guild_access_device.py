from resources.common import RadialOptions
from services.sui.SUIService import InputBoxType
from java.util import Vector
from main import NGECore
import sys

def createRadial(core, owner, target, radials):
    
    #radials.clear()
    
    if owner.getGuildId() != 0:
    	guild = core.guildService.getGuildById(owner.getGuildId)
    	
    	if guild is None:
    		return
    	
    	if guild.getLeader() == owner.getObjectID():
    	 radials.add(RadialOptions(0, RadialOptions.serverGuildGuildManagement, 3, '@guild:menu_guild_management'))
         radials.add(RadialOptions(0, RadialOptions.serverGuildMemberManagement, 3, '@guild:menu_member_management'))

		 #Guild Management
         radials.add(RadialOptions(3, RadialOptions.serverGuildDisband, 3, '@guild:menu_disband'))

		 #Member Management
         radials.add(RadialOptions(4, RadialOptions.serverGuildSponsor, 3, '@guild:menu_sponsor'))
        return
    return
    
def handleSelection(core, owner, target, option):
    return