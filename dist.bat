REM @echo off

setlocal

cd %~dps0core-customize/hybris/bin/platform


CALL setantenv.bat

CALL ant dist -Ddist.properties.file=..\..\..\..\dist.properties

endlocal
