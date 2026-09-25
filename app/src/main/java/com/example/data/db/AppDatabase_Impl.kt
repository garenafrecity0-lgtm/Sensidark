package com.example.data.db

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.annotation.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _presetDao: Lazy<PresetDao> = lazy {
    PresetDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1,
        "4f9e36a5dcd562bc747822b641f5e771", "fdb27acc4a50b96c0ce8df5b968e7545") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `saved_presets` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `brand` TEXT NOT NULL, `model` TEXT NOT NULL, `playstyleKey` TEXT NOT NULL, `general` INTEGER NOT NULL, `redDot` INTEGER NOT NULL, `scope2x` INTEGER NOT NULL, `scope4x` INTEGER NOT NULL, `sniper` INTEGER NOT NULL, `freeLook` INTEGER NOT NULL, `dpi` INTEGER NOT NULL, `fireButtonSize` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '4f9e36a5dcd562bc747822b641f5e771')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `saved_presets`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsSavedPresets: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSavedPresets.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSavedPresets.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSavedPresets.put("brand", TableInfo.Column("brand", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSavedPresets.put("model", TableInfo.Column("model", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSavedPresets.put("playstyleKey", TableInfo.Column("playstyleKey", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSavedPresets.put("general", TableInfo.Column("general", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSavedPresets.put("redDot", TableInfo.Column("redDot", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSavedPresets.put("scope2x", TableInfo.Column("scope2x", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSavedPresets.put("scope4x", TableInfo.Column("scope4x", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSavedPresets.put("sniper", TableInfo.Column("sniper", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSavedPresets.put("freeLook", TableInfo.Column("freeLook", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSavedPresets.put("dpi", TableInfo.Column("dpi", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSavedPresets.put("fireButtonSize", TableInfo.Column("fireButtonSize", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSavedPresets.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSavedPresets: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSavedPresets: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSavedPresets: TableInfo = TableInfo("saved_presets", _columnsSavedPresets,
            _foreignKeysSavedPresets, _indicesSavedPresets)
        val _existingSavedPresets: TableInfo = read(connection, "saved_presets")
        if (!_infoSavedPresets.equals(_existingSavedPresets)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |saved_presets(com.example.data.db.SavedPresetEntity).
              | Expected:
              |""".trimMargin() + _infoSavedPresets + """
              |
              | Found:
              |""".trimMargin() + _existingSavedPresets)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "saved_presets")
  }

  public override fun clearAllTables() {
    super.performClear(false, "saved_presets")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(PresetDao::class, PresetDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun presetDao(): PresetDao = _presetDao.value
}
