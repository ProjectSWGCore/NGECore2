import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    trvSvc = core.travelService
    ticketList = trvSvc.getTicketList(actor)
    nearestPoint = trvSvc.getNearestTravelPoint(actor)
    
    print (nearestPoint.getName())
    if nearestPoint.isShuttleAvailable:
        print ('inside nearestPoint if')
        if ticketList.size() >= 1:
            print ('inside list >= 1')
            trvSvc.sendTicketWindow(actor, target)
            print ('sent ticket window')
            return

        elif ticketList.isEmpty:
            actor.sendSystemMessage('You do not have a ticket to board this shuttle.', 0)
            return

        return
   
    else:
       actor.sendSystemMessage('The next shuttle will arrive in 60 seconds.', 0)
       return
    return
    # NOTE: In NGE video from Jan. 4th, 2010, ticket purchase window is shown with just 1 ticket and does not 
    # automatically transport the player.
