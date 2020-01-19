package szwarc.company_program.controller;

import szwarc.company_program.model.Pracownik;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.*;
import java.util.zip.*;

class zapisPracownika implements Runnable {
        Pracownik tmp;
        String in;
        zapisPracownika(Pracownik obj,String path){
                this.tmp = obj;
                this.in = path;
        }
        public void run() {
                try {
                        new File(in).getParentFile().mkdirs();
                        FileOutputStream fos = new FileOutputStream(in);
                        GZIPOutputStream gz = new GZIPOutputStream(fos);
                        ObjectOutputStream oos = new ObjectOutputStream(gz);
                        oos.writeObject(tmp);
                        gz.close();
                        fos.close();
                }catch (CancellationException e){
                        System.out.println("Watek zostal anulowany!");
                }catch (CompletionException E) {
                        System.out.println("Watek wyrzucil wyjatek!");
                }catch (Exception e){
                        return;
                }
        }
}

class odczytPracownika {
//        static String in;
//
//        public odczytPracownika(String path) {
//                this.in = path;
//        }

        public static Pracownik get(String in){
                try{
                FileInputStream fis = new FileInputStream( in );
                GZIPInputStream gz = new GZIPInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream( gz );
                Pracownik p = (Pracownik)ois.readObject();
                fis.close();
                gz.close();
                return p;
                }catch (Exception e){
                        return null;
                }
        }
}


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

        public static boolean zapisPracownikow(HashMap<String, Pracownik> Container,String path){
                int count = 0;
                ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                CompletableFuture<Void>[] cF = new CompletableFuture[Container.size()];

                for (HashMap.Entry<String, Pracownik> el:Container.entrySet())
                {
                       cF[count] = CompletableFuture.runAsync(new zapisPracownika(el.getValue(),"dat\\"+path+count+".gzip"),pool);
                       count++;
                }
                for (int k = 0; k<count;k++){

                        cF[k].join();
                        if(cF[k].isCompletedExceptionally()) {return false;};
                }

                return true;
        }

        public static HashMap<String, Pracownik> odczytPracownikow(String path){
                int count = 0;
                try{
                        count = (int) Files.list(Paths.get("dat\\")).count();
                }catch (Exception e){
                        return null;
                }
                ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                CompletableFuture<Pracownik>[] cF = new CompletableFuture[count];;

                        for (int i = count; i > 0; i--) {
                                try {
                                        int finalCount = i;
                                        cF[finalCount - 1] = CompletableFuture.supplyAsync(()->odczytPracownika.get("dat\\" + path + (finalCount - 1) + ".gzip"), pool);
                                }catch (Exception e) {

                                }
                        }

                HashMap<String, Pracownik> ret = new HashMap<>();
                try {
                        for (int i = count; i > 0; i--) {
                                if (cF[i - 1].get() == null) { continue; }
                                ret.put(cF[i - 1].get().getPesel(), cF[i - 1].get());
                        }
                }catch (CancellationException e){
                        System.out.println("Watek zostal anulowany!");
                }catch (CompletionException E) {
                        System.out.println("Watek wyrzucil wyjatek!");
                }catch (Exception e){
                        return null;
                }

                return ret;
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
