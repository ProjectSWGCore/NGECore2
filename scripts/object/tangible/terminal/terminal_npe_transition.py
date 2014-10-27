import sys
 
def setup(core, object):
	object.setAttachment('radial_filename', 'object/usable')
	return

def use(core, owner, target):
	core.scriptService.callScript("scripts/", "terminal_npe_transition", "screenOne", core, owner);
 	return