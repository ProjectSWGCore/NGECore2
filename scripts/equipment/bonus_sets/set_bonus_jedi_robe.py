import sys
from services.equipment import BonusSetTemplate
from java.util import Vector

def addBonusSet(core):
	bonusSet = BonusSetTemplate("set_bonus_jedi_robe")
	
	# Waistpack
	bonusSet.addRequiredTemplate("object/tangible/wearables/backpack/shared_fannypack_s01.iff")
	
	# Jedi Robes - Light
	bonusSet.addRequiredTemplate("object/tangible/wearables/robe/shared_robe_jedi_light_s01.iff")
	bonusSet.addRequiredTemplate("object/tangible/wearables/robe/shared_robe_jedi_light_s02.iff")
	bonusSet.addRequiredTemplate("object/tangible/wearables/robe/shared_robe_jedi_light_s03.iff")
	bonusSet.addRequiredTemplate("object/tangible/wearables/robe/shared_robe_jedi_light_s04.iff")
	bonusSet.addRequiredTemplate("object/tangible/wearables/robe/shared_robe_jedi_light_s05.iff")
	
	# Jedi Robes - Dark
	bonusSet.addRequiredTemplate("object/tangible/wearables/robe/shared_robe_jedi_dark_s01.iff")
	bonusSet.addRequiredTemplate("object/tangible/wearables/robe/shared_robe_jedi_dark_s02.iff")
	bonusSet.addRequiredTemplate("object/tangible/wearables/robe/shared_robe_jedi_dark_s03.iff")
	bonusSet.addRequiredTemplate("object/tangible/wearables/robe/shared_robe_jedi_dark_s04.iff")
	bonusSet.addRequiredTemplate("object/tangible/wearables/robe/shared_robe_jedi_dark_s05.iff")
	
	# Jedi Cloaks
	bonusSet.addRequiredTemplate("object/tangible/wearables/robe/shared_robe_s32.iff")
	bonusSet.addRequiredTemplate("object/tangible/wearables/robe/shared_robe_s32_h1.iff")
	bonusSet.addRequiredTemplate("object/tangible/wearables/robe/shared_robe_s33.iff")
	bonusSet.addRequiredTemplate("object/tangible/wearables/robe/shared_robe_s33_h1.iff")
	
	core.equipmentService.addBonusSetTemplate(bonusSet)
	
def handleChange(core, creature, set):
	wornItems = set.getWornTemplateCount(creature)
	
	if wornItems == 2:
		core.buffService.addBuffToCreature(creature, "set_bonus_jedi_robe_1", creature)
	else:
		core.buffService.removeBuffFromCreatureByName(creature, "set_bonus_jedi_robe_1")