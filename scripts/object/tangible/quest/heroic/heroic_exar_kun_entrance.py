import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'exar_kun_brazier')
	return

def use(core, actor, object):
	if actor:
		core.instanceService.queue('heroic_exar_kun', actor)