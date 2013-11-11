import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    trvSvc = core.travelService
    
    cmdArgs = commandString.split(" ")
    
    departurePlanet = cmdArgs[0]
    departureLoc = cmdArgs[1].replace("_"," ")
    arrivalPlanet = cmdArgs[2]
    arrivalLoc = cmdArgs[3].replace("_", " ")
    roundTrip = cmdArgs[4]

    trvSvc.purchaseTravelTicket(actor, departurePlanet, departureLoc, arrivalPlanet, arrivalLoc, roundTrip)
    
    return