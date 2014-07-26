import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'object/usable')
	return

def use(core, owner, target):
	core.scriptService.callScript("scripts/", "character_builder_terminal", "screenOne", core, owner);
	return