import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    trvSvc = core.travelService
    ticketList = trvSvc.getTicketList(actor)
    print ('command called')
    if ticketList.size() >= 1:
        trvSvc.sendTicketWindow(actor)
        return
        
    elif ticketList.size() == 1:
        planetArrival = ticketList.get[0].getStringAttribute('@obj_attr_n:travel_arrival_planet')
        locationArrival = ticketList.get[0].getStringAttribute('@obj_attr_n:travel_arrival_point')
        tpArrival = trvSvc.getTravelPointByName(planet, location)
        
        trvSvc.doTransport(actor, tpArrival)
        
        return
    elif ticketList.isEmpty == True:
        actor.sendSystemMessage("You don't have a travel ticket for this shuttle.", 0)
        return
    #--tp = core.travelService.getTravelPointByName(travelTicket.getStringAttribute('@obj_attr_n:travel_arrival_point'))
    return