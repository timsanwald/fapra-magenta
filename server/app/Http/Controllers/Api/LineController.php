<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;

use App\Http\Controllers\Controller;
use App\Models\Device;
use App\Models\Line;
use App\Models\Point;

class LineController extends Controller
{

	/**
	 * save lines
	 *
	 * @param  Request $request the request object
	 *
	 * @return reply
	 */
	public function postSaveLines(Request $request)
	{
		// parse json
		$data = json_decode($request->input('data', ''), true);

		if($data === null)
		{
			// invalid json
			return response('Invalid JSON', 400);
		}

		// check if device is valid
		$deviceHash = $data['device'];

		$device = Device::where('deviceHash', $deviceHash)->get();
		if(count($device) == 0)
		{
			// no such device
			return response('Invalid device', 412);
		}

		$device = $device[0];

		foreach($data['lines'] as $line)
		{
			// loop over lines and do some basic validation
			$line['startGridX'] = (int) $line['startGridX'];
			$line['startGridY'] = (int) $line['startGridY'];
			$line['endGridX'] = (int) $line['endGridX'];
			$line['endGridY'] = (int) $line['endGridY'];
			$line['startPxX'] = (int) $line['startPxX'];
			$line['startPxY'] = (int) $line['startPxY'];
			$line['endPxX'] = (int) $line['endPxX'];
			$line['endPxY'] = (int) $line['endPxY'];
			$line['startTime'] = (int) $line['startTime'];
			$line['endTime'] = (int) $line['endTime'];
			$line['isLandscape'] = (boolean) $line['isLandscape'];
			$line['scrollDirection'] = (int) $line['scrollDirection'];

			// create an eloquent model and save it
			$dbLine = new Line($line);
			$device->lines()->save($dbLine);

			// save the points for the line
			$linePoints = array();
			foreach($line['points'] as $point)
			{
				$point['xPx'] = (int) $point['xPx'];
				$point['yPx'] = (int) $point['yPx'];
				$point['timestamp'] = (int) $point['timestamp'];

				$linePoints[] = new Point($point);
			}

			// insert points into db
			$dbLine->points()->saveMany($linePoints);
		}

		// catch old clients
		if(!isset($data['lineId']))
		{
			$data['lineId'] = '';
		}

		return $data['lineId'];
	}
}
