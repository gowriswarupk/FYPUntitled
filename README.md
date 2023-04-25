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


<img width="255" alt="Firebasescreen1" src="https://user-images.githubusercontent.com/58232821/209458919-54f4edd1-3c9b-4aed-8c72-2528ba0d2906.png">&nbsp; &nbsp; &nbsp; <img width="550" alt="Firebasescreen2" src="https://user-images.githubusercontent.com/58232821/209458923-05d45720-3c55-4216-b45a-3d37c8f11250.png">

Firebase official Documentation

Realtime Database config: https://firebase.google.com/docs/database/android/start?hl=en&authuser=0

Google Auth : https://firebase.google.com/docs/auth/android/google-signin?hl=en&authuser=0


* Download google-service.json from your Firebase Console
* Place google-service.json file in the app/ folder
* Build & Run the app in Android Studio


### UI Design.

#### Onboarding Pages

<img width="315" alt="Onboarding1" src="https://user-images.githubusercontent.com/58232821/233893968-97e14444-ccff-4ef2-863a-0dbb78cade73.png">&nbsp; &nbsp; &nbsp; <img width="336" alt="Onboarding2" src="https://user-images.githubusercontent.com/58232821/233893969-00b0da5a-af06-4bcc-91ed-624b6e49ee1c.png">&nbsp; &nbsp; &nbsp; <img width="332" alt="Onboarding3" src="https://user-images.githubusercontent.com/58232821/233893971-63d3c74c-cca7-4bb3-b011-06d678d262c8.png">&nbsp; &nbsp; &nbsp; <img width="335" alt="Onboarding4" src="https://user-images.githubusercontent.com/58232821/233893972-6428b8fa-fc4c-488e-bd7c-cb37b9186226.png">&nbsp; &nbsp; &nbsp; <img width="335" alt="Onboarding5" src="https://user-images.githubusercontent.com/58232821/233893973-ffe6a8bd-3366-4213-af91-0b054149d0c7.png">

#### Auth, Login, Signup Pages

<img width="335" alt="AuthPage" src="https://user-images.githubusercontent.com/58232821/233894133-f9f30b42-987f-4bd4-854b-facb75df7181.png">&nbsp; &nbsp; &nbsp; <img width="335" alt="Login" src="https://user-images.githubusercontent.com/58232821/233894134-28e9622e-baea-40a1-bcf4-ca05cd8bd503.png">&nbsp; &nbsp; &nbsp; <img width="335" alt="SignUp" src="https://user-images.githubusercontent.com/58232821/233894137-1a45a94e-8d76-4e97-9279-7534e4a41c09.png">&nbsp; &nbsp; &nbsp; <img width="335" alt="google-auth-login" src="https://user-images.githubusercontent.com/58232821/233894335-e43b721d-0230-4a57-8517-2b8a012f8040.png">


#### Home Page

<img width="335" alt="homepage" src="https://user-images.githubusercontent.com/58232821/233894226-fb5ee97e-561e-4bcc-a46e-516961d4e560.png">&nbsp; &nbsp; &nbsp; <img width="335" alt="app-drawer" src="https://user-images.githubusercontent.com/58232821/233894225-8619e65b-a2c9-43b3-951a-9de12e918bf9.png">


### Logo/ App icon
<img width="200" alt="icon" src="https://user-images.githubusercontent.com/58232821/233780996-6d0abe5c-9b7b-46ea-94be-9588e2f43341.png">
--- Logo Design using Name.com Student pack




<br>

## Attributions
<br>

Instakotlin open-source template

[bertrandmartel/speed-test-lib | JSpeedTest | MIT License](https://github.com/bertrandmartel/speed-test-lib/blob/master/LICENSE.md)

[SpeedTest by OpenSpeedTest™ - Free and Open-Source HTML5 Network Speed Test Software](https://openspeedtest.com/)

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

