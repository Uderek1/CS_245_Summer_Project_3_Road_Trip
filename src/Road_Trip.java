import java.util.*;

public class Road_Trip {
    static HashMap<String,City> cities=new HashMap();
    final static int INF = 99999;// Integer.MAX_VALUE; // Integer.MAX_VALUE;
    // CANNOT USE Integer.MAX_VALUE because when we add to see if the distance is greater, the values rolls over to Integer.MIN_VALUE and give us a wrong value.
    final static String weightType = "distance";

    public ArrayList<ArrayList<Road>>route(City starting_city,City ending_city, ArrayList<Attraction> attractions){
        ArrayList<ArrayList<Road>> routes = new ArrayList<>();

        ArrayList<String> visitingCities = new ArrayList<>();
        visitingCities.add(starting_city.location);
        for(Attraction attraction : attractions){
            visitingCities.add(attraction.location);
        }
        visitingCities.add(ending_city.location);


        City sc = starting_city;
        City ec = ending_city;

        visitingCities.remove(sc.location);

        while(!visitingCities.isEmpty()){
            ArrayList<Road> route = new ArrayList<>();

            for(int v = 0; v < visitingCities.size(); v++){
                if(cities.containsKey(visitingCities.get(v)) && !visitingCities.get(v).equals(ending_city.location)){
                    ec = cities.get(visitingCities.get(v));
                    visitingCities.remove(ec.location);
                }
                else if(visitingCities.size() == 1){
                    ec = cities.get(visitingCities.get(v));
                    visitingCities.remove(ec.location);
                }
            }

            DijkstrasAlgo(sc, ec);

            City city = ec;
            while(city != null){
                if(city.prev != null)
                    route.add(city.createRoute(city.prev));
                city = city.prev;
            }

            sc = ec;
            ec = null;

            routes.add(route);
        }

        return routes; // return list of roads (your route)
    }

    public void DijkstrasAlgo(City source, City destination){

        // Create Vertex Set
        Set set = cities.entrySet();

        PriorityQueue<City> queue  = new PriorityQueue<>();

        // for each vertex v in Graph
        Iterator iterator = set.iterator();
        int i = 0;
        while (iterator.hasNext()) { // for every node
            Map.Entry entry = (Map.Entry) iterator.next();
            City node = (City) entry.getValue();

            node.isSource = false;
            node.isDestination = false;

            if(source.location.equals(node.location)){
                node.isSource = true;
                source = node;
            }
            if(destination.location.equals(node.location)){
                node.isDestination = true;
                destination = node;
            }

            node.visited = false;
            node.cost = INF;
            node.prev = null;
            queue.offer(node); // this node is connected, so add it to the PQ
            i++;
            // you can set its cost to Integer.MAX_VALUE for INFINITY, however, our node already does that when initializing.
        }

        source.cost = 0;
        queue.remove(source); // this node is connected, so add it to the PQ
        queue.offer(source); // this node is connected, so add it to the PQ

        while (!queue.isEmpty()){
            City city = queue.poll();

            city.visited = true;


            // for each neighbor v of u, and only v that are still in Q
            for (Road road : city.roads) {
                City nextCity = road.getNextDestination(city);
                if(!nextCity.visited){
                    int cost = city.cost + road.distance;

                    if(cost < nextCity.cost){
                        nextCity.cost = cost;
                        nextCity.prev = city;
                        queue.remove(nextCity); // remove O(log n)?
                        queue.offer(nextCity); // reinsert O(log n)
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        AttractionsLoader attractionsLoader =new AttractionsLoader();
        attractionsLoader.loadAttractions(cities);
        RoadsLoader roadsLoader =new RoadsLoader();
        roadsLoader.loadRoads(cities);
        Scanner input=new Scanner(System.in);
        String line="";
        System.out.println("Name of starting city (or EXIT to quit): ");
        line=input.nextLine();
        while(!line.equals("EXIT") && !cities.containsKey(line)){
            System.out.println("Invalid city");
            System.out.println("Name of starting city (or EXIT to quit): ");
            line=input.nextLine();
        }

        if(line.toUpperCase().equals("EXIT")){
            System.exit(0);
        }
        City starting_city=cities.get(line);
        System.out.println("Name of ending city: ");
        line=input.nextLine();

        while(!cities.containsKey(line)){
            System.out.println("Invalid city");
            System.out.println("Name of ending city: ");
            line=input.nextLine();
        }
        City ending_city=cities.get(line);

        System.out.println("List an attraction along the way (or ENOUGH to stop listing): ");

        ArrayList<Attraction> attractions = new ArrayList<>();
        while (!line.toUpperCase().equals("ENOUGH")){
            line=input.nextLine();
            if(AttractionsLoader.attractions.containsKey(line)){
                attractions.add(AttractionsLoader.attractions.get(line));
                System.out.println("List an attraction along the way (or ENOUGH to stop listing): ");
            }
            else if(line.toUpperCase().equals("ENOUGH")){
                break;
            }
            else{
                System.out.println("Attraction"+" "+line+" "+"unknown.");
                System.out.println("List an attraction along the way (or ENOUGH to stop listing): ");
            }

        }

        Road_Trip roadTrip =new Road_Trip();
        ArrayList<ArrayList<Road>> routes = roadTrip.route(starting_city,ending_city,attractions);
        int r = 1;

        System.out.println("Your trip will consist of " + routes.size() + " routes.");
        int distance=0;
        for(ArrayList<Road> route : routes){
            System.out.println(r + ") Route from " + route.get(route.size() - 1).startLocation + " to " + route.get(0).endLocation + ".");
            for(int i = route.size() - 1; i >= 0; i--){
                Road road = route.get(i);
                distance+=road.distance;
                System.out.print("\t->" + "Take road from " + road.startLocation + " to " + road.endLocation + ". ");
                System.out.println("Estimate: " + road.distance + " miles, " + road.time + " minutes.");
            }
            System.out.println("Total Cost:"+" "+distance+" "+"miles");
            r++;
        }
    }
}
