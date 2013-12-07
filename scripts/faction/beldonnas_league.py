import sys

def isAlly(ally):
	if ally == 'corsec':
		return 1
	return 0

def isEnemy(enemy):
	if enemy == 'followers_of_lord_nyax' or enemy == 'lost_aqualish':
		return 1
	return 0