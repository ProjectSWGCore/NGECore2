import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	if core.questService.adminActivateQuest(actor, commandString) is False:
		actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF activateQuest: Error. You have specified an invalid quest.', 0)
		return
	
	core.questService.sendQuestWindow(actor, actor.getPlayerObject().getQuestJournal().get(0))
	actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF activateQuest: Command completed successfully.', 0)
	return
