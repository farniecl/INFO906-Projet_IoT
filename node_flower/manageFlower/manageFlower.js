var async = require('async');
var sync = require('synchronize');
var FlowerPower = require('flower-power');
//var Room = require('../model/rooms');

exports.flowerHandler = function() {
      FlowerPower.discoverAll(function(flowerPower) {
        flowerPower.on('disconnect', function() {
          console.log('disconnected!');
        });
        flowerPower.on('airTemperatureChange', function(temperature) {
          setTimeout(function () {
            console.log('air temperature = ' + temperature.toFixed(2) + '°C');
          }, 15000);
        });
        sync(async,'series');
        async.series([
          function(callback) {
            console.log('connectAndSetup');
            flowerPower.connectAndSetup(callback);
          },
          function(callback) {
            console.log('readSystemId');
            flowerPower.readSystemId(function(error, systemId) {
              console.log('\tsystem id = ' + systemId);
              callback();
            });
          },
          function(callback) {
            console.log('readSerialNumber');
            flowerPower.readSerialNumber(function(error, serialNumber) {
              console.log('\tserial number = ' + serialNumber);
              callback();
            });
          },
          function(callback) {
            console.log('readFriendlyName');
            flowerPower.readFriendlyName(function(error, friendlyName) {
              console.log('\tfriendly name = ' + friendlyName);
                callback()
            });
          },
          function(callback) {
            console.log('readAirTemperature');
            flowerPower.readAirTemperature(function(error, temperature) {
              console.log('air temperature = ' + temperature.toFixed(2) + '°C');
              callback();
            });
          },
          function(callback) {
            console.log('enableLiveMode');
            flowerPower.enableLiveMode(function(callback) {
              console.log('live mode');
            });
            callback();
          },
        ]);
      });
}

exports.disconnect = function() {
  /**TODO**/
};
