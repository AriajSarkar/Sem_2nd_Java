@echo off
setlocal enabledelayedexpansion

if "%~1"=="" (
    echo Error: No class specified.
    echo Usage: run.bat [ClassName or JavaFileName]
    echo Examples: 
    echo   run.bat Q1_PrimeInRange
    echo   run.bat Factorial
    echo   run.bat Lab_Q/Syllabus_Q/Factorial.java
    exit /b 1
)

set "classname=%~1"

rem Check if input is a .java file and convert to class name
if "%classname:~-5%"==".java" (
    set "classname=%classname:~0,-5%"
    set "classname=!classname:/=.!"
    set "classname=!classname:\=.!"
)

rem Get the simple name (last part after the last dot)
for /f "tokens=* delims=." %%a in ("%classname%") do set "simple_name=%%a"
for /f "tokens=1* delims=." %%a in ("%classname%") do (
    if not "%%b"=="" (
        for /f "tokens=* delims=" %%c in ("%%b") do set "simple_name=%%c"
    )
)

echo Looking for class: %simple_name% in bin directory...

if not exist "bin" (
    echo Bin directory not found. Creating it...
    mkdir bin
)

rem Check if the class exists in bin directory
if exist "bin\%simple_name%.class" (
    echo Found class: %simple_name%
    java -cp bin %simple_name%
    exit /b 0
)

echo Class file not found. Searching for corresponding Java file...

rem Search for matching Java file
set "java_file_found=false"
set "java_file_path="

rem Try to find the Java file in different locations
if "!java_file_found!"=="false" (
    if exist "%simple_name%.java" (
        set "java_file_found=true"
        set "java_file_path=%simple_name%.java"
    )
)

if "!java_file_found!"=="false" (
    for /r %%F in (*%simple_name%.java) do (
        echo Found Java file: %%F
        set "java_file_found=true"
        set "java_file_path=%%F"
    )
)

if "!java_file_found!"=="true" (
    echo Compiling !java_file_path! first...
    call compile.bat "!java_file_path!"
    
    rem Check if compilation was successful
    if exist "bin\%simple_name%.class" (
        echo Compilation successful. Running %simple_name%...
        java -cp bin %simple_name%
        exit /b 0
    ) else (
        echo Compilation failed or class file not generated.
        exit /b 1
    )
) else (
    echo No matching Java file found for "%simple_name%".
    
    rem Try to find the class one more time with partial matches
    set "found=false"
    
    for %%F in ("bin\*.class") do (
        set "file_name=%%~nF"
        if "!file_name!"=="%simple_name%" (
            echo Found class match: !file_name!
            set "found=true"
            java -cp bin !file_name!
            exit /b 0
        )
    )
    
    rem If still not found, try case-insensitive partial matches
    if "!found!"=="false" (
        for %%F in ("bin\*.class") do (
            set "file_name=%%~nF"
            echo !file_name! | findstr /i /c:"%simple_name%" >nul
            if not errorlevel 1 (
                echo Found partial class match: !file_name!
                set "found=true"
                java -cp bin !file_name!
                exit /b 0
            )
        )
    )
    
    if "!found!"=="false" (
        echo No matching class or Java files found for "%simple_name%"
        echo.
        echo Available classes in bin:
        for %%F in ("bin\*.class") do (
            echo   %%~nF
        )
        exit /b 1
    )
)