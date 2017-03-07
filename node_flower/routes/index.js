var express = require('express');
var router = express.Router();
var FlowerPower =require ('flower-power-ble');
var async = require('async');
var hasCalibratedData = false;

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

router.get('/test', function(req, res, next) {
  FlowerPower.discoverAll(function (flowerPower) {
    console.log('readSystemId');
     flowerPower.readSystemId(function(error, systemId) {
       if (error) res.json({'error' :'error'});
       async.series([
    function(callback) {
      flowerPower.on('disconnect', function() {
        console.log('disconnected!');
        process.exit(0);
      });

      flowerPower.on('sunlightChange', function(sunlight) {
        console.log('sunlight = ' + sunlight.toFixed(2) + ' mol/m²/d');
      });

      // flowerPower.on('soilElectricalConductivityChange', function(soilElectricalConductivity) {
      //   console.log('soil electrical conductivity = ' + soilElectricalConductivity.toFixed(2));
      // });

     // flowerPower.on('soilChange', function() {
     //   console.log('soil  = ' + .toFixed(2) + '°C');
     // });

     // flowerPower.on('airChange', function() {
     //   console.log('air  = ' + .toFixed(2) + '°C');
    //  });

      flowerPower.on('soilMoistureChange', function(soilMoisture) {
        console.log('soil moisture = ' + soilMoisture.toFixed(2) + '%');
      });

      flowerPower.on('calibratedSoilMoistureChange', function(soilMoisture) {
        console.log('calibrated soil moisture = ' + soilMoisture.toFixed(2) + '%');
      });

      //flowerPower.on('calibratedAirChange', function() {
       // console.log('calibrated air  = ' + .toFixed(2) + '°C');
     // });

      flowerPower.on('calibratedSunlightChange', function(sunlight) {
        console.log('calibrated sunlight = ' + sunlight.toFixed(2) + ' mol/m²/d');
      });

      flowerPower.on('calibratedEaChange', function(ea) {
        console.log('calibrated EA = ' + ea.toFixed(2));
      });

      flowerPower.on('calibratedEcbChange', function(ecb) {
        console.log('calibrated ECB = ' + ecb.toFixed(2) + ' dS/m');
      });

      flowerPower.on('calibratedEcPorousChange', function(ecPorous) {
        console.log('calibrated EC porous = ' + ecPorous.toFixed(2)+ ' dS/m');
      });

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
      console.log('readFirmwareRevision');
      flowerPower.readFirmwareRevision(function(error, firmwareRevision) {
        console.log('\tfirmware revision = ' + firmwareRevision);

        var version = firmwareRevision.split('_')[1].split('-')[1];

        hasCalibratedData = (version >= '1.1.0');

        callback();
      });
    },
    function(callback) {
      console.log('readHardwareRevision');
      flowerPower.readHardwareRevision(function(error, hardwareRevision) {
        console.log('\thardware revision = ' + hardwareRevision);
        callback();
      });
    },
    function(callback) {
      console.log('readManufacturerName');
      flowerPower.readManufacturerName(function(error, manufacturerName) {
        console.log('\tmanufacturer name = ' + manufacturerName);
        callback();
      });
    },
    function(callback) {
      console.log('readBatteryLevel');
      flowerPower.readBatteryLevel(function(error, batteryLevel) {
        console.log('battery level = ' + batteryLevel);

        callback();
      });
    },
    function(callback) {
      console.log('readFriendlyName');
      flowerPower.readFriendlyName(function(error, friendlyName) {
        console.log('\tfriendly name = ' + friendlyName);

        console.log('writeFriendlyName');
        flowerPower.writeFriendlyName(friendlyName, callback);
      });
    },
    function(callback) {
      console.log('readColor');
      flowerPower.readColor(function(error, color) {
        console.log('\tcolor = ' + color);

        callback();
      });
    },
    function(callback) {
      console.log('readSunlight');
      flowerPower.readSunlight(function(error, sunlight) {
        console.log('sunlight = ' + sunlight.toFixed(2) + ' mol/m²/d');

        callback();
      });
    },
    function(callback) {
      console.log('readSoilMoisture');
      flowerPower.readSoilMoisture(function(error, soilMoisture) {
        console.log('soil moisture = ' + soilMoisture.toFixed(2) + '%');

        callback();
      });
    },
    function(callback) {
      if (hasCalibratedData) {
        async.series([
          function(callback) {
            console.log('readCalibratedSoilMoisture');
            flowerPower.readCalibratedSoilMoisture(function(error, soilMoisture) {
              console.log('calibrated soil moisture = ' + soilMoisture.toFixed(2) + '%');

              callback();
            });
          },
          function(callback) {
            console.log('readCalibratedSunlight');
            flowerPower.readCalibratedSunlight(function(error, sunlight) {
              console.log('calibrated sunlight = ' + sunlight.toFixed(2) + ' mol/m²/d');

              callback();
            });
          },
          function(callback) {
            console.log('readCalibratedEa');
            flowerPower.readCalibratedEa(function(error, ea) {
              console.log('calibrated EA = ' + ea.toFixed(2));

              callback();
            });
          },
          function(callback) {
            console.log('readCalibratedEcb');
            flowerPower.readCalibratedEcb(function(error, ecb) {
              console.log('calibrated ECB = ' + ecb.toFixed(2) + ' dS/m');

              callback();
            });
          },
          function(callback) {
            console.log('readCalibratedEcPorous');
            flowerPower.readCalibratedEcPorous(function(error, ecPorous) {
              console.log('calibrated EC porous = ' + ecPorous.toFixed(2) + ' dS/m');

              callback();
            });
          },
          function() {
            callback();
          }
        ]);
      } else {
        callback();
      }
    },
    function(callback) {
      console.log('enableLiveMode');
      flowerPower.enableLiveMode(callback);
    },
    function(callback) {
      console.log('live mode');
      setTimeout(callback, 5000000);
    },
    function(callback) {
      console.log('disableLiveMode');
      flowerPower.disableLiveMode(callback);
    },
    function(callback) {
      if (hasCalibratedData) {
        async.series([
          function(callback) {
            console.log('enableCalibratedLiveMode');
            flowerPower.enableCalibratedLiveMode(callback);
          },
          function(callback) {
            console.log('calibrated live mode');
            setTimeout(callback, 5000000);
          },
          function(callback) {
            console.log('disableCalibratedLiveMode');
            flowerPower.disableCalibratedLiveMode(callback);
          },
          function() {
            callback();
          }
        ]);
      } else {
        callback();
      }
    },
    function(callback) {
      console.log('ledPulse');
      flowerPower.ledPulse(callback);
    },
    function(callback) {
      console.log('delay');
      setTimeout(callback, 2000000);
    },
    function(callback) {
      console.log('ledOff');
      flowerPower.ledOff(callback);
    },
    function(callback) {
      console.log('disconnect');
      flowerPower.disconnect(callback);
    }
  ]);
      //  console.log('\tsystem id = ' + systemId);
      //  res.json({'id':systemId})

     });
  })
});

module.exports = router;
