import resources.objects.creature.CreatureObject;
from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 21, 1, ''))
	radials.add(RadialOptions(0, 7, 1, ''))
	radials.add(RadialOptions(0, 15, 1, ''))
	return

def handleSelection(core, owner, object, option):
	if option == 21:
		core.buffService.addBuffToCreature(owner, 'drink_breath_of_heaven', owner)

		core.objectService.destroyObject(object)
	return
