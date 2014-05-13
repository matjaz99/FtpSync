FtpSync
=======

---------------------------------------------------------------------
About
---------------------------------------------------------------------
FtpSync monitors a directory and uploads changes to remote ftp location.


---------------------------------------------------------------------
Set up project
---------------------------------------------------------------------
Clone FtpSync git repository.
Import project as Java project (I use Eclipse).
Convert the project to Maven project.


---------------------------------------------------------------------
Building
---------------------------------------------------------------------

Execute maven goal: clean package dependency:copy-dependencies verify


---------------------------------------------------------------------
Running
---------------------------------------------------------------------
Edit ftpsync.properties or make copy.

Start FtpSync from command line: $ ./start.sh <properties>

Stop FtpSync (running in command line): Ctrl + c




Matjaz Cerkvenik