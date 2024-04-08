package com.lab.trailblazer.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "RecordsDatabase"
        private const val TABLE_NAME = "Records"
        private const val KEY_ID = "id"
        private const val KEY_DATE = "date"
        private const val KEY_TIME = "time"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createRecords = ("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
                + KEY_TIME + " INTEGER" + ")")
        db.execSQL(createRecords)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addRecord(record: Record) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_DATE, record.date)
        values.put(KEY_TIME, record.time)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }
}
