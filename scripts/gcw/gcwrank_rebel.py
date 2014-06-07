import sys
from resources.datatables import GcwRank

def handleRankUp(core, actor, newrank):
	if newrank >= GcwRank.LIEUTENANT:
		actor.addAbility('pvp_retaliation_rebel_ability')
		core.collectionService.addCollection(actor, 'pvp_rebel_lieutenant')

	if newrank >= GcwRank.CAPTAIN:
		actor.addAbility('pvp_adrenaline_rebel_ability')
		core.collectionService.addCollection(actor, 'pvp_rebel_captain')

	if newrank >= GcwRank.MAJOR:
		actor.addAbility('pvp_unstoppable_rebel_ability')
		core.collectionService.addCollection(actor, 'pvp_rebel_major')

	if newrank >= GcwRank.COMMANDER:
		actor.addAbility('pvp_last_man_rebel_ability')
		core.collectionService.addCollection(actor, 'pvp_rebel_commander')

	if newrank >= GcwRank.COLONEL:
		actor.addAbility('pvp_aura_buff_self_rebel_ability')
		core.collectionService.addCollection(actor, 'pvp_rebel_colonel')

	if newrank >= GcwRank.GENERAL:
		actor.addAbility('pvp_airstrike_rebel_ability')
		core.collectionService.addCollection(actor, 'pvp_rebel_general')

	return

def handleRankDown(actor, newrank):
	if newrank < GcwRank.LIEUTENANT:
		actor.removeAbility('pvp_retaliation_rebel_ability')

	if newrank < GcwRank.CAPTAIN:
		actor.removeAbility('pvp_adrenaline_rebel_ability')

	if newrank < GcwRank.MAJOR:
		actor.removeAbility('pvp_unstoppable_rebel_ability')

	if newrank < GcwRank.COMMANDER:
		actor.removeAbility('pvp_last_man_rebel_ability')

	if newrank < GcwRank.COLONEL:
		actor.removeAbility('pvp_aura_buff_self_rebel_ability')

	if newrank < GcwRank.GENERAL:
		actor.removeAbility('pvp_airstrike_rebel_ability')

	return