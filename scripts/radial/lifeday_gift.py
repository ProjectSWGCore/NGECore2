from resources.common import RadialOptions
import sys
import java.util.Random

def createRadial(core, owner, target, radials):
	radials.clear()
	radials.add(RadialOptions(0, 21, 1, ''))
	return

def handleSelection(core, actor, object, option):
	if option == 21:
		if object.getAttachment('ownerId') and actor.getObjectID() == object.getAttachment('ownerId'):
			return
	
		random = java.util.Random()
		giftNum = random.nextInt(12)
		
		if giftNum == 0:
			objectName = 'rewards_09/shared_lifeday_decor_cookie'
		elif giftNum == 1:
			objectName = 'rewards_09/shared_lifeday_decor_fruitcake'
		elif giftNum == 2:
			objectName = 'rewards_09/shared_lifeday_decor_hoth_chocolate'
		elif giftNum == 3:
			objectName = 'rewards_09/shared_lifeday_decor_varactyl_nog'
		elif giftNum == 4:
			objectName = 'rewards_09/shared_lifeday_fireplace_01'
		elif giftNum == 5:
			objectName = 'rewards_09/shared_lifeday_fireplace_02'
		elif giftNum == 6:
			objectName = 'rewards_09/shared_lifeday_lamp_01'
		elif giftNum == 7:
			objectName = 'rewards_09/shared_lifeday_lamp_02'
		elif giftNum == 8:
			objectName = 'rewards_09/shared_lifeday_ornament'
		elif giftNum == 9:
			objectName = 'shared_life_day_present'
		elif giftNum == 10:
			objectName = 'shared_life_day_tree'
		elif giftNum == 11:
			objectName = 'shared_life_day_tree_dressed'
		else:
			objectName = 'rewards_09/shared_lifeday_ornament'
		
		inventory = actor.getSlottedObject('inventory')
		
		if not inventory:
			return
		
		reward1 = core.objectService.createObject('object/tangible/holiday/life_day/rewards_09/shared_lifeday_painting.iff', actor.getPlanet())
		reward2 = core.objectService.createObject('object/tangible/holiday/life_day/' + objectName + '.iff', actor.getPlanet())
		
		inventory.add(reward1)
		inventory.add(reward2)
		
		core.objectService.destroyObject(object)
	return
