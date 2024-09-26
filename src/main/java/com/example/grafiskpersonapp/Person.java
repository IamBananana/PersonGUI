package com.example.grafiskpersonapp;

import java.util.Comparator;

class Person implements Comparable<Person>{

    protected String navn;
    protected String adresse;
    protected int kundenummer;
    // lag dei metodane du treng...
    public Person(String navn, String adresse, int kundenummer) {
        this.navn = navn;
        this.adresse = adresse;
        this.kundenummer = kundenummer;
    }
    public Person(int kundenummer) {
        this.kundenummer = kundenummer;
    }

    public String getNavn() {
        return navn;
    }
    public String getAdresse() {
        return adresse;
    }
    public int getKundenummer() {
        return kundenummer;
    }

    @Override
    public int compareTo(Person p) {
        return this.kundenummer - p.kundenummer;
    }

}

// class SorterKundeNr implements Comparator<Person> {
//     @Override
//     public int compare(Person p1, Person p2) {
//         // TODO Auto-generated method stub
//         return Integer.compare(p1.kundenummer, p2.kundenummer);
//     }  
// }

class SorterAdresse implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        // TODO Auto-generated method stub
        return p1.getAdresse().compareTo(p2.getAdresse());
    }
}
class SorterNavn implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        // TODO Auto-generated method stub
        return p1.getNavn().compareTo(p2.getNavn());
    }
}