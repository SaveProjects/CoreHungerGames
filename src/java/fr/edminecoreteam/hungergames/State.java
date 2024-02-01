package fr.edminecoreteam.hungergames;

public enum State
{
    WAITING("WAITING", 0),
    STARTING("STARTING", 1),
    PREPARATION("PREPARATION", 2),
    NOPVP("NOPVP", 3),
    INGAME("INGAME", 4),
    FINISH("FINISH", 5);

    private State(final String name, final int ordinal) { }
}
