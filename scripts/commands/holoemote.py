import sys

def setup():
    return

def run(core, actor, target, commandString):
	player = actor.getSlottedObject('ghost')
	
	if player is None:
		return
	
	if commandString is not None:
		commandString = commandString.lower()
	
	installedEmote = ''
	
	if actor.hasCooldown('holoEmote'):
		actor.sendSystemMessage('Your Holo-Emote generator is in use or recharging.', 0) # Not sure if this is correct.
		return
	
	if player.getHoloEmote() is not None:
		installedEmote = player.getHoloEmote().replace('holoemote_','')
	
	if commandString is None or commandString == "":
		commandString = installedEmote

	if installedEmote == None or installedEmote == "":
		actor.sendSystemMessage('@image_designer:no_holoemote', 0)
		return
	
	if commandString == 'help':
		helpWindow = core.suiService.createMessageBox(1, 'HOLO-EMOTE HELP', holoPrompt(player, installedEmote), actor, actor, 10)
		core.suiService.openSUIWindow(helpWindow)
		return
	
	if player.getHoloEmoteUses() < 1:
		actor.sendSystemMessage('@image_designer:no_charges_holoemote', 0)
		return
	
	if commandString == "kitty":
		commandString = "technokitty"
	
	if commandString != installedEmote and installedEmote != 'all':
		actor.sendSystemMessage('Your installed Holo-Emote generator does not support that emote.', 0)
		return
	
	effectObj = 'clienteffect/holoemote_' + commandString + '.cef'
	actor.playEffectObject(effectObj, 'head')
	player.setHoloEmoteUses(player.getHoloEmoteUses() - 1)
	
	actor.addCooldown('holoEmote', long(30 * 1000))
	
	return

def holoPrompt(player, emote):
	emotesToPlay = "Your Holo-Emote generator can only play the " + emote + " Holo-Emote.\n"
	if emote == "all":
		emotesToPlay = "Your Holo-Emote generator can play all Holo-Emotes available.\n"
	
	remainingCharges = "You have " + str(player.getHoloEmoteUses()) + " charges remaining.\n"
	if player.getHoloEmoteUses() == 1:
		remainingCharges = "You have 1 charge remaining.\n"
	
	body = "To play a Holo-Emote, type /holoemote <name>.\n" \
"To delete your Holo-Emote type /holoemote delete.\n" \
"Purchasing a new Holo-Emote will automatically delete your current Holo-Emote.\n" \
"\n" \
"The available Holo-Emote names are:\n\n" \
"Beehive\t Blossom\tBrainstorm\n" \
"Bubblehead\t Bullhorns\tButterflies\n" \
"Champagne\t Haunted\tHearts\n" \
"Hologlitter\t Holonotes\tImperial\n" \
"Kitty\t\t Phonytail\tRebel\n" \
"Sparky"
	
	return emotesToPlay + remainingCharges + body