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
	
	member = guild.getMember(actor.getObjectID())
	
	if member is None:
		return
	
	if not member.hasSponsorPermission():
		actor.sendSystemMessage('@guild:guild_no_permission_operation', 0)
		return
	
	guildSvc.handleGuildSponsorWindow(actor)
	return
