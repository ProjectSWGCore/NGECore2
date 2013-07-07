import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	
	parsedMsg = commandString.split(' ')
	for i in parsedMsg:
		if len(i) == 0 or i == '-255':
			continue
		object = core.objectService.getObject(long(i))
		if object:
			core.attributeService.handleGetAttributes(object, actor)
			
	return
	
def isLong(a):
	try:
		long(a)
		return True
	except:
		return False
	