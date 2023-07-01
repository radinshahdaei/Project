package Server.Model;

import Client.Controller.GameMenuController;
import Client.Model.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PathFinder {
    private boolean[][] map;
    private int[][] distance;
    private Client.Model.Pair[][] parent;
    private Queue<Client.Model.Pair> q;
    private ArrayList <Client.Model.Pair> path;
    private int xStart , yStart , xEnd , yEnd , mapSize;

    public PathFinder(boolean[][] map , int mapSize , int x1 , int x2 , int y1 , int y2) {
        this.map = map;
        this.mapSize = mapSize;
        this.q = new LinkedList<>();
        this.path = new ArrayList<>();
        this.xStart = x1; this.xEnd = x2;
        this.yStart = y1; this.yEnd = y2;
        this.distance = new int[mapSize][mapSize];
        this.parent = new Client.Model.Pair[mapSize][mapSize];

        for (int i = 0; i < GameMenuController.mapSize; ++i) {
            for (int j = 0; j < GameMenuController.mapSize; ++j) {
                this.distance[i][j] = ((i == x1 && j == y1) ? 0 : 1000000);
                this.parent[i][j] = new Client.Model.Pair(-1, -1);
            }
        }
    }

    public void shortestPath() {
        q.add(new Client.Model.Pair(xStart , yStart));
        while(q.size() > 0) {
            Client.Model.Pair current = q.remove(); //front element
            int x = current.first , y = current.second;
            //left
            if(x > 0 && map[x - 1][y] == true && distance[x][y] + 1 < distance[x - 1][y]) {
                distance[x - 1][y] = distance[x][y] + 1;
                parent[x - 1][y].set(x , y);
                q.add(new Client.Model.Pair(x - 1, y));
            }
            //right
            if(x + 1 < mapSize && map[x + 1][y] == true && distance[x][y] + 1 < distance[x + 1][y]) {
                distance[x + 1][y] = distance[x][y] + 1;
                parent[x + 1][y].set(x, y);
                q.add(new Client.Model.Pair(x + 1 , y));
            }
            //up
            if(y + 1 < mapSize && map[x][y + 1] == true && distance[x][y] + 1 < distance[x][y + 1]) {
                distance[x][y + 1] = distance[x][y] + 1;
                parent[x][y + 1].set(x , y);
                q.add(new Client.Model.Pair(x , y + 1));
            }
            //down
            if(y > 0 && map[x][y - 1] == true && distance[x][y] + 1 < distance[x][y - 1]) {
                distance[x][y - 1] = distance[x][y] + 1;
                parent[x][y - 1].set(x, y);
                q.add(new Client.Model.Pair(x , y - 1));
            }
        }

        if(distance[xEnd][yEnd] == 1000000) {this.path = null; return;}

        Client.Model.Pair current = new Client.Model.Pair(xEnd, yEnd);
        while(!current.check()) {
            path.add(current);
            current = parent[current.first][current.second];
        }
    }

    public ArrayList<Client.Model.Pair> getPath() {
        return this.path;
    }

    public void showPath() {
        int[][] result = new int[mapSize][mapSize];
        for (int i = 0; i < mapSize; ++i) {
            for (int j = 0; j < mapSize; ++j) {
                result[i][j] = (map[i][j] == true ? 1 : 0);
            }
        }
        if(this.path != null) {
            for (Pair curr : this.path)
                result[curr.first][curr.second] = 2;
        }

        for (int i = 0; i < mapSize; ++i) {
            for (int j = 0; j < mapSize; ++j) {
                System.out.print(result[i][j] == 0 ? 'X' : result[i][j] == 1 ? 'O' : '-');
            }
            System.out.println("");
        }
    }
}

