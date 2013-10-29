from resources.common import RadialOptions
from protocol.swg import EnterTicketPurchaseModeMessage
import sys

def createRadial(core, owner, target, radials):
    return
    
def handleSelection(core, owner, target, option):

    if option == 21 and target:
        tpm = EnterTicketPurchaseModeMessage(owner.getPlanet().getName(), core.mapService.getClosestCityName(owner))
        owner.getClient().getSession().write(tpm.serialize())
        #print ('Planet name: ' + owner.getPlanet().getName())
        #print ('City name: ' + core.mapService.getClosestCityName(owner))
    return
    
def handleSUI(owner, window, eventType, returnList):

    return