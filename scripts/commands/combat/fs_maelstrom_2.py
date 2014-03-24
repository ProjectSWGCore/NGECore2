import sys

def setup(core, actor, target, command):

	if actor.getSkillMod('expertise_fs_dm_armor_bypass'):
		command.setBypassArmor(actor.getSkillModBase("expertise_fs_dm_armor_bypass"))
			
	return
	
def run(core, actor, target, commandString):
	return