# MicroService Hackathon Project

## Overview

In the continuation of this project, you will use the REST API created in Java/Spring Boot week as the core of a finance application, e.g., an application that will manage a portfolio of assets for a single user.

Use Swagger UI to interact with the endpoints.

If/When you have implemented the above basic system, you might take on one or more of the suggested improvements in this document. These improvements are open to your own enhancements whereby you can make use of your particular skills and experience.

At a minimum, you should aim for the following features:

1. A CRUD API, e.g., the Swagger UI + endpoints, should facilitate a single user to:
    * Place an order for a stock
    * Browse the order history and status
    * View the status of each of their historical Orders

2. As before, there will be no authentication, and a single user is assumed, i.e. there is no requirement to manage multiple users.

3. You should use MySQL for any persistent storage.

4. You should aim to use an agile methodology whereby incremental improvements are added so that the application is always in a "working" state.

5. You should design the core technical elements. This document is provided as an outline. However, all data storage, REST APIs etc., should be designed by your team.

6. **Important**: You should develop an application Roadmap.
This is YOUR TEAM'S application, part of the project is to design the application - your presentation should contain a description of a roadmap of features that you would add **if you were to continue work on this**.

What features would be added to this application? What would a fully featured UI look like?

You don't need to implement these features. This is a roadmap for what you would implement IF you were given more resources, time & staff for this project.

You can bring in your expertise from other fields - some examples might be:

Any ML features or components that you would consider?
Any web services or cloud services that this system could interface with.
Any additional or interesting UI features?
Integrations with other applications.
Security concerns.

## Suggested Schedule

Your team needs to allocate people to work on each area of this project. One of the keys to success is to ensure the team efficiently divides the work.

The entire system is not expected to be complete until the final day.

**The first step** should be to come together as a team and decide on the core vision for what your application should do. When you have decided on this, or if you have questions on this, you should then schedule a meeting with your instructor to give guidance before you proceed too far down a particular path.

### System Architecture
It is up to you and your team to decide on an overall architecture for the system. However, you should aim for a microservice-style architecture with your components deployed on Openshift.

The following architecture is a **possible** architecture for the core components. In this architecture, the "Dummy Trade Fulfillment Service will be provided to you IF you want to include it in your architecture. However, again bear in mind that this is **your design**. This example is just to help you with ideas.

![Trade Project Diagram](https://www.benivade.com/neueda-training/Tech2020/CoreTradeProject2020.png)

1. Trade REST API: Written in Java as a spring boot REST application. It will accept HTTP REST requests to Create & Retrieve Order records from the TradeDB. Update and Delete of Trade records should be subject to sensible business considerations. You should consider unit tests, sensible JavaDoc and use parameters in your application.properties file(s). 

2. TradeDB: Data stored in a MySQL database.

3. Dummy Order Fulfilment Service: A sample source code for this component can be provided. This **simulates a system that would send your orders to a stock exchange**. You could write this or something more suited to your application yourself, or you can use the simple version provided.
    - The example order filling simulator is here: https://bitbucket.org/fcallaly/dummy-order-filler
    - A pre-built docker image is available on dockerhub ```callalyf/dummy-order-filler:0.0.1```

4. Web Frontend: This is a web UI written in Javascript, Angular, or any other front-end framework you are experienced with (check with your instructor if using something else). This UI would allow users to browse their Trading history and request for Trades to be made by sending HTTP requests to the Trade REST API.

Note on terminology and limitations: For the purposes of this exercise, there are limitations to the functionality. The terms Order and Trade are largely interchangeable, though, in the real world, they are different things. An order is an "intention to trade", e.g. I'd like to buy 1000 Tesla shares, or I'd like to sell 2500 Apple shares, while a trade is the confirmation "you bought 200 Tesla shares @ 500USD and 372 Tesla shares @501 USD, etc. As such, in a real scenario, a single order may result in many smaller separate trades at a range of prices. For this exercise, all orders are considered as "immediate" or "Market" orders and will fill completely or not at all, and they will always fill at the current market price. There is no concept of "limit" orders, and no orders will "rest" in the market.


### Teamwork
It is expected that you work closely as a team during this project.

Your team should be self-organising but should raise issues with instructors if they are potential blockers to progress. 

Your team should use a task management system such as Trello to keep track of tasks and progress. You should include details of this in your final presentation.

Your team should keep track of all source code with git and bitbucket. You should include some details of this in your final presentation.

It would help if you created a separate bitbucket repository for each component you tackle, e.g. front-end code can be in its repository. If you create more than one spring-boot application, each can have its bitbucket repository. To keep track of your repositories, you can use a single bitbucket 'Project' that each of your repositories is part of.

Your instructor and team members need to access all repositories, so they should be either A) made public OR B) shared with your instructor and all team members.

You should ensure good communication and organise regular check-ins with each other and your instructor throughout your work.


### Appendix A: UI Ideas

The screen below might give you some ideas about the type of UI that could be useful. You are NOT expected to implement the screen below precisely as it is shown. This is JUST FOR DEMONSTRATION of the type of thing that COULD be shown.

![Demonstration Portfolio UI](https://www.benivade.com/neueda-training/Tech2020/DemoPortfolioScreen.png)


### Appendix B: Some Useful Services

There are some services written in Python that are running as lambdas on our training account. The source code is in the training AWS Account under the "Lambda Service" in N.Virginia.
Some of these may be useful depending on the features you choose to put in your system. You may use these directly, or if you want, we can provide the source code, and you can edit it to meet your needs.

1. Historical Daily Close Prices

    * Gives a number of days of price data from yahoo finance using pandas data reader. It will provide the Close price for a ticker only for each day.

    * This reads real stock price data from Yahoo Finance. The endpoint takes two query parameters:
        - ticker: A valid stock ticker string
        - num_days: the number of days for which you want price data

    * [Endpoint URL](https://3p7zu95yg3.execute-api.us-east-1.amazonaws.com/default/priceFeed2?ticker=TSLA&num_days=10)

    * This reads the latest list of stock tickers from yahoo finance, i.e. this is the list of tickers for which you can use the SimplePriceFeed URL
    
    * [Endpoint URL](https://v588nmxc10.execute-api.us-east-1.amazonaws.com/default/tickerList)
    
2. Historical Price Data at 5min Intervals
    * Gives available historical price data at 5 min increments from now. The amount of data returned will depend on the time of day and what's available:
        - ticker: A stock ticker string. Limited tickers are available, check with your instructor for the list or to request further tickers.

    * [Endpoint URL](https://c4rm9elh30.execute-api.us-east-1.amazonaws.com/default/cachedPriceData?ticker=AMZN)
    
    * This is built with the API at https://rapidapi.com. You can sign up to that service if you want to read further data from yahoo finance.

3. TradeAdvisor: Uses a very simple implementation of the [bollinger bands strategy](https://www.investopedia.com/trading/using-bollinger-bands-to-gauge-trends/) to give advice on a given ticker.

    * Takes a single parameter `ticker`: a valid stock ticker
    
    * [Endpoint URL](https://qz4sxjl623.execute-api.us-east-1.amazonaws.com/default/tradeAdvisor?ticker=NFLX)