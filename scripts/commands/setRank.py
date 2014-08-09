import sys
from resources.datatables import GcwRank
from services.gcw import GCWService

def run(core, actor, target, commandString):
	commandArgs = commandString.split(' ')
	arg1 = commandArgs[0]
	if len(commandArgs) > 1:
		arg2 = commandArgs[1]
		
	if target is None:
		target = actor
	
	if target.getFaction() == 'rebel' or 'imperial' and arg1 == 'up':
			playerObject = target.getSlottedObject('ghost')
			playerObject.setCurrentRank(int(arg2))
			newrank = playerObject.getCurrentRank()
			core.scriptService.callScript("scripts/collections/", "gcwrank_" + target.getFaction(), "handleRankUp", core, target, newrank)
			actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF setRank: Command completed successfully.', 0)
	if target.getFaction() == 'rebel' or 'imperial' and arg1 == 'down':
			playerObject = target.getSlottedObject('ghost')	
			playerObject.setCurrentRank(int(arg2))
			newrank = playerObject.getCurrentRank()
			core.scriptService.callScript("scripts/collections/", "gcwrank_" + target.getFaction(), "handleRankDown", target, newrank)
			actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF setRank: Command completed successfully.', 0)
			
#	if target.getFaction() == 'imperial' and arg1 == 'up':
#		if arg1 == 'up':
#			playerObject = actor.getSlottedObject('ghost')
#			playerObject.setCurrentRank(int(arg2))
#			newrank = playerObject.getCurrentRank()
#			core.scriptService.callScript("scripts/collections/", "gcwrank_" + target.getFaction(), "handleRankUp", core, target, newrank)
#			actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF setRank: Command completed successfully.', 0)
#	if target.getFaction() == 'imperial' and arg1 == 'down':
#			playerObject = actor.getSlottedObject('ghost')	
#			playerObject.setCurrentRank(int(arg2))
#			newrank = playerObject.getCurrentRank()
#			core.scriptService.callScript("scripts/collections/", "gcwrank_" + target.getFaction(), "handleRankDown", target, newrank)
#			actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF setRank: Command completed successfully.', 0)
	
	return