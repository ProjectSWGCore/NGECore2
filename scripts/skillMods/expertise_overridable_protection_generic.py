import sys

def add(core, actor, name, base):
	actor.addSkillMod(name, base)
	actor.addSkillMod('kinetic', actor.getSkillModBase('expertise_overridable_protection_generic') - (actor.getSkillModBase('kinetic')/2)
	actor.addSkillMod('energy', actor.getSkillModBase('expertise_overridable_protection_generic') - (actor.getSkillModBase('energy')/2)
	actor.addSkillMod('heat', actor.getSkillModBase('expertise_overridable_protection_generic') - (actor.getSkillModBase('heat')/2)
	actor.addSkillMod('cold', actor.getSkillModBase('expertise_overridable_protection_generic') - (actor.getSkillModBase('cold')/2)
	actor.addSkillMod('acid', actor.getSkillModBase('expertise_overridable_protection_generic') - (actor.getSkillModBase('acid')/2)
	actor.addSkillMod('electricity', actor.getSkillModBase('expertise_overridable_protection_generic') - (actor.getSkillModBase('electricity')/2)
	return
	
def deduct(core, actor, name, base):
	actor.deductSkillMod(name, base)
	actor.deductSkillMod('kinetic', actor.getSkillModBase('expertise_overridable_protection_generic') - (actor.getSkillModBase('kinetic')/2)
	actor.deductSkillMod('energy', actor.getSkillModBase('expertise_overridable_protection_generic') - (actor.getSkillModBase('energy')/2)
	actor.deductSkillMod('heat', actor.getSkillModBase('expertise_overridable_protection_generic') - (actor.getSkillModBase('heat')/2)
	actor.deductSkillMod('cold', actor.getSkillModBase('expertise_overridable_protection_generic') - (actor.getSkillModBase('cold')/2)
	actor.deductSkillMod('acid', actor.getSkillModBase('expertise_overridable_protection_generic') - (actor.getSkillModBase('acid')/2)
	actor.deductSkillMod('electricity', actor.getSkillModBase('expertise_overridable_protection_generic') - (actor.getSkillModBase('electricity')/2)
	return
	