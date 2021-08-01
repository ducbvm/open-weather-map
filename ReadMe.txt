System requirements:
- Microsoft Windows 10
- Eclipse IDE version Photon (4.8)
- Java: Install Java SE 8u301 from https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html
Add the following to System Environment variables "Path": C:\Program Files\Java\jdk1.8.0_301\bin (for 64-bit version)
- Download Selenium Server Standalone (Selenium Server-Grid) version: 3.141.59 at https://www.selenium.dev/downloads/
- Google Chrome version 92.0.4515.107 (Official Build) (64-bit) - Download the respective ChromeDriver version 92.0.4515.107 at https://chromedriver.chromium.org/
- Firefox version 90.0.2 (64-bit)- Download the respective GeckoDriver version 0.29.1 at https://github.com/mozilla/geckodriver/releases

Setup for running automation scripts:
- Import the project as Java Project
- Add Selenium Server Standalone 3.141.59 jar file to buildpath
- Install TestNG on Eclipse through Install New Software feature, add TestNG to buildpath
- Run testng.xml to start the test
- One script will run on both Chrome and Firefox
- Check folder test-output -> emailable-report.html for the test result
- Currently the script will fail due to following reasons:
+ The encoding for Celsius degree between the temperature displayed on search result and in detailed information are different 
=> this will always happen currently. The encoding on search result return for example 27°? - the "C" character has encoding issue
+ The temperature between what displayed on search result and what displayed in detailed information can be different (due to application)
+ The time displayed in detailed information and the time get can be different by 1 minute (rarely happen)
