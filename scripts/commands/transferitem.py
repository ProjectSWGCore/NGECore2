import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	parsedMsg = commandString.split(' ', 3)
	objService = core.objectService
	containerID = long(parsedMsg[1])
	container = objService.getObject(containerID)
	if target and container and target.getContainer():
		print 'Default Test'
		oldContainer = target.getContainer()
		oldContainer.transferTo(actor, container, target)
	
	return
	