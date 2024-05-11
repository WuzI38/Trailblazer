package com.app.trailblazer.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "RecordsDatabase"
        private const val TABLE_NAME = "Records"
        private const val KEY_ID = "id"
        private const val KEY_DATE = "date"
        private const val KEY_TIME = "time"
        private const val KEY_NAME = "name"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createRecords = ("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
                + KEY_TIME + " INTEGER," + KEY_NAME + " TEXT" + ")")
        db.execSQL(createRecords)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insert new timer record to the database
    fun addRecord(record: Record) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_DATE, record.date)
        values.put(KEY_TIME, record.time)
        values.put(KEY_NAME, record.name)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Delete single timer record from the database
    fun deleteRecord(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(id.toString()))
        db.close()
    }

    // Currently unused - delete all timer records from the record database
    @Suppress("unused")
    fun deleteAllRecords() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }

    // Used to get and display all data from the database
    @SuppressLint("Range")
    fun getAllRecords(): List<Record> {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        val records = mutableListOf<Record>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val date = cursor.getString(cursor.getColumnIndex(KEY_DATE))
                val time = cursor.getInt(cursor.getColumnIndex(KEY_TIME))
                val name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                records.add(Record(id, date, time, name))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return records
    }
}