from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
    radials.clear()
    
    radials.add(RadialOptions(0, 21, 1, 'Use Travel Ticket'))
    #radials.add(RadialOptions(0, 7, 1, ''))
    #radials.add(RadialOptions(0, 15, 1, ''))
    return
    
def handleSelection(core, owner, target, option):

    if option == 21 and target:
        
        tp = core.travelService.getNearestTravelPoint(owner)
        
        if owner is not None and tp is not None:
            print (str(tp.getLocation().getDistance2D(owner.getWorldPosition())))
            if tp.getLocation().getDistance2D(owner.getWorldPosition()) <= float(25):
                if tp.isShuttleAvailable() is True:
                    core.travelService.doTransport(owner, core.travelService.getTravelPointByName(target.getStringAttribute('@obj_attr_n:travel_arrival_planet'),
                                                                                                  target.getStringAttribute('@obj_attr_n:travel_arrival_point')))
                    core.objectService.destroyObject(target)
                    return
                return
            else:
                owner.sendSystemMessage('@travel:boarding_too_far', 0)
                return
            return
        return
    
    if option == 15 and target:
        core.objectService.destroyObject(target)
        return
    return
    
def handleSUI(owner, window, eventType, returnList):

    return