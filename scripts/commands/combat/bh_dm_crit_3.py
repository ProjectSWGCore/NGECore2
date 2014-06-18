import sys

def setup(core, actor, target, command):
	if actor.getSkillMod('expertise_cooldown_line_dm_crit'):
		command.setCooldown(command.getCooldown() - (actor.getSkillModBase('expertise_cooldown_line_dm_crit')/10))
		
	if actor.getSkillMod('expertise_damage_line_dm_crit'):
		command.setAddedDamage(command.getAddedDamage() + ((command.getAddedDamage()*actor.getSkillModBase('expertise_damage_line_dm_crit'))/100))
	
	if actor.getSkillMod('expertise_action_line_dm_crit'):
		command.setActionCost(command.getActionCost() - ((command.getActionCost()*actor.getSkillModBase('expertise_action_line_dm_crit'))/100))			
	
	if actor.getSkillMod('expertise_critical_line_dm_crit'):
		command.setCriticalChance(command.getCriticalChance() + actor.getSkillModBase('expertise_critical_line_dm_crit'))
		
	return
	
def run(core, actor, target, commandString):
	return