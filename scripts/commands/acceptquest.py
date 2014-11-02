import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	player = actor.getPlayerObject()
	
	if player is None:
		return
	
	questName = core.questService.getQuestStringByCrc(int(commandString))
	core.questService.immediatlyActivateQuest(actor, questName)
	return
