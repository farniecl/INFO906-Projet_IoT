var express = require('express');
var router = express.Router();
//var FlowerPower =require ('flower-power');
var async = require('async');
var hasCalibratedData = false;
var cors = require('cors');

router.get('/:num',cors(),function(req,res,next) {
    res.json({'out_temp':23.00,'in_temp':39.90,'state_fences':false,'state_windows':false});
});

router.post('/:num',cors(),function(req,res,next) {
    console.log(req.body);
    res.json({'ok':'ok'});
});

module.exports = router;
