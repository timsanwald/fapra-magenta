<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateLines extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('lines', function (Blueprint $table) {
            $table->increments('id');
            $table->integer('deviceId');
            $table->integer('startGridX');
            $table->integer('startGridY');
            $table->integer('endGridX');
            $table->integer('endGridY');
            $table->integer('startPxX');
            $table->integer('startPxY');
            $table->integer('endPxX');
            $table->integer('endPxY');
            $table->bigInteger('startTime');
            $table->bigInteger('endTime');
            $table->boolean('isLandscape');
            $table->tinyInteger('scrollDirection');

            $table->index('deviceId');
            $table->index(['startGridX', 'startGridY']);
            $table->index(['endGridX', 'endGridY']);
            $table->index(['startGridX', 'startGridY', 'endGridX', 'endGridY']);
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('lines');
    }
}
