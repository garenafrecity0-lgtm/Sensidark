package com.example.data.db

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.annotation.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class PresetDao_Impl(
  __db: RoomDatabase,
) : PresetDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSavedPresetEntity: EntityInsertAdapter<SavedPresetEntity>

  private val __deleteAdapterOfSavedPresetEntity: EntityDeleteOrUpdateAdapter<SavedPresetEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSavedPresetEntity = object : EntityInsertAdapter<SavedPresetEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `saved_presets` (`id`,`title`,`brand`,`model`,`playstyleKey`,`general`,`redDot`,`scope2x`,`scope4x`,`sniper`,`freeLook`,`dpi`,`fireButtonSize`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SavedPresetEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.brand)
        statement.bindText(4, entity.model)
        statement.bindText(5, entity.playstyleKey)
        statement.bindLong(6, entity.general.toLong())
        statement.bindLong(7, entity.redDot.toLong())
        statement.bindLong(8, entity.scope2x.toLong())
        statement.bindLong(9, entity.scope4x.toLong())
        statement.bindLong(10, entity.sniper.toLong())
        statement.bindLong(11, entity.freeLook.toLong())
        statement.bindLong(12, entity.dpi.toLong())
        statement.bindLong(13, entity.fireButtonSize.toLong())
        statement.bindLong(14, entity.timestamp)
      }
    }
    this.__deleteAdapterOfSavedPresetEntity = object :
        EntityDeleteOrUpdateAdapter<SavedPresetEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `saved_presets` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: SavedPresetEntity) {
        statement.bindLong(1, entity.id)
      }
    }
  }

  public override suspend fun insertPreset(preset: SavedPresetEntity): Long =
      performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfSavedPresetEntity.insertAndReturnId(_connection, preset)
    _result
  }

  public override suspend fun deletePreset(preset: SavedPresetEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __deleteAdapterOfSavedPresetEntity.handle(_connection, preset)
  }

  public override fun getAllPresets(): Flow<List<SavedPresetEntity>> {
    val _sql: String = "SELECT * FROM saved_presets ORDER BY timestamp DESC"
    return createFlow(__db, false, arrayOf("saved_presets")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfBrand: Int = getColumnIndexOrThrow(_stmt, "brand")
        val _columnIndexOfModel: Int = getColumnIndexOrThrow(_stmt, "model")
        val _columnIndexOfPlaystyleKey: Int = getColumnIndexOrThrow(_stmt, "playstyleKey")
        val _columnIndexOfGeneral: Int = getColumnIndexOrThrow(_stmt, "general")
        val _columnIndexOfRedDot: Int = getColumnIndexOrThrow(_stmt, "redDot")
        val _columnIndexOfScope2x: Int = getColumnIndexOrThrow(_stmt, "scope2x")
        val _columnIndexOfScope4x: Int = getColumnIndexOrThrow(_stmt, "scope4x")
        val _columnIndexOfSniper: Int = getColumnIndexOrThrow(_stmt, "sniper")
        val _columnIndexOfFreeLook: Int = getColumnIndexOrThrow(_stmt, "freeLook")
        val _columnIndexOfDpi: Int = getColumnIndexOrThrow(_stmt, "dpi")
        val _columnIndexOfFireButtonSize: Int = getColumnIndexOrThrow(_stmt, "fireButtonSize")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<SavedPresetEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SavedPresetEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpBrand: String
          _tmpBrand = _stmt.getText(_columnIndexOfBrand)
          val _tmpModel: String
          _tmpModel = _stmt.getText(_columnIndexOfModel)
          val _tmpPlaystyleKey: String
          _tmpPlaystyleKey = _stmt.getText(_columnIndexOfPlaystyleKey)
          val _tmpGeneral: Int
          _tmpGeneral = _stmt.getLong(_columnIndexOfGeneral).toInt()
          val _tmpRedDot: Int
          _tmpRedDot = _stmt.getLong(_columnIndexOfRedDot).toInt()
          val _tmpScope2x: Int
          _tmpScope2x = _stmt.getLong(_columnIndexOfScope2x).toInt()
          val _tmpScope4x: Int
          _tmpScope4x = _stmt.getLong(_columnIndexOfScope4x).toInt()
          val _tmpSniper: Int
          _tmpSniper = _stmt.getLong(_columnIndexOfSniper).toInt()
          val _tmpFreeLook: Int
          _tmpFreeLook = _stmt.getLong(_columnIndexOfFreeLook).toInt()
          val _tmpDpi: Int
          _tmpDpi = _stmt.getLong(_columnIndexOfDpi).toInt()
          val _tmpFireButtonSize: Int
          _tmpFireButtonSize = _stmt.getLong(_columnIndexOfFireButtonSize).toInt()
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          _item =
              SavedPresetEntity(_tmpId,_tmpTitle,_tmpBrand,_tmpModel,_tmpPlaystyleKey,_tmpGeneral,_tmpRedDot,_tmpScope2x,_tmpScope4x,_tmpSniper,_tmpFreeLook,_tmpDpi,_tmpFireButtonSize,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteById(id: Long) {
    val _sql: String = "DELETE FROM saved_presets WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
