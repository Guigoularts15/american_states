package com.example.americanstates

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DatabaseHelper(
    context: Context?
) : SQLiteOpenHelper(context, "myDB", null, 1) {

    private var context = context
    private val DATABASE_NAME = "states.db"
    private var DATABASE_VERSION = 1
    private val TAG = "DatabaseHelper"
    private val TABLE_NAME = "my_states"
    private val COLUMN_ID = "_id"
    private val COLUMN_STATE_ID = "_state_id"
    private val COLUMN_STATE = "_state"
    private val COLUMN_YEAR = "_year"
    private val COLUMN_POP = "_pop"
    private val COLUMN_SLUG = "_slug"

    fun DatabaseHelper(context: Context) {
        super.getReadableDatabase()
        this.context = context
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query: String =
            "CREATE TABLE " + TABLE_NAME +
                    " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_STATE_ID + " TEXT, " +
                    COLUMN_STATE + " TEXT, " +
                    COLUMN_YEAR + " TEXT, " +
                    COLUMN_POP + " INTEGER, " +
                    COLUMN_SLUG + " TEXT);"

        if (db != null) {
            db.execSQL(query)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
            onCreate(db)
        }
    }

    fun addSavedData(state_id: String, state_name: String, year: String, pop: Int, slug: String) {
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(COLUMN_STATE_ID, state_id)
        cv.put(COLUMN_STATE, state_name)
        cv.put(COLUMN_YEAR, year)
        cv.put(COLUMN_POP, pop)
        cv.put(COLUMN_SLUG, slug)

        val result = db.insert(TABLE_NAME, null, cv)

        if (result == -1L) {
            Toast.makeText(context, "FALHOU", Toast.LENGTH_SHORT,)
        }
        else {
            Toast.makeText(context, "CONSEGUIU", Toast.LENGTH_SHORT,)
        }
    }

}