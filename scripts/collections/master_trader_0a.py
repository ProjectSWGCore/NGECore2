import sys

def setup():
	return
	
def run():
	return
	
def addMasterBadge(core, actor):
	if actor.getLevel() == 90:
		core.collectionService.addCollection(actor, "new_prof_crafting_merchant_master")
		core.collectionService.addCollection(actor, "new_prof_crafting_artisan_master")
		core.collectionService.addCollection(actor, "new_prof_crafting_chef_master")
		core.collectionService.addCollection(actor, "new_prof_crafting_tailor_master")
	return