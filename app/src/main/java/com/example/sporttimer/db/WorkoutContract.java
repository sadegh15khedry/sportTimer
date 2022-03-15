package com.example.sporttimer.db;

import android.provider.BaseColumns;

final class WorkoutContract {
    private int workoutId;
    private String workoutName;
    private int workoutSets;
    private int workoutMinutes;
    private int workoutSeconds;
    private int restMinutes;
    private int restSeconds;

    public static final String CREATE_WORKOUT_ENTRY_TABLE =
            "CREATE TABLE "+ WorkoutEntry.TABLE_NAME +
            " ( "+
            WorkoutEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    WorkoutEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    WorkoutEntry.COLUMN_NAME + "TEXT NOT NULL, " +
                    WorkoutEntry.COLUMN_SETS + "INTEGER NOT NULL, " +
                    WorkoutEntry.COLUMN_WORKOUT_MINUTES + "INTEGER NOT NULL, " +
                    WorkoutEntry.COLUMN_WORKOUT_SECONDS + "INTEGER NOT NULL, " +
                    WorkoutEntry.COLUMN_REST_MINUTES + "INTEGER NOT NULL, " +
                    WorkoutEntry.COLUMN_REST_SECONDS + "INTEGER NOT NULL) " ;


    public static  class WorkoutEntry implements BaseColumns{
        public static final String TABLE_NAME = "workout";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SETS = "sets";
        public static final String COLUMN_WORKOUT_MINUTES = "workout_minutes";
        public static final String COLUMN_WORKOUT_SECONDS = "workout_seconds";
        public static final String COLUMN_REST_MINUTES = "rest_minutes";
        public static final String COLUMN_REST_SECONDS = "rest_seconds";

    }
}
