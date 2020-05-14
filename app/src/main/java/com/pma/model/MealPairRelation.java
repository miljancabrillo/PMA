package com.pma.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MealPairRelation {
    @Embedded
    private Meal meal;
    @Relation(
            parentColumn = "id",
            entityColumn = "mealId"
    )
    private List<GroceryAndAmountPair> pairs = new ArrayList<>();
}
