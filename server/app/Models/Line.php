<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Line extends Model
{
	/**
	 * table for model
	 *
	 * @var string
	 */
    protected $table = 'lines';

    /**
     * use timestamps like last_modified, created_at etc.
     *
     * @var boolean
     */
    public $timestamps = false;

	/**
	 * primary key column
	 *
	 * @var string
	 */
    protected $primaryKey = 'id';

    /**
     * use snake or camel case
     *
     * @var boolean
     */
    public static $snakeAttributes = false;

    /**
     * fast fillable fields
     *
     * @var array
     */
    protected $fillable = array(
        'deviceId',
        'startGridX',
        'startGridY',
        'endGridX',
        'endGridY',
        'startPxX',
        'startPxY',
        'endPxX',
        'endPxY',
        'startTime',
        'endTime',
        'isLandscape',
        'scrollDirection'
    );

    /**
     * attributes hidden from auto responses like json
     *
     * @var array
     */
    protected $hidden = array('id');

    /**
     * define relationship to points
     */
    public function points()
    {
        return $this->hasMany('App\Models\Point', 'lineId', 'id');
    }

    /**
     * define relationship to device
     */
    public function device()
    {
        return $this->belongsTo('App\Models\Device', 'deviceId', 'id');
    }
}
