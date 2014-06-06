import sys
from resources.datatables import GcwRank

def handleRankUp(actor, newrank):
	if newrank > GcwRank.LIEUTENANT:
		actor.addAbility('pvp_retaliation_ability')

	if newrank > GcwRank.CAPTAIN:
		actor.addAbility('pvp_adrenaline_ability')

	if newrank > GcwRank.MAJOR:
		actor.addAbility('pvp_unstoppable_ability')

	if newrank > GcwRank.LTCOLONEL:
		actor.addAbility('pvp_last_man_ability')

	if newrank > GcwRank.COLONEL:
		actor.addAbility('pvp_aura_buff_self_ability')

	if newrank > GcwRank.GENERAL:
		actor.addAbility('pvp_airstrike_ability')

	return

def handleRankDown(actor, newrank):
	if newrank < GcwRank.LIEUTENANT:
		actor.removeAbility('pvp_retaliation_ability')

	if newrank < GcwRank.CAPTAIN:
		actor.removeAbility('pvp_adrenaline_ability')

	if newrank < GcwRank.MAJOR:
		actor.removeAbility('pvp_unstoppable_ability')

	if newrank < GcwRank.LTCOLONEL:
		actor.removeAbility('pvp_last_man_ability')

	if newrank < GcwRank.COLONEL:
		actor.removeAbility('pvp_aura_buff_self_ability')

	if newrank < GcwRank.GENERAL:
		actor.removeAbility('pvp_airstrike_ability')

	return