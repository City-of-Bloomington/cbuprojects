echo off
set var=project
copy .\build\WEB-INF\classes\%var%\*.class .\WEB-INf\classes\%var%\.
goto done
:usage 
echo  need to pass an argument to cpc 
:done
