def setup(core, actor, target, command):
	core.buffService.addBuffToCreature(target, 'co_supressing_fire_0')
	if actor.getSkillMod('expertise_supression_speed'):
		core.buffService.addBuffToCreature(target, 'co_supressing_fire_1')
	if actor.getSkillMod('expertise_supression_speed'):
		core.buffService.addBuffToCreature(target, 'co_supressing_fire_2')
	if actor.getSkillMod('expertise_supression_speed'):
		core.buffService.addBuffToCreature(target, 'co_supressing_fire_3')
	if actor.getSkillMod('expertise_supression_speed'):
		core.buffService.addBuffToCreature(target, 'co_supressing_fire_4')
	return
        
def preRun(core, actor, target, command):
	return

def run(core, actor, target, commandString):
	return