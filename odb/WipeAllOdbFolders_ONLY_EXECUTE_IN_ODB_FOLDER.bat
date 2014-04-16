:: Odb file wiper by Charon
:: Use at your own risk!
:: Do NOT execute outside the odb folder
:: because it deletes files in subfolders!

@echo off
cls
setlocal EnableDelayedExpansion

set /a count=0

for /d %%d in (*) do (
    set /a count+=1
    @echo !count!. %%d 
    del "%%d\*.*" /s/q
    echo. 2>%%d\placeholder.txt
)
setlocal DisableDelayedExpansion

