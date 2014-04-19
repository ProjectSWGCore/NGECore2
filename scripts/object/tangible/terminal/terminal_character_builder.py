import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'object/usable')
	return

def use(core, owner, target):
	core.devService.sendCharacterBuilderSUI(owner, 0);
	return