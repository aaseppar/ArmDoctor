import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ArmDoctor {
    static HashMap<String, String> hmarmUsers = new HashMap<>();
    static Scanner sc = new Scanner(System.in);
    public static boolean initArm() throws IOException {
         Path path = Paths.get("armUsers.txt");

        if ((Files.exists(path) == true)) {
            ArrayList fileList = getList(path.toString());
            int listLength = fileList.size();
            String[] subb = new String[listLength];
            for (int i = 0; i < listLength; i++) {
                subb[i] = fileList.get(i).toString();
                System.out.printf("Array subbInitArm[%d]=%s", i, subb[i]);
                System.out.println("\n");
            }
            String[][] sub = new String[listLength][];
            for (int i = 0; i < listLength; i++) {
                sub[i] = subb[i].split(" ");
                System.out.printf("Array subInitArm[%d]= %s", i, Arrays.toString(sub[i]));

            }

            for (int j = 0; j < listLength; j++) {
                for (int i = 0; i < sub[j].length-1; i++) {
                    hmarmUsers.put(sub[j][i], sub[j][i + 1]);
                }
            }
        }
        else{
           Files.createFile(path);
        }
        return true;
    }


    public static boolean checkUser(String log, String pass,int choice) throws IOException {
             if (choice == 1) {
               if (!hmarmUsers.isEmpty()) {
                if (hmarmUsers.containsKey(log)) {
                    String checkPass = hmarmUsers.get(log);
                    if (checkPass.equals(pass)) {
                        return true;
                    } else return false;
                } else {
                    return false;
                }
               }
             }

            if (choice == 2) {

                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("armUsers.txt", true), "KOI8_R"));
                String str = new StringBuffer().append("\n").append(log).append(" ").append(pass).toString();
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


     public static boolean putOutSickHistory(){
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
         String ie=Patient.Patients.get(id);
         if(ie==null)
         {
             System.out.println("Выводить нечего");
            return false;
         }
         System.out.println(ie);
         System.out.println(CheckHealth.checkHealth.get(id));
         System.out.println(ComplaintsData.hmComplaints.get(id));
         System.out.println(MedProcedures.hmMedProcedures.get(id));
         return true;
     }

    public static void main(String[] args) throws IOException {

        System.out.println("Добро пожаловать в АРМ врача поликлиники!");
        initArm();
        Patient.initPatients();
        CheckHealth.initCheckHealth();
        ComplaintsData.initComplaints();
        MedProcedures.initMedProcedures();
        SickList.initSickLists();


        System.out.println("\nЕсли зарегистрированы нажмите 1");
        System.out.println("Если не зарегистрированы нажмите 2\n");
        int choice= sc.nextInt();
         int autOk=0;
          while(autOk==0){
             System.out.println("Введите свой логин:");
             String login= sc.next();
             System.out.println("Введите свой пароль:");
             String pass= sc.next();
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
            System.out.println("Для вывода истории болезни нажмите 4");
            System.out.println("Для выхода из АРМ нажмите 5");
            int choiseProc= sc.nextInt();
            switch(choiseProc){
                case 1:
                {
                    System.out.println("Запущена процедура первичного приема пациента");
                    Patient.makeFirstBodyCheck();
                    System.out.println("Запущена процедура записи результатов осмотра");
                    CheckHealth.writePatientsHealth();
                    System.out.println("Запущена процедура записи жалоб пациента");
                    ComplaintsData.writeComplaints();
                    System.out.println("Запущена процедура записи назначенного лечения");
                    MedProcedures.writeProcedures();
                    break;
                }
                case 2:
                {
                    boolean checkPat;
                    System.out.println("Запущена процедура записи результатов осмотра");
                    checkPat=CheckHealth.writePatientsHealth();
                    if(checkPat==false) continue;
                    System.out.println("Запущена процедура записи жалоб пациента");
                    ComplaintsData.writeComplaints();
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
                    boolean checkPat;
                    System.out.println("Запущена процедура вывода истории болезни");
                    checkPat= putOutSickHistory();
                    if(checkPat==false) continue;
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



    public static ArrayList getList(String myStr) throws IOException {

        Path path = Paths.get(myStr);
        List<String> miList= Files.readAllLines(path, StandardCharsets.UTF_8);
        ArrayList   filList= new ArrayList(miList);
        return filList;
    }

}

