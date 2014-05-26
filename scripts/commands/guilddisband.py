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
	if actor.getObjectID() != guild.getLeader() and member.hasDisbandPermission() is False:
		actor.sendSystemMessage('@guild:guild_no_permission_operation', 0)
		return
	else:
		guildSvc.showDisbandConfirmWindow(actor, target, guild)
		return

	return
