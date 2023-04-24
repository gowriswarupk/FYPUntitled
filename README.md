# SPYDER App - Mobile App Component for Spyder Project

Name: Gowriswarup Kailas Perumal

20087165

## Overview

A mobile app (Kotlin) which works as a user interface for the Spyder project for carrying out tasks on the Raspberry Pi component and notifying user.

Please checkout the component [Spyder-rpi repo here](https://github.com/gowriswarupk/spyder-rpi) for the complete project file.


### Features:

- [x]  Walkthrough Onboarding Flow
- [x]  Login, Registration & Welcome Screens
- [x]  Firebase Auth integration
- [x]  Firebase DB integration
- [x]  Firebase Cloud Messaging integration
- [x]  Firebase Auth integration
- [x]  Navigation Drawer
- [x]  Home Screen
- [x]  Login with Google
- [x]  Save Password
- [x]  Logout


Error Handling and notifications for Cloud Storage as well as error messages, along with CRUD Functionalities.

## Setup requirements:

To run the app locally, clone the repo thus:

```
    git clone git@github.com:gowriswarupk/spyderApp.git
```
Open in your preferred IDE, Android Studio used for production.


## Setup Config files.
Required for optimal runtime:

To add firebase config, on the menu, go to Tools > Firebase to open Assistant and follow prompts.


<img width="255" alt="Firebasescreen1" src="https://user-images.githubusercontent.com/58232821/209458919-54f4edd1-3c9b-4aed-8c72-2528ba0d2906.png">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <img width="550" alt="Firebasescreen2" src="https://user-images.githubusercontent.com/58232821/209458923-05d45720-3c55-4216-b45a-3d37c8f11250.png">

Firebase official Documentation

Realtime Database config: https://firebase.google.com/docs/database/android/start?hl=en&authuser=0

Google Auth : https://firebase.google.com/docs/auth/android/google-signin?hl=en&authuser=0


* Download google-service.json from your Firebase Console
* Place google-service.json file in the app/ folder
* Build & Run the app in Android Studio


### UI Design.

#### Onboarding Pages

<img width="315" alt="Onboarding1" src="https://user-images.githubusercontent.com/58232821/233780860-e8b0817f-e3de-444d-97c5-702cf30d7a40.png"><img width="336" alt="Onboarding2" src="https://user-images.githubusercontent.com/58232821/233780859-7a76bc07-cc7d-4c01-9408-aa36bb0df18e.png"><img width="332" alt="Onboarding3" src="https://user-images.githubusercontent.com/58232821/233780858-d990d438-0552-4ba7-aeb8-6dbaac4da15e.png"><img width="335" alt="Onboarding4" src="https://user-images.githubusercontent.com/58232821/233780857-8648a2fe-806a-4b60-a327-d93637c92fe9.png"><img width="335" alt="Onboarding5" src="https://user-images.githubusercontent.com/58232821/233780856-bf94e643-9762-493a-a983-a38e088bc7a2.png">

#### Auth, Login, Signup Pages

<img width="335" alt="AuthPage" src="https://user-images.githubusercontent.com/58232821/233877148-9a2f6829-0749-485a-9712-69e1ab54c325.png"><img width="335" alt="Login" src="https://user-images.githubusercontent.com/58232821/233780895-01fd4636-5ad1-45bd-a77b-f79766cc839f.png"><img width="335" alt="SignUp" src="https://user-images.githubusercontent.com/58232821/233780896-3a28b7dc-ce9a-4c19-84af-8e7afbc3df3b.png">

#### Home Page

<img width="335" alt="homepage" src="https://user-images.githubusercontent.com/58232821/233780932-65504ad3-7835-4de7-9cf2-a575d8d93b82.png">


### Logo/ App icon
<img width="200" alt="icon" src="https://user-images.githubusercontent.com/58232821/233780996-6d0abe5c-9b7b-46ea-94be-9588e2f43341.png">
--- Logo Design using Name.com Student pack




<br>

## Attributions
<br>

Instakotlin open-source template

Icons provided by icons8.com:   
    <a target="_blank" href="https://icons8.com/icon/Tn1voGNhzHsN/raspberry-pi-zero">Raspberry Pi Zero</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>  
    <a target="_blank" href="https://icons8.com/icon/62856/github">GitHub</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>   
    <a target="_blank" href="https://icons8.com/icon/rl0sIZNchpNB/cyber-security">Cyber Security</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>    
    <a target="_blank" href="https://icons8.com/icon/112272/stellar">Stellar</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>    
    <a target="_blank" href="https://icons8.com/icon/YYSGl-eTrZm8/cloud-firewall">Cloud Firewall</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
    <a target="_blank" href="https://icons8.com/icon/528elqVNqOrX/approved-delivery">Approved Delivery</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
<br>

## Troubleshooting

* __Firebase Auth Permissions__ - When configuring the Firebase console to assist with Authentication, if any errors arise, it will be displayed to the user at the Log-in page.    
Most likely causes for this error might be related to improper SHAsum implementations while creating the google-services.json file. Ensure that this is the same as the app gradle signingReport output in the Android Studio IDE while modifying the application.  
Additional errors might be due to improper configuration within the Authentication (Sign-in method) and Cloud Firestore pages within the Firebase console. 

* __Firebase Access Controls__ - If the user-specific permission set is enabled, ensure that the rules are modified accordingly to avoid auth issues. 

