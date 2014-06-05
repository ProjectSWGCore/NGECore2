import sys

def run(core, actor, target, commandString):
	instSvc = core.instanceService
	
	if instSvc.isInInstance(actor):
		instSvc.remove(instSvc.getActiveInstance(actor), actor)
	
