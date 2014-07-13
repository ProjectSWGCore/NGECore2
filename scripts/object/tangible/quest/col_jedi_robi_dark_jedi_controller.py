import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'object/usable')
	return

def use(core, actor, object):

	pos = actor.getWorldPosition()
	core.spawnService.spawnCreature('jedi_robe_dark_jedi', actor.getPlanet().getName(), 0, pos.x, pos.y, pos.z, 1, 0, 1, 0, 93)
	
	return