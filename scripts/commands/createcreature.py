import sys

def run(core, actor, target, commandString):

	commandArgs = commandString.split(' ')
	arg1 = commandArgs[0]
	if len(commandArgs) > 1:
		arg2 = commandArgs[1]

	if len(commandArgs) > 1:
		pos = actor.getWorldPosition()
		core.spawnService.spawnCreature(arg1, actor.getPlanet().getName(), 0, pos.x, pos.y, pos.z, 1, 0, 1, 0, int(arg2))
		actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF createCreature: Command completed successfully.' , 0)
		return
	
	else:
		actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF createCreature: Invalid Syntax. Syntax is: /createcreature <Mobile Template> <Level>.', 0)
		return
			

	return