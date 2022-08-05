import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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


    public static boolean initPatients(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        String [] PatientArray;
        if ((Files.exists(path) == true)) {
            PatientArray=ArmDoctor.getStringArrayFromFile(fileName);
            String[][] sub = new String[PatientArray.length][];
            for (int i = 0; i < PatientArray.length; i++) {
                sub[i] = PatientArray[i].split(" ");
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
                Patients.put(sub[j][0],strbArr[j].toString());

            }

//            System.out.println("\n\n");
//            for(Map.Entry m: Patients.entrySet())
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
    public static void makeFirstBodyCheck() throws IOException {
        int idOk=0;
        String id = null;
        Scanner sc=new Scanner(System.in);
        Scanner scl=new Scanner(System.in);
        getListIdPatient();
        while(idOk==0){
        System.out.println("\n\nВведите id пациента");
        id= sc.next();
        if(!Patients.containsKey(id)){
                System.out.println("id записан успешно!");
                idOk=1;
            } else{
                System.out.println("Этот id уже занят ");
                System.out.println("Попробуйте снова");
            }
        }

        System.out.println("\nВведите имя пациента");
        String name= sc.next();
        System.out.println("\nВведите отчество пациента");
        String middleName= sc.next();
        System.out.println("\nВведите фамилию пациента");
        String surName= sc.next();
        System.out.println("\nВведите дату рождения пациента");
        String birthDate= sc.next();
        System.out.println("\nВведите адрес проживания пациента");
        String address= scl.nextLine();
        System.out.println("\nВведите должность пациента");
        String jobTitle= scl.nextLine();
        System.out.println("\nВведите адрес работы пациента");
        String jobAddress= scl.nextLine();
        System.out.println("\nВведите номер паспорта пациента");
        String passNumber= scl.nextLine();
        System.out.println("\nВведите номер страхового полиса пациента");
        String insuranceNumber= scl.nextLine();


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


    public static String getListIdPatient(){
        if(!Patient.Patients.isEmpty()) {
            System.out.println("\nСписок пациентов для справки:\n");
            for (Map.Entry m : Patients.entrySet()) {
                System.out.println(m.getKey() + " " + Arrays.stream((m.getValue().toString()).split(" ")).findFirst());
            }
        }
        return null;
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
