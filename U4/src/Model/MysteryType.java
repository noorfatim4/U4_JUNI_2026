package Model;

public enum MysteryType {
    TIMEJUMP,
    NARCISSUS,
    ADDITIVE;

    /**
     * Denna metod returnerar en slumpmässigt vald mysterie-typ.
     * @return MysteryType
     * @author Noor-Fatima Nabi
     */
    public static MysteryType randomType() {
        MysteryType[] values = values();
        return values[(int)(Math.random() * values.length)];
    }
}
