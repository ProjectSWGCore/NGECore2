import sys

def setup():
    return
    
def run(core, actor, target, commandString):
    guild = core.guildService.getGuildById(actor.getGuildId())
    
    if guild is None:
        return
    
    core.chatService.sendChatRoomMessage(actor, guild.getChatRoomId(), 0, commandString)
    return