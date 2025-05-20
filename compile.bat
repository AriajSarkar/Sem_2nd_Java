@echo off
setlocal enabledelayedexpansion

echo Creating bin directory if it doesn't exist...
mkdir bin 2>nul

if "%~1"=="" (
    echo No specific file provided. Cleaning up old class files...
    for /r %%F in (*.class) do (
        if not "%%~dpF" == "%~dp0bin\" (
            echo Deleting %%F
            del "%%F"
        )
    )
    
    echo Compiling all Java files...
    for /r %%F in (*.java) do (
        echo Compiling %%F
        javac -d "%~dp0bin" "%%F"
    )
) else (
    echo Compiling specific file: %1
    
    rem Check if the file exists with the provided path
    if exist "%~1" (
        javac -d "%~dp0bin" "%~1"
    ) else (
        rem Try to find the file in the project
        set "found=false"
        for /r %%F in (*%~1) do (
            echo Found matching file: %%F
            javac -d "%~dp0bin" "%%F"
            set "found=true"
        )
        
        if "!found!"=="false" (
            echo Error: File %1 not found.
            exit /b 1
        )
    )
)

echo Compilation complete. Class files in bin directory.
exit /b 0