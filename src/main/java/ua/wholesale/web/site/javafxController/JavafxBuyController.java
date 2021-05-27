package ua.wholesale.web.site.javafxController;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import org.hibernate.LazyInitializationException;
import org.springframework.stereotype.Controller;
import ua.wholesale.web.site.model.Goods;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

@Controller
@NoArgsConstructor
public class JavafxBuyController implements Initializable{

    @FXML
    private ImageView ImageGoods;

    @FXML
    private Label TitleGoods;

    @FXML
    private Label DescriptionsGoods;

    @FXML
    private TextField CountGoods;

    @FXML
    private Button BuyButton;

    @FXML
    private Label ManufactureGoods;

    @FXML
    private Label StateGoods;

    @FXML
    private Label PriceGoods;

    private Set<Goods> goods;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) throws LazyInitializationException {
        System.out.println("OOOOOOO");
        for (Goods  good : goods){
            TitleGoods.setText(good.getTitle());
            DescriptionsGoods.setText(good.getDescription());
            ManufactureGoods.setText(good.getManufacturer());
            StateGoods.setText(good.getState());
            PriceGoods.setText(String.valueOf(good.getPrice()));
            CountGoods.setText("1");
            CountGoods.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d*")) {
                        CountGoods.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                    if (Integer.valueOf(newValue) > good.getCount()) {
                        CountGoods.setText(String.valueOf(good.getCount()));
                    }
                    BuyButton.setText(   String.valueOf  (  good.getPrice() * Long.parseLong( CountGoods.getText())) );

                }
            });
            BuyButton.setText(String.valueOf(good.getPrice()));
            BuyButton.setOnAction(actionEvent -> {
                BuyButton.getScene().getWindow().hide();
            });
        }
    }

    @FXML
    public void buyWindow(Set<Goods> id_good) {
        Platform.runLater(()->{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/templates/fxml/buyGoods.fxml"));
            JavafxBuyController javafxBuyController = new JavafxBuyController(id_good);
            loader.setController(javafxBuyController);
            try { loader.load(); }
            catch (IOException e) { e.printStackTrace(); }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
    }

    private JavafxBuyController(Set<Goods> goods) {
        this.goods = goods;
    }

}