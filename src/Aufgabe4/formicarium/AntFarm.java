package formicarium;

/**
 * ToDo: Write out specification and add subtype relation as stated
 * Eine Form eines Ameisennests bestehend aus einem Substrat
 * (z. B. Sand, Kies oder Erde) zwischen zwei Glas- oder Kunststoffplatten, die an den Rändern miteinander verbunden sind. Durch die
 * Platten sind die von den Ameisen ins Substrat gegrabenen Gänge beobachtbar. Je nach Substrat sind solche Ameisenfarmen für
 * Ameisen unterschiedlicher Größen und mit unterschiedlichen natürlichen Lebensräumen geeignet. Auch der Plattenabstand spielt für
 * die Ameisengröße eine Rolle, da Ameisen ohne ausreichend Platz
 * keine Gänge anlegen können und sich bei zu viel Platz in der Mitte
 * verstecken (von außen nicht beobachtbar sind).
 */
public class AntFarm implements Nest {
    @Override
    public Arena getArena() {
        return null;
    }
}
