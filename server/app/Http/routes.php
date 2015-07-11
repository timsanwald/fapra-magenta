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
	$lines = \App\Models\Line::with('points', 'device')->where('startGridX', $startX)->where('startGridY', $startY)->where('endGridX', $endX)->where('endGridY', $endY)->get();

	// do some explicit type casting for better json output
	foreach ($lines as &$line)
	{
		$line->id = (int) $line->id;
		$line->deviceId = (int) $line->deviceId;
		$line->startGridX = (int) $line->startGridX;
		$line->startGridY = (int) $line->startGridY;
		$line->endGridX = (int) $line->endGridX;
		$line->endGridY = (int) $line->endGridY;
		$line->startPxX = (int) $line->startPxX;
		$line->startPxY = (int) $line->startPxY;
		$line->endPxX = (int) $line->endPxX;
		$line->endPxY = (int) $line->endPxY;
		$line->startTime = (int) $line->startTime;
		$line->endTime = (int) $line->endTime;
		$line->isLandscape = (boolean) $line->isLandscape;
		$line->scrollDirection = (int) $line->scrollDirection;
		$line->complete = (boolean) $line->complete;
		$line->device->screenXPx = (int) $line->device->screenXPx;
		$line->device->screenYPx = (int) $line->device->screenYPx;
		$line->device->gridSizeX = (int) $line->device->gridSizeX;
		$line->device->gridSizeY = (int) $line->device->gridSizeY;
		$line->device->xDpi = (float) $line->device->xDpi;
		$line->device->yDpi = (float) $line->device->yDpi;
		$line->device->density = (float) $line->device->density;

		// loop over points for casting
		foreach ($line->points as &$point)
		{
			$point->lineId = (int) $point->lineId;
			$point->xPx = (int) $point->xPx;
			$point->yPx = (int) $point->yPx;
			$point->timestamp = (int) $point->timestamp;
		}

		unset($point);
	}

	unset($line);

	return $lines;
});

Route::get('api/dbLines', function() {
	return \App\Models\Line::with('points')->paginate(250);
});
