echo off

dir %PAYARA_HOME%\glassfish\domains\domain1\logs
echo=
del %PAYARA_HOME%\glassfish\domains\domain1\logs\*.log*
echo=
dir %PAYARA_HOME%\glassfish\domains\domain1\logs

pause
