package bank.develop.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum PurchaseTypeEnum {

    INCOME, OUTCOME;

    private static final List<PurchaseTypeEnum> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static PurchaseTypeEnum randomPurchaseType()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

}
