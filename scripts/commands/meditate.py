import sys

def setup():
    return

def run(core, actor, target, commandString):
	##To do make it reset moodanimations when meditation is cancled.

   # if core.collectionService.isComplete(actor, 'inv_holocron_collection_02') is False:
   #    return
	
    if actor.getPosture() != 0x08:
		actor.sendSystemMessage('You need to sit',0)
		actor.setMoodAnimation('neutral')
		return
    if actor.getPosture() == 0x08:
    	actor.setMoodAnimation('meditating')
    	return
    if actor.getPosture() == 0x00:
    	actor.setMoodAnimation('neutral')
    	return
    return

