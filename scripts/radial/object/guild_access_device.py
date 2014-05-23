from resources.common import RadialOptions
from services.sui.SUIService import InputBoxType
from java.util import Vector
from main import NGECore
import sys

def createRadial(core, owner, target, radials):
    
    #radials.clear()
    
    if owner.getGuildId() != 0:

    	guild = core.guildService.getGuildById(owner.getGuildId())
    	
    	if guild is None:
    		return
    	
        radials.add(RadialOptions(0, RadialOptions.serverGuildGuildManagement, 3, '@guild:menu_guild_management')) # Guild Management
        radials.add(RadialOptions(0, RadialOptions.serverGuildMemberManagement, 3, '@guild:menu_member_management')) # Member Management

        # Guild Management
        radials.add(RadialOptions(0, RadialOptions.serverGuildInfo, 3, '@guild:menu_info')) # Guild Information
        
        # Member Management
        
        # - Sponsor options
        if guild.getSponsers().contains(owner.getObjectID()):
            radials.add(RadialOptions(4, RadialOptions.serverGuildSponsor, 3, '@guild:menu_sponsor')) # Sponsor for Membership

            if guild.getSponsoredPlayers().size() > 0:
                radials.add(RadialOptions(4, RadialOptions.serverGuildSponsored, 3, '@guild:menu_sponsored')) # Sponsored for Membership
        
        
        return
    
    return
    
def handleSelection(core, owner, target, option):
    if option == RadialOptions.serverGuildInfo:
        guild = core.guildService.getGuildById(owner.getGuildId())
        
        guildInfoPrompt = "Guild Name: " + guild.getName() + "\n" + \
                        "(neutral)\n" + \
                        "Guild Abbreviation: " + guild.getAbbreviation() + "\n" + \
                        "Guild Leader: " + guild.getLeaderName() + "\n" + \
                        "GCW Region Defender: None\n" + \
                        "GCW Region Defender Bonus: 0%\n" + \
                            "Guild Members: " + str(guild.getMembers().size())
        wndGuildInfo = core.suiService.createMessageBox(core.suiService.MessageBoxType.MESSAGE_BOX_OK, '@guild:info_title', guildInfoPrompt, owner, target, 10)
        core.suiService.openSUIWindow(wndGuildInfo)
        return
    
    elif option == RadialOptions.serverGuildSponsor:
        core.guildService.handleGuildSponsor(owner)
        return
    
    elif option == RadialOptions.serverGuildSponsored:
        core.guildService.handleManageSponsoredPlayers(owner)
        return
    return