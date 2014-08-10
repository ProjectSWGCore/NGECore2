def setup(core, object):
	object.setAttachment('radial_filename', 'object/usable')
	return

def use(core, owner, target):
	core.invasionService.useTower(owner, target);
	return