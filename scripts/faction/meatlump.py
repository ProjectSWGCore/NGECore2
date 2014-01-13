import sys

def isAlly(ally):
	return 0

def isEnemy(enemy):
	if enemy == 'corsec' or enemy == 'rogue_corsec' or enemy == 'beldonnas_league':
		return 1
	return 0