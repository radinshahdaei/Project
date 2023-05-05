package Model;

public class Pair {
    public int first , second;

    public Pair(int first , int second) {
        this.first = first;
        this.second = second;
    }

    public void set(int x , int y) {
        this.first = x;
        this.second = y;
    }

    public boolean check() {
        return (this.first == -1 || this.second == -1);
    }
}