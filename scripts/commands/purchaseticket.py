import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    #print ('Command string: ' + commandString)
    
    trvSvc = core.travelService
    
    cmdArgs = commandString.split(" ")
    
    departurePlanet = cmdArgs[0]
    departureLoc = cmdArgs[1].replace("_"," ")
    arrivalPlanet = cmdArgs[2]
    arrivalLoc = cmdArgs[3].replace("_", " ")
    # 4 int
    # 5 int
    trvSvc.purchaseTravelTicket(actor, departurePlanet, departureLoc, arrivalPlanet, arrivalLoc, '1')
    
    return