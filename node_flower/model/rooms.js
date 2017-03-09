var mongoose = require('mongoose');
var sync = require('synchronize');
mongoose.Promise = global.Promise
mongoose.connect('mongodb://localhost/rooms',function(err) {
  if(err) console.log(err);
});
var Rooms = mongoose.model('Rooms', new mongoose.Schema({
    'id': String,
    'out_temp':Number,
    'in_temp':Number,
    'state_fences':Boolean,
    'state_windows':Boolean
}));

exports.setTemp = function (id,temp) {
  Rooms.findOne({ 'id': id }).exec(function (err, room) {
    if (!err) {
        if(room){
          room.in_temp = temp;
          room.save(function (err) {
            if(err) console.log(err);
          });
        }
      }
  });
};

exports.setFenceState = function (id,state) {
  Rooms.findOne({ 'id': id }, function (err, room) {
    if (!err) {
      room.state_fences = state;
      room.save(function (err) {
        if(err) console.log(err);
      });
    }
  });
};

exports.setWindowState = function (id,state) {
  Rooms.findOne({ 'id': id }, function (err, room) {
    if (!err) {
      room.state_windows = state;
      room.save(function (err) {
        if(err) console.log(err);
      });
    }
  });
};

exports.getRoomById = function(id,callback){
  Rooms.findOne({ 'id': id },callback);
};
