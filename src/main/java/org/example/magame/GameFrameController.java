package org.example.magame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.*;

/**
 * First game Created
 * updated version from Ucademy
 * Brian Espinoza
 */
public class GameFrameController implements Initializable {

    @FXML
    private ImageView p1 ,p2,p3,p4,p5,p11,p22,p33,p44,p55,wetDog;
    @FXML
    private Label label1,label2,label3,label4,label5,label6,label7,label8,label9,label10,displayLives;
    private HashMap<String,String> labelAimage;
    //we only get 3 lives
    private int lives = 3;
    //if we get to the end of the road then we win.
    private int winCount = 0;

    private ImageView paw = createImageView( "/images/paw.jpg");
    private ImageView thorns = createImageView("/images/thorn.jpg");
    //random generator
    Random rd = new Random();
    //random image
    int ranImage;
    //array of labels
    ImageView[][] images;
    ArrayList<ImageView> temp;
    ArrayList<Label> labels;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        temp = new ArrayList<>(Arrays.asList(p1,p2,p3,p4,p5,p11,p22,p33,p44,p55));
        labels = new ArrayList<>(Arrays.asList(label1,label2,label3,label4,label5,label6,label7,label8,label9,label10));
        labelAimage = new HashMap<>();
        setImageProperties(paw);
        setImageProperties(thorns);
        displayImages();
        setDisplayLives(lives);
        temp.forEach(imageView -> imageView.setVisible(false));
        enableLabels();
    }

    private ImageView createImageView(String resourcepath){
        URL resource = getClass().getResource(resourcepath);
        if (resource == null){
            throw  new IllegalArgumentException("image not found: "+ resourcepath);
        }
        ImageView imageView = new ImageView(resource.toString());
        setImageProperties(imageView);
        return imageView;
    }

    private void enableLabels(){
        labels.forEach(label -> {
            label.setOnMouseClicked(onMouseClick ->{
                    label.getGraphic().setVisible(true);
                    checkLives(label);
            });
        });
    }

    private void setImageProperties(ImageView imageview){
        imageview.setFitHeight(80);
        imageview.setFitWidth(80);
    }

    private void displayImages(){
        images  = new ImageView[][]{ {p1,p2,p3,p4,p5},
                                {p11,p22,p33,p44,p55},
        };
        generateRandomImages();
    }

    @FXML
    void resetMethod(ActionEvent event) {
        //make images invisibles
        temp.forEach( imageView -> imageView.setVisible(false));
        //set lives back to 3
        lives = 3;
        //reset winCount back to 0
        winCount = 0;
        //display lives
        setDisplayLives(lives);
        wetDog.setVisible(false);
        generateRandomImages();
        labels.forEach(label->label.setDisable(false));
    }

    private void generateRandomImages(){
            for (int i =0 ; i < 5 ; i ++) {
                //if 0 then set paw if one then set thorns
                ranImage = rd.nextInt(2);
                if (ranImage == 0) {
                    images[0][i].setImage(paw.getImage());
                    labelAimage.put(labels.get(i).getId(), "paw");
                    images[1][i].setImage(thorns.getImage());
                    labelAimage.put(labels.get(i+5).getId(), "thorns");
                }
                else {
                    images[0][i].setImage(thorns.getImage());
                    labelAimage.put(labels.get(i).getId() , "thorns");
                    images[1][i].setImage(paw.getImage());
                    labelAimage.put(labels.get(i+5).getId(), "paw");
                }
            }
    }

    /**
     * if player gets 2 times a thorn or cactus then game will be over.
     * Otherwise, he will win
     */
    private void checkLives(Label label){
        String imageType = labelAimage.get(label.getId());
        //set alert
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (imageType != null && imageType.equals("thorns")) {
            lives--;
        }else if (imageType != null && imageType.equals("paw")){
            winCount++;
        }

        if (lives == 0) {
            alert.setTitle("Game Over!!");
            alert.setHeaderText("You suck, Try again.");
            setDisplayLives(lives);
            disableLabels();
            alert.showAndWait();
        } else if (winCount == 5) {
            alert.setTitle("Game Over!!");
            alert.setHeaderText("You are the winner!!!!!! ");
            setDisplayLives(lives);
            disableLabels();
            wetDog.setVisible(true);
            alert.showAndWait();
        }
        setDisplayLives(lives);

    }

    private void disableLabels(){
        labels.forEach(label->label.setDisable(true));
    }
    private void setDisplayLives(int l){
        displayLives.setText("Lives: " + l);
    }
}
