# This file will be updated at a later date with a new command.

import sys
from resources.common import ProsePackage
from resources.common import OutOfBand
from protocol.swg import CreateClientPathMessage
def setup():
	return
	
def run(core, actor, target, commandString):
	core.questService.giveBasicQuest(actor)
	return