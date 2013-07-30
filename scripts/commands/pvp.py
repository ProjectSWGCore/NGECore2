from resources.objects.creature import CreatureObject
from protocol.swg import UpdatePVPStatusMessage
import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    actorFaction = actor.getFaction()
    actorStatus = actor.getFactionStatus()
    pvpStatus = UpdatePVPStatusMessage(actor.getObjectId())
    
    if commandString == ('imperial') and actorFaction != "imperial":
        actor.setFaction('imperial')
        return
    
    if commandString == ('rebel') and actorFaction != "rebel":
        actor.setFaction('rebel')
        return
    
    if commandString == ('neutral') and actorFaction != "neutral":
        actor.setFaction('neutral')
        return
    
    #if actorStatus == 0 and actorFaction == "imperial" or "rebel":
        #actor.setFactionStatus(1)
        #print ("FactionStatus: " + str(actor.getFactionStatus()))
        #actor.sendSystemMessage('You are no longer On Leave.', 0)
        #return

    if actorStatus == 1 and actorFaction == "imperial":
        actor.setFactionStatus(2)
        pvpStatus.setFaction(UpdatePVPStatusMessage.factionCRC.Imperial)
        pvpStatus.setStatus(55)
        actor.notifyObservers(pvpStatus.serialize(), True)
        actor.sendSystemMessage('@faction_recruiter:overt_complete', 0)
        print ("FactionStatus: " + str(actor.getFactionStatus()))
        return
    
    if actorStatus == 1 and actorFaction == "rebel":
        actor.setFactionStatus(2)
        pvpStatus.setFaction(UpdatePVPStatusMessage.factionCRC.Rebel)
        pvpStatus.setStatus(55)
        actor.notifyObservers(pvpStatus.serialize(), True)
        actor.sendSystemMessage('@faction_recruiter:overt_complete', 0)
        print ("FactionStatus: " + str(actor.getFactionStatus()))
        return
    
    if actorStatus == 2 and actorFaction == "imperial":
        actor.setFactionStatus(1)
        pvpStatus.setFaction(UpdatePVPStatusMessage.factionCRC.Imperial)
        pvpStatus.setStatus(16)
        actor.notifyObservers(pvpStatus.serialize(), True)
        actor.sendSystemMessage('@faction_recruiter:covert_complete', 0)
        print ("FactionStatus: " + str(actor.getFactionStatus()))
        return
    
    if actorStatus == 2 and actorFaction == "rebel":
        actor.setFactionStatus(1)
        pvpStatus.setFaction(UpdatePVPStatusMessage.factionCRC.Rebel)
        pvpStatus.setStatus(16)
        actor.notifyObservers(pvpStatus.serialize(), True)
        actor.sendSystemMessage('@faction_recruiter:covert_complete', 0)
        print ("FactionStatus: " + str(actor.getFactionStatus()))
        return
    
    
    return