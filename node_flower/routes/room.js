var express = require('express');
var router = express.Router();
//var FlowerPower =require ('flower-power');
var async = require('async');
var hasCalibratedData = false;

router.get('/:num',function(req,res,next) {
    res.json({'out_temp':23,'in_temp':39,'state_fences':false,'state_windows':false});
});

router.post('/:num',function(req,res,next) {
    console.log(req.body);
    res.send('ok');
});




module.exports = router;