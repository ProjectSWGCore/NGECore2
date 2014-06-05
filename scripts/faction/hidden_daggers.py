import sys

def isAlly(ally):
	if ally == 'rogue_corsec':
		return 1
	return 0

def isEnemy(enemy):
	if enemy == 'corsec' or enemy == 'beldonnas_league' or enemy == 'flail':
		return 1
	return 0