# EasyCloset

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)

## Overview
### Description
A closet App that recommeds clothing items to wear based on the weather in the users location

### App Evaluation
- **Category:** lifestyle / health
- **Mobile:** This app would be primarily developed for mobile but would perhaps be just as viable on a computer, however mobile version could potentially have more features.
- **Story:** Analyzes the current weather and the clothes in the user closet and then suggest what peice of clothing to wear and items to carry.
- **Market:** Any individual could choose to use this app.
- **Habit:** This app could be used as often especially if the individual is working.
- **Scope:** First we would let the user input the clothing items he/she owns and then we use a weather api with that geolocation to suggest a clothing items based on the weather.
## Product Spec
### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User log-in 
* User picks what clothing item they own
* Register screen
* Home page which shows users recommedation based on the weather
* Closet page to add and remove items
* profile page to set push notifications and temperature units

**Optional Nice-to-have Stories**

* add items via photo
* update user on least frequently worn clothes and suggest donations
* One click Share "outfit of the day" to social accounts


### 2. Screen Archetypes

* Login 
* Register - User signs up or logs into their account
   * Upon Download/Reopening of the application, the user is prompted to log in to gain access to their profile information to be properly matched with another person. 
   * ...
* Home Screen - Shows weather update and suggestion for that day
   * the hoome shows the user weather updates as well as clothing suggestions 
* Closet Screen 
   * Allows user to view clothing items they own as well as add and remove some items
* Profile Screen.
   * Allows users to connect the app to social media account, set push notifications for specific times and change language.

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home Screen
* Closet screen
* Profile
 
Optional:
* Image view for suggestions

**Flow Navigation** (Screen to Screen)
* Forced Log-in -> Account creation if no log in is available -> selects items user owns 
* Home screen -> Closet screen
* Profile -> 
 

## Wireframes
![](https://i.imgur.com/Xia9bQB.jpg)


## Schema 
### Models

#### User

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user post (default field) |
   | Username      | String   | the users username for easy sign in |
   | Password      | String   | the users password |
   | image         | File     | image for user profile image |
   | gender        | String   | the users gender |
   | Zip code      | Number   | the users zip code to get location |
   | Email         | String   | the users email for registration |
   | createdAt     | DateTime | date the account is created (default field) |
   | updatedAt     | DateTime | date the account was last updated (default field) |
   
   
   #### Items

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | objectId      | String   | unique id for the user item (default field) |
   | name          | String   | the name of the item |
   | image         | File     | image for the clothing |
   | User          | Pointer   | the owner of the item |
   | Color         | String   | the colorof that item |
   | createdAt     | DateTime | date the item is created (default field) |
   | updatedAt     | DateTime | date the item was last updated (default field) |
### Networking
#### List of network requests by screen

#### [OPTIONAL:] Existing API Endpoints
##### OpenWeatherApi
- Base URL - [https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}

   HTTP Verb | Endpoint | Description
   ----------|----------|------------
    `GET`    | /pressure | get current weather for that location
    `GET`    | /windSpeed| return specific character by name
    `GET`    | /main   | get the weather
    `GET`    | /icon | return weather icon
     `GET`   | /description   | get the weather description
    `GET`    | /humidity | return the humidity
     `GET`   | /feels_like | return what the weather feels like 
     `GET`   | /speed | return the wind speed
    
##### unsplash API
- Base URL - [https://source.unsplash.com/1600x900/?+{city}"]
- https://unsplash.com/documentation
- use this api to display images of landmark from the users location


