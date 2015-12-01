# NearFind
A simple Android App that lets the user to search for different kinds of places around him using his location and Google Places API.

#App Flow
1. User has to select a type of place from the prompted Autocomplete Textfield and click OK.
2. Relevant places will be shown in Listview.
3. User can click a place and check its Details like contact no. etc.

#Features
1. Network check is implemented and prompts the user in the beginning if not connected to a network. You can check its implementation in 
app/src/main/java/com/example/anilkumar/askmeanything/ConnectionDetector.java
2. GPS location check is implemented and prompts the user in the beginning if location is not enabled. 
