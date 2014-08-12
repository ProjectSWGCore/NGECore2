import sys

def setup():
	return
	
def run(core, actor, target, commandString):

	# WARNING: Quest may not show up if you haven't completed a prior quest if it's a chain - this is clientside, no way to circumvent atm
	core.questService.adminActivateQuest(actor, commandString)
	actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF ActivateQuest: Command completed successfully.', 0)
	return