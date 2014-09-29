import sys
from resources.datatables import Posture
from tools import DevLogQueuer

def setup():
	return
	
def run(core, actor, target, commandString):
	object = actor
	print(object.getObjectID());
	core.buffService.addBuffToCreature(actor, 'set_bonus_officer_utility_b_3', actor);
	#core.getSWGObjectODB().put(object.getObjectID(), object);

	return
	