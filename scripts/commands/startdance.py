from services.sui import SUIWindow
from services.sui import SUIService
from services.sui.SUIWindow import Trigger
from services.sui.SUIService import ListBoxType
from java.util import Vector
from java.util import HashMap
from resources.datatables import Posture
import sys

def setup():
    return

def run(core, actor, target, commandString):
    
    entSvc = core.entertainmentService
    global actorObject
    global coreObject
    global availableDances
    global suiWindow
    actorObject = actor
    coreObject = core

    if len(commandString) > 0:
        params = commandString.split(" ")
        startDance(core, actor, params[0], 0)
        return
    else:

        availableDances = entSvc.getAvailableDances(actor)

        suiSvc = core.suiService
        suiWindow = suiSvc.createListBox(ListBoxType.LIST_BOX_OK_CANCEL, "@performance:select_dance", "@performance:available_dances", availableDances, actor, None, 10)

        returnList = Vector()
        returnList.add("List.lstList:SelectedRow")
        suiWindow.addHandler(0, '', Trigger.TRIGGER_OK, returnList, handleStartdance)
        
        suiSvc.openSUIWindow(suiWindow)
        return
    return


def handleStartdance(core, owner, eventType, returnList):

    item = suiWindow.getMenuItems().get(int(returnList.get(0)))

    if not item:
      return

    #if eventType == 0:
    startDance(coreObject, actorObject, '', int(item.getObjectId()))
    return

def startDance(core, actor, danceName, visual):

    entSvc = core.entertainmentService

    if visual <= 0:
      visual = entSvc.getDanceVisualId(danceName)

    if visual <= 0:
      actor.sendSystemMessage('@performance:dance_unknown_self',0)
      return

    if not entSvc.isDance(visual):
      actor.sendSystemMessage('@performance:dance_unknown_self',0)
      return

    if not entSvc.canDance(actor, visual):
      actor.sendSystemMessage('@performance:dance_lack_skill_self',0)
      return

    if actor.getPosture() == 0x09:
      actor.sendSystemMessage('@performance:already_performing_self',0)
      return

    #TODO: check costume
    
    if actor.getPosture() != Posture.Upright:
    	actor.sendSystemMessage('@performance:dance_fail', 0)
    	return
    
    actor.sendSystemMessage('@performance:dance_start_self',0);
    # i'm not sure about this. i think stopping just stopped any watchers anyways
    # method doesn't exist now.
    #actor.notifyAudience('@performance:dance_start_other');

    danceVisual = 'dance_' + str(visual)

    if not actor.getPerformanceWatchee():
      #this also notifies the client with a delta4
      actor.setPerformanceWatchee(actor)
      actor.addSpectator(actor)
 
    #this should send a CREO3 
    actor.setPosture(0x09);

    playerObject = actor.getSlottedObject('ghost')
    if playerObject and playerObject.getProfession() == "entertainer_1a" and actor.getLevel() != 90:
      entSvc.startPerformanceExperience(actor)

    dance = entSvc.getDance(visual)

    # performanceId > 0 seems to trigger the note bubble stuff, so use 0 here
    # instead of dance.getLineNumber()
    # send CREO6 here
    # second param is some sort of counter or start tick
    entSvc.startPerformance(actor, dance.getLineNumber(), -842249156 , danceVisual, 1)

    return
