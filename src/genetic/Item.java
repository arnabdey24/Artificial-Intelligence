package genetic;

public class Item implements Cloneable{

    private String name;

    private double weight;

    private int value;

    private boolean included;

    public Item(String name, double weight, int value){
        this.name=name;
        this.weight=weight;
        this.value=value;
        included=false;
    }

    public Item(Item other){
        this.name=other.name;
        this.value=other.value;
        this.weight= other.weight;
        this.included= other.included;
    }

    public double getWeight(){
        return weight;
    }
    public int getValue(){
        return value;
    }
    public boolean isIncluded(){
        return included;
    }

    public void setIncluded(boolean included){
        this.included=included;
    }

    public String toString(){
        return String.format("%s (%f lbs, $%d)",name,weight,value);
    }

    @Override
    public Item clone() {
        try {
            Item clone = (Item) super.clone();
            clone.name=this.name;
            clone.weight=this.weight;
            clone.value=this.value;
            clone.included=this.included;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
