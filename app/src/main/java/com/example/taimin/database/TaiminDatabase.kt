package com.example.taimin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taimin.clases.elementos.Elemento
import com.example.taimin.clases.elementos.ElementoCreable
import com.example.taimin.clases.elementos.*
import com.example.taimin.clases.Evento
import com.example.taimin.clases.EventoInterno
import com.example.taimin.clases.Usuario
import com.example.taimin.clases.elementos.Subtarea


@Database(entities = [
    Pantalla::class, Proyecto::class, Lista::class, Tarea::class, Subtarea::class,
    Evento::class, EventoInterno::class, Usuario::class], version = 3, exportSchema = false)

@TypeConverters(Converters::class)
abstract class TaiminDatabase : RoomDatabase() {
    abstract val taiminDAO: TaiminDAO

    companion object {
        @Volatile
        private var INSTANCE: TaiminDatabase? = null

        fun getInstance(context: Context): TaiminDatabase {
            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaiminDatabase::class.java,
                    "taimin_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }
            return instance
        }
    }
}