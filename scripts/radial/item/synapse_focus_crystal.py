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
		bl = object.getStringAttribute('bio_link')
		if not bl == owner.getCustomName():
			owner.sendSystemMessage('@base_player:not_linked_to_holder', 1)
			return
		core.buffService.addBuffToCreature(owner, 'forceCrystalForce', owner)
		
	return
