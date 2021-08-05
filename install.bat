REM @echo off

setlocal

cd %~dps0core-customize/hybris/bin/platform


CALL setantenv.bat

CALL ant clean

CALL ant addoninstall -Daddonnames="ampliencedmaddon" -DaddonStorefront.yacceleratorstorefront="yacceleratorstorefront"

CALL ant clean all

CALL ant initialize

endlocal
