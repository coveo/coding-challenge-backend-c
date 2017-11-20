# TLM Coding challenge
(inspired by https://github.com/coveo/backend-coding-challenge)

## Requirements

### Backend
Design an API endpoint that provides a ninja name based on a list of technology buzzwords

- The endpoint is exposed at `/ninjify`
- The search has to be deterministic
- The search term is passed as a querystring parameter `x`
- The endpoint returns a JSON with a ninja name

### Frontend
Design an user interface to input web technology buzzwords and then generate an awesome ninja name.

- Responsive
- Mobile/Desktop compatibility

## "The rules"

- *You can use the language and technology of your choosing.* It's OK to try something new (tell us if you do), but feel free to use something you're comfortable with. We don't care if you use something we don't; the goal here is not to validate your knowledge of a particular technology.
- End result should be deployed on a public Cloud (Heroku, AWS etc. all have free tiers you can use).
- The results have to be SFW and politically correct

## Advices

- **Try to design and implement your solution as you would do for real production code**. Show us how you create clean, maintainable code that does awesome stuff. Build something that we'd be happy to contribute to. This is not a programming contest where dirty hacks win the game.
- Feel free to add more features! Really, we're curious about what you can think of. We'd expect the same if you worked with us.
- Documentation and maintainability is a plus.
- Don't you forget those unit tests.
- We don’t want to know if you can do exactly as asked (or everybody would have the same result). We want to know what **you** bring to the table when working on a project, what is your secret sauce. More features? Best solution? Thinking outside the box?
- Make sure you apply security good practices

## Bonuses
- Add easter egg with the `Konami` code.

## Sample responses

These responses are meant to provide guidance. The exact values can vary based on the data source and scoring algorithm

    GET /ninjify?x=sass,rails,html

```json
{
  "name": "Red Drop Shadow"
}
```

## Getting Started

Begin by forking this repo and cloning your fork. GitHub has apps for [Mac](http://mac.github.com/) and
[Windows](http://windows.github.com/) that make this easier.
