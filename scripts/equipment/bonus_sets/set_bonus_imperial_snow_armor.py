import sys
from services.equipment import BonusSetTemplate
from java.util import Vector

def addBonusSet(core):
	bonusSet = BonusSetTemplate("set_bonus_imperial_snow_armor")
	
	bonusSet.addRequiredItem("backpack_snowtrooper")
	bonusSet.addRequiredItem("armor_snowtrooper_belt")
	bonusSet.addRequiredItem("armor_snowtrooper_bicep_l")
	bonusSet.addRequiredItem("armor_snowtrooper_bicep_r")
	bonusSet.addRequiredItem("armor_snowtrooper_boots")
	bonusSet.addRequiredItem("armor_snowtrooper_bracer_l")
	bonusSet.addRequiredItem("armor_snowtrooper_bracer_r")
	bonusSet.addRequiredItem("armor_snowtrooper_chest_plate")
	bonusSet.addRequiredItem("armor_snowtrooper_gloves")
	bonusSet.addRequiredItem("armor_snowtrooper_helmet")
	bonusSet.addRequiredItem("armor_snowtrooper_leggings")
	
	core.equipmentService.addBonusSetTemplate(bonusSet)
	
def handleChange(core, creature, set):
	wornItems = set.getWornTemplateCount(creature)
	
	if wornItems == 3:
		core.buffService.addBuffToCreature(creature, "set_bonus_imperial_snow_armor_3", creature)
		creature.sendSystemMessage('@set_bonus:set_bonus_imperial_snow_armor_3_sys', 0)
	elif wornItems == 5:
		core.buffService.addBuffToCreature(creature, "set_bonus_imperial_snow_armor_5", creature)
		creature.sendSystemMessage('@set_bonus:set_bonus_imperial_snow_armor_5_sys', 0)
	elif wornItems == 7:
		core.buffService.addBuffToCreature(creature, "set_bonus_imperial_snow_armor_7", creature)
		creature.sendSystemMessage('@set_bonus:set_bonus_imperial_snow_armor_7_sys', 0)
	elif wornItems == 9:
		core.buffService.addBuffToCreature(creature, "set_bonus_imperial_snow_armor_9", creature)
		creature.sendSystemMessage('@set_bonus:set_bonus_imperial_snow_armor_9_sys', 0)
	elif wornItems == 11:
		core.buffService.addBuffToCreature(creature, "set_bonus_imperial_snow_armor_11", creature)
		creature.sendSystemMessage('@set_bonus:set_bonus_imperial_snow_armor_11_sys', 0)	
	else:
		core.buffService.removeBuffFromCreatureByName(creature, "set_bonus_imperial_snow_armor_3")
		core.buffService.removeBuffFromCreatureByName(creature, "set_bonus_imperial_snow_armor_5")
		core.buffService.removeBuffFromCreatureByName(creature, "set_bonus_imperial_snow_armor_7")
		core.buffService.removeBuffFromCreatureByName(creature, "set_bonus_imperial_snow_armor_9")
		core.buffService.removeBuffFromCreatureByName(creature, "set_bonus_imperial_snow_armor_11")