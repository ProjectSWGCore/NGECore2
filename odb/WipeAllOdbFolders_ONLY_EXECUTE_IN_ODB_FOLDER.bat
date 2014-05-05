:: Odb file wiper by Charon
:: Use at your own risk!
:: Do NOT execute outside the odb folder
:: because it deletes files in subfolders!

@echo off
cls
setlocal EnableDelayedExpansion

for %%* in (.) do set CurrDirName=%%~n*

if "%CurrDirName%" == "eclipse" (
    echo You are running this script from inside Eclipse ^^!
    echo You must execute it via windows in the odb folder ONLY ^^!
    echo Terminating for your own data safety.
    pause
    EXIT
)

if NOT "%CurrDirName%" == "odb" (
    echo You are not executing this file from the odb folder^^!
    echo Terminating for your own data safety.
    pause
    EXIT
)

set /a count=0

for /d %%d in (*) do (
    set /a count+=1
    @echo !count!. %%d 
    del "%%d\*.*" /s/q
    echo. 2>%%d\placeholder.txt
)
setlocal DisableDelayedExpansion