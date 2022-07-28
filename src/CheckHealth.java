import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class CheckHealth {
    private static String id;
    private static String temp;
    private static String bloodPr;
    private static String pulse;
    private static String checkBlood;
    private static String checkUrine;
    static HashMap<String, String> checkHealth = new HashMap<>();



    public CheckHealth(String id,String temp, String bloodPr, String pulse,String checkBlood, String checkUrine) {
        this.id=id;
        this.temp = temp;
        this.bloodPr = bloodPr;
        this.pulse = pulse;
        this.checkBlood = checkBlood;
        this.checkUrine = checkUrine;
    }

    @Override
    public String toString() {
        return  id+" "+
                temp + " " +
                bloodPr + " " +
                pulse+" "+
                checkBlood+" "+
                checkUrine;

    }

    public static boolean initCheckHealth(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        String [] HealthArray;
        if ((Files.exists(path) == true)) {
            HealthArray=ArmDoctor.getStringArrayFromFile(fileName);
            String[][] sub = new String[HealthArray.length][];
            for (int i = 0; i < HealthArray.length; i++) {
                sub[i] = HealthArray[i].split(" ");
                System.out.printf("Array subInitArm[%d]= %s", i, Arrays.toString(sub[i]));

            }

            StringBuilder strbArr[]=new StringBuilder[sub.length];
            for (int j = 0; j < sub.length; j++) {
                strbArr[j]=new StringBuilder();
            }

            for (int j = 0; j < sub.length; j++) {
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
            return true;


        }
        else{
            Files.createFile(path);
        }
        return true;
    }


    static boolean writePatientsHealth(int flag) throws IOException {
        int idOk=0;
        Scanner sc=new Scanner(System.in);
        Scanner scl=new Scanner(System.in);
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
        if(flag==1){
        System.out.println("\n\nВведите измеренную температуру пациента");
        temp= sc.next();
        System.out.println("\n\nВведите измеренное давление пациента");
        bloodPr= sc.next();
        System.out.println("\n\nВведите измеренный пульс пациента");
        pulse= sc.next();
            checkBlood="";
            checkUrine="";

        }
        if(flag==2){
            System.out.println("\n\nВведите измеренную температуру пациента");
            temp= sc.next();
            System.out.println("\n\nВведите измеренное давление пациента");
            bloodPr= sc.next();
            System.out.println("\n\nВведите измеренный пульс пациента");
            pulse= sc.next();
            System.out.println("\n\nВведите данные анализа крови");
            checkBlood= scl.nextLine();
            System.out.println("\n\nВведите данные анализа мочи");
            checkUrine= scl.nextLine();
        }
        CheckHealth patHealth=new CheckHealth(id,temp,bloodPr,pulse,checkBlood,checkUrine);
        StringBuilder strb=new StringBuilder();
        strb.append(temp+" ").append(bloodPr+" ").append(pulse+" ").append(checkBlood+" ").append(checkUrine);
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
