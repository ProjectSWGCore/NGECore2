import sys

def preRun(core, actor, target, command):
	return

def run(core, actor, target, commandString):
	return

def setup(core, actor, target, command):
	args = command.getCommandArguments()
	
	if args and args[1]:
		weapon = core.objectService.getObject(long(args[1]))
		core.objectService.useObject(actor, weapon)
	return