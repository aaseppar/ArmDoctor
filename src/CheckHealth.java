import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class CheckHealth {
    private static String id;
    private static String temp;
    private static String bloodPr;
    private static String pulse;
    static HashMap<String, String> checkHealth = new HashMap<>();



    public CheckHealth(String id,String temp, String bloodPr, String puls) {
        this.id=id;
        this.temp = temp;
        this.bloodPr = bloodPr;
        this.pulse = puls;
    }

    @Override
    public String toString() {
        return  id+" "+
                temp + " " +
                bloodPr + " " +
                pulse;

    }

    public static boolean initCheckHealth() throws IOException {
        Path path = Paths.get("CheckHealth.txt");

        if ((Files.exists(path) == true)) {
            ArrayList fileList = ArmDoctor.getList(path.toString());
            int listLength = fileList.size();
            String[] subb = new String[listLength];
            for (int i = 0; i < listLength; i++) {
                subb[i] = fileList.get(i).toString();
                System.out.printf("Array subbInitCheckHealth[%d]=%s", i, subb[i]);
                System.out.println("\n");
            }
            String[][] sub = new String[listLength][];
            for (int i = 0; i < listLength; i++) {
                sub[i] = subb[i].split(" ");
                System.out.printf("Array subInitCheckHealth[%d]= %s", i, Arrays.toString(sub[i]));

            }

            StringBuilder[] strbArr=new StringBuilder[listLength];
            for (int j = 0; j < listLength; j++) {
                strbArr[j]=new StringBuilder();
            }

            for (int j = 0; j < listLength; j++) {
                for (int i = 0,k=1;i < sub[j].length-1; i++,k++) {
                    strbArr[j].append(sub[j][k]+" ");
                }
                checkHealth.put(sub[j][0],strbArr[j].toString());

            }

            System.out.println("\n\n");
            for(Map.Entry m: checkHealth.entrySet())
            {
                System.out.println(m.getKey()+" "+m.getValue());
            }



        }
        else{
            Files.createFile(path);
        }
        return true;
    }


    static boolean writePatientsHealth() throws IOException {
        int idOk=0;
        String id="";
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

        System.out.println("\n\nВведите измеренную температуру пациента");
        temp= ArmDoctor.sc.next();
        System.out.println("\n\nВведите измеренное давление пациента");
        bloodPr= ArmDoctor.sc.next();
        System.out.println("\n\nВведите измеренный пульс пациента");
        pulse= ArmDoctor.sc.next();
        CheckHealth patHealth=new CheckHealth(id,temp,bloodPr,pulse);
        StringBuilder strb=new StringBuilder();
        strb.append(temp+" ").append(bloodPr+" ").append(pulse);
        checkHealth.put(id,strb.toString());


        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("CheckHealth.txt",true), "KOI8_R"));

        for (int i = 0; i < patHealth.toString().length(); i++) {
            bw.write(patHealth.toString().charAt(i));
        }
        bw.write('\n');
        bw.close();
      return true;

    }
}
