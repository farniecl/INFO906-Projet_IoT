var express = require('express');
var getRoomById = require('../model/rooms').getRoomById;
var postInfo = require('../model/rooms').postInfo
var router = express.Router();
//var FlowerPower =require ('flower-power');
var async = require('async');
var hasCalibratedData = false;
var cors = require('cors');

router.get('/:id',cors(),function(req,res,next) {
    getRoomById(req.params.id, function (err, room) {
      if (!err) {
        res.json(room);
      } else {
        res.status(400).json(err);
      }
    });
});

router.post('/:id',cors(),function(req,res,next) {
    getRoomById(req.params.id,function (err, room) {
      if (!err) {
        room.state_fences = req.body.state_fences;
        room.state_windows = req.body.state_windows;
        room.out_temp = req.body.in_temp;
        room.save(function (err) {
          if(err) res.json(err);
          res.json({'result':'Envoi des données réussi !'});
        });
      }
    });

});

module.exports = router;
