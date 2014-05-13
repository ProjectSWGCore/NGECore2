from resources.common import RadialOptions
from services.sui.SUIService import InputBoxType
from java.util import Vector
from main import NGECore
import sys

def createRadial(core, owner, target, radials):
    
    #radials.clear()
    
    if owner.getGuildId() == 0:
        radials.add(RadialOptions(0, RadialOptions.serverGuildCreate, 3, '@guild:menu_create'))
        return
    
    else:
        radials.add(RadialOptions(0, RadialOptions.serverGuildGuildManagement, 3, '@guild:menu_guild_management'))
        radials.add(RadialOptions(0, RadialOptions.serverGuildMemberManagement, 3, '@guild:menu_member_management'))
    
        #Guild Management
    
        radials.add(RadialOptions(3, RadialOptions.serverGuildDisband, 3, '@guild:menu_disband'))
    
        #Member Management
        radials.add(RadialOptions(4, RadialOptions.serverGuildSponsor, 3, '@guild:menu_sponsor'))
        return
    
    return
    
def handleSelection(core, owner, target, option):

    # Create Guild
    if option == 185 and target:
        wndGuildCreate = core.suiService.createInputBox(InputBoxType.INPUT_BOX_OK_CANCEL, '@guild:create_name_title', '@guild:create_name_prompt', owner, owner, 0, handleGuildCreateName)
        wndGuildCreate.setProperty("txtInput:MaxLength", "25");
        suiSvc.openSUIWindow(wndGuildCreate)
        return
 
    return
    
def handleGuildCreateName(owner, window, eventType, returnList):

    if eventType == 0:
        guildName = str(returnList.get(0))
        if guildName is not None:
        	owner.setAttachment('guildName', guildName)
        	wndGuildInitials = NGECore().getInstance().suiService.createInputBox(InputBoxType.INPUT_BOX_OK, '@guild:create_abbrev_title', '@guild:create_abbrev_prompt', owner, owner, 0, doGuildCreate)
        	wndGuildInitials.setProperty("txtInput:MaxLength", "5");
        	suiSvc.openSUIWindow(wndGuildInitials)
        return
    return

def doGuildCreate(owner, window, eventType, returnList):
    if eventType == 0:
        guild = NGECore().getInstance().guildService.createGuild(str(returnList.get(0)), str(owner.getAttachment('guildName')), owner)
        owner.setGuildId(guild.getId())
        return
    return