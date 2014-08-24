def setup(core):
	core.questService.addCollisionEvent('find_majolnir', 'task2', 3526, 5, -4619, 10, 'tatooine')
	return

def task2(core, activator, collidable):
	print ('called.')
	if activator.getPlayerObject() is None:
		return
	
	player = activator.getPlayerObject()

	if player.getQuest('find_majolnir') is None:
		print ('not in quest journal')
		return
		
	quest = player.getQuest('find_majolnir')

	if quest.isCompleted() or quest.getActiveStep() != 2:
		return
	
	core.questService.completeActiveTask(activator, quest)
	return