# fitness_tracker_servlet_maven

This is the current core of the fitness_tracker website. There are currently 3 packages:

CORE:
This package contains the core classes that make up the current functionality of the site, it deals with the following items

Authentication,Authorization,Creating new accounts,Database access (when its finished),Storing global values,
Storing the clients API,Session management,Misc objects

WEBPAGESERVLETS:
This package contains the actual servlets that will process web pages for the clients, it is rather spartan at the moment but
contain two skeleton placeholder servlets that deliver a main page and a session test page (to make sure sessions work) to the client.

UNUSED:
This package contains classes that were removed from the other packages so they are never called and dont actually do anything. They are useful for a reference though and as a reminder of why X is a bad idea etc, they tell a story of sorts.
Most classes were removed because of one of the follwing reasons 

A better method of doing something was found (usually a built into Java method),They were no longer needed,
Refactoring made them obsolete,It was a bad idea
