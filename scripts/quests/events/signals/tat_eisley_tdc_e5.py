def setup(core):
	return

def wait(core, actor):
	return

def activate(core, actor, quest):
	core.questService.completeActiveTask(actor, quest)
	return