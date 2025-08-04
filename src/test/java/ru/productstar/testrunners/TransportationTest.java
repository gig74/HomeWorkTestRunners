package ru.productstar.testrunners;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.support.ParameterDeclarations;
import ru.productstar.testrunners.exceptions.MovingFragileFarawayException;

import java.util.stream.Stream;

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

    @Tag("Smoke")
    @ParameterizedTest
    @ArgumentsSource(CalcPriceArgumentsProvider.class)
    @DisplayName("Расчеты стоимости тестовые")
    void calcPriceTransportation(Transportation transportation, float price) {
        assertEquals(price, Transportation.getCurrentPrice(transportation), "Неверно посчитана цена" + " для " + transportation.toString() + " Расчетная цена " + Transportation.getCurrentPrice(transportation));
    }

    @Test
    @Tag("Negative")
    @DisplayName("Некорректно указано расстояние перевозки")
    void illegalDistance() {
        assertThrows(IllegalArgumentException.class, () -> new Transportation(0, TransitServiceWorkload.NORMAL, false, false));
        assertThrows(RuntimeException.class, () -> new Transportation(-1, TransitServiceWorkload.NORMAL, false, false)); // Проверка, что IllegalArgumentException унаследован от RuntimeException (на всякий случай)
    }

    @Test
    @Tag("Negative")
    @DisplayName("Хрупкую вещь слишком далеко везут")
    void movingFragileFaraway() {
        assertThrows(MovingFragileFarawayException.class, () -> new Transportation(31, TransitServiceWorkload.NORMAL, false, true));
        assertThrows(RuntimeException.class, () -> new Transportation(31, TransitServiceWorkload.NORMAL, false, true)); // Проверка, что IllegalArgumentException унаследован от MovingFragileFarawayException (на всякий случай)
    }

    static class CalcPriceArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    Arguments.of(new Transportation(1, TransitServiceWorkload.NORMAL, false, false), 150f),
                    Arguments.of(new Transportation(25, TransitServiceWorkload.LITTLEHIGH, false, false), 360f),
                    Arguments.of(new Transportation(10, TransitServiceWorkload.LITTLEHIGH, true, false), 360f),
                    Arguments.of(new Transportation(30, TransitServiceWorkload.VERYHIGH, true, true), 1120f),
                    Arguments.of(new Transportation(2147483647, TransitServiceWorkload.MEDIUMHIGH, true, false), 700f)
            );
        }
    }
}