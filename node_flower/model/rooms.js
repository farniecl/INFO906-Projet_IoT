var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/rooms');
var Room = mongoose.model('Room');
var Schema = mongoose.Schema

exports.setTemp = function (id,temp) {
  Room.findById(id, function (err, room) {
    if (!err) {
      room.temp = temp;
      post.save(function (err) {
      // do something
      });
    }
  });
};

exports.setFenceState = function (id,state) {
  Room.findById(id, function (err, room) {
    if (!err) {
      room.state_fences = state;
      post.save(function (err) {
      // do something
      });
    }
  });
};

exports.setWindowState = function (id,state) {
  Room.findById(id, function (err, room) {
    if (!err) {
      room.state_windows = state;
      post.save(function (err) {
      // do something
      });
    }
  });
};

exports.getRoomById = function(id){
  Room.findById(id, function (err, room) {
    if (!err) {
      return room;
      });
    }
  });
};
