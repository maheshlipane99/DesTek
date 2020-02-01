package com.example.destek.DB.dao;





import com.example.destek.DB.tabels.Variant;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface VariantDao {

    @Query("SELECT * FROM Variant")
    LiveData<List<Variant>> getAllItem();

    @Query("SELECT * FROM Variant where id=:id")
    Variant findItemById(int id);

    @Query("SELECT * FROM Variant where ProductId=:id")
    LiveData<List<Variant>> findItemByProductId(int id);

    @Query("SELECT COUNT(*) from Variant")
    int itemCount();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(Variant variant);

    @Delete
    int deleteItem(Variant variant);

    @Query("DELETE FROM Variant")
    int deleteAllItem();
}
