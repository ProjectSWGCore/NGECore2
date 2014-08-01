def setup(core, object):
	object.setAttachment('radial_filename', 'object/usable')
	return

def use(core, owner, target):
	core.invasionService.useTurret(owner, target);
	return