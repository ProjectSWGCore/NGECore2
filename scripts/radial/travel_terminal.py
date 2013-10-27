from resources.common import RadialOptions
from protocol.swg import EnterTicketPurchaseModeMessage
import sys

def createRadial(core, owner, target, radials):
    return
    
def handleSelection(core, owner, target, option):

    if option == 21 and target:
        tpm = EnterTicketPurchaseModeMessage()
        owner.getClient().getSession().write(tpm.serialize())
        #print ('Sent EnterTicketPurchaseModeMessage!')
    return
    
def handleSUI(owner, window, eventType, returnList):

    return