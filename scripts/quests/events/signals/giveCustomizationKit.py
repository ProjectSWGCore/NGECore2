def setup(core):
	return

def wait(core, actor):
	print ('waiting')
	return

def activate(core, actor, quest):
	print ('Activate!!')
	core.questService.completeActiveTask(actor, quest)
	return