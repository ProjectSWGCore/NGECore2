from resources.common import RadialOptions
from services.sui.SUIService import InputBoxType
from java.util import Vector
from main import NGECore
import sys

def createRadial(core, owner, target, radials):

    if owner.getGuildId() == 0:
        radials.add(RadialOptions(0, RadialOptions.serverGuildCreate, 3, '@guild:menu_create'))
        return

    return
    
def handleSelection(core, owner, target, option):
    # Create Guild
    if option == 185 and target:
    	core.guildService.handleCreateGuildName(owner, target)
        return
    return
