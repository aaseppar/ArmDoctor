import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SickList {
    private static String id;
    private static String insuranceCompanyName;
    private static String sickListDateFrom;
    private static String sickListDateTo;
    private static String  premDiag;
    private static String secDateReception;
    static HashMap<String, String> SickLists = new HashMap<>();

    public SickList(String id,String insuranceCompanyName,String sickListDateFrom,
                    String sickListDateTo,String  premDiag,String secDateReception) {

        this.id=id;
        this.insuranceCompanyName=insuranceCompanyName;
        this.sickListDateFrom=sickListDateFrom;
        this.sickListDateTo=sickListDateTo;
        this.premDiag=premDiag;
        this.secDateReception=secDateReception;
    }

    public static boolean initSickLists(String fileName) throws IOException {
        String [] SickArray;
        Path path = Paths.get(fileName);

        if ((Files.exists(path) == true)) {
            SickArray=ArmDoctor.getStringArrayFromFile(fileName);
            String[][] sub = new String[SickArray.length][];
            for (int i = 0; i < SickArray.length; i++) {
                sub[i] = SickArray[i].split(" ");
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
                SickLists.put(sub[j][0],strbArr[j].toString());

            }

            System.out.println("\n\n");
            for(Map.Entry m: SickLists.entrySet())
            {
                System.out.println(m.getKey()+" "+m.getValue());
            }
            return true;
        }
        Files.createFile(path);
     return true;
    }

    static boolean writeSickList() throws IOException {
        int idOk=0;
        Scanner sc=new Scanner(System.in);

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

        System.out.println("\nВведите название страховой компании пациента");
        insuranceCompanyName= sc.nextLine();
        System.out.println("\nВведите дату открытия больничного листа");
        sickListDateFrom= sc.nextLine();
        if(ArmDoctor.flagCloseSickList==1){
        System.out.println("\nВведите дату закрытия больничного листа");
        sickListDateTo= sc.nextLine();
        }
        System.out.println("\nВведите предварительный диагноз");
        premDiag= sc.nextLine();
        System.out.println("\nВведите дату следующего приема");
        secDateReception= sc.nextLine();

        SickList patSickList=new SickList(id,insuranceCompanyName,sickListDateFrom,sickListDateTo,premDiag,secDateReception);
        StringBuilder strb=new StringBuilder();
        strb.append(insuranceCompanyName+" ").append(sickListDateFrom+" ").append(sickListDateTo+" ").append(premDiag+" ").append(secDateReception);
        SickLists.put(id,strb.toString());

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("SickLists.txt", true), "KOI8_R"));

        for (int i = 0; i < patSickList.toString().length(); i++) {
            bw.write(patSickList.toString().charAt(i));
        }
        bw.write('\n');
        bw.close();
        return true;

    }



    @Override
    public String toString() {
        return id+" "+
               insuranceCompanyName + " " +
               sickListDateFrom + " " +
               sickListDateTo + " " +
               premDiag +" "+
               secDateReception;
    }
}
