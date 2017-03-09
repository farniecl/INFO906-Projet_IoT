var express = require('express');
var getRoomById = require('../model/rooms').getRoomById;
var router = express.Router();
//var FlowerPower =require ('flower-power');
var async = require('async');
var hasCalibratedData = false;
var cors = require('cors');

router.get('/:id',cors(),function(req,res,next) {
    var room = getRoomById(req.params.id, function (err, room) {
      if (!err) {
        res.json(room);
      } else {
        res.status(400).json(err);
      }
    });
});

router.post('/:id',cors(),function(req,res,next) {
    console.log(req.body);
    res.json({'result':'Envoi des données réussi !'});
});

module.exports = router;
