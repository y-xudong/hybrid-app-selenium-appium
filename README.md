# hybrid-app-selenium-appium

This codebase is made to discover how different it is to test a hybrid mobile app using Appium and Selenium. 

## Background

### Hybrid app
  
- Hybrid app refers to the app that some or the most of its content is presented via a WebView, which is essentially a webpage. 
- In this codebase, the demo app is an iOS app written in SwiftUI and UIKit. It only has a `WKWebView` (imagine this as an embedded Safari without the URL bar if you are not familiar with these) covering the whole page
- The hybrid app for this demo locates in `src/test/java/com/thoughtworks/hybridappseleniumappium/appium/Thoughtworks Web.app`

### Test case

1. Selenium
   * Use an iPhone XR sized browser, and navigate to `https://www.thoughtworks.com`
   * Accept optional cookies
   * Click the menu button in the top right corner
   * Search "Technology Radar"
   * Verify the first search result contains string "Technology Radar"

2. Appium
   * Launch an iPhone XR simulator using Appium, and install the hybrid app
   * Accept optional cookies
   * Click the menu button in the top right corner
   * Search "Technology Radar"
   * Verify **the title** of the first search result contains "Technology Radar"

Note:

There is a slight difference in assertion of the two cases. The Selenium one checks if the whole result contains the string, whereas the Appium one only checks the title. The reason is discussed in the Discussion section. 

## Run the test

1. Selenium
   * `./gradlew clean test --tests 'com.thoughtworks.hybridappseleniumappium.selenium.SearchTest'`

2. Appium
   * `./gradlew clean test --tests 'com.thoughtworks.hybridappseleniumappium.appium.IosTest'`

## Discussion

In theory, Appium and Selenium should be easily interoperable as they both implement or extend the WebDriver protocol. This repository evaluates the difficulty to mitigate a pure Selenium test suite to Appium for the web section of the hybrid apps.  

In short, it is not extremely difficult, but it can be time-consuming and rather more fragile and limiting than Selenium. I will elaborate my findings in three points. 

First, let's talk about the ease of the migration. Generally, the code for Selenium and Appium are not structurally different. They both conform to the client server mode (code as the Web Driver client, and there is either a Web Driver made by the developer of the browser or an Appium Server maintained by the Appium project), so the structural changes to be made are minimal. I have only changed the driver type from `WebDriver` to `IOSDriver`, removed `url` from the `AbstractPage` because hybrid app apparently doesn't have URL anymore, and of course the instantiation process and configuration of driver becomes different now as the testing tool has been changed.

Notwithstanding that Appium and Selenium sound similar, there is still a fundamental difference behind the scenes. The Web Driver of Selenium is able to talk to the browser directly in their own language because usually the Web Driver is developed by the browser team officially, whereas Appium Server sits as a proxy and translates automation commands from WebDriver protocol to other mobile native testing tools, and that is XCUITest in our iOS case. XCUITest then manipulates the iOS simulator or real device, and we see the testing behaviour. 

That brings us to the second point: how quickly a Selenium test suite can be successfully migrated to Appium. My discovery shows the time varies depending on the types of selector used in the Selenium test. XPath has a larger chance to migrate faster than CSS selector, as CSS simply doesn't exist in mobile apps; using CSS selector basically means rewriting all selectors. Nonetheless, even if the initial selector is written in XPath, if the usage of the asterisk (such as `//*[]`) is less preferred (it is recommended to avoid since it is unsafe), we will still need to rewrite them because the tag name in HTML does not persist in apps. What's even worse? The structure of the DOM can be different with the app source structure; for example, five `div` under a big `div` container may turn into six mobile elements in the same level. 

Thirdly, for hybrid apps, Appium is evidently more fragile and limiting than Selenium. 

Let's talk about the fragility first. In this demo, the actions for "Search Technology Radar" are not same. The Selenium test just types the keyword and presses the search button. That's all, while the Appium test additionally clicks somewhere else (in the demo, the page title is clicked) to dismiss the virtual keyboard. In the native app, when a view transits to another, unless there is a piece of code specifically to allocate the focus to an input, the keyboard will not show up, but that does not seem to be the behaviour of the Web View. Appium apparently attempted to simplify these limitations by providing APIs to change those states (e.g. [Hide Keyboard](https://appium.io/docs/en/commands/device/keys/hide-keyboard/)). Unfortunately, these workarounds do not always work, especially in the newer iOS versions, as Apple keeps strengthening their control on OS, there have been a number of related issues on the Appium GitHub pages ([example](https://github.com/appium/appium/issues/15073)). Appium is unable to fix them because there is just no way for Appium to support it, and the recommended workaround now becomes clicking somewhere else if that happens, but the problem is that it becomes less predictable when it will happen, and it is rather a distraction than a subject the test should care. 

In terms of the limitation, we mentioned the difference of the test case in the background section. I only verified the title of the first search result in Appium because there is not a simple way to retrieve all text from a big section as Selenium. In Selenium, we can call `webElement.getText()` to get the text inside the HTML tags of the current and all its child levels recursively. We cannot simply do the same thing in Appium because the text will be null if it is nested in the child. That makes sense to some extent because the view model of mobile app is not same as the HTML DOM. Each element in the mobile app is generally independent even though they can be grouped together visually. As a result, if we want to achieve the same capability as `getText()` in Selenium, we will either combine the batch query for elements and loop operations. That grows into heavier code smell as we are introducing complicated code logic in the test, which potentially makes the code harder to maintain and easier to break, thus essentially undermines the value of tests. 

In my opinion, I believe Appium is a handy tool to test native apps. I continue to be skeptical to any attempt to forcibly carry out Appium simply because it's for mobile. If the web element cannot be the first citizen in Appium, I don't believe it should be tested in Appium anyway. 

## Key takeaways

1. It is possible to port a pure Selenium test suite to Appium
2. It is not ideal to use Appium for the Web View part of a hybrid app. Selenium or other Web UI testing tool is a better choice
