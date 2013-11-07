from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
    return
    
def handleSelection(core, owner, target, option):
    trvSvc = core.travelService
    if option == 21 and target:
        core.commandService.callCommand(owner, 'boardshuttle', target, '')
        print ('command called')
        return
    return
    
def handleSUI(owner, window, eventType, returnList):

    return