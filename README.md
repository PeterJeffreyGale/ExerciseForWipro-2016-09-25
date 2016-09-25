Test Automation exercise for Wipro Digital

### Building and Executing the Solution

This solution requires Maven & a JDK to be installed and can be built and run with the command:

...>mvn verify

A batch file is provided which can be edited to set the Maven and JDK paths where these are not set in the local PC environment:

...>RunMavenWithTests.bat

The test results can be found at: ...\target\surefire-reports

Alternatively the tests can be run within the IntelliJ IDE, for example.

### Approach

While I am not a very experienced user with Cucumber, I wanted to use this tool as the bases for this exercise, to demonstrate at least a basic level of competence.

* A POM (Page Object Model) approach has been taken, even though the application is a single page application (SPA) - the page objects represent logical sets of functionality.

* I prefer not to use the full PageFactory class available from the Selenium project,
  as I find it is not always suitable for working with dynamic element locators,
  which results in needing multiple locate strategies ("@FindBy" and individual 'getElement()' methods)

* I prefer to use specific element getters in all cases where necessary, which makes for more consistency/simplicity (a single approach in all cases).

* While many choose to use ID's for locating elements where they are available,
  as these are deemed to be fasted and least likely to change and cause test failures,
  I feel that they are an implementation choice, not part of the actual application design, and not something real users are aware of.
  So I prefer [dynamic] xpaths which are more flexible and can implicitly test something design aspect of the Design/UI which the user actively relies on interact with the page.
  This approach may cause more test failures, but those test will mostly be due to [planned or unplanned] UI changes which the ID approach will not have flagged.
  When such xpath are initially selected, or where subsequent failures occur related to them, a conversation can be had with the designers/developers as to what level of inbuilt test may be appropriate.
  In practice, I am happy to adopt the ID approach where the other UI aspects are seen to be adequately covered in other tests.

* I have restricted my page object to using Domain Specific Language (DSL), and making no direct references to the underlying automation tool (Selenium).
  In this way, the automation tool could potentially be quickly replace if a better choice came along or if it needed upgrading, without having to refactor the actual tests implemented.
  Also, this supports the Cucumber/Gherkin style approach of letting non-technical people get involved in writing automated tests in greater detail

* All Selenium based automation code is wrapped in a single "WebBrowser" object so that  simple improvements can be made and applied universally
  (e.g. always putting a wait for elements before executing 'clicks' or other actions on them.)

* I have adopted the approach of executing all tests through a "UsesSession" object.
  This leaves potential for simulating tests where [test] users have multiple user interfaces open at the same time, e.g. front-end web browsers and back-end databases,
  or where there are multiple users/actors involved in a test (e.g. a Customer and a Customer Services agent) where they each have an independent separate web browser session open) at the same time.
  Though feature has not been absolutely necessary here, but makes the setup more readily extensible if it became necessary.

### Desirable Improvements To The Testing

If I had more time, I would like to have:

* Investigate why the browser opens multiple times, and reduce the need for this (browser closed after each scanario rather than all?)

* Facilitate switching browser type for cross-browser testing

* Implementation of Cucumber's own html base test reports?

* Better reporting and execution logging in the "WebBrowser" object for additional test debugging as necessary.

* Tests for the suggested applications improvements listed below

* Extra negative tests to confirm no forecast details shown when an invalid city name entered

* Find a regular expression to match on integers only (for 'number of days forecast' step definition, etc)

* Better handling for the check on the days of the week that are displayed - use an array and loop through the list, rather than hardcoding multiple assertions

* Understand how to split out the checks on each day for each city as separate lines in Cucumber feature files (i.e. not hide them as nested checks)

* Check that the forecast entries are in 3 hourly intervals

* Better extraction/manipulation of dates from json data (not just by substring)

* Change assertions to have show a description where they are not the only assertion in a step definition

* There is very limited edge case testing that can be done with just a single input and predefined data sets,
  but I presume it would be possible to run the apl locally and edit the JSON data files for each city,
  or to make edited copies of them simulated more complex/edge case data scenarios
  that would test the handling of such data by the application itsel,
  but that would consume much more time than is available for this exercise

* Extract JSON data methods to a separate utility class

* Error handling? e.g. for reading data from files

* Check that expanding/contract in row data does make the rows visible/hidden

* Expand/contract the forecast days only once each (not once per forecast time in the data file)

* Create/Modify some XPath methods to use relative xpaths from a previously found element, rather than always from the DOM root element

* Finish testing forecast data for each individual row

* Investigate and fix intermittent assertion failures (need to add some 'wait for Angular' calls?

* Testing summary forecast data for each day based

* Check all "aria" labels are defined where necessary and that these work with, e.g. windows narrator

### Suggested Improvements To The Application

* It isn't very clear that you have to click on [default] city name to change it - perhaps it is better for it not to defaul tto any particular city?

* The screen only seems to update after pressing the enter key after entering a city name, any other keys (such as <TAB>) are ignored ...
  ... which may be confusing - the user may not realise that the screen hasn't been updated

* The city name input box allows whitespace around an otherwise valid city name, making it invalid, which looks confusing since the whitespace is not apparent from the input box.
  Either a frame of some sort around the input box, or trimming any leading/trailing whitespace input might help

* The times of day for forecasts in the file are not shown for any particular timezone, and the ones displayed on screen show as an hour different for apparent reason
  though I assume it it for BST/GMT+1 ... the timezones need to be encoded in the data and onscreen if they are going to be shown differently
