import sys

def setup(core, actor, target, command):
	if actor.getSkillMod('expertise_area_size_line_co_grenade'):
		command.setConeLength(command.getConeLength() + actor.getSkillMod('expertise_area_size_line_co_grenade').getBase())
	if actor.getSkillMod('expertise_damage_line_co_grenade'):
		command.setAddedDamage(command.getAddedDamage() * (1 + (actor.getSkillMod('expertise_damage_line_co_grenade').getBase() / 100)))
	if actor.getSkillMod('expertise_action_line_co_grenade'):
		command.setActionCost(command.getActionCost() * (1 - (actor.getSkillMod('expertise_action_line_co_grenade').getBase() / 100)))
	if actor.getSkillMod('expertise_delay_reduce_line_co_grenade'):
		command.setDelayAttackInterval(command.getDelayAttackInterval() - 1)
	return
	
def run(core, actor, target, commandString):
	return