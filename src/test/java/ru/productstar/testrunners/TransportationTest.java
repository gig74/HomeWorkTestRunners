package ru.productstar.testrunners;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.productstar.testrunners.exceptions.MovingFragileFarawayException;

import static org.junit.jupiter.api.Assertions.*;

class TransportationTest {

    @Test
    @Tag("Smoke")
    @DisplayName("Проверка соответствия коэффициентов в зависимости от загрузки")
    void TransitServiceWorkloadCheckFactor() {
        TransitServiceWorkload veryHigh = TransitServiceWorkload.VERYHIGH;
        assertAll(
                () -> assertEquals(1.6f, TransitServiceWorkload.VERYHIGH.getFactor(), "Некорректен коэффициент для очень высокой загруженности"),
                () -> assertEquals(1.4f, TransitServiceWorkload.MEDIUMHIGH.getFactor(), "Некорректен коэффициент для высокой загруженности"),
                () -> assertEquals(1.2f, TransitServiceWorkload.LITTLEHIGH.getFactor(), "Некорректен коэффициент для повышенной загруженности"),
                () -> assertEquals(1.0f, TransitServiceWorkload.NORMAL.getFactor(), "Некорректен коэффициент без загруженности")
        );
    }

    @Test
    @Tag("Smoke")
    @ParametrizedTest
    @DisplayName("Расчеты стоимости тестовые")
    void calcPriceTransportation() {
        Transportation transportation01 = new Transportation(1, TransitServiceWorkload.NORMAL, false, false);
        Transportation transportation02 = new Transportation(25, TransitServiceWorkload.LITTLEHIGH, false, false);
        Transportation transportation03 = new Transportation(10, TransitServiceWorkload.LITTLEHIGH, true, false);
        Transportation transportation04 = new Transportation(30, TransitServiceWorkload.VERYHIGH, true, true);
        Transportation transportation05 = new Transportation(2147483647, TransitServiceWorkload.MEDIUMHIGH, true, false);
        float price01 = 150;
        float price02 = 360;
        float price03 = 360;
        float price04 = 1120;
        float price05 = 700;
        assertAll(
                () -> assertEquals(price01, Transportation.getCurrentPrice(transportation01), "Неверно посчитана цена" + " для " + transportation01.toString() + " Расчетная цена " + Transportation.getCurrentPrice(transportation01)),
                () -> assertEquals(price02, Transportation.getCurrentPrice(transportation02), "Неверно посчитана цена" + " для " + transportation02.toString() + " Расчетная цена " + Transportation.getCurrentPrice(transportation02)),
                () -> assertEquals(price03, Transportation.getCurrentPrice(transportation03), "Неверно посчитана цена" + " для " + transportation03.toString() + " Расчетная цена " + Transportation.getCurrentPrice(transportation03)),
                () -> assertEquals(price04, Transportation.getCurrentPrice(transportation04), "Неверно посчитана цена" + " для " + transportation04.toString() + " Расчетная цена " + Transportation.getCurrentPrice(transportation04)),
                () -> assertEquals(price05, Transportation.getCurrentPrice(transportation05), "Неверно посчитана цена" + " для " + transportation05.toString() + " Расчетная цена " + Transportation.getCurrentPrice(transportation05))
        );
    }

    @Test
    @Tag("Negative")
    @DisplayName("Некорректно указано расстояние перевозки")
    void illegalDistance() {
        assertThrows(IllegalArgumentException.class, () -> new  Transportation(0, TransitServiceWorkload.NORMAL, false, false) ) ;
        assertThrows(RuntimeException.class, () -> new  Transportation(-1 , TransitServiceWorkload.NORMAL, false, false) ) ; // Проверка, что IllegalArgumentException унаследован от RuntimeException (на всякий случай)
    }

    @Test
    @Tag("Negative")
    @DisplayName("Хрупкую вещь слишком далеко везут")
    void movingFragileFaraway() {
        assertThrows(MovingFragileFarawayException.class, () -> new  Transportation(31, TransitServiceWorkload.NORMAL, false, true) ) ;
        assertThrows(RuntimeException.class, () -> new  Transportation(31 , TransitServiceWorkload.NORMAL, false, true) ) ; // Проверка, что IllegalArgumentException унаследован от MovingFragileFarawayException (на всякий случай)
    }

}