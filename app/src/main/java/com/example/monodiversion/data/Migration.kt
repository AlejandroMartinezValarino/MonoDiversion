package com.example.monodiversion.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Hecho por Alejandro Martínez Valarino el 28/01/2024
 */

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Elimina la columna 'flag' de la tabla 'USER'
        database.execSQL("CREATE TABLE new_USER AS SELECT id, name, country FROM USER")
        database.execSQL("DROP TABLE USER")
        database.execSQL("ALTER TABLE new_USER RENAME TO USER")
    }
}
