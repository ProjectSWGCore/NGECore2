import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	player = actor.getSlottedObject('ghost')

	maxHolos = 1
	
	if actor.getSkillMod('expertise_en_holographic_additional_backup') is not None:
		maxHolos += actor.getSkillMod('expertise_en_holographic_additional_backup').getBase()
	
	holo = core.objectService.createObject(actor.getTemplate(), 0, actor.getPlanet(), actor.getPosition(), actor.getOrientation(), None, False)
	holo.setHologram(True)
	holo.setCustomName('a Hologram') # a Hologram
	holo.setHeight(actor.getHeight())
	holo.setCustomization(actor.getCustomization())
	
	core.simulationService.add(holo, holo.getPosition().x, holo.getPosition().z, True)
	return