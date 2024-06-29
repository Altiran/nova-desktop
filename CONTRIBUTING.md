# Contributing

First and foremost, thank you! We appreciate that you want to contribute to the project, your time is
valuable, and your contributions mean a lot to us.

## Issues

Do not create issues about bumping dependencies unless a bug has been identified, and you can demonstrate that it
affects this library.

**Help us to help you**

Remember that we’re here to help, but not to make guesses about what you need help with:

- Whatever bug or issue you're experiencing, assume it will not be as obvious to the maintainers as it is to you.
- Explain the issue thoroughly. Remember, maintainers need to consider all potential use cases. It's crucial to
  detail how you're using the library so maintainers can understand and address the problem effectively.

_It can't be understated how frustrating and draining it can be to maintainers to have to ask clarifying questions on
the most basic things, before it's even possible to start debugging. Please try to make the best use of everyone's time
involved, including yourself, by providing this information up front._

## Repo Setup

The package manager used to install and link dependencies must be npm v7 or later.

1. Clone repo
2. `npm run watch` start electron app in watch mode.
3. `npm run compile` build app but for local debugging only.
4. `npm run lint` lint your code.
5. `npm run typecheck` Run typescript check.
6. `npm run test` Run app test.
7. `npm run format` Reformat all codebase to project code style.
