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
rem dossier d'extensions (ex: WaypointExtension-1.0.0.jar) en plus de la notre.
rem On la supprime pour eviter d'avoir deux jars charges (= deux singletons
rem WaypointDisplayManager isoles, etat jamais synchronise entre add/remove).
del /q "%TARGET%\WaypointExtension-*.jar" 2>nul

for %%F in ("%~dp0build\libs\WaypointExtension-*.jar") do (
    copy /y "%%F" "%TARGET%\WaypointExtension.jar"
)

echo.
echo Jar copie dans %TARGET%\WaypointExtension.jar
