def setup(core, actor, target, command):
    core.buffService.addBuffToCreature(target, 'co_supressing_fire_0')
    if actor.getSkillMod('expertise_supression_speed', 10):
		core.buffService.addBuffToCreature(target, 'co_supressing_fire_1')
    if actor.getSkillMod('expertise_supression_speed', 20):
		core.buffService.addBuffToCreature(target, 'co_supressing_fire_2')
	if actor.getSkillMod('expertise_supression_speed', 30):
		core.buffService.addBuffToCreature(target, 'co_supressing_fire_3')
	if actor.getSkillMod('expertise_supression_speed', 40):
		core.buffService.addBuffToCreature(target, 'co_supressing_fire_3')
		return
	
def preRun(core, actor, target, command):
	return

def run(core, actor, target, commandString):
	return