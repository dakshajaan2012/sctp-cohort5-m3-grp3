@echo off
rem Change directory to the location of dependency-check.bat
cd /d "C:\dependency-check-9.1.0-release\dependency-check\bin"

rem Run dependency-check.bat with the correct option for specifying the output folder
dependency-check.bat --project "MyProject" --scan "C:\Users\daksh\SCTP\Spring-boot-parking-app\sctp-cohort5-m3-grp3" --format HTML --out "C:\dependency-check-9.1.0-release"

