Course project is a Crappy Password Manager (with multiple security flaws).
=======

Markdown version of this text is found in **https://github.com/Kaapelikala/cybersecuritybase-project/blob/master/problems.md**.

The project is found at https://github.com/Kaapelikala/cybersecuritybase-project and is installed and run in the same way as the template project.

Before playing around, run localhost:8080/setup to setup the tables and some test data.

##Vulnerability 1: SQL Injections
How to reproduce:
1. Go to localhost:8080/login
2. Write ”admin” to username -field
3. Write ”a’ or ’1’=’1” to password field
4. Press ”login”
5. You are now logged in as admin.

It is extremely easy to gain access to other people’s data by using SQL injection in the login screen. As the software is using direct SQL queries, anything the user writes into the login forms, are actually run in the database. The user’s name can also be used as an attackvector.

Identification: OWASP Zap points into the possibility of SQL injection, however it is not completely sure about that. When you try the possibility out, you find out that there is indeed SQL injection flaw in the login form.

How to fix: Use prepared statements, with sanitation if possible. This way it is possible to consider the whole input as a single string, and as such it won’t be run as part of the SQL script

##Vulnerability 2: Broken session handling
How to reproduce:
1. Go to localhost:8080/login
2. Login (using SQL injection if you want)
3. Check the cookies. You see that there is a cookie ”session”, with an md5-sum value.
4. Save the value.
5. on /insert page click ”logout”
6. Empty cookies and set a new cookie by hand with the same details.
7. Go to localhost:8080/insert
8. You see the passwords even after logout

This is caused due to extremely lazy way of handling sessions. The sessions are saved as md5sum(username+password) to database, and the md5 sum is added to the cookie. The cookie’s value is then on /insert page checked against the one in DB to see if it is the same. If it is, allow access. Logout function is actually done also badly. It just sends the user back to the ”login” page without doing anything else at all. Also, if the user leaves the computer, another person can user the computer to login to his data with the cookie.

Identification: This can be detected by copying the cookies and then resent to the system. A Man-In-The-Middle attack can succeed in this extremely easily.

How to fix: The sessions should be 1) salted, 2) timed to end after a certain amount of inactivity – this can be done for example 15-30 minutes after the last page load, and 3) the Logout function should both remove the cookie and the session from the database.

##Vulnerability 3: XSS Injections
How to reproduce:
1. Go to localhost:8080/login
2. Login (using SQL injection to target’s account)
3. Write Javascript attack to any of the three fields in the form. For example:
Username = username
Password = password
Comments = <script language="javascript" type="text/javascript">alert("Moi!");</script>
4. Save the value.
5. The script is ran on every reload of the page for the user.

Identification: This attack is easily found out if the user just tries this. Reason for the attack is that the system does no validation or sanitation whatsoever to any inputs. Even the username of the user can be used as an attack vector.

How to fix: All inputs should be validated before anything is done to them. If nothing else, HTML-encode the data so that the tags will not act as tags when the user goes to the page.

##Vulnerability 4: Sensitive data leakage
How to reproduce:
1. Once the software is set up, go to localhost:8080/check
2. You see all relevant info – usernames, passwords, … - in the database in a nice readable form

The developers have forgotten a debug page to the software. This page is set up so that it creates tables that have the contents of all the tables in the database in plain text. This can be used to gather data and also be used to map different usernames to the owner. For example Usernames ”a@gmail.com” and ”b@gmail.com” belong to ”person@firma.fi”, and as such they can be used to connect the accounts together.

Identification: The attacker can map out the system by brute forcing all possible pages possibly by using a dictionary attack. The attacker can then check each page by hand and see what they do. The /setup page is also a problem as it can be used to purge the database of all data.

How to fix: Remove the controllers for the pages.

##Vulnerability 5: Security Misconfiguration & CSRF problems
How to reproduce (for CSRF problem):
1. Open network monitor in your browser
2. Go to localhost:8080/login
3. Login
4. Notice that no CSRF tokens are sent anywhere

As the more secure options can potentially cause problems and or slowing with the program, the developers decided to simply turn some offline. This way the software runs smoother and does not whine about missing security headers.

Identification: There is no CSRF in the links whenever they are followed / forms are filled.

Fixing: Set the program’s settings correctly. In this case in SecurityConfiguration.java remove ”http.csrf().disable();” line. The settings should be tweaked in other ways too, such as they should be set to use HTTPS instead of HTTP

##Potential vulnerabilies:

Unvalidated Redirects: As the software actually does no validation whatsoever, it is extremely easy to redirect the user to a potentially dangerous site, for example by using XSS injections. 

