import sys

def setup(core, actor, target, command):
	if actor.getSkillMod('expertise_damage_line_fs_dm'):
		command.setAddedDamage(command.getAddedDamage() + ((command.getAddedDamage() * actor.getSkillMod('expertise_damage_line_fs_dm').getBase())/100))

	if actor.getSkillMod('expertise_critical_line_fs_dm'):
		command.setCriticalChance(command.getCriticalChance() + actor.getSkillMod('expertise_critical_line_fs_dm').getBase())

	return
	
def run(core, actor, target, commandString):
	return