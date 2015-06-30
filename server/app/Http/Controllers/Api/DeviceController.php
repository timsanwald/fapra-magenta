<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;

use App\Http\Controllers\Controller;
use App\Models\Device;

class DeviceController extends Controller
{

	/**
	 * @param  String $hash hash of the device
	 * @return String JSON of device with given hash or 404
	 */
	public function getInfo($hash)
	{
		$devices = Device::where('deviceHash', $hash)->get();

		if(count($devices) == 0)
		{
			return response('', 404);
		}

		return $devices[0];
	}

	/**
	 * @return 201 status code
	 */
	public function postUpdate(Request $request)
	{
		// load device with postdata hash
		$devices = Device::where('deviceHash', $request->input('deviceHash', ''))->get();

		if(count($devices) == 0)
		{
			// create a new device
			$deviceData = array(
				'deviceHash' => $request->input('deviceHash', ''),
				'screenXPx' => (int) $request->input('screenXPx', 0),
				'screenYPx' => (int) $request->input('screenYPx', 0),
				'gridSizeX' => (int) $request->input('gridSizeX', 0),
				'gridSizeY' => (int) $request->input('gridSizeY', 0),
				'xDpi' => (float) $request->input('xDpi', 0),
				'yDpi' => (float) $request->input('yDpi', 0),
				'density' => (float) $request->input('density', 0)
			);
			$device = new Device($deviceData);
		}
		else
		{
			$device = $devices[0];

			// update the device
			$device->screenXPx = (int) $request->input('screenXPx', 0);
			$device->screenYPx = (int) $request->input('screenYPx', 0);
			$device->gridSizeX = (int) $request->input('gridSizeX', 0);
			$device->gridSizeY = (int) $request->input('gridSizeY', 0);
			$device->xDpi = (float) $request->input('xDpi', 0);
			$device->yDpi = (float) $request->input('yDpi', 0);
			$device->density = (float) $request->input('density', 0);
		}

		// save the device / the changes
		$device->save();

		return response('', 201);
	}
}
