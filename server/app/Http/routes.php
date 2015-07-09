<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

Route::get('/', function () {
    return view('welcome');
});

Route::controller('api/device', '\App\Http\Controllers\Api\DeviceController');
Route::controller('api/line', '\App\Http\Controllers\Api\LineController');
Route::get('api/dbLines/start-{startX}-{startY}/end-{endX}-{endY}', function($startX, $startY, $endX, $endY) {
	return \App\Models\Line::with('points')->where('startGridX', $startX)->where('startGridY', $startY)->where('endGridX', $endX)->where('endGridY', $endY)->get();
});

Route::get('api/dbLines', function() {
	return \App\Models\Line::with('points')->get();
});
