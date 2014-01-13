import sys

def isAlly(ally):
	if ally == 'rebel' or ally == 'imperial':
		return 1
	return 0

def isEnemy(enemy):
	if enemy == 'thug' or enemy == 'bandit':
		return 1
	return 0