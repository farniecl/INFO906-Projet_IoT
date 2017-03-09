var async = require('async');
var sync = require('synchronize');
var FlowerPower = require('flower-power');
var setTemp = require('../model/rooms').setTemp;
var capteurRoom ={
  'a0:14:3d:00:00:7d:aa:b2':"001",
  'a0:14:3d:00:00:7d:94:0a':"002"
}

exports.flowerHandler = function() {
      FlowerPower.discoverAll(function(flowerPower) {
        var room;
        flowerPower.on('disconnect', function() {
          console.log('disconnected!');
        });
        flowerPower.on('airTemperatureChange', function(temperature) {
            setTemp(room,temperature.toFixed(2))
            console.log('air temperature = ' + temperature.toFixed(2) + '°C '+room);
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
              if(error)
              {
                console.log(error);
              }
              room = capteurRoom[systemId];
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
