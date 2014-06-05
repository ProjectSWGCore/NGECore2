import sys

def setup():
    return
    
def run(core, actor, target, commandString):

    playerObject = actor.getSlottedObject('ghost')
    #print ("Title set to: " + commandString)
    #print ("List of Titles: " + playerObject.getTitleList().toString())
    if playerObject is None:
        return
    
    if str(commandString) in playerObject.getTitleList() or str(commandString) == "citizenship" or str(commandString) == "": playerObject.setTitle(str(commandString))
   
    return