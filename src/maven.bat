cls 
@ECHO OFF
color 0a 
TITLE Maven 命令 For Windows 
GOTO MENU 
:MENU 
CLS
ECHO. 
ECHO. * * * * * Maven 命令 For Windows  * * * * *  *
ECHO. * * 
ECHO. * 1 Install *
ECHO. * * 
ECHO. * 2 compile * 
ECHO. * * 
ECHO. * 3 deploy * 
ECHO. * * 
ECHO. * 4 clean * 
ECHO. * * 
ECHO. * 5 package * 
ECHO. * * 
ECHO. * 99 退 出 * 
ECHO. * * 
ECHO. * * * * * * * * * * * * * * * * * * * * * * * * 
ECHO. 
ECHO.请输入命令的序号： 
set /p ID= 
IF "%id%"=="1" GOTO Install 
IF "%id%"=="2" GOTO compile 
IF "%id%"=="3" GOTO deploy 
IF "%id%"=="4" GOTO clean 
IF "%id%"=="5" GOTO package
IF "%id%"=="99" EXIT 
PAUSE 
:Install 
ECHO. 开始 install
@call Install.bat
PAUSE 
GOTO MENU 
:compile 
ECHO. 开始 compile
@call compile.bat
PAUSE 
GOTO MENU
:deploy 
ECHO. 开始 deploy
@call deploy.bat
PAUSE 
GOTO MENU 
:clean 
ECHO. 开始 clean
@call clean.bat
PAUSE 
GOTO MENU 
:package 
ECHO. 开始 package
@call package.bat
PAUSE 
GOTO MENU 