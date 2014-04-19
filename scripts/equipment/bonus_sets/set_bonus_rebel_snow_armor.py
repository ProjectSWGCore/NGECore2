import sys
from services.equipment import BonusSetTemplate
from java.util import Vector

def addBonusSet(core):
	bonusSet = BonusSetTemplate("set_bonus_rebel_snow_armor")
	
	bonusSet.addRequiredItem("armor_rebel_snow_backpack")
	bonusSet.addRequiredItem("armor_rebel_snow_belt")
	bonusSet.addRequiredItem("armor_rebel_snow_boots")
	bonusSet.addRequiredItem("armor_rebel_snow_chest_plate")
	bonusSet.addRequiredItem("armor_rebel_snow_gloves")
	bonusSet.addRequiredItem("armor_rebel_snow_helmet")
	bonusSet.addRequiredItem("armor_rebel_snow_bicep_l")
	bonusSet.addRequiredItem("armor_rebel_snow_bicep_r")
	bonusSet.addRequiredItem("armor_rebel_snow_bracer_l")
	bonusSet.addRequiredItem("armor_rebel_snow_bracer_r")
	bonusSet.addRequiredItem("armor_rebel_snow_leggings")
	
	core.equipmentService.addBonusSetTemplate(bonusSet)
	
def handleChange(core, creature, set):
	wornItems = set.getWornTemplateCount(creature)
	
	if wornItems == 3:
		core.buffService.addBuffToCreature(creature, "set_bonus_rebel_snow_armor_3", creature)
		creature.sendSystemMessage('@set_bonus:set_bonus_rebel_snow_armor_3_sys', 0)
	elif wornItems == 5:
		core.buffService.addBuffToCreature(creature, "set_bonus_rebel_snow_armor_5", creature)
		creature.sendSystemMessage('@set_bonus:set_bonus_imperial_snow_armor_5_sys', 0)
	elif wornItems == 7:
		core.buffService.addBuffToCreature(creature, "set_bonus_rebel_snow_armor_7", creature)
		creature.sendSystemMessage('@set_bonus:set_bonus_rebel_snow_armor_7_sys', 0)
	elif wornItems == 9:
		core.buffService.addBuffToCreature(creature, "set_bonus_rebel_snow_armor_9", creature)
		creature.sendSystemMessage('@set_bonus:set_bonus_rebel_snow_armor_9_sys', 0)
	elif wornItems == 11:
		core.buffService.addBuffToCreature(creature, "set_bonus_rebel_snow_armor_11", creature)
		creature.sendSystemMessage('@set_bonus:set_bonus_rebel_snow_armor_11_sys', 0)	
	else:
		core.buffService.removeBuffFromCreatureByName(creature, "set_bonus_rebel_snow_armor_3")
		core.buffService.removeBuffFromCreatureByName(creature, "set_bonus_rebel_snow_armor_5")
		core.buffService.removeBuffFromCreatureByName(creature, "set_bonus_rebel_snow_armor_7")
		core.buffService.removeBuffFromCreatureByName(creature, "set_bonus_rebel_snow_armor_9")
		core.buffService.removeBuffFromCreatureByName(creature, "set_bonus_rebel_snow_armor_11")