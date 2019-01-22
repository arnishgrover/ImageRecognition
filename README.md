# ImageRecognition
An Image Recognition Java based Application that takes an image file an input and outputs the text data from it in an editable format adding the functionality of logging in

Before using this piece of code you will need to configure the compiler first 
Recommended java compiler is Java 8 or ABOVE

First these all files need to be added into your workspace
And install all the libraries included in the Required Libraries into the project Build Path alongside the Java compiler
Next for the functionality of Database, you will need to have Oracle Database Installed(Prefferd version 10 G)
Ensure that username and password of your database is SYSTEM and SYS respectively(capitals)
Into the database create a table named info having two varchar fields name and password
  The prototype of the table looks like
    create table info(name varchar(15), password varchar(15));

If you already have a database you will need to change the required in the interface.java file located at /src/ocr_img

You can use this Java Application for any use you intend to.
This might contain bugs, the user will be liable for any data loss or any problem for that sort by using this application

To start the application locate interface.java file at /src/ocr_img  and run the java file
Thanks for using this software
I hope you find this helpful





