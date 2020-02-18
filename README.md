# blogbox

Tech-stack: Java 11, Spring Boot, Spring Data, Spring Security, Spring WEB, Hibernate, PostgreSQL, Flyway, Gradle.

BlogBox is a news application that can be used by anyone. The application can be used without being registered, but its functionality will be limited in this way. Once registered, you are given full control over your personal profile, your news, favorites, likes and comments. The application offers the ability to build an administrative and user interface. There are 3 types of implemented roles: 
User, Writer and Administrator. 
The administrative portal can be accessed on port 8086 and the user portal on 8084.

User-related features in the user panel:
-	Login;
-	Register;
-	Anyone can view a particular active user by his id, the returned data number is determined by the role of the applicant;
-	Anyone can view a particular active user by his username, the returned data number is determined by the role of the applicant;
-	Only a user can view their own favorite list;
-	Only a user can view a list of news created by a particular user. If the user views his list of created news, then he will see all irrespective of their status, while if the user views a foreign list of created news, he will see only those with the status "APPROVED";
-	Anyone can search users in the system by specific criteria;
-	Only a user can update his own account.

User-related features in the admin panel:
-	Login;
-	Can view any user by his id, always getting all the data;
-	Can view any user by his username, always getting all the data;
-	Can view all users' favorite lists;
-	Can view all users’ created news;
-	May require a request for all users in the system;
-	Can search for users;
-	Can change the profile of any user, which involves changing only names;
-	Can delete / block / disable any user;
-	Can control the roles of each user.

User-related features in the archive admin panel:
-	All features of the admin panel, but they will only be applied to deleted / inactive users;
-	Can recover / activate any deleted user.

News-related features in the user panel:
-	Only a writer can create news, news automatically accepting a "WAITING" status;
-	Anyone can view a specific news with an "APPROVED" status by id (does not apply if the news is yours), the returned data number is determined by the role of the applicant;
-	Only a user can view their created news regardless of status;
-	Anyone can search for news with a status APPROVED by specific criteria;
-	Anyone can view the three types of statistics on a particular news with an APPROVED status;
-	Only a writer can correct their own news regardless of status;
-	Only a user can create new statistics for a certain news with the status APPROVED (likes, dislikes, favorites);
-	Only a writer can delete their own news regardless of status.

News-related features in the admin panel:
-	Can create news;
-	Can view a specific news by id regardless of its status;
-	Can view the news created by a specific user regardless of status;
-	Can request all news in the system regardless of status;
-	Can search for news by a certain criteria and choose the status;
-	Can view statistics of a particular news regardless of status;
-	Can edit any news;
-	Can create new statistics for any news regardless of status;
-	Can change the status of the news;
-	Can delete specific news.

News-related features in the archive admin panel:
-	Оffers some admin panel actions but only applies to deleted ones.

Comment-related features in the user panel:
-	Only a user can comment on a specific news;
-	Anyone can read comments;
-	Only a user can update their own comment;
-	Only a user can delete their own comment;

