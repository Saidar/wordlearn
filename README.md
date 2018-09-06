# wordLern
Application to Learn Foreign Words

The App uses JavaFX to implement MVC model. The DataBase is MySQL server. The App can be connected to local or external server during work.
SQL requests are made using two technics: HQL and Criteria.
The description of classes for sql server firstly was made by XML, but then they were changed to JPA.
Passwords of all users are stored at the DB, but they are encripted by SHA256.

To run the programm one should download *.exe file and run it on PC.
After instalation one can run the programm and check if there is connection to DB.
Then one can log in by login/password - admin/admin.
Then person has apportunity to load new words into DB(sigle or list from file).

User can run his or her own DB and then manage DB connection in the App at Login scene.

