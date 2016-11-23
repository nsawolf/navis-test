package enumerations;

public enum Action {
    Hit ("h"), Stay ("s"), Busted ("b");
    private final String value;

    Action(String value) {this.value = value;}

    public String getValue() {return this.value;}
}
