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

    @Query("UPDATE  Product SET shares=:shareCount where id=:id")
    int setShareCount(int id, int shareCount);

    @Query("UPDATE  Product SET order_count=:orderCount where id=:id")
    int setOrderCount(int id, int orderCount);

    @Query("UPDATE  Product SET view_count=:viewsCount where id=:id")
    int setViewsCount(int id, int viewsCount);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(Product product);

    @Delete
    int deleteItem(Product product);

    @Query("DELETE FROM Product")
    int deleteAllItem();

    @Query("SELECT * FROM Product ORDER BY view_count DESC LIMIT 10")
    LiveData<List<Product>> getMostViewedProduct();

    @Query("SELECT * FROM Product ORDER BY shares DESC LIMIT 10 ")
    LiveData<List<Product>> getMostSharedProduct();

    @Query("SELECT * FROM Product ORDER BY order_count DESC LIMIT 10")
    LiveData<List<Product>> getMostOrderedProduct();

}
