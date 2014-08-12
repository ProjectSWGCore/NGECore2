import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	if core.questService.adminActivateQuest(actor, commandString) is False:
		actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF ActivateQuest: You entered a quest that does not exist or forgot to enter one!', 0)
		return
	
	core.questService.sendQuestWindow(actor, commandString)
	actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF ActivateQuest: Command completed successfully.', 0)
	return