from resources.common import Console
import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    trvSvc = core.travelService
    ticketList = trvSvc.getTicketList(actor)
    nearestPoint = trvSvc.getNearestTravelPoint(actor)
    
    #print (nearestPoint.getName())

    if nearestPoint.isShuttleAvailable() is True:

        if ticketList.size() >= 1:
            trvSvc.sendTicketWindow(actor, target)
            return

        elif ticketList.isEmpty:
            actor.sendSystemMessage('You do not have a ticket to board this shuttle.', 0)
            return
        return
    
    elif nearestPoint.isShuttleLanding() is True:
        actor.sendSystemMessage('The next shuttle is about to begin boarding.', 0)
        return
    else:
        # We don't want the remaining time to show as soon as the shuttle leaves, instead, we want an unavilable message.
        if nearestPoint.isShuttleAvailable() is False and nearestPoint.isShuttleDeparting() is True:
            actor.sendSystemMessage('@travel:shuttle_not_available', 0)
            return
        elif nearestPoint.getSecondsRemaining() <= 0:
            actor.sendSystemMessage('The next shuttle is about to begin boarding.', 0)
            return
        else:
            actor.sendSystemMessage('The next shuttle will arrive in ' + str(nearestPoint.getSecondsRemaining()) + ' seconds.', 0)
            return
        return
    return
    # NOTE: In NGE video from Jan. 4th, 2010, ticket purchase window is shown with just 1 ticket and does not 
    # automatically transport the player.
