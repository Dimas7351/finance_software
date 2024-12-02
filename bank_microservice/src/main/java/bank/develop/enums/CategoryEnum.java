package bank.develop.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum CategoryEnum {

    FOOD, HEALTH, TRANSPORT, OTHERS;

    private static final List<CategoryEnum> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static CategoryEnum randomCategory()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
