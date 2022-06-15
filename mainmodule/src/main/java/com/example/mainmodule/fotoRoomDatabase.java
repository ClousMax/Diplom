package com.example.mainmodule;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {FotoData.class}, version = 2, exportSchema = false)
public abstract class fotoRoomDatabase extends RoomDatabase {

    abstract FotoDao fotoDao();

    private static volatile fotoRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static fotoRoomDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (fotoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    fotoRoomDatabase.class, "fotoss_database")
                            .addCallback(sFotoDatabaseCallback)
                            .build();


                }
            }
        }
        return INSTANCE;
    }
    private static fotoRoomDatabase.Callback sFotoDatabaseCallback = new fotoRoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                FotoDao dao = INSTANCE.fotoDao();
                dao.deleteAll();
                FotoData word = new FotoData("Name","Test");
                dao.insert(word);

            });
        }
    };
}
