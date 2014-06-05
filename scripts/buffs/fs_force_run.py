import sys

def setup(core, actor, buff):
	buff.setDuration(buff.getDuration()+(actor.getSkillModBase('expertise_buff_duration_line_fs_force_run')))
	return

def add(core, actor, buff):
	actor.playEffectObject('clienteffect/pl_force_run.cef', '')
	actor.setSpeedMultiplierBase(actor.getSpeedMultiplierBase() + 2.5 + (2.5 * (actor.getSkillModBase('expertise_movement_buff_fs_force_run')) / 100))
	core.skillModService.addSkillMod(actor, 'slope_move', 5)
	core.skillModService.addSkillMod(actor, 'movement_resist_snare', 100)
	core.skillModService.addSkillMod(actor, 'movement_resist_root', 100)
	
	##For Testing Purposes
	##actor.sendSystemMessage('You are running at ' + str(actor.getSpeedMultiplierBase()) + ' times the running speed.', 0)
	
	return
	
def remove(core, actor, buff):
	actor.stopEffectObject('fs_force_run')
	actor.setSpeedMultiplierBase(actor.getSpeedMultiplierBase() - 2.5 - (2.5 * (actor.getSkillModBase('expertise_movement_buff_fs_force_run')) / 100))
	core.skillModService.deductSkillMod(actor, 'slope_move', 5)
	core.skillModService.deductSkillMod(actor, 'movement_resist_snare', 100)
	core.skillModService.deductSkillMod(actor, 'movement_resist_root', 100)
	return
