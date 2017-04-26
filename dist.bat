REM @echo off

setlocal

cd %~dps0hybris/bin/platform


CALL setantenv.bat

CALL ant dist -Ddist.properties.file=..\..\..\dist.properties

endlocal
