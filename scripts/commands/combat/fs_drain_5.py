import sys

def setup(core, actor, target, command):

	if actor.getSkillMod('expertise_fs_dm_armor_bypass'):
		command.setBypassArmor(actor.getSkillModBase("expertise_fs_dm_armor_bypass"))
		
	return
	
def run(core, actor, target, damage):

	if actor.getSkillMod('expertise_fs_imp_drain'):
		damage *= (100 + float(actor.getSkillModBase('expertise_fs_imp_drain')))/100
		
	core.combatService.doDrainHeal(actor, int(damage))
	return