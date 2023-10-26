package dtos;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DogDTO {

    private int ID;
    private static int nextID = 1;
    private String name;
    private String breed;
    private String gender;
    private int age;

    public DogDTO(String name, String breed, String gender, int age) {
        this.ID = nextID;
        nextID++;
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.age = age;
    }
}
