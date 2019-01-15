# MachineLearningFramework
machine learning framework originally created for SYSC3110 term project.

---------------------------------------------------------------------------------
THE REMAINDER OF THIS DOCUMENT CONTAINS THE ORIGINAL README FOR THE TERM PROJECT
---------------------------------------------------------------------------------

***run runMe.java to run the non GUI code and see some examples run
***run Controller.java to run the GUI 

This project contains a simple machine learning framework. This program's purpose is to estimate values for new data points, 
given old data that the user has inputted via a non-graphical UI.

running runMe.java will run both the house pricing example and the soccer example that were given in class, once with each point distance function we have provided to show difference in predictions.


Team Members of Original Project
-----------------------------------------------------------------------------------------------------------------------------------

Luke Newton

Madelyn Krasnay

Cameron Rushton


Known bugs:
-----------------------------------------------------------------------------------------------------------------------------------

-selecting "cancel" on the first pop-up window for creating a new problem throws a nullPointerException (because you try to create a Problem with number of features equal to null)

-attempting to edit a training/test example when the appropriate list is empty throws a nullPointerException

-there is no checking to make sure that elements types are consistent among all examples, ClassCastExceptions will occur if types are not equal

-panels are not scrollable, so while you can specify that you want 100+ fields when you create a new problem set, the components will be too small to be useful

-the user can select add/remove/edit training/test examples before creating a problem. this will cause excptions to be thrown.
