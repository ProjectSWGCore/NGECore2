import sys
from services.equipment import BonusSetTemplate
from java.util import Vector

def addBonusSet(core):
	bonusSet = BonusSetTemplate("set_bonus_jedi_robe")
	bonusSet.addRequiredTemplate("object/tangible/wearables/necklace/shared_necklace_primitive_03.iff")
	
	core.equipmentService.addBonusSetTemplate(bonusSet)
	
def handleEquip(core, creature, set):
	wornItems = set.getWornTemplateCount(creature)
	
	if wornItems == 1:
		print('Success!')