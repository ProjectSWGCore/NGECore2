import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	# commandString = 0 quest/quest_name

	player = actor.getPlayerObject()
	
	if player is None:
		return
	
	questName = commandString.split("quest/")[1]

	quest = player.getQuest(questName)
	
	if quest is None:
		return
	
	if quest.isCompleted():
		return
		
	player.removeQuest(questName)
	return