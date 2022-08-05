import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ArmDoctor {
    static HashMap<String, String> hmarmUsers = new HashMap<>();
    static String [] arnUsersArray;
       static int flagCloseSickList=0;
    public static boolean initArm(String fileName) throws IOException {
         Path path = Paths.get(fileName);

              if (Files.exists(path) == true) {
                  arnUsersArray=getStringArrayFromFile(fileName);
                  for(int i=0;i<arnUsersArray.length;i++){
                 // System.out.println("initArm"+arnUsersArray[i]);
                  }
                String[][] sub = new String[arnUsersArray.length][];
                for (int i = 0; i < arnUsersArray.length; i++) {
                    sub[i] = arnUsersArray[i].split(" ");
                   // System.out.printf("Array subInitArm[%d]= %s", i, Arrays.toString(sub[i]));

                }

                StringBuilder strbArr[]=new StringBuilder[sub.length];
                for (int j = 0; j < sub.length; j++) {
                    strbArr[j]=new StringBuilder();
                }

                for (int j = 0; j < sub.length; j++) {
                    for (int i = 0,k=1;i < sub[j].length-1; i++,k++) {
                        strbArr[j].append(sub[j][k]+" ");

                    }
                    hmarmUsers.put(sub[j][0],strbArr[j].toString().trim());

                }

//                System.out.println("\n\n");
//                for(Map.Entry m: hmarmUsers.entrySet())
//                {
//                    System.out.println("hmarmUsers :"+m.getKey()+" "+m.getValue());
//                }

                return true;
            }


        else{
           Files.createFile(path);
        }
        return true;
    }


    public static boolean checkUser(String log, String pass,int choice) throws IOException {
        System.out.printf("\n\ncheckUser():log=%s pass=%s choice=%d \n",log,pass,choice);

           if (choice == 1) {
               if (!hmarmUsers.isEmpty()) {
                if (hmarmUsers.containsKey(log)) {
                    String checkPass = hmarmUsers.get(log);
                    System.out.println("\ncheckUser(): checkPass="+checkPass);
                    if (checkPass.equals(pass)) {
                        return true;
                    }
                    else
                    {
                        System.out.printf("\nIn hmarmUsers  this password =%s not equals input pass=%s\n",checkPass,pass);
                        return false;}
                } else {
                    System.out.printf("\nhmarmUsers is not containts this login=%s return false\n",log);
                    return false;
                }
               }
               else{
                   System.out.println("hmarmUsers is EMPTY!!! return false");
                   return false;
               }
             }

            if (choice == 2) {

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("armUsers.txt", true), "KOI8_R"));
                String str = new StringBuffer().append(log).append(" ").append(pass).append("\n").toString();
                for (int i = 0; i < str.length(); i++) {
                    bw.write(str.charAt(i));
                }
                bw.close();
                if (!hmarmUsers.containsKey(log))
                    hmarmUsers.put(log, pass);
                else return false;
            }
      return true;
    }


     public static boolean putOutSickList(){
        Scanner sc=new Scanner(System.in);
         int idOk=0;
         String id="";
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
         String ie=Patient.Patients.get(id);
         if(ie==null)
         {
             System.out.println("Выводить нечего");
            return false;
         }
         System.out.println(ie);
         System.out.println(CheckHealth.checkHealth.get(id));
         System.out.println(ComplaintsData.hmComplaints.get(id));
         System.out.println(SickList.SickLists.get(id));
         System.out.println(MedProcedures.hmMedProcedures.get(id));
         return true;
     }

    public static void main(String[] args) throws IOException {

        System.out.println("Добро пожаловать в АРМ врача поликлиники!");
        initArm("armUsers.txt");
        Patient.initPatients("Patients.txt");
        CheckHealth.initCheckHealth("CheckHealth.txt");
        ComplaintsData.initComplaints("Complaints.txt");
        MedProcedures.initMedProcedures("MedProcedures.txt");
        SickList.initSickLists("SickLists.txt");


        System.out.println("\nЕсли зарегистрированы нажмите 1");
        System.out.println("Если не зарегистрированы нажмите 2\n");
        Scanner sc = new Scanner(System.in);
        int choice= sc.nextInt();
        Scanner scl = new Scanner(System.in);
         int autOk=0;
          while(autOk==0){
             System.out.println("Введите свой логин:");
             String login= scl.nextLine();
             System.out.println("Введите свой пароль:");
             String pass= scl.nextLine();
             if(checkUser(login,pass,choice)){
                System.out.println("Аутентификация прошла успешно!");
                autOk=1;
              } else{
                System.out.println("Аутентификация не прошла");
                System.out.println("Попробуйте снова");
               }
          }

         while (true){
            System.out.println("Для запуска процедуры первичного приема пациента нажмите 1");
            System.out.println("Для запуска процедуры вторичного приема пациента нажмите 2");
            System.out.println("Для запуска процедуры оформления больничного листа нажмите 3");
             System.out.println("Для запуска процедуры печати больничного листа нажмите 4");
            System.out.println("Для выхода из АРМ нажмите 5");
            int choiseProc= sc.nextInt();
            switch(choiseProc){
                case 1:
                {
                    System.out.println("Запущена процедура первичного приема пациента");
                    Patient.makeFirstBodyCheck();
                    System.out.println("Запущена процедура записи результатов осмотра");
                    CheckHealth.writePatientsHealth(1);
                    System.out.println("Запущена процедура записи жалоб пациента");
                    ComplaintsData.writeComplaints(1);
                    System.out.println("Запущена процедура записи назначенного лечения");
                    MedProcedures.writeProcedures();
                    break;
                }
                case 2:
                {
                    boolean checkPat;
                    System.out.println("Запущена процедура записи результатов осмотра");
                    checkPat=CheckHealth.writePatientsHealth(2);
                    if(checkPat==false) continue;
                    System.out.println("Запущена процедура записи жалоб пациента");
                    ComplaintsData.writeComplaints(2);
                    System.out.println("Запущена процедура записи назначенного лечения");
                    MedProcedures.writeProcedures();
                    break;
                }
                case 3:
                {
                    boolean checkPat;
                    System.out.println("Запущена процедура оформления больничного листа");
                    checkPat= SickList.writeSickList();
                    if(checkPat==false) continue;
                    break;

                }
                case 4:
                {
                    putOutSickList();
                    break;

                }
                case 5:
                {
                    System.out.println("Вы вышли из АРМ врача поликлиники");
                    return;
                }

            }
         }
    }



    public static String [] getStringArrayFromFile(String fileName) throws IOException {
        String localStringArray [];

        File myFile=new File(fileName);

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "KOI8_R"));
            StringBuilder strb= new StringBuilder();
            //System.out.printf("\ngetStringArrayFromFile(): Length of file=%s is %d \n",fileName,myFile.length());
            for (int i = 0; i < myFile.length(); i++) {
                strb.append((char)br.read());
            }
            br.close();
            localStringArray= (strb.toString().split("\n"));
            //System.out.printf("\ngetStringArrayFromFile(): Contents of file=%s is %s \n",fileName,strb);
            return localStringArray;
        }

}

