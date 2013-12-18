import sys

def setup(core, actor, target, command):

	if actor.getSkillMod('expertise_critical_line_fs_dm'):
		command.setCriticalChance(command.getCriticalChance() + (actor.getSkillModBase('expertise_critical_line_fs_dm')))
		
	if actor.getSkillMod('expertise_damage_line_fs_dm'):
		command.setAddedDamage(command.getAddedDamage() + ((command.getAddedDamage()*actor.getSkillModBase('expertise_damage_line_fs_dm'))/100))
	return
	
def run(core, actor, target, commandString):
	return