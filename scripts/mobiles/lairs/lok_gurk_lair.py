import sys
from java.util import Vector

def addTemplate(core):
	lairCRCs = Vector()
	lairCRCs.add('object/tangible/terminal/shared_terminal_character_builder.iff')
	core.spawnService.addLairTemplate('lok_gurk_lair', 'lok_gurk', 15, lairCRCs,'reclusive_gurk_king')
	return