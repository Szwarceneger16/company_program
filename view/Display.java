package szwarc.company_program.view;

import static szwarc.company_program.controller.Operations.dodajPracownika;
import static szwarc.company_program.controller.Operations.usunPracownika;
import static szwarc.company_program.controller.Operations.znajdzPracownika;

import szwarc.company_program.model.Dyrektor;
import szwarc.company_program.model.Handlowiec;
import szwarc.company_program.model.Pracownik;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class Display {
    private static Locale locale = new Locale("Polish");
    private static HashMap<String, Pracownik> bazaDanych = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println(" MENU ");
            System.out.println("    1. Lista Pracownikow");
            System.out.println("    2. Dodaj Pracownika");
            System.out.println("    3. Usun Pracownika");
            System.out.println("    4. Kopia Zapasowa");

            //Dyrektor obj = new Dyrektor("99182304053", "abc", "def", new BigDecimal(34343), "3434343", new BigDecimal(34343), "4535345", new BigDecimal(34343));

            int select = scanner.nextInt();
            switch (select){
                case 1:
                    bazaDanych.forEach((k,v) ->
                            {
                                wyswietlPracownika(v);
                                while(!scanner.nextLine().isEmpty()) {};
                            }
                            );
                    break;
                case 2:
                    System.out.print("  [D]yrektor/[H]andlowiec:  ");

                    while(true){
                        Pracownik obj = null;
                        char select2 = scanner.next().charAt(0);
                        if (select2 == 'D' || select2 == 'd' || select2 == 'H' || select2 == 'h'){
                            if(select2 == 'D' || select2 == 'd'){
                                //Dyrektor obj = stworzDyrektora();
                                obj = new Dyrektor("99112304053", "abc", "def", new BigDecimal(34343), "3434343", new BigDecimal(34343), "4535345", new BigDecimal(34343));
                            }else if(select2 == 'H' || select2 == 'h'){
                                //Handlowiec obj = stworzHandlowca();
                                obj = new Handlowiec("99032304053", "abcd", "dddef", new BigDecimal(34343), "3434343", new BigDecimal(34343), new BigDecimal(34343));
                            }
                            System.out.println("[E] Potwierdz , [Q] Anuluj");
                            while(true) {
                                select2 = scanner.next().charAt(0);
                                if(select2 == 'E' || select2 == 'e'){
                                    if(!dodajPracownika(bazaDanych,obj)) System.out.println("Taki pracownik już istnieje!");
                                    break;
                                }else if(select2 == 'Q' || select2 == 'q'){
                                    break;
                                }
                            }
                            break;
                        }else{
                            System.out.println("Nie wybrano prawidlowo typu pracownika !");
                        }
                    }

                    break;
                case 3:
                    System.out.print("Pesel: "); String pesel = scanner.next();
                    Pracownik obj = znajdzPracownika(bazaDanych,pesel);
                    if(obj == null){
                        System.out.println("Nie odnaleziono uzytkownika o podanym nr pesel ! ");
                    }else{
                        wyswietlPracownika(obj);
                        System.out.print("[E] Potwierdz , [Q] Anuluj");
                        while(true) {
                            char select2 = scanner.next().charAt(0);
                            if(select2 == 'E' || select2 == 'e'){
                                usunPracownika(bazaDanych,pesel);
                                break;
                            }else if(select2 == 'Q' || select2 == 'q'){
                                break;
                            }
                        }
                    }
                    break;
                case 4:

                    break;
                default:

            }

        }
    }


    private static Dyrektor stworzDyrektora(){
        while(true)
        {
            System.out.print("Pesel: "); String pesel = scanner.next();
            System.out.print("imie: "); String imie = scanner.next();
            System.out.print("nazwisko: "); String nazwisko = scanner.next();
            System.out.print("Wyangordzenie: "); BigDecimal wynagrodzenie = scanner.nextBigDecimal();
            System.out.print("Numer telefonu: "); String nr_tel = scanner.next(); System.out.println();
            System.out.print("Dodatek Sluzbowy: "); BigDecimal dodatekSluzbowy = scanner.nextBigDecimal();
            System.out.print("Numer Karty Sluzbowej: "); String karta_sluzb = scanner.next();
            System.out.print("Limit kosztow/miesiac: "); BigDecimal limitKosztow = scanner.nextBigDecimal();

            try{
                Dyrektor dyr = new Dyrektor(pesel,imie,nazwisko,wynagrodzenie,nr_tel,dodatekSluzbowy,karta_sluzb,limitKosztow);
                return dyr;
            }catch (NumberFormatException e){
                System.out.println("Podano zle dane spporboj jeszcze raz !");
            }
        }
    }

    private static Handlowiec stworzHandlowca(){
        while(true)
        {
            System.out.print("Pesel: "); String pesel = scanner.next();
            System.out.print("imie: "); String imie = scanner.next();
            System.out.print("nazwisko: "); String nazwisko = scanner.next();
            System.out.print("Wyangordzenie: "); BigDecimal wynagrodzenie = scanner.nextBigDecimal();
            System.out.print("Numer telefonu: "); String nr_tel = scanner.next();
            System.out.print("Prowizja %: "); BigDecimal prowizja = scanner.nextBigDecimal();
            System.out.print("Limit prowizji/miesiac: "); BigDecimal limitProwizji = scanner.nextBigDecimal();

            try{
                Handlowiec hand = new Handlowiec(pesel,imie,nazwisko,wynagrodzenie,nr_tel,prowizja,limitProwizji);
                return hand;
            }catch (NumberFormatException e){
                System.out.println("Podano zle dane sproboj jeszcze raz !");
            }
        }
    }

    private static void wyswietlPracownika(Pracownik in){
        if(in instanceof Dyrektor){
            Dyrektor obj = (Dyrektor) in;
            System.out.println("Pesel: " + obj.getPesel());
            System.out.println("Imie: "+ obj.getImie());
            System.out.println("Nazwisko: " + obj.getNazwisko());
            System.out.println("Stanowisko: Dyrektor");
            System.out.println("Wyangordzenie: " + obj.getWynagrodzenie());
            System.out.println("Numer telefonu: "+obj.getNumerTelefonu());
            System.out.println("Dodatek Sluzbowy: "+obj.getDodatekSluzbowy());
            System.out.println("Numer Karty Sluzbowej: "+obj.getKartaSluzbowa());
            System.out.println("Limit kosztow/miesiac: "+obj.getLimitKosztow());
            System.out.println("------------------------------------------------");
        }else if(in instanceof Handlowiec){
            Handlowiec obj = (Handlowiec) in;
            System.out.println("Pesel: " + obj.getPesel());
            System.out.println("Imie: "+ obj.getImie());
            System.out.println("Nazwisko: " + obj.getNazwisko());
            System.out.println("Stanowisko: Handlowiec");
            System.out.println("Wyangordzenie: " + obj.getWynagrodzenie());
            System.out.println("Numer telefonu: "+obj.getNumerTelefonu());
            System.out.println("Prowizja %: "+obj.getProwizja());
            System.out.println("Limit prowizji/miesiac: "+obj.getLimitProwizji());
            System.out.println("------------------------------------------------");
        }else{
            System.out.println("Blad w odczycie pracownika");
        }

    }

}
