import sys

def setup(core, actor, buff):
	buff.setDuration(buff.getDuration()+(actor.getSkillModBase('expertise_buff_duration_line_fs_force_run')))
	return

def add(core, actor, buff):
	actor.playEffectObject('clienteffect/pl_force_run.cef', '')

	
	##For Testing Purposes
	##actor.sendSystemMessage('You are running at ' + str(actor.getSpeedMultiplierBase()) + ' times the running speed.', 0)
	
	return
	
def remove(core, actor, buff):
	actor.stopEffectObject('fs_force_run')
	return
