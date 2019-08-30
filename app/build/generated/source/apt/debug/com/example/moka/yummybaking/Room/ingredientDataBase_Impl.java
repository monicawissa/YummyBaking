package com.example.moka.yummybaking.Room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class ingredientDataBase_Impl extends ingredientDataBase {
  private volatile ingredientDao _ingredientDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `ingredient_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `recipename` INTEGER NOT NULL, `ingredient` TEXT, `quantity` REAL NOT NULL, `measure` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"ce0edc394b9e2b989a8427f9e71710b7\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `ingredient_table`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsIngredientTable = new HashMap<String, TableInfo.Column>(5);
        _columnsIngredientTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsIngredientTable.put("recipename", new TableInfo.Column("recipename", "INTEGER", true, 0));
        _columnsIngredientTable.put("ingredient", new TableInfo.Column("ingredient", "TEXT", false, 0));
        _columnsIngredientTable.put("quantity", new TableInfo.Column("quantity", "REAL", true, 0));
        _columnsIngredientTable.put("measure", new TableInfo.Column("measure", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysIngredientTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesIngredientTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoIngredientTable = new TableInfo("ingredient_table", _columnsIngredientTable, _foreignKeysIngredientTable, _indicesIngredientTable);
        final TableInfo _existingIngredientTable = TableInfo.read(_db, "ingredient_table");
        if (! _infoIngredientTable.equals(_existingIngredientTable)) {
          throw new IllegalStateException("Migration didn't properly handle ingredient_table(com.example.moka.yummybaking.Room.ingredient).\n"
                  + " Expected:\n" + _infoIngredientTable + "\n"
                  + " Found:\n" + _existingIngredientTable);
        }
      }
    }, "ce0edc394b9e2b989a8427f9e71710b7", "757f89d4b05782e90987503c8bcdd5bf");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "ingredient_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `ingredient_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public ingredientDao ingredientDao() {
    if (_ingredientDao != null) {
      return _ingredientDao;
    } else {
      synchronized(this) {
        if(_ingredientDao == null) {
          _ingredientDao = new ingredientDao_Impl(this);
        }
        return _ingredientDao;
      }
    }
  }
}
