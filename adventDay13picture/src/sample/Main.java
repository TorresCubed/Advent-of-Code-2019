package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.text.TableView;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("SpaceCade Games");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }



    public static void main(String[] args) {


        new Canvas();

        ArrayList<Long> instructions = new ArrayList<>();
        try{
            File day12 = new File("C:\\Users\\torre\\IdeaProjects\\advent txt\\adventDay12.txt");
            Scanner scanner = new Scanner(day12);
            String[] data = scanner.nextLine().split(",");

            for (String num: data) {
                instructions.add(Long.parseLong(num));
            }
            scanner.close();
        }catch (
                FileNotFoundException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        for (long i = 0; i <10000; i++) {
            instructions.add((long)0);
        }

        IntCode game = new IntCode(instructions);
        game.intCode();

        int blockCount = 0;
        for (Integer object: game.getGrid().values()) {
            if (object.intValue() == 2) {

                blockCount++;
            }
        }
        System.out.println(blockCount);
        launch(args);

    }


}
