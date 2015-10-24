package group8.mealhelper.database;

import java.lang.ref.SoftReference;

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
            public static final String RECIPE = "recipe";

        }
    }
    public static final class IngredientTable{
        public static final String NAME = "ingredients";

        public static final class Cols {
            public static final String INGREDIENT_ID = "id";
            public static final String MEAL_ID = "mealId";
            public static final String NAME = "name";
            public static final String AMOUNT = "amount";
            public static final String UNIT = "unit";

        }
    }

    public static final class MenuTable{
        public static final String NAME = "menu";

        public static final class Cols{
            public static final String Menu_ID = "id";
            public static final String DAY = "day";
            public static final String MEAL = "meal";
        }
    }

//    public static final class UnitTable{
//        public static final String NAME = "units";
//
//        public static final class Cols{
//            public static final String UNIT_ID = "id";
//            public static final String NAME = "name";
//        }
    //}
}
