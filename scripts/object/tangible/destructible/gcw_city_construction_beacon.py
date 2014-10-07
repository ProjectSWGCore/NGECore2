def setup(core, object):
	object.setAttachment('radial_filename', 'object/usable')
	return

def use(core, owner, target):
	core.invasionService.usePylon(owner, target);
	return