from engine.resources.common import CRC

def run(core, activator, collidable):

	if activator.getPlayerObject() is None:
		return
	
	player = activator.getPlayerObject()
	
	if player.getQuest(collidable.getAttachedQuest()) == None:
		return
		
	quest = player.getQuest(collidable.getAttachedQuest())

	if quest.isCompleted():
		return
	
	if quest.getActiveTask() != collidable.getTask():
		return
		
	core.questService.completeActiveTask(activator, quest)
	return