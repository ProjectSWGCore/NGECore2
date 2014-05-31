import sys

def setup():
	return
	
def run(core, actor, target, commandString):

	if commandString != " " and commandString != "":
		target = core.objectService.getObjectByFirstName(commandString.split(" ")[0])
	
	if target is None:
		actor.sendSystemMessage('Could not find member ' + commandString + ' (CASE SENSITIVE!)', 0)
		return
	
	if actor.getGuildId() == 0:
		return

	if actor.getGuildId() != target.getGuildId():
		actor.sendSystemMessage('@guild:guild_no_permission_operation', 0)
		return
	
	guildSvc = core.guildService
	
	guild = guildSvc.getGuildById(actor.getGuildId())
	
	if guild is None:
		return
	
	if actor.getObjectID() == target.getObjectID():
		guildSvc.leaveGuild(actor, target, guild)
		return
	else:
		guildSvc.showKickConfirmWindow(actor, target, guild)
		return

	return
