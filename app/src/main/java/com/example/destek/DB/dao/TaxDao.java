package com.example.destek.DB.dao;






import com.example.destek.DB.tabels.Tax;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface TaxDao {

    @Query("SELECT * FROM Tax")
    LiveData<List<Tax>> getAllItem();

    @Query("SELECT * FROM Tax where id=:id")
    Tax findItemById(int id);

    @Query("SELECT * FROM Tax where ProductId=:id")
    LiveData<Tax> findItemByProductId(int id);

    @Query("SELECT COUNT(*) from Tax")
    int itemCount();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(Tax tax);

    @Delete
    int deleteItem(Tax tax);

    @Query("DELETE FROM Tax")
    int deleteAllItem();
}
