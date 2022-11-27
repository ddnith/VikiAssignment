# VikiAssignment

- What code architecture did you choose and why
I chose MVVM for implementation over MVC, MVP or MVI
MVC is oldest patter, it ends up creating all thee UI and logic inside activity which 
makes it hard to test the code.
MVP is testable but it has tight coupling with Android components. 
A lot of boiler plate code is also needed to 
define relationship between presenter and view and presenter and model.
MVVM is unit test friendly and also view model in MVVM only exposes data to view. 
It has no tight coupling with view. Only view has reference to view model.
Some other advantages of mvvm are View Model can survive lifecycle and configuration changes of the activity and
Multiple activities / fragments can use the same View Model simultaneously
MVI could have been also a good choice as it renders UI from a single source of truth state but it also suffer 
from lifecycle problem so I ended up picking the MVVM.



- Further improvements to the code, if any
A few improvement can be
Selecting currency from dropdown could a bit difficult and there can be a search option to make it easier.
I am using Resource class to represent data state, I could update it to contain loading state as well 
or create a new sealed class inside view model to represent different UI states.
A few more test cases to properly cover all test cases.

- Feedback about the assignment, if any
Uploading code on Github would be better as compared to sending it as zip file.

Libraries used
Retrofit wih Okhttp for networking
Widely accepted networking library for android. 
It supports automatic parsing of responses, to their respective data types.

Coroutines for async calls - makes it easier to handle async events.
Junit, Mockito for unit test cases.
Koin for dependency injection - Mainly because it's simple and build time is better.

Some decision points
I used Live data for observing data from view to View model. I could have used some other reactive patters such as
Rx or Kotlin Flow but Live data comes with automatic support of lifecycle awareness so picked it up for the task.


