SF Movies APP
============

Link to hosted Application : http://sfmoviesapp.elasticbeanstalk.com/
Link to hosted repository: https://github.com/amgrover/sfmovies

The project I choose was Sf Movies App

The technical stack I chose was full stack.

Architecture
============
The architecture that I followed was MVC architecture, I used JSON as a communication medium between UI and server. The idea was to build an in memory trie based data structure that can provide autocomplete service. The reason I chose in memory was because I had a limited data set and I wanted  faster response time. I used Java language to write server side code and used external library to read data from csv and chose JAX-RS as my web service library. The endpoints exposed were RESTful endpoints. The controller layer is completely isolated from dao and service layer. Unit tests have been written for service layer.
 
On the UI side I chose Google maps API and JQuery as a javascript library. For autocomplete user have to enter minimum 3 characters to give user a suggestion. As soon as user enters a character in the search bar a restful call is made to the server. The server then respond with the list of movies and their location that matches the prefix. A movie will have many locations. Every location is sent to google maps API for geocoding. Once lat long is received for all the locations markers are placed on the map. 

Future Work
============

1.Since I could only spend 5 hrs on this exercise, I did not discover new technologies. I went with the technology stack that I was comfortable with. If given time i would have discovered new technologies.

2.The second thing that i wanted to do was to rank the autocomplete list according to the users rating of the movies.

3.Make it more than just finding locations. There is a lot of information about movies apart from location and name. I do process them on server side but i could not do much on UI. So this information can be used to give user more information like actors, director, producer etc.

4.If we have more data then in memory trie is not a good option. We can make a distributed trie or we can serialize it in the database;

