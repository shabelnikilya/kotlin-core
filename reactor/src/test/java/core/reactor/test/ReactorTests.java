package core.reactor.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Locale;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class ReactorTests {

    private static final Flux<String> colorFlux = Flux.just("red", "green", "blue");
    private final static Flux<String> fluxFruits = Flux.just("apple", "pear", "plum");
    private static final Flux<Integer> fluxAmounts = Flux.just(10, 20, 30, 40);

    /**
     * Flux - это publisher, который может испускать от 0 до n элементов.
     * Mono - от 0 до 1, в отличие от Flux.
     */
    @Test
    public void simpleFluxExample() {
        colorFlux.subscribe(System.out::println);
    }

    /**
     * Почти все методы в Flux API возвращают Flux или Mono, что означает что можно выстроить цепочку операторов.
     * Каждый оператор добавляет поведение к Publisher (Flux/Mono) и переносит publisher предыдущего
     * шага в новый экземпляр. Данные поступают от первого издателя и перемещаются по цепочке, трансформируясь
     * каждым оператором. В конце концов подписчик завершает процесс.
     *
     * Ничего не происходит пока подписчик не подпишется на издателя.
     */
    @Test
    public void simpleFluxExampleWithLog() {
        colorFlux.log()
                .subscribe(System.out::println);

        System.out.println("Демонстрация, что без подписки обработка не запустится(дальше вывода не будет)!");
        colorFlux.log();
    }

    @Test
    public void fluxExampleMap() {
        colorFlux.log()
                .map(c -> c.toUpperCase(Locale.ROOT))
                .log()
                .subscribe(System.out::println);
    }

    @Test
    public void fluxExampleZip() {
        Flux.zip(colorFlux, fluxAmounts, fluxFruits)
                .log()
                .subscribe(System.out::println);
    }

    /**
     * Обработка ошибок.
     * В reactive streams ошибки - это терминальные события. При возникновении ошибки вся последовательность
     * останавливается, и ошибка передается методу onError подписчика, который всегда должен быть определен.
     * Если не определен то onError вызовет UnsupportedOperationException.
     *
     */
    @Test
    public void onErrorExample() {
        Flux<String> fluxCalc = Flux.just(-1, 0, 1)
                .log()
                .map(i -> "10 / " + i + " = " + (10 / i))
                .onErrorReturn(ArithmeticException.class, "Арифметическая ошибка!") // похоже на перехват исключение и его обработку
                .log();

        fluxCalc.subscribe(value -> System.out.println("Next: " + value),
                error -> System.err.println("Error: " + error));
    }

    /**
     * Тестирование реактивного стека с помощью StepVerify
     */
    @Test
    public void stepVerifierSimpleTest() {
        Flux<String> fluxCalc = Flux.just(-1, 0, 1)
                .map(i -> "10 / " + i + " = " + (10 / i));

        StepVerifier.create(fluxCalc)
                .expectNextCount(1)
                .expectError(ArithmeticException.class)
                .verify();
    }

    @Test
    public void publishSubscribeExample() {
        Scheduler schedulerA = Schedulers.newParallel("Scheduler A");
        Scheduler schedulerB = Schedulers.newParallel("Scheduler B");
        Scheduler schedulerC = Schedulers.newParallel("Scheduler C");

        Flux.just(1)
                .map(i -> {
                    System.out.println("First map: " + Thread.currentThread().getName());
                    return i;
                })
                .subscribeOn(schedulerA)
                .map(i -> {
                    System.out.println("Second map: " + Thread.currentThread().getName());
                    return i;
                })
                .publishOn(schedulerB)
                .map(i -> {
                    System.out.println("Third map: " + Thread.currentThread().getName());
                    return i;
                })
                .subscribeOn(schedulerC)
                .map(i -> {
                    System.out.println("Fourth map: " + Thread.currentThread().getName());
                    return i;
                })
                .publishOn(schedulerA)
                .map(i -> {
                    System.out.println("Fifth map: " + Thread.currentThread().getName());
                    return i;
                })
                .blockLast();
    }
}
