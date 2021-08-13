import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AttractionsLoader {
    File attractionFile = new File("C:\\Users\\udere\\Documents\\attractions.csv");
    static ArrayList attractionList = new ArrayList();
    static HashMap<String, Attraction> attractions = new HashMap<>();

    public void loadAttractions(HashMap<String,City> cities){
        BufferedReader br;
        {
            try {
                br = new BufferedReader(new FileReader(attractionFile));
                String line="";
                while ((line=br.readLine())!=null){
                    if (line.contains("Attraction") || line.contains("Location")) {
                        line=br.readLine();
                    }
                    attractionList.add(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < attractionList.size() ; i++) {
            String attractionDataString = ((String) attractionList.get(i));
            String[] attractionData = attractionDataString.split(",");
            String place=attractionData[0];
            String location=attractionData[1];
            City city= null;
            if(cities.containsKey(location)) {
                city=cities.get(location);
            }
            else{
                city=cities.put(location, new City(location));
                city=cities.get(location);
            }
            Attraction attraction = new Attraction(place,location);
            city.attractions.put(place,new Attraction(place,location));
            attractions.put(place, attraction);
        }

    }

    public static void main(String[] args) {
        AttractionsLoader attractionsLoader = new AttractionsLoader();
        attractionsLoader.loadAttractions(null);
        for (int i = 0; i <attractionList.size() ; i++) {
            System.out.println(attractionsLoader.attractionList.get(i));
        }
    }

}
