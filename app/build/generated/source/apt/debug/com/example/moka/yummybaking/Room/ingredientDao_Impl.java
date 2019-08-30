package com.example.moka.yummybaking.Room;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import android.support.annotation.NonNull;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class ingredientDao_Impl implements ingredientDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfingredient;

  private final SharedSQLiteStatement __preparedStmtOfDeletetable;

  private final SharedSQLiteStatement __preparedStmtOfDeleteingredientbyId;

  public ingredientDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfingredient = new EntityInsertionAdapter<ingredient>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `ingredient_table`(`id`,`recipename`,`ingredient`,`quantity`,`measure`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ingredient value) {
        stmt.bindLong(1, value.id);
        stmt.bindLong(2, value.getRecipename());
        if (value.getIngredient() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getIngredient());
        }
        stmt.bindDouble(4, value.getQuantity());
        if (value.getMeasure() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getMeasure());
        }
      }
    };
    this.__preparedStmtOfDeletetable = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELete From ingredient_table";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteingredientbyId = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM ingredient_table WHERE ingredientid =?";
        return _query;
      }
    };
  }

  @Override
  public void insertingredient(ingredient ingredientEntry) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfingredient.insert(ingredientEntry);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deletetable() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeletetable.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeletetable.release(_stmt);
    }
  }

  @Override
  public void deleteingredientbyId(int ingredient_id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteingredientbyId.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      _stmt.bindLong(_argIndex, ingredient_id);
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteingredientbyId.release(_stmt);
    }
  }

  @Override
  public LiveData<List<ingredient>> getAllingredient_ofrecipe(String recipename) {
    final String _sql = "SELECT * FROM ingredient_table where recipename=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (recipename == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, recipename);
    }
    return new ComputableLiveData<List<ingredient>>() {
      private Observer _observer;

      @Override
      protected List<ingredient> compute() {
        if (_observer == null) {
          _observer = new Observer("ingredient_table") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfRecipename = _cursor.getColumnIndexOrThrow("recipename");
          final int _cursorIndexOfIngredient = _cursor.getColumnIndexOrThrow("ingredient");
          final int _cursorIndexOfQuantity = _cursor.getColumnIndexOrThrow("quantity");
          final int _cursorIndexOfMeasure = _cursor.getColumnIndexOrThrow("measure");
          final List<ingredient> _result = new ArrayList<ingredient>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final ingredient _item;
            final int _tmpRecipename;
            _tmpRecipename = _cursor.getInt(_cursorIndexOfRecipename);
            final String _tmpIngredient;
            _tmpIngredient = _cursor.getString(_cursorIndexOfIngredient);
            final double _tmpQuantity;
            _tmpQuantity = _cursor.getDouble(_cursorIndexOfQuantity);
            final String _tmpMeasure;
            _tmpMeasure = _cursor.getString(_cursorIndexOfMeasure);
            _item = new ingredient(_tmpRecipename,_tmpIngredient,_tmpQuantity,_tmpMeasure);
            _item.id = _cursor.getInt(_cursorIndexOfId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
