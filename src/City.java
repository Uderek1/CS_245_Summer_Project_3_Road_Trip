import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class City implements Comparable<City> {

    public String location;
    public HashMap<String,Attraction> attractions;
    public ArrayList<Road> roads;

    // For Dijkstra's
    public boolean visited = false;
    public int cost = 0;
    public City prev;
    public boolean isSource = false;
    public boolean isDestination = false;

    public City(String location) {
        this.location = location;
        attractions=new HashMap<>();
        roads=new ArrayList<>();
    }



    public Boolean isConnected(City city,int[][] distanceMatrix,int a,int b){
        if(this == city){
            distanceMatrix[a][b]=0;
            return true;
        }
        for (int i = 0; i <roads.size() ; i++) {
            if(city==roads.get(i).startLocation){
                distanceMatrix[a][b]=roads.get(i).distance;
                return true;
            }
            if(city==roads.get(i).endLocation){
                distanceMatrix[a][b]=roads.get(i).distance;
                return true;
            }
        }
        distanceMatrix[a][b]=Road_Trip.INF;

        return false;
    }

    // Returns a Road that can be used as a route.
    //Perfect when you need the start location to be the end location, and vice versa
    public Road createRoute(City city){
        for(Road road: roads){
            if(road.getNextDestination(city) != null){
                return new Road(city, this, road.distance, road.time);
            }
        }
        return null;
    }

    @Override
    public String toString(){
        return location;
    }

    @Override
    public boolean equals(Object obj) {
        //null instanceof Object will always return false
        if (!(obj instanceof City))
            return false;
        if (obj == this)
            return true;
        return this.location.equals(((City)obj).location);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.location);
        return hash;
    }

    public void reset(){
        // For Dijkstra's
        visited = false;
        cost = 0;
        City prev = null;
        isSource = false;
        isDestination = false;
    }

    @Override
    public int compareTo(City city) {
        if(this.cost > city.cost)
            return 1;
        else if(this.cost < city.cost)
            return -1;
        return 0;
    }
}

