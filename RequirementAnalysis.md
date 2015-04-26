# Requirement Analysis #



## Summary ##

“We are what we eat” project is a nutritional assistant and it increases awareness by providing information about nutritional values and some other properties of the food in a food-social-network.

## Background ##

This project is a web and android client awareness tool that aims to help its users to know more about what they eat. It is also a nutritional assistant in the sense that it helps users to see the nutritional values of various meals and products and facilitates their decision of what to eat according to their needs, wants and some physical/medical situations (allergy, diet etc.). The consumer should specify these conditions.This is basically the consumer perspective. This project also considers the eating habits from producers perspective and helps them to improve their food by feedbacks from consumers. This project also considers eating as a social activity and helps its users to share the recipes and their food-centric responses about the meals, ingredients, and locations with their food social network (In this network, consumers can follow other consumers or producers) and with the producers and also get location and similar-taste-based recommendations from the system.

## 1. Functional Requirements ##
> ### 1.1 User Requirements ###
> > #### 1.1.1 Sign-up ####
      * 1.1.1.1 Users shall provide valid user name, email and password.
      * 1.1.1.2 Users shall receive an activation e-mail upon registration.
      * 1.1.1.3 Users shall only be allowed to create content after activating their accounts via the activation e-mail.
> > #### 1.1.2 Profile Management ####
      * 1.1.2.1 Users and providers shall be able to upload profile photo.
      * 1.1.2.2 Users shall be able to provide personal information such as full name, age and location.
      * 1.1.2.3 Users should be able to declare their food intolerances, health issues, food preferences and their diets.
      * 1.1.2.4 Food intolerance options shall include allergy, gluten intolerance, and lactose intolerance...
      * 1.1.2.5 Health issue options shall include diabetes, heart disease, cholesterol, and blood pressure...
      * 1.1.2.6 Food preferences shall include food and/or ingredients they do not like or like specifically.
      * 1.1.2.7 User should be able to deactivate his/her account.
