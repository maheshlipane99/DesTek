package com.example.destek.DB.dao;




import com.example.destek.DB.tabels.Category;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM Category")
    LiveData<List<Category>> getAllItem();

    @Query("SELECT * FROM Category where id=:id")
    Category findItemById(int id);

    @Query("SELECT COUNT(*) from Category")
    int itemCount();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(Category category);

    @Delete
    int deleteItem(Category category);

    @Query("DELETE FROM Category")
    int deleteAllItem();
}
