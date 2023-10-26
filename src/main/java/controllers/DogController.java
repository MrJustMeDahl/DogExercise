package controllers;

import dtos.DogDTO;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Handler;

import java.util.HashMap;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.*;

public class DogController {

    private static Map<Integer, DogDTO> dogs = new HashMap(){
        {put(1, new DogDTO("Bulder", "Golden Retriever", "M", 5));}
        {put(2, new DogDTO("Pjevse", "Chihuahua", "M", 2));}
        {put(3, new DogDTO("Lady", "Terrier", "F", 4));}
    };

    public static EndpointGroup allDogEndPoints(){
        return () ->{
          path("/api/dogs", () ->{
              get("/", getAllDogs());
              post("/dog", createDog());
              put("/dog/{id}", updateDog());
              delete("/dog/{id}", deleteDog());
              get("/{id}", getDogByID());
          });
        };
    }

    private static Handler getAllDogs(){
        return (ctx) -> ctx.json(dogs);
    }

    private static Handler getDogByID(){
        return (ctx) -> {
            try {
                if(dogs.containsKey(Integer.parseInt(ctx.pathParam("id")))) {
                    ctx.status(200);
                    ctx.json(dogs.get(Integer.parseInt(ctx.pathParam("id"))));
                } else {
                    ctx.status(404);
                }
            } catch (NumberFormatException e){
                ctx.status(400);
            }
        };
    }

    private static Handler createDog(){
        return (ctx) -> {
            DogDTO newDog;
            try {
                if(ctx.queryParam("name") != null && ctx.queryParam("breed") != null && ctx.queryParam("gender") != null && ctx.queryParam("age") != null) {
                    ctx.status(201);
                    newDog = new DogDTO(ctx.queryParam("name"), ctx.queryParam("breed"), ctx.queryParam("gender"), Integer.parseInt(ctx.queryParam("age")));
                    dogs.put(newDog.getID(), newDog);
                    ctx.json(dogs.get(newDog.getID()));
                } else {
                    ctx.status(400);
                    ctx.json("{\"Status code\": \"400\" Not all query parameters present\"}");
                }
            } catch (NumberFormatException e){
                ctx.status(400);
            }
        };
    }

    private static Handler updateDog(){
        return (ctx) -> {
            try{
                if(dogs.containsKey(Integer.parseInt(ctx.pathParam("id")))){
                    DogDTO foundDog = dogs.get(Integer.parseInt(ctx.pathParam("id")));
                    if(ctx.queryParam("name") != null){
                        foundDog.setName(ctx.queryParam("name"));
                    }
                    if(ctx.queryParam("age") != null){
                        foundDog.setAge(Integer.parseInt(ctx.queryParam("age")));
                    }
                    if(ctx.queryParam("breed") != null){
                        foundDog.setBreed(ctx.queryParam("breed"));
                    }
                    if(ctx.queryParam("gender") != null){
                        foundDog.setGender(ctx.queryParam("gender"));
                    }
                    ctx.json(foundDog);
                } else {
                    ctx.status(404);
                }
            } catch (NumberFormatException e){
                ctx.status(400);
            }
        };
    }

    private static Handler deleteDog(){
        return (ctx) -> {
            try {
                if (dogs.containsKey(Integer.parseInt(ctx.pathParam("id")))) {
                    ctx.status(204);
                    dogs.remove(Integer.parseInt(ctx.pathParam("id")));
                } else {
                    ctx.status(404);
                }
            } catch (NumberFormatException e){
                ctx.status(400);
            }
        };
    }
}
