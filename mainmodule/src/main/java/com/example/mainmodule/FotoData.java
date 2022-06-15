package com.example.mainmodule;

import java.util.Calendar;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fotoData")
public class FotoData {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "comment")
    private String comment;
    @ColumnInfo(name = "date")
    private String date;

    public FotoData(@NonNull String name, String comment) {

        this.name = name;
        this.comment = comment;
        this.date = getCurrentTime();

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;


    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }


    private String getCurrentTime() {
        // Текущее время в формате HH:MM:SS
        final Calendar calendar = Calendar.getInstance();
        return (new StringBuilder().append(calendar.get(Calendar.HOUR_OF_DAY))
                .append(":").append(calendar.get(Calendar.MINUTE)).append(":")
                .append(calendar.get(Calendar.SECOND)).append(" ")).toString();
    }


}
