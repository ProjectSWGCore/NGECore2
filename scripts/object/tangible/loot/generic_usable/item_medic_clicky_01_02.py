import sys

def setup(core, object):
	object.setAttachment('radial_filename', 'object/usable')
	object.setStfFilename('static_item_n')
	object.setStfName('item_medic_clicky_01_02')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_medic_clicky_01_02')
	object.setStringAttribute('class_required', 'Medic')
	return
def use(core, actor, object):
	core.buffService.addBuffToCreature(actor, 'roadmapMedicClicky', actor)
	return
