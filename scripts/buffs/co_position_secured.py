import sys

def setup(core, actor, buff):
	return

def add(core, actor, buff):

	actor.setSpeedMultiplierMod(0)

	core.skillModService.addSkillMod(actor, 'precision_modified', 200)
	core.skillModService.addSkillMod(actor, 'strength_modified', 200)

	if actor.getSkillMod('expertise_action_line_co_imp_pos_sec'):
		core.skillModService.addSkillMod(actor, 'expertise_action_all', actor.getSkillMod('expertise_action_line_co_imp_pos_sec').getBase())
	#if actor.getSkillMod('expertise_co_pos_secured_line_burst_fire_proc'):
	if actor.getSkillMod('expertise_co_pos_secured_line_burst_fire_devastation_bonus'):
		core.skillModService.addSkillMod(actor, 'expertise_devastation_bonus', actor.getSkillMod('expertise_co_pos_secured_line_burst_fire_devastation_bonus').getBase() * 10)
	if actor.getSkillMod('expertise_co_pos_secured_line_critical'):
		core.skillModService.addSkillMod(actor, 'display_only_critical', actor.getSkillMod('expertise_co_pos_secured_line_critical').getBase() * 100)
	if actor.getSkillMod('expertise_co_pos_secured_line_protection'):
		core.skillModService.addSkillMod(actor, 'display_only_expertise_critical_hit_reduction', actor.getSkillMod('expertise_co_pos_secured_line_protection').getBase() * 100)
	
	if actor.hasSkill('expertise_co_base_of_operations_1'):
		core.buffService.addBuffToCreature(actor, 'co_base_of_operations')
	
	return
	
def remove(core, actor, buff):

	actor.setSpeedMultiplierMod(1)

	core.skillModService.deductSkillMod(actor, 'precision_modified', 200)
	core.skillModService.deductSkillMod(actor, 'strength_modified', 200)

	if actor.getSkillMod('expertise_action_line_co_imp_pos_sec'):
		core.skillModService.deductSkillMod(actor, 'expertise_action_all', actor.getSkillMod('expertise_action_line_co_imp_pos_sec').getBase())
	#if actor.getSkillMod('expertise_co_pos_secured_line_burst_fire_proc'):
	if actor.getSkillMod('expertise_co_pos_secured_line_burst_fire_devastation_bonus'):
		core.skillModService.deductSkillMod(actor, 'expertise_devastation_bonus', actor.getSkillMod('expertise_co_pos_secured_line_burst_fire_devastation_bonus').getBase() * 10)
	if actor.getSkillMod('expertise_co_pos_secured_line_critical'):
		core.skillModService.deductSkillMod(actor, 'display_only_critical', actor.getSkillMod('expertise_co_pos_secured_line_critical').getBase() * 100)
	if actor.getSkillMod('expertise_co_pos_secured_line_protection'):
		core.skillModService.deductSkillMod(actor, 'display_only_expertise_critical_hit_reduction', actor.getSkillMod('expertise_co_pos_secured_line_protection').getBase() * 100)

	if actor.hasSkill('expertise_co_base_of_operations_1'):
		if actor.getGroupId() != 0 and core.objectService.getObject(actor.getGroupId()):
			group = core.objectService.getObject(actor.getGroupId())
			for member in group.getMemberList():
				core.buffService.removeBuffFromCreature(member, member.getBuffByName('co_base_of_operations'))
		else:
			core.buffService.removeBuffFromCreature(actor, actor.getBuffByName('co_base_of_operations'))
		
	return