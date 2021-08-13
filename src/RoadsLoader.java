import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RoadsLoader {
    File roadFile = new File("C:\\Users\\udere\\Documents\\roads.csv");
    static ArrayList roadList = new ArrayList();
    static ArrayList<Road> roads = new ArrayList();

    public void loadRoads(HashMap<String,City> cities){
        BufferedReader br;
        {
            try {
                br = new BufferedReader(new FileReader(roadFile));
                String line="";
                while ((line=br.readLine())!=null){
                    roadList.add(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < roadList.size() ; i++) {
            String roadDataString = ((String) roadList.get(i));
            String[] roadData = roadDataString.split(",");
            String start=roadData[0];
            String end=roadData[1];
            int distance=Integer.parseInt(roadData[2]);
            int time=Integer.parseInt(roadData[3]);
            City city1= null;
            City city2=null;
            if(cities.containsKey(start) && cities.containsKey(end)) {
                city1=cities.get(start);
                city2=cities.get(end);
            }
            else if(cities.containsKey(start)){
                city1=cities.get(start);
                city2=cities.put(end,new City(end));
                city2=cities.get(end);
            }
            else if(cities.containsKey(end)){
                city1=cities.put(start,new City(start));
                city2=cities.get(end);
                city1=cities.get(start);
            }
            else{
                city1=cities.put(start,new City(start));
                city2=cities.put(end,new City(end));
                city2=cities.get(end);
                city1=cities.get(start);
            }
            Road road=new Road(city1,city2,distance,time);
            city1.roads.add(road);
            city2.roads.add(road);
        }

    }

    public static void main(String[] args) {
        RoadsLoader roadsLoader =new RoadsLoader();
        roadsLoader.loadRoads(null);
        for (int i = 0; i <roadList.size() ; i++) {
            System.out.println(roadsLoader.roadList.get(i));
        }
    }

}
