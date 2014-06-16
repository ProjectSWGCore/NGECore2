import sys
from java.lang import Long

def setup():
	return

def run(core, actor, target, objectid):
	object = core.objectService.getObject(long(objectid))
	target.setAttachment('UsedObjectID',Long(objectid))
	core.objectService.useObject(actor, target)
	return