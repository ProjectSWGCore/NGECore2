from resources.objects.creature import CreatureObject
from java.util import Date
from engine.resources.objects import SWGObject
from services.chat import ChatService
from services.chat import Mail
from services.sui import SUIWindow
from services.sui import SUIService
from services.sui.SUIWindow import Trigger
from services.sui.SUIService import MessageBoxType
from java.util import Vector
from main import NGECore
import sys
import math

def setup():
    return
    
def run(core, actor, target, commandString):
    processTip = {0: failure,
                    1: tipTarget,
                    2: tipTargetBank,
                    3: tipName,
                    4: tipNameBank}    
    commandArgs = commandString.split(" ")
    type = tipType(core, actor, target, commandArgs)
    processTip[type](core, actor, target, commandArgs)

def failure(core, actor, target, commandArgs):
    sys.stdout.write("Failure\n")
    
def tipTarget(core, actor, target, commandArgs):
    tipTo = getDirectTarget(core, actor, target)
    tipAmount = int(commandArgs[0])
    # Prioritize LOCKED target. ONLY IF NO LOCKED TARGET, do we transfer to the mouseover target!
    transferMoneyDirect(actor, tipTo, tipAmount) 
    
def tipTargetBank(core, actor, target, commandArgs):
    tipTo = getDirectTarget(core, actor, target)
    tipAmount = int(commandArgs[0])
    # Prioritize LOCKED target. ONLY IF NO LOCKED TARGET, do we transfer to the mouseover target!
    transferMoneyBank(actor, tipTo, tipAmount, core) 
    
def tipName(core, actor, target, commandArgs):
    tipTo = getRemoteTarget(core, commandArgs[0])
    if tipTo is not None:
        tipAmount = int(commandArgs[1])
        transferMoneyDirect(actor, tipTo, tipAmount)
    else:
        return  
    
def tipNameBank(core, actor, target, commandArgs):
    tipTo = getRemoteTarget(core, commandArgs[0])
    if tipTo is not None:
        tipAmount = int(commandArgs[1])
        transferMoneyBank(actor, tipTo, tipAmount, core)
    else:
        return

def transferMoneyDirect(tipFrom, tipTo, transferTotal):
    actorFunds = tipFrom.getCashCredits()
    
    if (tipFrom.inRange(tipTo.getPosition(), 100)): # 100 = 10m
        if int(transferTotal) > 0 and int(transferTotal) <= 1000000:
            if actorFunds >= int(transferTotal):
                tipTo.addCashCredits(int(transferTotal))  
                tipFrom.deductCashCredits(int(transferTotal))
    
                tipTo.sendSystemMessage(tipFrom.getCustomName() + ' tips you ' + `transferTotal` + ' credits.', 0)
                tipFrom.sendSystemMessage('You successfully tip ' + `transferTotal` + ' credits to ' + tipTo.getCustomName() + '.', 0)
                return
            tipFrom.sendSystemMessage('You lack the cash funds to tip ' + `transferTotal` + ' credits to ' + tipTo.getCustomName() + '.', 0)
            return
        tipFrom.sendSystemMessage('Invalid tip amount, set amount between 1 and 1,000,000 credits', 0)
        return
    tipFrom.sendSystemMessage('Target is too far away. Try a wire bank transfer instead.', 0)
    return

