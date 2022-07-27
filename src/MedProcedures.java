import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class MedProcedures {
    private static String id;
    public static HashMap<String, String> hmMedProcedures = new HashMap<>();

    public static boolean initMedProcedures() throws IOException {
        Path path = Paths.get("MedProcedures.txt");
        if ((Files.exists(path) == true)) {
            ArrayList fileList = ArmDoctor.getList(path.toString());
            int listLength = fileList.size();
            String[] subb = new String[listLength];
            for (int i = 0; i < listLength; i++) {
                subb[i] = fileList.get(i).toString();
                System.out.printf("Array subb[%d]=%s", i, subb[i]);
                System.out.println("\n");
            }
            String[][] sub = new String[listLength][];
            for (int i = 0; i < listLength; i++) {
                sub[i] = subb[i].split(" ");
                System.out.printf("Array sub[%d]= %s", i, Arrays.toString(sub[i]));

            }
            StringBuilder[] strbArr=new StringBuilder[listLength];
            for (int j = 0; j < listLength; j++) {
                strbArr[j]=new StringBuilder();
            }

            for (int j = 0; j < listLength; j++) {
                for (int i = 0,k=1;i < sub[j].length-1; i++,k++) {
                    strbArr[j].append(sub[j][k]+" ");
                }
                hmMedProcedures.put(sub[j][0],strbArr[j].toString());

            }

            System.out.println("\n\n");
            for(Map.Entry m: hmMedProcedures.entrySet())
            {
                System.out.println(m.getKey()+" "+m.getValue());
            }

        }
        else{
            Files.createFile(path);
        }
        return true;
    }

    public static boolean writeProcedures() throws IOException {
        String procedures;
        int idOk=0;
          while(idOk==0){
            System.out.println("\n\nВведите id пациента");
            id= ArmDoctor.sc.next();
            id=Patient.CheckExistPatient(id);
            if(id==null){
                return false;
            }
            if(id.equals("notId")){
                continue;
            }
            else idOk=1;
        }
        System.out.println("\nВведите назначенные процедуры пациента");
        procedures=ArmDoctor.sc.next();
        hmMedProcedures.put(id,procedures);
        StringBuilder strb=new StringBuilder();
        for(Map.Entry m: hmMedProcedures.entrySet())
        {
            strb.append(m.getKey()+" "+m.getValue());
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("MedProcedures.txt", true), "KOI8_R"));
        for (int i = 0; i < strb.length(); i++) {
            bw.write(strb.charAt(i));
        }
        bw.write("\n");
        bw.close();
        return true;
    }

}
