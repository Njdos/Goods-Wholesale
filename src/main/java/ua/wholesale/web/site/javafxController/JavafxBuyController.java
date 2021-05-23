package ua.wholesale.web.site.javafxController;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.wholesale.web.site.model.Goods;
import ua.wholesale.web.site.service.GoodsService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@Data
public class JavafxBuyController{

    @Autowired
    private GoodsService goodsService;

    @FXML
    private AnchorPane Stage;

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

    @FXML
    public void initialize() {
        System.out.println("OOOOOOO");
    }

    @FXML
    public void buyWindow(String title) {
        Platform.runLater(()->{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/templates/fxml/buyGoods.fxml"));
            try { loader.load(); }
            catch (IOException e) { e.printStackTrace(); }
            Parent root = loader.getRoot();
            PriceGoods.setText(title);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
    }
}