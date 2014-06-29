import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'deeds/petDeed')
	return

def use(core, actor, object):
	core.petService.generatePet(actor, object, 'pet_gurreck', 'object/intangible/pet/shared_gurreck_hue.iff')
	#core.petService.generatePet(actor, object, 'pet_gurreck', 'object/intangible/vehicle/shared_speederbike_swoop_pcd.iff')
	return
	