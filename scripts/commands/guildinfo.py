import sys

def setup():
	return
	
def run(core, actor, target, commandString):

	if actor.getGuildId() == 0:
		return
	
	guildSvc = core.guildService
	
	guild = guildSvc.getGuildById(actor.getGuildId())
	
	if guild is None:
		return
	
	guildSvc.showGuildInfoWindow(actor, guild)
	return
