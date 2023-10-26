package dat3;

import controllers.DogController;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7007);
        app.routes(DogController.allDogEndPoints());
        app.error(404,ctx ->{
            ctx.result("404 - Not found");
        });
    }
}