![http://i.imgur.com/wClVoIF.png](http://i.imgur.com/wClVoIF.png)

# USE CASE #1 #

  * **Name:** Registration
  * **Actors:** Guests
  * **Goal :** Creating user account
  * **Preconditions:**
    1. User shall have a valid user name and e-mail address that is not registered in the system currently.
  * **Steps:**
    1. User will enter valid user name, password, e-mail address.
    1. User will enter his/her country, city, birth date and gender.
    1. System will send activation mail upon registration.
    1. User will verify this mail and activate his/her account.
  * **Postconditions:**
    1. A profile page for the user will be created in the system.
    1. New user record will be created

# USE CASE #2 #

  * **Name:** Login
  * **Actors:** Registered user
  * **Goal :** Signing into the system
  * **Preconditions:**
    1. User shall have a registered account in the system.
    1. User shall enter valid user name and password.
  * **Steps:**
    1. User will enter his/her user name or e-mail address.
    1. User will enter his/her password.
    1. The given information will be checked in the system for login.
  * **Postconditions:**
    1. User will be logged into the system and will be directed to the home page if the given login information is validated by the system.
    1. User will be requested to enter the login information again if the given data is not correct.

# USE CASE #3 #

  * **Name:** Profile Management
  * **Actors:** Registered User
  * **Goal :** Setting or changing profile info
  * **Preconditions:**
    1. To be logged in.
  * **Steps:**
    1. User enters his/her profile page
    1. Clicks edit button
    1. Makes changes adding/removing/changing personal information
    1. Clicks save button
  * **Postconditions:**
    1. Database will be updated.

# USE CASE #4 #

  * **Name:** Deactivate Account
  * **Actors:** Registered users
  * **Goal :** To deactivate the account when user wants to do it.
  * **Preconditions:**
    1. To be logged in.
  * **Steps:**
    1. User will go to settings after logging in.
    1. User will click the button "Deactivate My Account".
  * **Postconditions:**
    1. User account will be deactivated.
    1. User will not be visible in the website so that all activities, all comments and user's own page will be invisible.

# USE CASE #5 #

  * **Name:** Notification
  * **Actors:** Registered users
  * **Goal :** To switch on/off notifications
  * **Preconditions:**
    1. To be logged in.
  * **Steps:**
    1. User will go to settings after logging in.
    1. User will switch the notifications.
  * **Postconditions:**
    1. User will not receive notifications if s/he switches the notifications to off.


# USE CASE #6 #

  * **Name:** Follow Users
  * **Actors:** Registered User
  * **Goal :** To see the preferences of people with the same taste
  * **Preconditions:**
    1. To be logged in
    1. There should be another user to follow in the system
  * **Steps:**
    1. User will go to another user's profile.
    1. User will click the 'follow the user' button.
  * **Postconditions:**
    1. Followed user will be notified.
    1. Database will be updated.
    1. User will be able to see the posts and comments of the user that s/he follows.

# USE CASE #7 #

  * **Name:** Add Recipe
  * **Actors:**Registered Users
  * **Goal :** Recipe will be able to see for other users of web page
  * **Preconditions:**
    1. To be a logged in.
  * **Steps:**
    1. User will click the "Add Recipe".
    1. User will be directed to the New Recipe page.
    1. User will choose a name for recipe.
    1. User will add semantic tags.
    1. User will add the ingredients.
    1. User will write about preparation of recipe and add photo of recipe.
    1. User will click the "Add" button.
  * **Postconditions:**
    1. System will automatically store the recipe.
    1. Database will be updated.
    1. System will provide nutritional information about recipe .
    1. User will directed to the his/her profile page.

# USE CASE #8 #

  * **Name:** Basic Search
  * **Actors:** Guest Users and Registered Users
  * **Goal :** To get all the related results of the searched keywords.
  * **Preconditions:**
  * **Steps:**
    1. Registered user or guest user enters the keywords in the search text box.
    1. Registered user or guest user clicks the search button.
  * **Postconditions:**
    1. System should add the searched keywords to search history to offer him the most recently searched keywords of the registered user.
    1. System returns all related results (based on the personal preferences if user is registered).

# USE CASE #9 #

  * **Name:** Advanced Search
  * **Actors:** Registered Users
  * **Goal :** To get results with given filters
  * **Preconditions:**
    1. To be a logged in.
  * **Steps:**
    1. Registered user should arrange filters.
    1. Registered user clicks the search button.
  * **Postconditions:**
    1. System should add the searched keywords to search history to offer him the most recently searched keywords of the registered user.
    1. System returns all related results based on the filters.

# USE CASE #10 #

  * **Name:** Activity Feed
  * **Actors:** Registered Users
  * **Goal :** To see the latest activities of people whom the user follow
  * **Preconditions:**
    1. To be logged in.
    1. To follow at least one user.
  * **Steps:**
  * **Postconditions:**
    1. User will see the activity feed on the main page

# USE CASE #11 #

  * **Name:** Like/Dislike/Comment
  * **Actors:** Registered Users
  * **Goal :** Suggesting opinion via comment or rating the content
  * **Preconditions:**
    1. To be logged in.
  * **Steps:**
    1. User will open the page of the content which s/he wants to add comment or rate.
    1. User will add comment by clicking “Add comment”.
    1. User will rate the content by liking or disliking.
  * **Postconditions:**
    1. System will show the comment or rate in the content's page.

# USE CASE #12 #

  * **Name:** Report
  * **Actors:** Registered Users
  * **Goal :** To take action about inappropriate content
  * **Preconditions:**
    1. To be logged in.
  * **Steps:**
    1. User detects an inappropriate comment, user etc.
    1. User clicks the "Report" button.
    1. User fills the report form and clicks "Send".
  * **Postconditions:**
    1. The content that has too much reports is removed from the comments section.