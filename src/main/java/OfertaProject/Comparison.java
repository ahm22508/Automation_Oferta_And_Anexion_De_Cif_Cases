package OfertaProject;

import java.util.HashSet;

public class Comparison {

    private final HashSet<String> DescuentosComparator = new HashSet<>();
    private final HashSet<String> PosventaComparator = new HashSet<>();

    public void addToDescuentosComparator (String Tren){
        DescuentosComparator.add(Tren);
    }
    public HashSet<String> getDescuentosComparator(){
        return DescuentosComparator;
    }
    public void addToPosventaComparator(String Posventa){
        PosventaComparator.add(Posventa);
    }
    public HashSet<String> getPosventaComparator(){
        return PosventaComparator;
    }

}
