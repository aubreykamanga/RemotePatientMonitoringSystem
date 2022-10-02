package com.example.rpms;

public class Note {
    private String name;
    private int age;
    private String sex;



    /**
     * creating an empty constructor,Firebase need it to create Note object from the documents in the database
     */
    public Note(){
        //empty constructor
    }
/**Creating constructor for the fields*/
    public Note(String name, int age, String sex){
        this.name = name;
        this.age = age;
        this.sex = sex;
    }


/**Getter methods for the three fields*/
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }


    public String getSex() {
        return sex;
    }
}
