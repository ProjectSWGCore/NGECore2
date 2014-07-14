import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/petDeed')
	return

def use(core, actor, object):
	core.petService.generatePet(actor, object, 'pet_merek', 'object/intangible/pet/shared_merek_hue.iff')
	return
	