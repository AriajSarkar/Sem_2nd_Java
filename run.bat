@echo off
setlocal enabledelayedexpansion

REM Create escape character (ASCII 27) with a special trick
for /F %%a in ('echo prompt $E ^| cmd') do set "ESC=%%a"

REM Set up colors with proper escape character
set "RED=%ESC%[91m"
set "GREEN=%ESC%[92m"
set "YELLOW=%ESC%[93m"
set "BLUE=%ESC%[94m"
set "MAGENTA=%ESC%[95m"
set "CYAN=%ESC%[96m"
set "WHITE=%ESC%[97m"
set "RESET=%ESC%[0m"

REM Try to enable VT100 sequences using PowerShell if available
powershell -Command "& {if (Get-Command -Name 'Set-ItemProperty' -ErrorAction SilentlyContinue) { Set-ItemProperty -Path 'HKCU:\Console' -Name 'VirtualTerminalLevel' -Value 1 -Type DWORD -Force }}" >nul 2>&1

REM Use Windows native color as fallback in case ANSI colors don't work
color 0B

REM Cache directory and files
set "CACHE_DIR=.cache"
set "CACHE_FILE=%CACHE_DIR%\compile_cache.json"
set "ERROR_FILE=%CACHE_DIR%\errors.txt"
set "LOG_FILE=%CACHE_DIR%\compile_log.txt"
set "TIMESTAMP_FILE=%CACHE_DIR%\current.timestamp"

echo %CYAN%Java Program Runner%RESET%
echo %CYAN%=================%RESET%

if "%~1"=="" (
    echo %RED%Error: No class specified.%RESET%
    echo %YELLOW%Usage: run.bat [ClassName or JavaFileName]%RESET%
    echo %YELLOW%Examples:%RESET% 
    echo %GREEN%  run.bat Q1_PrimeInRange%RESET%
    echo %GREEN%  run.bat Factorial%RESET%
    echo %GREEN%  run.bat Lab_Q/Syllabus_Q/Factorial.java%RESET%
    exit /b 1
)

set "classname=%~1"
set "original_input=%~1"

REM Check if input is a .java file and convert to class name
if "%classname:~-5%"==".java" (
    set "classname=%classname:~0,-5%"
    set "classname=!classname:/=.!"
    set "classname=!classname:\=.!"
)

REM Get the simple name (last part after the last dot)
for /f "tokens=* delims=." %%a in ("%classname%") do set "simple_name=%%a"
for /f "tokens=1* delims=." %%a in ("%classname%") do (
    if not "%%b"=="" (
        for /f "tokens=* delims=" %%c in ("%%b") do set "simple_name=%%c"
    )
)

echo %YELLOW%Looking for class: %simple_name% in bin directory...%RESET%

if not exist "bin" (
    echo %YELLOW%Bin directory not found. Creating it...%RESET%
    mkdir bin
)

REM Check if the class exists in bin directory
if exist "bin\%simple_name%.class" (
    echo %GREEN%Found class: %simple_name%%RESET%
    echo %CYAN%Running %simple_name%...%RESET%
    echo %MAGENTA%======== PROGRAM OUTPUT ========%RESET%
    java -cp bin %classname% %2 %3 %4 %5 %6 %7 %8 %9
    set "EXIT_CODE=!ERRORLEVEL!"
    echo %MAGENTA%===============================%RESET%
    
    if !EXIT_CODE! NEQ 0 (
        echo %RED%Program exited with code !EXIT_CODE!%RESET%
    ) else (
        echo %GREEN%Program completed successfully%RESET%
    )
    exit /b !EXIT_CODE!
)

echo %YELLOW%Class file not found. Searching for corresponding Java file...%RESET%

REM Search for matching Java file
set "java_file_found=false"
set "java_file_path="

REM Try to find the Java file in different locations
if "!java_file_found!"=="false" (
    if exist "%simple_name%.java" (
        set "java_file_found=true"
        set "java_file_path=%simple_name%.java"
    )
)

if "!java_file_found!"=="false" (
    for /r %%F in (*%simple_name%.java) do (
        echo %GREEN%Found Java file: %%F%RESET%
        set "java_file_found=true"
        set "java_file_path=%%F"
    )
)

if "!java_file_found!"=="true" (
    echo %YELLOW%Compiling !java_file_path! first...%RESET%
    call compile.bat "!java_file_path!"
    
    REM Check if compilation was successful
    if exist "bin\%simple_name%.class" (
        echo %GREEN%Compilation successful. Running %simple_name%...%RESET%
        echo %MAGENTA%======== PROGRAM OUTPUT ========%RESET%
        java -cp bin %classname% %2 %3 %4 %5 %6 %7 %8 %9
        set "EXIT_CODE=!ERRORLEVEL!"
        echo %MAGENTA%===============================%RESET%
        
        if !EXIT_CODE! NEQ 0 (
            echo %RED%Program exited with code !EXIT_CODE!%RESET%
        ) else (
            echo %GREEN%Program completed successfully%RESET%
        )
        exit /b !EXIT_CODE!
    ) else (
        echo %RED%Compilation failed or class file not generated.%RESET%
        echo Check %ERROR_FILE% for more details.
        exit /b 1
    )
) else (
    echo %YELLOW%No matching Java file found for "%simple_name%".%RESET%
    
    REM Try to find the class one more time with partial matches
    set "found=false"
    
    for %%F in ("bin\*.class") do (
        set "file_name=%%~nF"
        if "!file_name!"=="%simple_name%" (
            echo %GREEN%Found class match: !file_name!%RESET%
            set "found=true"
            echo %MAGENTA%======== PROGRAM OUTPUT ========%RESET%
            java -cp bin !file_name! %2 %3 %4 %5 %6 %7 %8 %9
            set "EXIT_CODE=!ERRORLEVEL!"
            echo %MAGENTA%===============================%RESET%
            
            if !EXIT_CODE! NEQ 0 (
                echo %RED%Program exited with code !EXIT_CODE!%RESET%
            ) else (
                echo %GREEN%Program completed successfully%RESET%
            )
            exit /b !EXIT_CODE!
        )
    )
    
    REM If still not found, try case-insensitive partial matches
    if "!found!"=="false" (
        for %%F in ("bin\*.class") do (
            set "file_name=%%~nF"
            echo !file_name! | findstr /i /c:"%simple_name%" >nul
            if not errorlevel 1 (
                echo %GREEN%Found partial class match: !file_name!%RESET%
                set "found=true"
                echo %MAGENTA%======== PROGRAM OUTPUT ========%RESET%
                java -cp bin !file_name! %2 %3 %4 %5 %6 %7 %8 %9
                set "EXIT_CODE=!ERRORLEVEL!"
                echo %MAGENTA%===============================%RESET%
                
                if !EXIT_CODE! NEQ 0 (
                    echo %RED%Program exited with code !EXIT_CODE!%RESET%
                ) else (
                    echo %GREEN%Program completed successfully%RESET%
                )
                exit /b !EXIT_CODE!
            )
        )
    )
    
    if "!found!"=="false" (
        echo %RED%No matching class or Java files found for "%simple_name%"%RESET%
        echo.
        echo %CYAN%Available classes in bin:%RESET%
        for %%F in ("bin\*.class") do (
            echo %GREEN%  %%~nF%RESET%
        )
        exit /b 1
    )
)