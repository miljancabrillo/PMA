package com.pma.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Activity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int activityTypeId;
    private String name;
    private String userId;
    private Date date;
    private Date startTime;
    private Date endTime;
    private boolean finished = false;
    private float duration;
    private float kcalBurned;
    private boolean isSynced = false;
    @Ignore
    private List<Location> locations;
    @Ignore
    private ActivityType activityType;
    @Ignore
    private User user;

    public float calculateBurnedKcals(){
        if(user == null || activityType == null) return 0;
        this.kcalBurned = (activityType.getMet() * 3.5f *user.getWeight()/200)*duration;
        return this.kcalBurned;
    }

    public void setActivityType(ActivityType activityType){
        this.activityTypeId = activityType.getId();
        this.activityType = activityType;
        this.name = activityType.getName();
    }

    public void setUser(User user){
        this.userId = user.getEmail();
        this.user = user;
    }

    public void setSynced(boolean synced){
        this.isSynced = synced;
    }
}
