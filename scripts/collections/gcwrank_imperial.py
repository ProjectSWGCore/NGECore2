import sys
from resources.datatables import GcwRank

def handleRankUp(core, actor, newrank):
	if newrank >= GcwRank.LIEUTENANT:
		actor.addAbility('command_pvp_retaliation_ability')
		core.collectionService.addCollection(actor, 'pvp_imperial_lieutenant')

	if newrank >= GcwRank.CAPTAIN:
		actor.addAbility('command_pvp_adrenaline_ability')
		core.collectionService.addCollection(actor, 'pvp_imperial_captain')

	if newrank >= GcwRank.MAJOR:
		actor.addAbility('command_pvp_unstoppable_ability')
		core.collectionService.addCollection(actor, 'pvp_imperial_major')

	if newrank >= GcwRank.LTCOLONEL:
		actor.addAbility('command_pvp_last_man_ability')
		core.collectionService.addCollection(actor, 'pvp_imperial_lt_colonel')

	if newrank >= GcwRank.COLONEL:
		actor.addAbility('pvp_aura_buff_imperial_self')
		core.collectionService.addCollection(actor, 'pvp_imperial_colonel')

	if newrank >= GcwRank.GENERAL:
		actor.addAbility('pvp_airstrike_ability')
		core.collectionService.addCollection(actor, 'pvp_imperial_general')

	return

def handleRankDown(actor, newrank):
	if newrank < GcwRank.LIEUTENANT:
		actor.removeAbility('command_pvp_retaliation_ability')

	if newrank < GcwRank.CAPTAIN:
		actor.removeAbility('command_pvp_adrenaline_ability')

	if newrank < GcwRank.MAJOR:
		actor.removeAbility('command_pvp_unstoppable_ability')

	if newrank < GcwRank.LTCOLONEL:
		actor.removeAbility('command_pvp_last_man_ability')

	if newrank < GcwRank.COLONEL:
		actor.removeAbility('pvp_aura_buff_imperial_self')

	if newrank < GcwRank.GENERAL:
		actor.removeAbility('pvp_airstrike_ability')

	return