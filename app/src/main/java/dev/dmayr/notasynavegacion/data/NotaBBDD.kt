package dev.dmayr.notasynavegacion.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.dmayr.notasynavegacion.model.Nota

@Database(entities = [Nota::class], version = 1, exportSchema = false)
abstract class NotaBBDD : RoomDatabase() {
    abstract fun notaDao(): NotaDao

    companion object {
        @Volatile
        private var INSTANCE: NotaBBDD? = null

        fun getDatabase(context: Context): NotaBBDD =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    NotaBBDD::class.java, "nota_database"
                ).build().also {
                    INSTANCE = it
                }
            }
    }
}
