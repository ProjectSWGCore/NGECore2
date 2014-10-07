import sys

def setup():
	return
	
def run(core, actor, target, commandString):

	if actor is None:
		return
		
	player = actor.getPlayerObject()
	
	if player is None:
		return
	
	quest = player.getQuest(int(commandString))
	
	if quest is None:
		return
	
	core.questService.completeQuest(player, quest)
	return