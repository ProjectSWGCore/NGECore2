import sys

def setup(core, actor, target, command):
	if actor.hasBuff('fs_buff_def_1_1') and actor.getSkillMod('expertise_stance_healing_line_fs_heal'):
		command.setAddedDamage(command.getAddedDamage() + ((command.getAddedDamage() * actor.getSkillMod('expertise_stance_healing_line_fs_heal').getBase())/100))

	return
def run(core, actor, target, commandString):
	return