import sys

def setup(core, actor, buff):

	return

def add(core, actor, buff):
	actor.setGCWFatigue(actor.getGCWFatigue()+1)
	return
	
def remove(core, actor, buff):
	if actor.getGCWFatigue()>0:
		actor.setGCWFatigue(actor.getGCWFatigue()-1)
	# clear defbuff
	
	return
	