from java.lang import System
import sys

def modify(core, actor, count):
	return

def complete(core, actor, collection):
	ghost = actor.getSlottedObject('ghost')
	actor.sendSystemMessage('@collection_n:inv_holocron_collection_01_finished', 0)
	print ('Collection Finished Test')
	bb = core.objectService.createObject('object/tangible/wearables/backpack/shared_fannypack_s01.iff', object.getPlanet())
	core.playerService.giveItem(actor, bb)
	print ('test2')
	
	return