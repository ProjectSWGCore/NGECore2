import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/petDeed')
	return

def use(core, actor, object):
	core.petService.generatePet(actor, object, 'pet_droideka', 'object/intangible/pet/shared_droideka.iff')

	return
	