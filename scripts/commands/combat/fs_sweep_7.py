import sys

def setup(core, actor, target, command):
	if actor.getSkillMod('expertise_damage_line_fs_sweep'):
		command.setAddedDamage(command.getAddedDamage() + ((command.getAddedDamage() * actor.getSkillMod('expertise_damage_line_fs_sweep').getBase())/100))

	if actor.getSkillMod('expertise_action_line_fs_sweep'):
		command.setActionCost(command.getActionCost() - ((command.getActionCost() * actor.getSkillMod('expertise_action_line_fs_sweep').getBase())/100))		
	
	return
	
def run(core, actor, target, commandString):
	return
	