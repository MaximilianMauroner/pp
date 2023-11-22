package formicarium;

/**
 * Ein Bestandteil eines Formicariums, das dafür vorgesehen ist eine
 * Ameisenkönigin zu beherbergen. Für kurze Zeit (wenige Tage, etwa
 * für den Transport) kann das Nest alleine als Formicarium genutzt
 * werden, auf Dauer sind Ameisen darin ohne zusätzliche Arena nicht
 * überlebensfähig. Bei kleineren Formicarien wird das Nest häufig in
 * einer Arena untergebracht, bei größeren ist das Nest nicht selten
 * ein eigenständiger, mit mehr oder weniger langen Röhren mit der
 * Arena verbundener Behälter
 */
public interface Nest extends Formicarium {
    // Pre: -
    // Post: returns true if the Nest is a Formicarium, false otherwise
    boolean isFormicarium();
}
