package dev.dmayr.notasynavegacion.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.dmayr.notasynavegacion.model.Nota

@Dao
interface NotaDao {
    @Query("SELECT * FROM notas ORDER BY fechaCreacion DESC")
    fun getAll(): LiveData<List<Nota>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nota: Nota): Long

    @Update
    suspend fun update(nota: Nota)

    @Delete
    suspend fun delete(nota: Nota)

    @Query("SELECT * FROM notas WHERE id = :id")
    suspend fun findById(id: Long): Nota?

    @Query("DELETE FROM notas")
    suspend fun deleteAll()
}
