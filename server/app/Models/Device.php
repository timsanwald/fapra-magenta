<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Device extends Model
{
	/**
	 * table for model
	 *
	 * @var string
	 */
    protected $table = 'devices';

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
    protected $fillable = array('deviceHash', 'screenXPx', 'screenYPx', 'gridSizeX', 'gridSizeY', 'xDpi', 'yDip', 'density');

    /**
     * attributes hidden from auto responses like json
     *
     * @var array
     */
    protected $hidden = array('id');

    /**
     * define relationship to lines
     */
    public function lines()
    {
        return $this->hasMany('App\Models\Line', 'deviceId', 'id');
    }
}
