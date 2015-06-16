<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateDevices extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('devices', function (Blueprint $table) {
            $table->increments('id');
            $table->string('deviceHash');
            $table->integer('screenXPx');
            $table->integer('screenYPx');
            $table->integer('gridSizeX');
            $table->integer('gridSizeY');
            $table->float('xDpi');
            $table->float('yDpi');
            $table->float('density');

            $table->index('deviceHash');
            $table->index(['gridSizeX', 'gridSizeY']);
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('devices');
    }
}
