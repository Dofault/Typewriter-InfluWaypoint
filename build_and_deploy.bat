@echo off
cd /d "%~dp0"

call "%~dp0gradlew.bat" build --console=plain
if errorlevel 1 (
    echo.
    echo Build echoue, rien n'a ete copie.
    exit /b 1
)

set "TARGET=%~dp0..\..\serveurs\03_semirp\plugins\Typewriter\extensions"
if not exist "%TARGET%" (
    echo Dossier introuvable : %TARGET%
    exit /b 1
)

rem Le plugin Gradle Typewriter peut deposer sa propre copie versionnee dans le
rem dossier d'extensions en plus de la notre (anciens noms inclus). On les
rem supprime pour eviter d'avoir deux jars charges.
del /q "%TARGET%\CompassWaypointExtension-*.jar" 2>nul
del /q "%TARGET%\InfluWaypointExtension-*.jar" 2>nul
del /q "%TARGET%\InfluWaypointExtension.jar" 2>nul
del /q "%TARGET%\influ-waypoint-*.jar" 2>nul
del /q "%TARGET%\influ-waypoint.jar" 2>nul
del /q "%TARGET%\InfluWaypoint-*.jar" 2>nul

copy /y "%~dp0build\libs\CompassWaypointExtension.jar" "%TARGET%\CompassWaypointExtension.jar"

echo.
echo Jar copie dans %TARGET%\CompassWaypointExtension.jar
