import sys
import main.NGECore
import services.AdminService

def setup():
	return


def run(core, actor, target, commandString):
	if target:
		actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF Command Completed Successfully: Account name for target is ' + core.adminService.getAccountNameFromDB(target.getClient().getAccountId()) + '.', 0)
	
	
	else:
		actor.sendSystemMessage(' \\#FE2EF7 [GM] \\#FFFFFF getStationName: Invalid Syntax. Syntax is: /getStationName <Character First Name>', 0)
		return