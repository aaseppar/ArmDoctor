import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    public static boolean initSickLists() throws IOException {
        Path path = Paths.get("SickLists.txt");

        if ((Files.exists(path) == true)) {
            ArrayList fileList = ArmDoctor.getList(path.toString());
            int listLength = fileList.size();
            String[] subb = new String[listLength];
            for (int i = 0; i < listLength; i++) {
                subb[i] = fileList.get(i).toString();
                System.out.printf("Array subbInitSickLists[%d]=%s", i, subb[i]);
                System.out.println("\n");
            }
            String[][] sub = new String[listLength][];
            for (int i = 0; i < listLength; i++) {
                sub[i] = subb[i].split(" ");
                System.out.printf("Array subInitSickLists[%d]= %s", i, Arrays.toString(sub[i]));

            }

            StringBuilder[] strbArr=new StringBuilder[listLength];
            for (int j = 0; j < listLength; j++) {
                strbArr[j]=new StringBuilder();
            }

            for (int j = 0; j < listLength; j++) {
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

        }
        else{
            Files.createFile(path);
        }
        return true;
    }

    static boolean writeSickList() throws IOException {
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

        System.out.println("\n\nВведите название страховой компании пациента");
        insuranceCompanyName= ArmDoctor.sc.next();
        System.out.println("\n\nВведите дату открытия больничного листа");
        sickListDateFrom= ArmDoctor.sc.next();
        System.out.println("\n\nВведите дату закрытия больничного листа");
        sickListDateTo= ArmDoctor.sc.next();
        System.out.println("\n\nВведите предварительный диагноз");
        premDiag= ArmDoctor.sc.next();
        System.out.println("\n\nВведите дату следующего приема");
        secDateReception= ArmDoctor.sc.next();

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
