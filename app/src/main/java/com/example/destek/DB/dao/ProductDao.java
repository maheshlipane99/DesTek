package com.example.destek.DB.dao;




import com.example.destek.DB.tabels.Product;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM Product")
    LiveData<List<Product>> getAllItem();

    @Query("SELECT * FROM Product where id=:id")
    LiveData<Product> findItemById(int id);

    @Query("SELECT * FROM Product where CategoryId=:id")
    LiveData<List<Product>> findItemByCatId(int id);

    @Query("SELECT COUNT(*) from Product")
    int itemCount();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(Product product);

    @Delete
    int deleteItem(Product product);

    @Query("DELETE FROM Product")
    int deleteAllItem();
}
