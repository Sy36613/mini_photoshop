import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SecondGUI extends Application {
    private static Scene scene, sceneW, sceneR, sceneG, sceneB, sceneD;
    private static WritableImage wImage, grayImage;
    private static int[] rarray;
    private static int[] garray;
    private static int[] barray;

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        stage.setTitle("PNG");

        Label label1 = new Label("Which PNG file do you want to read?");
        Button button1 = new Button("autumn.png");
        button1.setOnAction(e -> {
            readPNG("autumn.png");
            showSceneW(stage);
        });
        Button button2 = new Button("balloons.png");
        button2.setOnAction(e -> {
            readPNG("balloons.png");
            showSceneW(stage);
        });
        Button button3 = new Button("board.png");
        button3.setOnAction(e -> {
            readPNG("board.png");
            showSceneW(stage);
        });
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1, button2, button3);
        scene = new Scene(layout1, 300, 250);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }

    public static void readPNG(String fileName) {
        try {
            //Creating an image
            Image image = new Image(new FileInputStream(fileName));
            int width = (int)image.getWidth();
            int height = (int)image.getHeight();

            //Creating a writable image
            wImage = new WritableImage(width, height);
            grayImage = new WritableImage(width, height);
            //Reading color from the loaded image
            PixelReader pixelReader = image.getPixelReader();

            //getting the pixel writer
            PixelWriter writer = wImage.getPixelWriter();
            PixelWriter grayWriter = grayImage.getPixelWriter();
            rarray = new int[256];
            garray = new int[256];
            barray = new int[256];
            //Reading the color of the image
            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    //Retrieving the color of the pixel of the loaded image
                    Color color = pixelReader.getColor(x, y);
                    Color gray = color.grayscale();
                    rarray[(int)(255*color.getRed())]++;
                    garray[(int)(255*color.getGreen())]++;
                    barray[(int)(255*color.getBlue())]++;

                    //Setting the color to the writable image
                    writer.setColor(x, y, color);
                    grayWriter.setColor(x, y, gray);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void showSceneW(Stage stage) {
        //Setting the view for the writable image
        ImageView imageView = new ImageView(wImage);
        Button button = new Button("red frequency");
        button.setOnAction(e -> {
            showSceneR(stage);
        });
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(imageView, button);
        sceneW = new Scene(layout1, wImage.getWidth()+100, wImage.getHeight()+100);
        //Setting title to the Stage
        stage.setTitle("Writing pixels ");
        //Adding scene to the stage
        stage.setScene(sceneW);
        //Displaying the contents of the stage
        stage.show();
    }

    public static void showSceneR(Stage stage) {
        List<String> bins = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            bins.add(i+"");
        }
        CategoryAxis xAxis1 = new CategoryAxis();
        xAxis1.setCategories(FXCollections.<String>
                observableArrayList(bins));
        xAxis1.setLabel("value");
        NumberAxis yAxis1 = new NumberAxis();
        yAxis1.setLabel("frequency");
        BarChart<String, Number> barChart1 = new BarChart<>(xAxis1, yAxis1);
        barChart1.setTitle("Red frequency");
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Red");
        ObservableList<XYChart.Data<String,Number>> data1 = series1.getData();
        for (int i = 0; i < 256; i++) {
            System.out.println(i + ": " + rarray[i]);
            data1.add(new XYChart.Data<>(i+"", rarray[i]));
        }
        series1.setData(data1);
        barChart1.getData().addAll(series1);
        Button button = new Button("green frequency");
        button.setOnAction(e -> {
            showSceneG(stage);
        });
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(barChart1, button);
        sceneR = new Scene(layout1, 300, 250);
        stage.setScene(sceneR);
    }

    public static void showSceneG(Stage stage) {
        List<String> bins = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            bins.add(i+"");
        }
        CategoryAxis xAxis1 = new CategoryAxis();
        xAxis1.setCategories(FXCollections.<String>
                observableArrayList(bins));
        xAxis1.setLabel("value");
        NumberAxis yAxis1 = new NumberAxis();
        yAxis1.setLabel("frequency");
        BarChart<String, Number> barChart1 = new BarChart<>(xAxis1, yAxis1);
        barChart1.setTitle("Green frequency");
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Green");
        ObservableList<XYChart.Data<String,Number>> data1 = series1.getData();
        for (int i = 0; i < 256; i++) {
            System.out.println(i + ": " + garray[i]);
            data1.add(new XYChart.Data<>(i+"", garray[i]));
        }
        series1.setData(data1);
        barChart1.getData().addAll(series1);
        Button button = new Button("blue frequency");
        button.setOnAction(e -> {
            showSceneB(stage);
        });
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(barChart1, button);
        sceneG = new Scene(layout1, 300, 250);
        stage.setScene(sceneG);
    }

    public static void showSceneB(Stage stage) {
        List<String> bins = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            bins.add(i+"");
        }
        CategoryAxis xAxis1 = new CategoryAxis();
        xAxis1.setCategories(FXCollections.<String>
                observableArrayList(bins));
        xAxis1.setLabel("value");
        NumberAxis yAxis1 = new NumberAxis();
        yAxis1.setLabel("frequency");
        BarChart<String, Number> barChart1 = new BarChart<>(xAxis1, yAxis1);
        barChart1.setTitle("Blue frequency");
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Blue");
        ObservableList<XYChart.Data<String,Number>> data1 = series1.getData();
        for (int i = 0; i < 256; i++) {
            System.out.println(i + ": " + barray[i]);
            data1.add(new XYChart.Data<>(i+"", barray[i]));
        }
        series1.setData(data1);
        barChart1.getData().addAll(series1);
        Button button = new Button("dithered");
        button.setOnAction(e -> {
            showSceneD(stage);
        });
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(barChart1, button);
        sceneB = new Scene(layout1, 300, 250);
        stage.setScene(sceneB);
    }

    public static void showSceneD(Stage stage) {
        int width = (int)wImage.getWidth();
        int height = (int)wImage.getHeight();

        //int[][] matrix = {{0,8,2,10},{12,4,14,6},{3,11,1,9},{15,7,13,5}};
       int[][] matrix = {{0,2},{3,1}};
        //Creating a writable image
        WritableImage dImage = new WritableImage(width, height);
        PixelReader pixelReader = grayImage.getPixelReader();
        PixelReader pixelReaderColor = wImage.getPixelReader();
        PixelWriter writer = dImage.getPixelWriter();


        // this is for 4x4 matrix algorithm
//        for(int y = 0; y < height; y++) {
//            for(int x = 0; x < width; x++) {
//                //Retrieving the color of the pixel of the loaded image
//                Color color = pixelReaderColor.getColor(x, y);
//                int red = (int)(color.getRed()*256);
//                System.out.printf("red color is: %d\n", red);
//                int green = (int)(color.getGreen()*256);
//                System.out.printf("green color is: %d\n", green);
//                int blue =  (int)(color.getBlue()*256);
//                System.out.printf("blue color is: %d\n", blue);
//                int i = x%4, j = y%4;
//                if (red/(256.0/17) > matrix[i][j] && green/(256.0/17) > matrix[i][j] && blue/(256.0/17) > matrix[i][j]) {
//                    writer.setColor(x, y, Color.rgb(255,255,255));
//
//                } else if(red/(256.0/17) > matrix[i][j] && green/(256.0/17) < matrix[i][j] && blue/(256.0/17) > matrix[i][j]){
//                    writer.setColor(x, y, Color.rgb(255,0,255));
//                } else if(red/(256.0/17) > matrix[i][j] && green/(256.0/17) < matrix[i][j] && blue/(256.0/17) < matrix[i][j]){
//                    writer.setColor(x, y, Color.rgb(255,0,0));
//                }else if(red/(256.0/17) < matrix[i][j] && green/(256.0/17) > matrix[i][j] && blue/(256.0/17) > matrix[i][j]){
//                    writer.setColor(x, y, Color.rgb(0,255,255));
//                }else if(red/(256.0/17) < matrix[i][j] && green/(256.0/17) < matrix[i][j] && blue/(256.0/17) > matrix[i][j]){
//                    writer.setColor(x, y, Color.rgb(0,0,255));
//                }else if(red/(256.0/17) < matrix[i][j] && green/(256.0/17) > matrix[i][j] && blue/(256.0/17) < matrix[i][j]){
//                    writer.setColor(x, y, Color.rgb(0,255,0));
//                }else if(red/(256.0/17) > matrix[i][j] && green/(256.0/17) > matrix[i][j] && blue/(256.0/17) > matrix[i][j]){
//                    writer.setColor(x, y, Color.rgb(255,255,0));
//                }else if(red/(256.0/17) < matrix[i][j] && green/(256.0/17) < matrix[i][j] && blue/(256.0/17) < matrix[i][j]){
//                    writer.setColor(x, y, Color.rgb(0,0,0));
//                }
//            }
//        }
      // this is for 2x2 matrix algorithm
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                //Retrieving the color of the pixel of the loaded image
                Color color = pixelReaderColor.getColor(x, y);
                int red = (int)(color.getRed()*256);
                System.out.printf("red color is: %d\n", red);
                int green = (int)(color.getGreen()*256);
                System.out.printf("green color is: %d\n", green);
                int blue =  (int)(color.getBlue()*256);
                System.out.printf("blue color is: %d\n", blue);
                int i = x%2, j = y%2;
                if (red/(256.0/5) > matrix[i][j] && green/(256.0/5) > matrix[i][j] && blue/(256.0/5) > matrix[i][j]) {
                    writer.setColor(x, y, Color.rgb(255,255,255));

                } else if(red/(256.0/5) > matrix[i][j] && green/(256.0/5) < matrix[i][j] && blue/(256.0/5) > matrix[i][j]){
                    writer.setColor(x, y, Color.rgb(255,0,255));
                } else if(red/(256.0/5) > matrix[i][j] && green/(256.0/5) < matrix[i][j] && blue/(256.0/5) < matrix[i][j]){
                    writer.setColor(x, y, Color.rgb(255,0,0));
                }else if(red/(256.0/5) < matrix[i][j] && green/(256.0/5) > matrix[i][j] && blue/(256.0/5) > matrix[i][j]){
                    writer.setColor(x, y, Color.rgb(0,255,255));
                }else if(red/(256.0/5) < matrix[i][j] && green/(256.0/5) < matrix[i][j] && blue/(256.0/5) > matrix[i][j]){
                    writer.setColor(x, y, Color.rgb(0,0,255));
                }else if(red/(256.0/5) < matrix[i][j] && green/(256.0/5) > matrix[i][j] && blue/(256.0/5) < matrix[i][j]){
                    writer.setColor(x, y, Color.rgb(0,255,0));
                }else if(red/(256.0/5) > matrix[i][j] && green/(256.0/5) > matrix[i][j] && blue/(256.0/5) > matrix[i][j]){
                    writer.setColor(x, y, Color.rgb(255,255,0));
                }else if(red/(256.0/5) < matrix[i][j] && green/(256.0/5) < matrix[i][j] && blue/(256.0/5) < matrix[i][j]){
                    writer.setColor(x, y, Color.rgb(0,0,0));
                }
            }
        }



        ImageView imageView = new ImageView(dImage);
        Button button = new Button("back");
        button.setOnAction(e -> {
            stage.setScene(scene);
        });
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            stage.close();
        });
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(imageView, button, exitButton);
        sceneD = new Scene(layout1, dImage.getWidth()+100, dImage.getHeight()+100);
        //Setting title to the Stage
        stage.setTitle("Writing pixels ");
        //Adding scene to the stage
        stage.setScene(sceneD);
        //Displaying the contents of the stage
        stage.show();
    }
}