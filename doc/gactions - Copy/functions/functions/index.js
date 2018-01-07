'use strict';

const ActionsSdkApp = require('actions-on-google').ActionsSdkApp;
const functions = require('firebase-functions');

exports.sayNumber = functions.https.onRequest((request, response) => {
  const app = new ActionsSdkApp({request, response});

  function mainIntent (app) {
    console.log('mainIntent');
    let inputPrompt = app.buildInputPrompt(true, '<speak>Hi! <break time="1"/> ' +
      'I can read out an ordinal like ' +
      '<say-as interpret-as="ordinal">123</say-as>. Say a number.</speak>', NO_INPUTS);
    app.ask(inputPrompt);
  }

  function rawInput (app) {
    if (app.getRawInput() === 'bye') {
      app.tell('Goodbye!');
    } else {
      let inputPrompt = app.buildInputPrompt(true, '<speak>You said, <say-as interpret-as="ordinal">' +
        app.getRawInput() + '</say-as></speak>', NO_INPUTS);
      app.ask(inputPrompt);
    } 
  }

  const helpHandler = (app) => {
    console.log('raw.input intent triggered.');
    app.tell('<speak>Sure, I can help, tell me your issue</speak>');
  };

  let actionMap = new Map();
  actionMap.set(app.StandardIntents.MAIN, mainIntent);
  actionMap.set("app.StandardIntents.HELP", helpHandler);
  actionMap.set(app.StandardIntents.TEXT, rawInput);

  app.handleRequest(actionMap);
});