import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class ComplaintsData {
    private static String id;
     public static HashMap<String, String> hmComplaints = new HashMap<>();

    public static boolean initComplaints() throws IOException {
        Path path = Paths.get("Complaints.txt");
        if ((Files.exists(path) == true)) {
            ArrayList fileList = ArmDoctor.getList(path.toString());
            int listLength = fileList.size();
            String[] subb = new String[listLength];
            for (int i = 0; i < listLength; i++) {
                subb[i] = fileList.get(i).toString();
                System.out.printf("Array Complaints[%d]=%s", i, subb[i]);
                System.out.println("\n");
            }
            String[][] sub = new String[listLength][];
            for (int i = 0; i < listLength; i++) {
                sub[i] = subb[i].split(" ");
                System.out.printf("Array subComplaints[%d]= %s", i, Arrays.toString(sub[i]));

            }
            StringBuilder[] strbArr=new StringBuilder[listLength];
            for (int j = 0; j < listLength; j++) {
                strbArr[j]=new StringBuilder();
            }

            for (int j = 0; j < listLength; j++) {
                for (int i = 0,k=1;i < sub[j].length-1; i++,k++) {
                    strbArr[j].append(sub[j][k]+" ");
                }
                hmComplaints.put(sub[j][0],strbArr[j].toString());

            }

            System.out.println("\n\n");
            for(Map.Entry m: hmComplaints.entrySet())
            {
                System.out.println(m.getKey()+" "+m.getValue());
            }
        }
        else{
            Files.createFile(path);
        }
        return true;
    }

    public static boolean writeComplaints() throws IOException {
        String complaints;
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
         System.out.println("\n\nВведите жалобы пациента");
         complaints=ArmDoctor.sc.next();
         hmComplaints.put(id,complaints);
         StringBuilder strb=new StringBuilder();
         for(Map.Entry m: hmComplaints.entrySet())
         {
             strb.append(m.getKey()+" "+m.getValue());
         }
         BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Complaints.txt", true), "KOI8_R"));
         for (int i = 0; i < strb.length(); i++) {
             bw.write(strb.charAt(i));
         }
         bw.write('\n');
         bw.close();
         return true;
    }

}
