import sys

def setup():
	return
	
def run():
	return
	
def addMasterBadge(core, actor):
	if actor.getLevel() == 90:
		core.collectionService.addCollection(actor, "new_prof_bountyhunter_master")
	return