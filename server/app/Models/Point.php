<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Point extends Model
{
	/**
	 * table for model
	 *
	 * @var string
	 */
    protected $table = 'points';

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
    protected $fillable = array('lineId', 'xPx', 'yPx', 'timestamp');

    /**
     * attributes hidden from auto responses like json
     *
     * @var array
     */
    protected $hidden = array('id');

    /**
     * define relationship to line
     */
    public function line()
    {
        return $this->belongsTo('App\Models\Line', 'lineId', 'id');
    }
}
