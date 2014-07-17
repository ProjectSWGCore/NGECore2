import sys

def setup():
	return
	
def run(core, actor, target, commandString):

	commandArgs = commandString.split(' ')
	newString = None
	# removes the strange varying color arguments the client sends to server
	for arg in commandArgs:
		if 'color' not in arg:
			if newString is None:
				newString = arg
			else:
				newString = newString + ' ' + arg
	

	core.commandService.callCommand(actor, 'waypoint', None, newString)
	return