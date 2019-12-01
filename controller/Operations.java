package szwarc.company_program.controller;

import szwarc.company_program.model.Pracownik;

import java.util.*;


public class Operations {
        public static boolean dodajPracownika(HashMap<String, Pracownik> Container, Pracownik obj){
                if(Container.get(obj.getPesel()) == null){
                        Container.put(obj.getPesel(),obj);
                        return true;
                }
                return false;
        }

        public static boolean usunPracownika(HashMap<String, Pracownik> Container, String in){
                if(in.length() != 11) {
                        throw new NumberFormatException("Wrong Pesel Format");
                }
                try {
                        int msc = Integer.parseInt(in.substring(2,4));
                        msc %= 20;
                        if (msc > 12) throw new NumberFormatException("Wrong Pesel Format");
                }
                catch (NumberFormatException nfe) {
                        throw new NumberFormatException("Wrong Pesel Format");
                }

                if(Container.remove(in)==null){
                        return false;
                }else{
                        return true;
                }
        }

        public static Pracownik znajdzPracownika(HashMap<String, Pracownik> Container, String in){
                if(in.length() != 11) {
                        throw new NumberFormatException("Wrong Pesel Format");
                }
                try {
                        int msc = Integer.parseInt(in.substring(2,4));
                        msc %= 20;
                        if (msc > 12) throw new NumberFormatException("Wrong Pesel Format");
                }
                catch (NumberFormatException nfe) {
                        throw new NumberFormatException("Wrong Pesel Format");
                }

                return Container.get(in);
        }

}
