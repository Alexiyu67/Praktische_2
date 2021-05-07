import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

  private Scanner in = new Scanner(System.in);

  ArrayList<Card> list = new ArrayList<Card>();

  private class Card{
    String company;
    String category;
    double preis;

    public Card(String a, String b, double c){
      company = a;
      category = b;
      preis = c;
    }
  }

  //Prinzip:
  /*Es werden ausschließlich Transaktionen, mit Shop, Kategorie und Preis gespeichert.
  Dadurch ist die Eingabe erleichtert, die Ausgabemethoden "report" und "detail" müssen
  jedoch die Informationen verarbeiten.
  Die geschieht, in dem zwei Listen erstellt werden.
  Die String-Liste speichert alle Kategorien / Shops, und am selben Index i wie der dazugehörige
  Shop oder die dazugehörige Kategorie, ist in der int-Liste "sum" die entsprechende Summe gespeichert.
  
  Zum Beispiel:
  Index 0 in str: Amazon
  Index 1 in str: Otto
  
  Index 0 in sum: 4, gehört zu Amazon
  Index 1 in sum: 5, gehört zu Otto    */
  private void details(String[] parts) {
    String comp = parts[1];

    List<String> cat = new ArrayList<>();
    List<Double> sum = new ArrayList<>();
    boolean found = false;

    //System.out.println("details() started.");

    for(Card e: list){//Schleife, um alle Transaktionen durchzugehen
      if(e.company.equals(comp)){//Wenn Transaktion von gesuchter Firma gefunden
        for(int i = 0; i < cat.size();i++){//Passende Kategorie in cat suchen
          if(cat.get(i).equals(e.category)){//Wenn passende Kategorie gefunden
            double tmp = sum.get(i);
            tmp = tmp + e.preis; //Entsprechende Summe draufaddieren
            sum.set(i, tmp);
            found = true;
            break; //Bricht aus Kategorie-Suche aus
          }
        }//Ende Kategorie-Suche

        if(!found){//Wenn nicht gefunden
          cat.add(e.category); //Kategorie hinzufuegen
          sum.add(e.preis); //Ersten Wert auf entsprechende Stelle legen
        }
        found = false;
      }
    }
    //PRINT
    for(int i = 0; i < cat.size(); i++){
      System.out.println(cat.get(i) + ": " + sum.get(i));
    }
  }

  private void report(String[] parts) {
    // Prüfen: parts hat 2 Elemente
    // 0 ist "report", 1 ist entweder "category" oder "shop"
    // Bei Fehlern invalid() aufrufen und die Methode mit return beenden
    int type = 0; //0 == ungültig, 1 == category, 2 == shop
    if(parts[1].equals("category")){
      type = 1;
    }
    if(parts[1].equals("shop")){
      type = 2;
    }
    if(type == 0){
      invalid();
      return;
    }

    List<String> str = new ArrayList<>();
    List<Double> sum = new ArrayList<>();
    boolean f = false;

    if(type == 1){//Wenn Kategorie
      for(Card e:list){

        for(int i = 0; i < str.size(); i++){//str durchlaufen, nach Kategorie suchen
          if(str.get(i).equals(e.category)){//Wenn passende Kategorie gefunden
            f = true;
            sum.set(i, (sum.get(i) + e.preis));//Summe entsprechend erhöhen
            break; //Aus str-Schleife raus
          }
        }//Ende der str-Schleife

        if(f == false){//Wenn Kategorie noch nicht in str
          str.add(e.category);
          sum.add(e.preis);//Erste Summe setzen
        }else{//Wenn gefunden und eingefuegt
          f = false;
        }

      }
    }else if(type == 2){//Wenn Shop
      for(Card e:list){

        for(int i = 0; i < str.size(); i++){//str durchlaufen, nach Kategorie suchen
          if(str.get(i).equals(e.company)){//Wenn passende Kategorie gefunden
            f = true;
            sum.set(i, (sum.get(i) + e.preis));//Summe entsprechend erhöhen
            break; //Aus str-Schleife raus
          }
        }//Ende der str-Schleife
        
        if(f == false){//Wenn Kategorie noch nicht in str
          str.add(e.company);
          sum.add(e.preis);//Erste Summe setzen
        }else{//Wenn gefunden und eingefuegt
          f = false;
        }

      }
    }
    //PRINT
    for(int i = 0; i < str.size(); i++){
      System.out.println(str.get(i) + ": " + sum.get(i));
    }
  }

  private void add(String[] parts) {
    // Prüfen: parts hat 4 Elemente
    // 0 ist "add", 1 und 2 sind String, 3 ist ein String, in dem ein Double Wert steht
    // Bei Fehlern invalid() aufrufen und die Methode mit return beenden
    if(parts.length < 4){
      invalid();
      return;
    }

    String a = parts[1];//Unternehmen
    String b = parts[2];//Kategorie
    double c = Double.parseDouble(parts[3]);
    list.add(new Card(a, b, c));//Fügt Transaktion zu Liste hinzu
  }

  private void exit() {
    System.out.println("Bye.");
  }

  public void run() {
    boolean run = readAndProcess();
    while(run){
      run = readAndProcess();
    }
  }


  /**
   * Diese Methode soll aufgerufen werden, wenn es einen Fehler bei den Parametern gibt
   */
  private void invalid() {
    System.out.println("Ungültige Eingabe.");
  }

  private boolean readAndProcess() {
    String line;
    line = in.nextLine();
    String[] parts = line.split(" ");
    switch (parts[0]) {
      case "add": {
        add(parts);
        return true;
      }
      case "report": {
        report(parts);
        return true;
      }
      case "details": {
        details(parts);
        return true;
      }
      case "exit": {
        exit();
        return false;
      }
      default: {
        invalid();
        return true;
      }
    }
  }

  public static void main(String[] args) {
    Main main = new Main();
    main.run();
  }

}