> > #### 1.1.3 Recipes ####
      * 1.1.3.1 Users and providers shall be able to specify recipes.
      * 1.1.3.2 Recipes should be able to have semantic tags.
      * 1.1.3.3 Recipes should be able to contain photos of end product or of special treatments([Julienning](http://en.wikipedia.org/wiki/Julienning)).
      * 1.1.3.4 Providers shall be able to add daily menus for their existing recipes.
> > #### 1.1.4 Search ####
      * 1.1.4.1 By default, search shall consider user preferences and gives result according to them unless otherwise specifically pointed out:
        * 1.1.4.1.1 Results that contradict given health conditions and food intolerances will not be shown.
        * 1.1.4.1.2 Results that contradict given food preferences and given diet will be shown in lower positions.
        * 1.1.4.1.3 Results that have higher rate and users favorites according to previous actions will be shown in higher positions.
        * 1.1.4.1.4 Results that esteemed to be harmful will be shown with warnings on it.
> > #### 1.1.5 Advanced (Semantic) Search ####
      * 1.1.5.1 Users shall be able to search recipes via the advanced search feature
      * 1.1.5.2 The advanced search results shall be filterable by contents, semantic tags and location
      * 1.1.5.3 The results shall include recipes, ingredients and restaurants that are semantically related to the search query via semantic tagging.
      * 1.1.5.4 The results shall be ordered by semantic relevance, previous ratings by the user and location.
> > #### 1.1.6 Social Network ####
      * 1.1.6.1 Users shall be able to follow other users and also providers.
      * 1.1.6.2 Users and providers shall be able to see the information about other profiles.
      * 1.1.6.3 Users shall be able to comment and/or like any recipes and like providers.
      * 1.1.6.4 Users shall be able to see the average ratings of recipes and menus.
      * 1.1.6.5 Users and providers should be able to add daily, weekly or monthly diet programs that suggests meals.
      * 1.1.6.6 Users shall get notifications of the actions of the people they follow.
> > #### 1.1.7 Guest Access ####
      * 1.1.7.1 Both application and web interface shall be open for guests.
      * 1.1.7.2 Guests shall be able to search and examine recipes and public profiles but not interact with them.
      * 1.1.7.3 Guest should not be able to use the advanced search system.

> ### 1.2 System Requirements ###
> > #### 1.2.1 Accounting ####
      * 1.2.1.1 Users and providers shall be able to change their passwords and emails.
      * 1.2.1.2 Users and providers shall be able to change their profile photo and info.
> > #### 1.2.2 Recipes ####
      * 1.2.2.1 System shall provide the information about ingredients nutrition values, also recipes and add this information as a tag to given recipes.
      * 1.2.2.2 System should treat recipes according to their rates, likes, nutrition values and user view counts, also user location and profile analysis to have better recommendations and search results.
> > #### 1.2.3 Abusive Usage ####
      * 1.2.3.1 Users and providers shall be able to report the system about abusive users and providers.
      * 1.2.3.2 If abusive action is detected, related user/s and/or provider/s shall be banned.
## 2. Non-Functional Requirements ##

> ### 2.1 Accessibility ###
    * 2.1.1 Default language should be English to have worldwide scope.
    * 2.1.2 The web site should be easily accessible for sight-impaired people using screen readers.
    * 2.1.3 The color scheme of the website should not cause major issues for color blind people.
> ### 2.2 Security ###
    * 2.2.1 System should be backed up daily.
    * 2.2.2 The daily backups should be kept for at least a week.
    * 2.2.3 Privacy of users and providers should be ensured from hackers.
    * 2.2.4 User passwords shall be stored hashed on the database.
> ### 2.3 Efficiency ###
    * 2.3.1 Average response time of both system and mobile application should be lower than 1 second (not including network latency).
> ### 2.4 Flexibility ###
    * 2.4.1 Web site and mobile application should be configurable to be able to react fast to customer's requests.
> ### 2.5 Ease of use ###
    * 2.5.1 Web site and mobile application should be natural in navigation and usage, so that even a first user could do any requested task.
> ### 2.6 Maintainability ###
    * 2.6.1 The system should give customers immediate access to the maintenance updates—both small changes and critical upgrades should be able to be installed immediately.

<a href='Hidden comment: 
Dictionary part completely taken from "2014group1" so it is important to control and internalize this part...
'></a>

## Dictionary ##

**Allergy:** A damaging immune response by the body to a substance, especially a particular food, pollen, fur, or dust, to which it has become hypersensitive.

**Ban:** To forbid a user to access the system. Banning could be temporary or permanent.

**Cholesterol:** A compound of the sterol type found in most body tissues. Cholesterol and its derivatives are important constituents of cell membranes and precursors of other steroid compounds, but high concentrations in the blood are thought to promote atherosclerosis.

**Consumer:** A person that uses the application for finding/providing recipes/providers according to his/her food preferences.

**Diabetes:** A group of metabolic diseases in which there are high blood sugar levels over a prolonged period.

**Diet:** A person who does not eat meat for health or religious reasons or because they want to avoid being cruel to animals.

**Food Intolerance:** A detrimental reaction, often delayed, to a food, beverage, food additive, or compound found in foods that produces symptoms in one or more body organs and systems, but it is not a true food allergy.

**Food Preferences:** Indicates how a person selects the food he/she eats.

**Gluten:** A protein composite found in wheat and related grains, including barley and rye.

**Guest:** A user without profile.

**Ingredient:** A food that is used with other foods in the preparation of a particular dish.

**Interaction:** Commenting, liking, or bookmarking any post.

**Lactose:** A disaccharide sugar derived from galactose and glucose that is found in milk.

**Nutrition:** The substances that you take into your body as food and the way that they influence your health.

**Nutritional Value:** Defines what a food is made of and it's impact on the body.

**Provider:** A business that uses the application for advertising its own product. It regularly provides recipes and menus to other users.

**Recipe:** A set of instructions telling you how to prepare and cook food, including a list of what food is needed for this.

**Similar-Taste-Based Recommendations:** Any suggested provider or recipe to a user according to his/her food preferences.

**User:** A person or a business that uses the application.