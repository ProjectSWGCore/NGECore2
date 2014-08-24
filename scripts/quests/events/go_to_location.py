from engine.resources.common import CRC

def run(core, activator, collidable):

	if activator.getPlayerObject() is None:
		return
	
	player = activator.getPlayerObject()
	
	if player.getActiveQuestName() != collidable.getAttachedQuest():
		return
		
	quest = player.getQuest(player.getActiveQuest())

	if quest.isCompleted():
		return
	
	if quest.getActiveTask() != collidable.getTask():
		return
		
	core.questService.completeActiveTask(activator, quest)
	return