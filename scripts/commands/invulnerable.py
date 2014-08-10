import sys
from resources.datatables import Options

def run(core, actor, target, commandString):
	commandArgs = commandString.split(' ')
	command = commandArgs[0]

	
	if  command == 'invisible':
		if (actor.isInStealth()):
			actor.setInStealth(False)
			actor.setRadarVisible(True)
			actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Invulnerable invisible: Command completed successfully. You are no longer Invisible.', 0)
			
		else:
			actor.setInStealth(True)
			actor.setRadarVisible(False)
			actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Invulnerable invisible: Command completed successfully. You are now Invisible.', 0)
	
	if actor.getOptionsBitmask() == 128:
		actor.setOptionsBitmask(Options.NONE)
		actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Invulnerable: Command completed successfully. You are now Invulnerable.', 0)
	
	
	elif actor.getOptionsBitmask() == 0:
		actor.setOptionsBitmask(Options.ATTACKABLE)
		print(actor.getOptionsBitmask())
		
		actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Invulnerable: Command completed successfully. You are no longer Invulnerable.', 0)

	return
	
	