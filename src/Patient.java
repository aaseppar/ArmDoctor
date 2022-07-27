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

public class Patient {
    public static HashMap<String, String> Patients = new HashMap<>();

    private String id;
    private static String name;
    private static String middleName;
    private static String surName;
    private static String birthDate;
    private static String address;
    private static String jobTitle;
    private static String jobAddress;
    private static String passNumber;
    private static String insuranceNumber;


    public static boolean initPatients() throws IOException {
        Path path = Paths.get("Patients.txt");

        if ((Files.exists(path) == true)) {
            ArrayList fileList = ArmDoctor.getList(path.toString());
            int listLength = fileList.size();
            String[] subb = new String[listLength];
            for (int i = 0; i < listLength; i++) {
                subb[i] = fileList.get(i).toString();
                System.out.printf("\n\nArray patients[%d]=%s", i, subb[i]);
                System.out.println("\n\n");
            }
            String[][] sub = new String[listLength][];
            for (int i = 0; i < listLength; i++) {
                sub[i] = subb[i].split(" ");
                System.out.printf("\nArray subPatients[%d]= %s", i, Arrays.toString(sub[i]));

            }
            StringBuilder[] strbArr=new StringBuilder[listLength];
            for (int j = 0; j < listLength; j++) {
                strbArr[j]=new StringBuilder();
            }

            for (int j = 0; j < listLength; j++) {
                for (int i = 0,k=1;i < sub[j].length-1; i++,k++) {
                    strbArr[j].append(sub[j][k]+" ");
                }
                Patients.put(sub[j][0],strbArr[j].toString());

            }

            System.out.println("\n\n");
            for(Map.Entry m: Patients.entrySet())
            {
                System.out.println(m.getKey()+" "+m.getValue());
            }
        }
        else{
            Files.createFile(path);
        }
        return true;
    }
    public static void makeFirstBodyCheck() throws IOException {
        int idOk=0;
        String id = null;
        while(idOk==0){
        System.out.println("\n\nВведите id пациента");
        id= ArmDoctor.sc.next();
        if(!Patients.containsKey(id)){
                System.out.println("id записан успешно!");
                idOk=1;
            } else{
                System.out.println("Этот id уже занят ");
                System.out.println("Попробуйте снова");
            }
        }
        System.out.println("\n\nВведите имя пациента");
        String name= ArmDoctor.sc.next();
        System.out.println("\n\nВведите отчество пациента");
        String middleName= ArmDoctor.sc.next();
        System.out.println("\n\nВведите фамилию пациента");
        String surName= ArmDoctor.sc.next();
        System.out.println("\n\nВведите дату рождения пациента");
        String birthDate= ArmDoctor.sc.next();
        System.out.println("\n\nВведите адрес проживания пациента");
        String address= ArmDoctor.sc.next();
        System.out.println("\n\nВведите должность пациента");
        String jobTitle= ArmDoctor.sc.next();
        System.out.println("\n\nВведите адрес работы пациента");
        String jobAddress= ArmDoctor.sc.next();
        System.out.println("\n\nВведите номер паспорта пациента");
        String passNumber= ArmDoctor.sc.next();
        System.out.println("\n\nВведите номер страхового полиса пациента");
        String insuranceNumber= ArmDoctor.sc.next();


        Patient patFirst=new Patient(id,name,middleName,surName,birthDate,address,jobTitle,jobAddress,passNumber,insuranceNumber);
        StringBuilder strb=new StringBuilder();
        strb.append(name+" ").append(middleName+" ").append(surName+" ").append(birthDate+" ").append(address+" ").append(jobTitle+" ").
                append(jobAddress+" ").append(passNumber+" ").append(insuranceNumber);
        Patients.put(id,strb.toString());
        System.out.println(Patients.toString());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Patients.txt",true), "KOI8_R"));
          for (int i = 0; i < patFirst.toString().length(); i++) {
            bw.write(patFirst.toString().charAt(i));
        }
        bw.write('\n');
        bw.close();
        return;
    }

    public static String CheckExistPatient(String id){
          if(!Patient.Patients.isEmpty()){
            if(Patient.Patients.containsKey(id)){
              System.out.println("id найден успешно!");
              return id;
                } else {
                    System.out.println("Этот id не найден ");
                    System.out.println("Попробуйте снова");
                    return "notId";
                }
            }

        else{
            System.out.println("В базе нет ни одного пациента \n");
            return null;
        }

    }


    public Patient(String id,String name, String middleName, String surName, String birthDate, String address, String jobTitle,
                   String jobAddress, String passNumber, String insuranceNumber) {
        this.id = id;
        this.name = name;
        this.middleName = middleName;
        this.surName = surName;
        this.birthDate = birthDate;
        this.address = address;
        this.jobTitle = jobTitle;
        this.jobAddress = jobAddress;
        this.passNumber = passNumber;
        this.insuranceNumber = insuranceNumber;

    }


    @Override
    public String toString() {
        return   id +  " " +
                 name + " " +
                 middleName + " " +
                 surName + " " +
                 birthDate + " " +
                 address + " " +
                 jobTitle + " " +
                 jobAddress + " " +
                 passNumber + " " +
                 insuranceNumber;
    }
}
