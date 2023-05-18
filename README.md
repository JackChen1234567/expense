# Expense sharing Backend
an API for an expense-sharing app
## Introduction
The app has several key features, including the
ability to create and manage groups of users, create
and manage expenses within those groups, track
payments made by individual users towards those
expenses, and calculate and distribute the amount
owed by each user for each expense.
There are four models in the solution
- User
- Group
- Expense
- Payment

In order to reach high performance, low latency and high throughput, use non-blocking frameworks(reactor, Micronaut data hibernate reactive and netty) instead of traditional
blocking rest api

## Technologies used
- Intellij
- Micronaut
- Kotlin
- Micronaut data hibernate reactive
- Micronaut Reactor
- flyway
- mysql 8
## Testing
- Unit tests
- Mockk 
