public class Object implements Comparable<Object> {


    private int value, weight;
    private double ratio;



    public Object(int v, int w){
        this.value = v;
        this.weight = w;
        ratio = (double) v/w;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    @Override
    public int compareTo(Object other){
        return Double.compare(other.getRatio(), this.ratio);
    }

    @Override
    public String toString(){
        return "( " + weight + ", " + value + ")";
    }
}