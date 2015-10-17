package group8.mealhelper.database;

/**
 * Created by curtis on 10/16/15.
 */
public class DbSchema {
    public static final class MealTable{
        public static final String NAME = "meals";

        public static final class Cols{
            public static final String MEAL_ID = "id";
            public static final String NAME = "name";
            public static final String PIC_PATH = "picPath";

        }
    }
    public static final class IngredientTable{
        public static final String NAME = "ingredients";

        public static final class Cols {
            public static final String Ingredient_ID = "id";
            public static final String Name = "name";
            public static final String AMOUNT = "amount";
            public static final String Unit = "unit";
            public static final String MEAL_ID = "mealId";
        }
    }

    public static final class MenuTable{
        public static final String NAME = "menu";

        public static final class Cols{
            public static final String DAY = "day";
            public static final String Meal = "meal";
        }
    }
}
