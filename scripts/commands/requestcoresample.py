import sys

def setup():
    return
    
def run(core, actor, target, commandString):
	core.surveyService.requestSampling(actor, target, commandString);
	return