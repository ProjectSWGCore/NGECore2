from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 21, 1, ''))
	radials.add(RadialOptions(0, 7, 1, ''))
	radials.add(RadialOptions(0, 15, 1, ''))
	return
	
def handleSelection(core, owner, target, option):
	if option == 21 and target:
		handleBanner(core, owner, target, option)
	if option == 15 and target:
		core.objectService.destroyObject(target)
	return
	
def handleBanner(core, owner, bannerinventoryobject, option):

	# check if faction alignment matches
	if len(owner.getFaction()) == 0:
		owner.sendSystemMessage('You are not aligned appropriately to use this banner' , 0)
		return
	if bannerinventoryobject.getTemplate().find(owner.getFaction()) == -1:
		owner.sendSystemMessage('You are not aligned appropriately to use this banner' , 0)
		return
	
	# check if banner object is not already spawned
	if bannerinventoryobject.getAttachment('BannerSpawned'):
		owner.sendSystemMessage('Banner already spawned' , 0)
		return
	
	# spawn banner object
	stcSvc = core.staticService
	banner = stcSvc.spawnObject(bannerinventoryobject.getTemplate(), owner.getPlanet().getName(), long(0), owner.getWorldPosition().x + 1, owner.getWorldPosition().y, owner.getWorldPosition().z, owner.getOrientation().w, owner.getOrientation().x, owner.getOrientation().y, owner.getOrientation().z) 
	if len(bannerinventoryobject.getStfFilename())>0:
		banner.setStfFilename(bannerinventoryobject.getStfFilename())
	if len(bannerinventoryobject.getStfName())>0:
		banner.setStfName(bannerinventoryobject.getStfName())
	if len(bannerinventoryobject.getDetailFilename())>0:
		banner.setDetailFilename(bannerinventoryobject.getDetailFilename())
	if len(bannerinventoryobject.getDetailName())>0:
		#banner.setDetailDescription(bannerinventoryobject.getBaseline(6).get("detailedDescription").getString())
		banner.setDetailName(bannerinventoryobject.getDetailName())
	bannerinventoryobject.setAttachment('BannerSpawned', True)
	
	# start buffing process
	core.buffService.initiateBannerBuffProcess(owner, banner)

	return	