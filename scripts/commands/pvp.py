from resources.objects.creature import CreatureObject
import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    actorFaction = actor.getFaction()
    actorStatus = actor.getFactionStatus()
    if commandString.startswith('imperial') and actorFaction != "imperial":  
        actor.setFaction('imperial')
        return
    
    if commandString.startswith('rebel') and actorFaction != "rebel":
        actor.setFaction('rebel')
        return
    
    if actorStatus != 0 and actorStatus == 1:
        actor.setFactionStatus(2)
        actor.sendSystemMessage('@faction_recruiter:overt_complete', 0)
        return
    
    if actorStatus == 2:
        actor.setFactionStatus(1)
        actor.sendSystemMessage('@faction_recruiter:covert_complete', 0)
        return   
    return
    