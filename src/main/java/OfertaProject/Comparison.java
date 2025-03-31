package OfertaProject;


import java.util.HashSet;

public class Comparison {

    private final HashSet<String> DescuentosComparator = new HashSet<>();
    private final HashSet<String> PosventaComparator = new HashSet<>();
    private final HashSet<String> MinutosComparator = new HashSet<>();
    private final HashSet<String> TrenesComparator = new HashSet<>();

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
    public void addToMinutosComparator(String Minutos){
        MinutosComparator.add(Minutos);
    }
    public HashSet<String> getMinutosComparator(){
        return MinutosComparator;
    }

    public void addToTrenesComparator(String Tren){
        TrenesComparator.add(Tren);
    }
    public HashSet<String> getTrenesComparator(){
        return TrenesComparator;
    }

}
