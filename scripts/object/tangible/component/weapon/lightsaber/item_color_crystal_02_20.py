import sys
from resources.datatables import LightsaberColors

def setup(core, object):
	object.setCustomizationVariable('/private/index_color_1', LightsaberColors.getByName('Windu\'s Guile'))
	object.setStfFilename('static_item_n')
	object.setStfName('item_color_crystal_02_20')
	object.setDetailFilename('static_item_d')
	object.setDetailName('item_color_crystal_02_20')
	object.setIntAttribute('no_trade', 1)
	object.setStringAttribute('condition', '1000/1000')
	object.setStringAttribute("@obj_attr_n:color", "Windu's Guile")
	object.setAttachment('radial_filename', 'item/tunable')
	object.setStringAttribute('@obj_attr_n:crystal_owner", "\\#D1F56F UNTUNED \\#FFFFFF ')
	return
	
