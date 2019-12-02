package szwarc.company_program.controller;

import szwarc.company_program.model.Pracownik;

import java.util.*;
import java.io.*;
import java.util.zip.*;


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

        public static boolean zapisBazyDanych(HashMap<String, Pracownik> Container, String in) throws IOException {
                if(in.substring((in.length()-4)).equals(".zip") ) {
                        FileOutputStream fos = new FileOutputStream( in );
                        ZipOutputStream gz = new ZipOutputStream(fos);
                        gz.putNextEntry(new ZipEntry("file"));
                        try(ObjectOutputStream oos = new ObjectOutputStream( gz )){
                                oos.writeObject(Container);
                                gz.closeEntry();
                                fos.close();
                        }
                }else if(in.substring((in.length()-5)).equals(".gzip")){
                        FileOutputStream fos = new FileOutputStream( in );
                        GZIPOutputStream gz = new GZIPOutputStream(fos);
                        try(ObjectOutputStream oos = new ObjectOutputStream( gz )){
                                oos.writeObject(Container);
                                gz.close();
                                fos.close();
                        }
                }else{
                        return false;
                }

                return true;
        }

        public static HashMap<String, Pracownik> odczytBazyDanych( String in) throws IOException {
                if(in.substring((in.length()-4)).equals(".zip") ) {
                        FileInputStream fis = new FileInputStream( in );
                        ZipInputStream gz = new ZipInputStream(fis);
                        ZipEntry ze = gz.getNextEntry();
                        try(ObjectInputStream ois = new ObjectInputStream( gz )){
                                HashMap<String, Pracownik> Container = (HashMap<String, Pracownik>)ois.readObject();
                                fis.close();
                                return Container;
                        }catch (ClassNotFoundException e){
                                return null;
                        }
                }else if(in.substring((in.length()-5)).equals(".gzip")){
                        FileInputStream fis = new FileInputStream( in );
                        GZIPInputStream gz = new GZIPInputStream(fis);
                        try(ObjectInputStream ois = new ObjectInputStream( gz )){
                                HashMap<String, Pracownik> Container = (HashMap<String, Pracownik>)ois.readObject();
                                fis.close();
                                return Container;
                        }catch (ClassNotFoundException e){
                                return null;
                        }
                }
                return null;
        }


}
