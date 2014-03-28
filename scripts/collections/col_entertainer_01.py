from java.lang import System
import sys

def modify(core, actor, count):
	return

def complete(core, actor, collection):
	ghost = actor.getSlottedObject('ghost')
	
	actor.sendSystemMessage('@collection_n:col_entertainer_01_finished', 0)
	ghost.addAbility('bm_dancing_pet_entertainer')
	return