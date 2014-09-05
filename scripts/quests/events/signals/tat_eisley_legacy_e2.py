def setup(core):
	return

def wait(core, actor):
	return

def activate(core, actor, quest):
	core.questService.completeActiveTask(actor, quest)
	
	if (actor.getLevel() >= 10):
		core.questService.sendQuestAcceptWindow(actor, 'quest/tatooine_eisley_gotoniko')
	else:
		core.questService.sendQuestAcceptWindow(actor, 'quest/tatooine_eisley_gotomayor')
	return