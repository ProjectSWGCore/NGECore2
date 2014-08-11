import sys
from java.lang import Short

def setup(core, actor, buff):

	return

def add(core, actor, buff):
	
	#fatigue = actor.getGCWFatigue() + 1
	#actor.setGCWFatigue(fatigue)
	return
	
def remove(core, actor, buff):
	#if actor.getGCWFatigue()>0:
		#fatigue = actor.getGCWFatigue() - 1
		#actor.setGCWFatigue(fatigue)
	# Major flaw in Buff system. It does not consider infinite, stackable buffs
	# The GCW buff gets automatically removed upon each new stack
	# resulting in +1-1 Fatigue, if the fatigue gets set here.
	# Only way is to set it, is in the java methods
	# and then invent a way so that entertainer can heal this
	
	return
	