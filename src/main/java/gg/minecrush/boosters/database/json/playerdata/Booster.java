package gg.minecrush.boosters.database.json.playerdata;

public class Booster {
    private int amount;
    private int time;
    private int multiplier;
    private String name;

    public Booster(int amount, int time, int multiplier, String name) {
        this.amount = amount;
        this.time = time;
        this.multiplier = multiplier;
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setName(String name){
        this.name = name;
    }


    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getName(){
        return name;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }
}
