# Getting Started

This tool is a Java Application that extracts text from image files whilst providing an interface
It uses Tesseract-OCR, an open-source software contributed my major companies like Google to extract text from an image

### Features
* Custom login sessions for multiple users 
* Database at the backend to ensure proper login and session management
* Simple interface which is easy to navigate and interact with
* Save the extracted data from the image either locally or edit it on-the-go

### Installation
* Import the repository to your local machine and open it on any Java-IDE
* Configure the default Java compiler, install the required libraries from the repository alonside the default java compiler
* Install Oracle Database to your system and create a table inside having two attributes name and password
* The basic SQL query to create the table would be
* create table <table name>(name varchar(15), password varchar(15));
* Now to enable database communication with your application
* Open interface.java file located at /src/ocr_img and modify the username and password to the database username and password
* Also change the table name to the name of the table created into your database
* Run interface.java file to start this application
  
### Note
Anyone can use this Java Application for any use they intend to. However, this might contain bugs hence the user will be liable for any data loss or any problem they encounter while using this applicaton.

  
