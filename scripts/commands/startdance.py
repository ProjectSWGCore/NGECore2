from services.sui import SUIWindow
from services.sui import SUIService
from services.sui.SUIWindow import Trigger
from services.sui.SUIService import ListBoxType
from java.util import Vector
from java.util import HashMap
import sys

def setup():
    return

def run(core, actor, target, commandString):
    
    entSvc = core.entertainmentService

    if len(commandString) > 0:
        params = commandString.split(" ")
        startDance(core, actor, params[0])
        return
    else:

        available_dances = entSvc.getAvailableDances(actor)

        suiSvc = core.suiService
        suiWindow = suiSvc.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, "@performance:select_dance", "@performance:available_dances", available_dances, actor, actor, 10)

        returnParams = Vector()
        returnParams.add('btnOk:Text')
        returnParams.add('btnCancel:Text')
        suiWindow.addHandler(0, '', Trigger.TRIGGER_OK, returnParams, handleStartdance)
        suiWindow.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnParams, handleStartdance)
        
        suiSvc.openSUIWindow(suiWindow)
        return
    return


def handleStartdance(core, owner, eventType, returnList):
    return

def startDance(core, actor, danceName):

    entSvc = core.entertainmentService

    if not entSvc.isDance(danceName):
      actor.sendSystemMessage('@performance:dance_unknown_self',0)
      return

    if not entSvc.canDance(actor, danceName):
      actor.sendSystemMessage('@performance:dance_lack_skill_self',0)
      return
    return

    if actor.getPerformanceId() > 0:
      actor.sendSystemMessage('@performance:already_performing_self',0)
      return

    performance = entSvc.getPerformance(danceName)
    #TODO: check costume, posture, etc

    actor.sendSystemMessage('@performance:dance_start_self');
    actor.notifyAudience('@performance:dance_start_other');

    if not actor.getPerformanceWatchee():
      #this also needs to notify the client with a delta4
      actor.setPerformanceWatchee(actor)
   
    #this should send a CREO3 
    actor.setPosture(0x09);
    # send CREO6 here
    # second param is some sort of counter or start tick
    actor.startPerformance(performance.getLineNumber(), 0xCDCC4C3C, 'dance_' . performance.getVisualId true)

   

