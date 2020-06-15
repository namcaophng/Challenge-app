package com.sunasterisk.a14day_challenge.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sunasterisk.a14day_challenge.data.model.Exercise
import com.sunasterisk.a14day_challenge.data.model.User

class DataBaseHandler private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    //user
    private val CREATE_USER_TABLE = "CREATE TABLE $TABLE_USER (" +
            " $USER_ACC TEXT PRIMARY KEY," +
            " $USER_NAME TEXT," +
            " $USER_PASS TEXT," +
            " $USER_BIR TEXT," +
            " $USER_HEIGHT TEXT," +
            " $USER_WEIGHT TEXT," +
            " $USER_PROCESS INTEGER)"

    //exercise
    private val CREATE_EXERCISE_TABLE = "CREATE TABLE $TABLE_EXERCISE (" +
            " $COLUMN_TYPE TEXT PRIMARY KEY," +
            " $EX_D1 REAL," +
            " $EX_D2 REAL," +
            " $EX_D3 REAL," +
            " $EX_D4 REAL," +
            " $EX_D5 REAL," +
            " $EX_D6 REAL," +
            " $EX_D7 REAL," +
            " $EX_D8 REAL," +
            " $EX_D9 REAL," +
            " $EX_D10 REAL," +
            " $EX_D11 REAL," +
            " $EX_D12 REAL," +
            " $EX_D13 REAL," +
            " $EX_D14 REAL)"

    //process
    private val CREATE_PROCESS_TABLE = "CREATE TABLE $TABLE_PROCESS (" +
            " $PR_ACC TEXT PRIMARY KEY," +
            " $PR_DAY INTEGER)"

    //current
    private val CREATE_CURRENT_TABLE = "CREATE TABLE $TABLE_CURRENT (" +
            " $CR_ACC TEXT PRIMARY KEY," +
            " $CR_PLANK INTEGER," +
            " $CR_PUSHUP INTEGER" +
            " $CR_RUN INTEGER)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.run {
            execSQL(CREATE_USER_TABLE)
            execSQL(CREATE_EXERCISE_TABLE)
            execSQL(CREATE_PROCESS_TABLE)
            execSQL(CREATE_CURRENT_TABLE)
        }

        initializeDefaultData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Nothing in version 1
    }

    private fun initializeDefaultData(db: SQLiteDatabase?) {
        //exercise
        val runData = arrayOf(1.0, 1.5, 1.5, 2.0, 2.5, 2.5, 3.0, 3.0, 3.5, 4.0, 4.0, 3.0, 4.0, 5.0)
        val plankData = arrayOf(
            20.0,
            20.0,
            30.0,
            35.0,
            40.0,
            20.0,
            45.0,
            45.0,
            60.0,
            60.0,
            60.0,
            90.0,
            70.0,
            90.0
        )
        val pushUpData =
            arrayOf(5.0, 6.0, 6.0, 8.0, 8.0, 8.0, 10.0, 10.0, 12.0, 12.0, 12.0, 14.0, 14.0, 16.0)

        db?.run {
            insert(TABLE_EXERCISE, null, getValues(runData, "Run"))
            insert(TABLE_EXERCISE, null, getValues(plankData, "Plank"))
            insert(TABLE_EXERCISE, null, getValues(pushUpData, "Push Up"))
        }
    }

    private fun getValues(arr: Array<Double>, type: String): ContentValues {
        val value = ContentValues()
        value.put(COLUMN_TYPE, type)
        for (i in 0..13) {
            value.put("day${i + 1}", arr[i])
        }
        return value
    }

    fun getUserAll(): List<User> {
        val userList = ArrayList<User>()
        val query = "SELECT * FROM $TABLE_USER"

        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        while (!cursor.isAfterLast) {
            val user = User(cursor)
            userList.add(user)
            cursor.moveToNext()
        }
        cursor.close()
        db.close()
        return userList
    }

    fun addUser(user: User): Boolean? {
        val userList = this.getUserAll()
        var isValidUser = true
        userList.forEach {
            if (it.account == user.account) {
                isValidUser = false
            }
        }
        if (isValidUser) {
            val db = this.writableDatabase
            val result = db.insert(TABLE_USER, null, user.getContentValues()) != -1L
            db.close()
            return result
        }
        return false
    }

    fun updateUser(user: User?): Boolean {
        val db = this.writableDatabase
        user ?: return false

        db.update(TABLE_USER, user.getContentValues(), "$USER_ACC= ?", arrayOf(user.account))
        return true
    }

    fun getCurrentUser(account: String): User? {
        val db = this.readableDatabase
        var user: User?
        val cursor =
            db.query(TABLE_USER, COLUMNS_USER, "$USER_ACC=?", arrayOf(account), null, null, null)
        cursor.run {
            moveToFirst()
            user = User(cursor)
            close()
        }
        return user
    }

    fun getDataForCurrentDay(process: Int): Exercise {
        val db = this.readableDatabase
        val day = getCurrentDay(process)
        val query = "SELECT $COLUMN_TYPE, $day FROM $TABLE_EXERCISE"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
        val data = mutableListOf<Double>()
        while (!cursor.isAfterLast) {
            data.add(cursor.getDouble(cursor.getColumnIndex(day)))
            cursor.moveToNext()
        }
        cursor.close()
        db.close()
        return Exercise(data)
    }

    private fun getCurrentDay (process: Int) = "day$process"

    companion object {
        private const val DATABASE_NAME = "data"
        private const val DATABASE_VERSION = 1

        //user
        private const val TABLE_USER = "user"
        private const val USER_ACC = "account"
        private const val USER_NAME = "name"
        private const val USER_PASS = "password"
        private const val USER_BIR = "birthday"
        private const val USER_HEIGHT = "height"
        private const val USER_WEIGHT = "weight"
        private const val USER_PROCESS = "process"
        private val COLUMNS_USER = arrayOf(
            USER_ACC,
            USER_NAME,
            USER_PASS,
            USER_BIR,
            USER_HEIGHT,
            USER_WEIGHT,
            USER_PROCESS
        )

        //exercises
        private const val TABLE_EXERCISE = "exercise"
        private const val COLUMN_TYPE = "type"
        private const val EX_D1 = "day1"
        private const val EX_D2 = "day2"
        private const val EX_D3 = "day3"
        private const val EX_D4 = "day4"
        private const val EX_D5 = "day5"
        private const val EX_D6 = "day6"
        private const val EX_D7 = "day7"
        private const val EX_D8 = "day8"
        private const val EX_D9 = "day9"
        private const val EX_D10 = "day10"
        private const val EX_D11 = "day11"
        private const val EX_D12 = "day12"
        private const val EX_D13 = "day13"
        private const val EX_D14 = "day14"

        //processUser
        private const val TABLE_PROCESS = "process"
        private const val PR_ACC = "user"
        private const val PR_DAY = "day"

        //currentDay
        private const val TABLE_CURRENT = "current"
        private const val CR_ACC = "user"
        private const val CR_RUN = "run"
        private const val CR_PLANK = "plank"
        private const val CR_PUSHUP = "pushUp"

        private var instance: DataBaseHandler? = null

        fun getInstance(context: Context): DataBaseHandler =
            instance ?: synchronized(lock = Any()) {
                instance ?: DataBaseHandler(context).also { instance = it }
            }
    }
}
