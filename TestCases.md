## TEST ID: #1 ##

**Test Priority:** 9/10

**Test Title:** Verify that user can get notification

**Description:** Test that user can get notification

**Module Name:** Notification

**Test Designed by:** [Çağla Balçık](https://code.google.com/p/bounswe2015group1/wiki/CaglaBalcik)

**Test Designed date:** 29.03.2015

**Test Executed by:** N/A

**Test Execution date:** N/A

**Requirements Tested:** 1.1.6.7

**Preconditions:**
  * To be a registered user of the system.

| Step | Test Steps | Test Data | Expected Result | Actual Result | Status | Notes |
|:-----|:-----------|:----------|:----------------|:--------------|:-------|:------|
| 1 | Go to main page  | MainPageURL | See homepage for the user and a menu at the top | -- | -- | -- |
| 2 | Click on Settings  | SettingsPageURL | See the settings page of the registered user. | -- | -- | -- |
| 3 |Click on Add Menu  | SettingsPageURL | See the page which has the notifications turned on. | -- | -- | -- |


**Postconditions:**
  * User will receive notifications if someone follows him/her or likes his/her post and user will be notified if someone makes a comment to his/her post.


---


## Test ID: #2 ##

**Test Priority:** 8/10

**Test Title:** Verify that user can deactivate his/her account

**Description:** Test that user can deactivate his/her account

**Module Name:** User account settings

**Test Designed by:** [Onur Musaoglu](https://code.google.com/p/bounswe2015group1/wiki/OnurMusaoglu)

**Test Designed date:** 03.29.2015

**Test Executed by:** N/A

**Test Execution date:** N/A

**Requirements Tested:** 1.1.1


**Preconditions:**
  * To be logged in.

| Step | Test Steps | Test Data | Expected Result | Actual Result | Status | Notes |
|:-----|:-----------|:----------|:----------------|:--------------|:-------|:------|
| 1 | Go to main page  | MainPageURL | See homepage for the user and a menu at the top | N/A | N/A |    |
| 2 | Click on Settings  | SettingsPageURL | | See the settings page | N/A | N/A |    |
| 3 |Click on Deactivate my account button  | Pop-up appears | See a verification message | N/A | N/A |    |
| 4 | Click 'I am sure button' | status = deactivated | User redirected to Home Page | N/A | N/A |    |

**Postconditions:**
  * Account should be deactivated.
  * User cannot logged in.


---


## Test ID: #3 ##

**Test Priority:** 8/10

**Test Title:** Verify that user can follow other users

**Description:** Test that user can follow other users

**Module Name:** Following

**Test Designed by:** [Onur Güler](https://code.google.com/p/bounswe2015group1/wiki/OnurGuler)

**Test Designed date:** 29.03.2015

**Test Executed by:** N/A

**Test Execution date:** N/A

**Requirements Tested:** 1.1.6.1


**Preconditions:**
  * To be a registered user.
  * There should be another user to follow in the system

| Step | Test Steps | Test Data | Expected Result | Actual Result | Status | Notes |
|:-----|:-----------|:----------|:----------------|:--------------|:-------|:------|
| 1 | Go to main page  | MainPageURL | See homepage for the user and a menu at the top | -- | -- | -- |
| 2 | Write a name to search bar and search  | text = "Onur Güler" | Search results are seen in another page | -- | -- | -- |
| 3 | Click the "follow" button on one of the results  |  | "Follow" button changes to "Following" | -- | -- | -- |
| 4 | Go to main page again | MainPageURL | Activities of followed user can be seen in Activity Feed | -- | -- | -- |

**Postconditions:**
  * Followed user receive a notification that they are followed
  * System updates database about the following


---


## Test ID: #4 ##

**Test Priority:** 9/10

**Test Title:** Profile Management

**Description:** Test that user can change his/her profile.

**Module Name:** User account settings

**Test Designed by:** [Mert Tiftikci](https://code.google.com/p/bounswe2015group1/wiki/MertTiftikci)

**Test Designed date:** 29.03.2015

**Test Executed by:** N/A

**Test Execution date:** N/A

**Requirements Tested:** 1.1.2


**Preconditions:**
  * To be a registered user.

| Step | Test Steps | Test Data | Expected Result | Actual Result | Status | Notes |
|:-----|:-----------|:----------|:----------------|:--------------|:-------|:------|
| 1 | Go to main page  | MainPageURL | See homepage for the user and a menu at the top | -- | -- | -- |
| 2 | Click on My Profile  | ProfilePageURL | See the profile page which shows menus, meals etc. | -- | -- | -- |
| 3 | Click on edit button  | ProfilePageURL | See a page with editable informations, and add/remove/change at will | -- | -- | -- |
| 4 | Click on save button | ProfilePageURL | See the edited profile page | -- | -- | -- |

**Postconditions:**
  * System updates database related to User.


---


## Test ID: #5 ##

**Test Priority:** 9/10

**Test Title:** Test of Basic Search functionality

**Description:** Test that any user (registered or guest) can search for things using basic search

**Module Name:** Basic Search

**Test Designed by:** [Pınar Sığın](https://code.google.com/p/bounswe2015group1/wiki/PinarSigin)

**Test Designed date:** 03.29.2015

**Test Executed by:** --

**Test Execution date:** --

**Requirements Tested:** 1.1.4


**Preconditions:**
  * Search textbox should be accessible from every page (except possibly from login or register pages)
  * System should be able to select, sort and display results in a descending rating order for queried keywords by users.
  * System should not differentiate between registered or guest users and should work well for these 2 parties.


| Step | Test Steps | Test Data | Expected Result | Actual Result | Status | Notes |
|:-----|:-----------|:----------|:----------------|:--------------|:-------|:------|
| 1 | Any page  | AnyPageURL | See search textbox at the top | -- | -- | -- |
| 2 | Enter keywords in the textbox | BSReturnURL | If the user is registered, see previously searched-for items as hints. Recently searched first. If user is guest, then no search history  | -- | -- | -- |
| 3 | Click on search icon or and press enter | BSReturnURL | Matched recipes, nutritions, meals, recommendations etc. in descending rating order | -- | -- | -- |


**Postconditions:**
  * User's keywords should be logged for more tailored recommendations.


## Test ID: #6 ##
**Test Priority:** 9/10

**Test Title:**  Verify that providers can add new menus

**Description:** Test that a provider can form new menus from previously added products.

**Module Name:** Provider Menu

**Test Designed by:** Mustafa Efendioğlu

**Test Designed date:** 03.29.2015

**Test Executed by:** --

**Test Execution date:** --

**Requirements Tested:** 1.1.3.4

**Preconditions:** To be logged in a provider account, Provider previously added SuperDuperUber Wrap, Ayran, Pommes Frites to recipes or meals.

| Step | Test Steps | Test Data | Expected Result | Actual Result | Status | Notes |
|:-----|:-----------|:----------|:----------------|:--------------|:-------|:------|
| 1 | Go to main page  | MainPageURL | See homepage for the user and a menu at the top | -- | -- | -- |
| 2 | Click on My Profile  | ProfilePageURL | | See the profile page for the provider which shows menus, meals etc. | -- | -- | -- |
| 3 |Click on Add Menu  | AddNewMenuURL | See a page with meals previously added which allows selection of different meals | -- | -- | -- |
| 4 | Select meals, enter a name for menu and click add button | Selections: SuperDuperUber Wrap, Ayran, Pommes Frites  MenuName: SuperDuperUber Menu | See provider page with a message which indicates that menu is succesfully added | -- | -- | -- |

**Postconditions:** Menu should be successfully added.

---

## Test ID: #7 ##

**Test Priority:** 7/10
-
**Test Title:** Rate/Comment a Recipe

**Description:** Test that user can suggest his/her opinion via comment or rating the meal

**Module Name:** Rating and Commenting

**Test Designed by:** Alper Yayıkçı

**Test Designed date:** 29.03.2015

**Test Executed by:** N/A

**Test Execution date:** N/A

**Requirements Tested:** 1.1.6


**Preconditions:**
  * To be a registered user.
  * There should be at least one meal in the system.

| Step | Test Steps | Test Data | Expected Result | Actual Result | Status | Notes |
|:-----|:-----------|:----------|:----------------|:--------------|:-------|:------|
| 1 | Navigate to the page of the recipe | URL | The comments and rate of the recipe are visible | -- | -- | -- |
| 2 | Click the rate button underneath the page  |  | A rating scale is visible | -- | -- | -- |
| 3 | Rate the recipe 7 over 10 | rate = "7" | User's chosen rate can be seen | -- | -- | -- |
| 4 | Click on ok button |  | Recipe's rating is updated on the database | -- | -- | -- |
| 5 | Click on add comment button at the top of the comments section |  | A text box to enter comment is visible | -- | -- | -- |
| 6 | Write comment for the recipe | comment="Awesome recipe!" | User's new comment can be seen in text box | -- | -- | -- |
| 7 | Click on send button |  | The comment is saved to the database and displayed under the recipe | -- | -- | -- |

**Postconditions:**
  * System will show the comment and rate in the meal's page.


---

---

## Test ID: #8 ##

**Test Priority:** 9/10
-
**Test Title:** Add a Recipe

**Description:** Test that user can add a recipe to other users can benefit.

**Module Name:** Add a Recipe

**Test Designed by:** Alp Hekimoğlu

**Test Designed date:** 05.04.2015

**Test Executed by:** N/A

**Test Execution date:** N/A

**Requirements Tested:** 1.1.11

**Preconditions:**
  * User shall be a registered user of system.
  * User shall login the system.

| Step | Test Steps | Test Data | Expected Result | Actual Result | Status | Notes |
|:-----|:-----------|:----------|:----------------|:--------------|:-------|:------|
| 1 | Go to main page | Main page URL | See homepage for the user and a menu at the top | -- | -- | -- |
| 2 | Click on My Profile  | Profile page URL | See profile page and profile informations | -- | -- | -- |
| 3 | Click add a new recipe buttom | Add a recipe page URL | See add recipe page and old recipies which added by user before | -- | -- | -- |
| 4 | User fills name, category, tags and ingredient fields and adds instructions. User clicks add photo/add video button and uploads photo/video of recipe. User clicks save button. | recipe name, category, tag, ingredient, instructions, photo URL, video URL| The recipe is added to the database. User can see the new recipe under my recipes tab and other users can find the recipe using search methods.  | -- | -- | -- |


**Postconditions:**
  * System will show the recipe on the profile of user under added recipes.


---

---