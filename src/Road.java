public class Road implements Comparable<Road> {
    City startLocation;
    City endLocation;
    int distance;
    int time;

    public Road(City startLocation, City endLocation, int distance, int time) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distance = distance;
        this.time = time;
    }

    public City getNextDestination(City city){
        if(city == startLocation)
            return endLocation;
        else if(city == endLocation)
            return startLocation;
        else
            return null;
    }

    public int compareTo(Road compareEdge) {
        if(Road_Trip.weightType.equals("time")){
        }
        return this.distance - compareEdge.distance;
    }

    @Override
    public String toString(){
        return startLocation+" to "+endLocation+" "+distance+" "+time;
    }
}
