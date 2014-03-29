from resources.common import RadialOptions
from protocol.swg import EnterTicketPurchaseModeMessage
import sys

def createRadial(core, owner, target, radials):
    return
    
def handleSelection(core, owner, target, option):

    if option == 21 and target:

        if owner is not None:
            if owner.getCombatFlag() == 1:
				owner.sendSystemMessage('You can\'t use that while in combat.', 0)
				return
            tpm = EnterTicketPurchaseModeMessage(owner.getPlanet().getName(), core.mapService.getClosestCityName(owner), owner)
            owner.getClient().getSession().write(tpm.serialize())
            return
    return
    
def handleSUI(owner, window, eventType, returnList):

    return