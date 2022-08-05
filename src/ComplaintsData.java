import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class ComplaintsData {
    private static String id;
     public static HashMap<String, String> hmComplaints = new HashMap<>();

    public static boolean initComplaints(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        String []  ComplaintsArray;
        if ((Files.exists(path) == true)) {
            ComplaintsArray=ArmDoctor.getStringArrayFromFile(fileName);
            String[][] sub = new String[ComplaintsArray.length][];
            for (int i = 0; i < ComplaintsArray.length; i++) {
                sub[i] = ComplaintsArray[i].split(" ");
                //System.out.printf("Array subInitArm[%d]= %s", i, Arrays.toString(sub[i]));

            }

            StringBuilder strbArr[]=new StringBuilder[sub.length];
            for (int j = 0; j < sub.length; j++) {
                strbArr[j]=new StringBuilder();
            }

            for (int j = 0; j < sub.length; j++) {
                for (int i = 0,k=1;i < sub[j].length-1; i++,k++) {
                    strbArr[j].append(sub[j][k]+" ");

                }
                hmComplaints.put(sub[j][0],strbArr[j].toString());

            }

//            System.out.println("\n\n");
//            for(Map.Entry m: hmComplaints.entrySet())
//            {
//                System.out.println(m.getKey()+" "+m.getValue());
//            }
            return true;
        }
        else{
            Files.createFile(path);
        }
        return true;
    }

    public static boolean writeComplaints(int flag) throws IOException {
        StringBuilder complaints=new StringBuilder();
        Scanner sc=new Scanner(System.in);
        Scanner scl=new Scanner(System.in);
        int idOk=0;
        Patient.getListIdPatient();
        while(idOk==0){
            System.out.println("\n\nВведите id пациента");
            id= sc.next();
            id=Patient.CheckExistPatient(id);
            if(id==null){
                return false;
            }
            if(id.equals("notId")){
                continue;
            }
            else idOk=1;
        }
         System.out.println("\nВведите жалобы пациента");
         complaints.append(scl.nextLine());
         if(complaints.equals("нет")){
             ArmDoctor.flagCloseSickList=1;
         }
         else {
           if(flag==1){
              hmComplaints.put(id,complaints.toString());
           }
           if(flag==2){
             System.out.println("\nВведите название больницы для пациента");
             complaints.append(scl.nextLine());
             hmComplaints.put(id,complaints.toString());
           }
         }
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
