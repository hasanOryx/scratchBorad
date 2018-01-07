'use strict';

process.env.DEBUG = 'actions-on-google:*';

const ActionsSdkApp = require('actions-on-google').ActionsSdkApp;
const functions = require('firebase-functions');

const NO_INPUTS = [
  'I didn\'t hear that.',
  'If you\'re still there, say that again.',
  'We can stop here. See you soon.'
];

exports.sayNumber = functions.https.onRequest((request, response) => {
  const app = new ActionsSdkApp({request, response});

  var math = function(app){
    var operation = RegExp.$3;
    var a = parseFloat(RegExp.$2);
    var b = parseFloat(RegExp.$4);
    switch(operation){
      case '+':
      case 'and':
      case 'plus':
         app.ask('<speak> The sum of: '+a+' and '+b+' is: '+(a+b)+'</speak>');
     break;
     default:
     break;
  }
}
  
  var commands = new Map();
  commands.set(
    new RegExp(/^(What is|What's|Calculate|How much is) ([\w.]+) (\+|and|plus|\-|less|minus|\*|\x|by|multiplied by|\/|over|divided by) ([\w.]+)$/),
    math)

  function mainIntent (app) {
    console.log('mainIntent');
    let inputPrompt = app.buildInputPrompt(true, '<speak>Hi! <break time="1"/> ' +
      'I can read out an ordinal like ' +
      '<say-as interpret-as="ordinal">123</say-as>. Say a number.</speak>', NO_INPUTS);
    app.ask(inputPrompt);
  }

  function rawInput (app) {
    let input = app.getRawInput();
    if (input === 'bye') {
      app.tell('Goodbye!');
    } else {
        commands.forEach(function(value, key, map) {
          if (input.match(key)) {
          value.apply();
        }
      })
    }
  }

  let actionMap = new Map();
  actionMap.set(app.StandardIntents.MAIN, mainIntent);
  actionMap.set(app.StandardIntents.TEXT, rawInput);

  app.handleRequest(actionMap);
});