def transferMoneyBank(tipFrom, tipTo, transferTotal, core):
    def handleBankTip(core, owner, eventType, returnList):
        bankSurcharge = int(math.ceil(0.05 * float(transferTotal))) 
        core = NGECore.getInstance()
        chatSvc = core.chatService
        actorGlobal = tipFrom
        targetGlobal = tipTo
        actorFunds = actorGlobal.getBankCredits()
        totalLost = int(transferTotal) + bankSurcharge
        
        if eventType == 0:
            if int(totalLost) > actorFunds:
                actorGlobal.sendSystemMessage('You do not have ' + str(totalLost) + ' credits (surcharge included) to tip the desired amount to ' + targetGlobal.getCustomName() + '.', 0)
                return
            if int(transferTotal) > 0 and int(actorFunds) >= int(totalLost):
                date = Date()
                targetName = targetGlobal.getCustomName()
     
                targetMail = Mail()
                targetMail.setMailId(chatSvc.generateMailId())
                targetMail.setTimeStamp((int) (date.getTime() / 1000))
                targetMail.setRecieverId(targetGlobal.getObjectId())
                targetMail.setStatus(Mail.NEW)
                targetMail.setMessage(`transferTotal` + ' credits from ' + actorGlobal.getCustomName() + ' have been successfully delivered from escrow to your bank account')
                targetMail.setSubject('@base_player:wire_mail_subject')
                targetMail.setSenderName('bank')
     
                actorMail = Mail()
                actorMail.setMailId(chatSvc.generateMailId())
                actorMail.setRecieverId(actorGlobal.getObjectId())
                actorMail.setStatus(Mail.NEW)
                actorMail.setTimeStamp((int) (date.getTime() / 1000))
                actorMail.setMessage('An amount of ' + `transferTotal` + ' credits have been transfered from your bank to escrow. It will be delivered to ' + targetGlobal.getCustomName() + ' as soon as possible.')
                actorMail.setSubject('@base_player:wire_mail_subject')
                actorMail.setSenderName('bank')
                 
                targetGlobal.addBankCredits(int(transferTotal))
                actorGlobal.deductBankCredits(int(totalLost))
                actorGlobal.sendSystemMessage('You have successfully sent ' + `transferTotal` + ' bank credits to ' + targetGlobal.getCustomName(), 0)
                targetGlobal.sendSystemMessage('You have successfully received ' + `transferTotal` + ' bank credits from ' + actorGlobal.getCustomName(), 0)
                 
                chatSvc.storePersistentMessage(actorMail)
                chatSvc.storePersistentMessage(targetMail)
                chatSvc.sendPersistentMessageHeader(actorGlobal.getClient(), actorMail)
                chatSvc.sendPersistentMessageHeader(targetGlobal.getClient(), targetMail)
                return             
        else:
            actorGlobal.sendSystemMessage('You lack the bank funds to wire ' + `transferTotal` + ' bank funds to ' + targetGlobal.getCustomName() + '.', 0)
            return        
        return
    
    suiSvc = core.suiService
    suiWindow = suiSvc.createMessageBox(MessageBoxType.MESSAGE_BOX_YES_NO, "@base_player:tip_wire_title", "@base_player:tip_wire_prompt", tipFrom, tipFrom, 10)
    
    returnParams = Vector()
    returnParams.add('btnOk:Text')
    returnParams.add('btnCancel:Text')
    suiWindow.addHandler(0, '', Trigger.TRIGGER_OK, returnParams, handleBankTip)
    suiWindow.addHandler(1, '', Trigger.TRIGGER_CANCEL, returnParams, handleBankTip)
    
    suiSvc.openSUIWindow(suiWindow)
    

def getDirectTarget(core, actor, target):
    if core.objectService.getObject(actor.getTargetId()) is not None and target is None:
        return core.objectService.getObject(actor.getTargetId())
    else:
        return target

def getRemoteTarget(core, name):
        target = core.chatService.getObjectByFirstName(name)
        if target is not None:
            return target
        return None
            

def tipType(core, actor, target, commandArgs):
    commandLength = len(commandArgs)
    if commandLength == 1 and commandArgs[0].isdigit() and (((actor.getTargetId() is not None) and (actor.getTargetId() != actor.getObjectId()) and (actor.getTargetId() != 0L)) or (target is not None)):
        return 1
    if commandLength == 2:
        if commandArgs[0].isdigit() and isinstance(commandArgs[1], basestring) and (((actor.getTargetId() is not None) and (actor.getTargetId() != actor.getObjectId()) and (actor.getTargetId() != 0L)) or (target is not None)):
            if commandArgs[1].lower() == "bank":
                return 2
        if isinstance(commandArgs[0], basestring) and commandArgs[1].isdigit():
            if not commandArgs[0].lower() == "bank":
                return 3
    if commandLength == 3 and isinstance(commandArgs[0], basestring) and commandArgs[1].isdigit() and isinstance(commandArgs[2], basestring):
            if commandArgs[2].lower() == "bank" and not commandArgs[0].lower() == "bank":
                return 4
    return 0