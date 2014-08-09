import sys

def setup(core, object):
	object.setStringAttribute('bio_link', '@obj_attr_n:bio_link_pending')
	object.setStringAttribute('required_level', '22')
	object.setStringAttribute('@set_bonus:piece_bonus_count_3', '@set_bonus:set_bonus_imperial_snow_armor_3')
	object.setStringAttribute('@set_bonus:piece_bonus_count_5', '@set_bonus:set_bonus_imperial_snow_armor_5')
	object.setStringAttribute('@set_bonus:piece_bonus_count_7', '@set_bonus:set_bonus_imperial_snow_armor_7')
	object.setStringAttribute('@set_bonus:piece_bonus_count_9', '@set_bonus:set_bonus_imperial_snow_armor_9')
	object.setStringAttribute('@set_bonus:piece_bonus_count_11', '@set_bonus:set_bonus_imperial_snow_armor_11')
	object.setAttachment('setBonus', 'set_bonus_imperial_snow_armor')
	object.setStringAttribute('faction_restriction', 'Imperial')
	object.skillModService.addSkillMod(actor, 'agility_modified', 25)
	object.skillModService.addSkillMod(actor, 'constitution_modified', 30)
	object.skillModService.addSkillMod(actor, 'luck_modified', 25)
	object.skillModService.addSkillMod(actor, 'precision_modified', 35)
	object.skillModService.addSkillMod(actor, 'stamina_modified', 30)
	object.skillModService.addSkillMod(actor, 'strength_modified', 35)
	return	