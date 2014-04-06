import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	core.surveyService.requestSurvey(actor, target, commandString);
	return