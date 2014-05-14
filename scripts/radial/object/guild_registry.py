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
    
    return
    
def handleSelection(core, owner, target, option):
    suiSvc = NGECore().getInstance().suiService
    # Create Guild
    if option == 185 and target:
        wndGuildCreate = core.suiService.createInputBox(InputBoxType.INPUT_BOX_OK_CANCEL, '@guild:create_name_title', '@guild:create_name_prompt', owner, owner, 0, handleGuildCreateName)
        wndGuildCreate.setProperty("txtInput:MaxLength", "24");
        wndGuildCreate.setProperty("txtInput:NumericInteger", "false")
        suiSvc.openSUIWindow(wndGuildCreate)
        return
 
    return
    
def handleGuildCreateName(owner, window, eventType, returnList):
    suiSvc = NGECore().getInstance().suiService
    if eventType == 0:
        guildName = str(returnList.get(0))
        if guildName is not None:
        	owner.setAttachment('guildName', guildName)
        	wndGuildInitials = NGECore().getInstance().suiService.createInputBox(InputBoxType.INPUT_BOX_OK, '@guild:create_abbrev_title', '@guild:create_abbrev_prompt', owner, owner, 0, doGuildCreate)
        	wndGuildInitials.setProperty("txtInput:MaxLength", "4");
        	wndGuildInitials.setProperty("txtInput:NumericInteger", "false")
        	suiSvc.openSUIWindow(wndGuildInitials)
        return
    return

def doGuildCreate(owner, window, eventType, returnList):
    if eventType == 0:
        guild = NGECore().getInstance().guildService.createGuild(str(returnList.get(0)), str(owner.getAttachment('guildName')), owner)
        owner.setGuildId(guild.getId())
        return
    return