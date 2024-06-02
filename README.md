### Program projective
Program converts .md files into .html files with saving punctuation

### Check for java and kotlin
First, you need to check if Kotlin Compiler and JDK (Java Development Kit) are installed.
```
  java -version
  kotlin -version
```
If something is not installed - install. For JDK is just one installer but for Kotlin Compiler you need a few more steps.
Download .zip file from [this link](https://github.com/JetBrains/kotlin/releases/tag/v1.9.23), next step you need to unzip it in any folder,
next you need to search for "Edit the system environment variables" > go to the “Advanced” tab > "Environment variables" button >
"path" > edit > new > type the full path to bin from Kotlin Compilier (you may need to restart your computer).

Done ! All installed.

### Build and run
Download Main.kt file from converter/src
Open CMD
```
  kotlinc Main.kt -include-runtime -d Main.jar
  java -jar Main.jar
```
After the first command you will get a .jar file and the second command will run program.

### Usage
Enter path to your .md file and program will convert .md file into .html file in same directory

This is a revert commit
