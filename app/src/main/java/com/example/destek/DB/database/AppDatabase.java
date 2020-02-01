package com.example.destek.DB.database;

import android.content.Context;

import com.example.destek.DB.converter.ArrayListConverter;
import com.example.destek.DB.converter.DateConverter;
import com.example.destek.DB.dao.CategoryDao;
import com.example.destek.DB.dao.ProductDao;
import com.example.destek.DB.dao.TaxDao;
import com.example.destek.DB.dao.VariantDao;
import com.example.destek.DB.tabels.Category;
import com.example.destek.DB.tabels.Product;
import com.example.destek.DB.tabels.Tax;
import com.example.destek.DB.tabels.Variant;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {
         Category.class, Product.class, Tax.class, Variant.class}, version =1, exportSchema = false)
@TypeConverters({DateConverter.class, ArrayListConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    public static final String DATABASE_NAME = "destek_app";


    public abstract CategoryDao getCategoryDao();
    public abstract ProductDao getProductDao();
    public abstract VariantDao getVariantDao();
    public abstract TaxDao getTaxDao();


    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getAppDatabase(final Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext());
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        AppDatabase database = AppDatabase.getAppDatabase(appContext);
                        database.setDatabaseCreated();
                    }
                })
                .fallbackToDestructiveMigration()
                .build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}

