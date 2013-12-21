import sys

def setup(core, actor, target, command):

	if actor.getSkillMod('expertise_cooldown_line_fs_sweep'):
		command.setCooldown(command.getCooldown() + (actor.getSkillModBase('expertise_cooldown_line_fs_sweep')/10))

	if actor.getSkillMod('expertise_damage_line_fs_sweep'):
		command.setAddedDamage(command.getAddedDamage() + ((command.getAddedDamage() * actor.getSkillModBase('expertise_damage_line_fs_sweep'))/100))

	if actor.getSkillMod('expertise_action_line_fs_sweep'):
		command.setActionCost(command.getActionCost() - ((command.getActionCost() * actor.getSkillModBase('expertise_action_line_fs_sweep'))/100))		
	
	return
	
def run(core, actor, target, commandString):
	return
	