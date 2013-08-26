from resources.objects.creature import CreatureObject
import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    actorFaction = actor.getFaction()
    
    print 'test'
    
    if commandString == ('imperial') and actorFaction != "imperial":
        actor.setFaction('imperial')
        actor.sendSystemMessage('You are aligned to the Imperial faction', 0)
        return
    
    if commandString == ('rebel') and actorFaction != "rebel":
        actor.setFaction('rebel')
        actor.sendSystemMessage('You are aligned to the Rebellion', 0)
        return
    
    if commandString == ('neutral') and actorFaction != "neutral":
        actor.setFaction('neutral')
        actor.sendSystemMessage('You aren\'t aligned to any faction.', 0)
        return
	
    return
    