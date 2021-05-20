package ua.wholesale.web.site.javafxController;

import io.swagger.annotations.ApiOperation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ua.wholesale.web.site.model.Goods;
import ua.wholesale.web.site.model.User;
import ua.wholesale.web.site.service.GoodsService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
@SpringBootApplication
public class JavafxBuyController {

    @Autowired
    private GoodsService goodsService;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    void initialize() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/buyGoods.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @GetMapping("/usernotice/{id_user}/{id_goods}")
    @ApiOperation(value = "Display ad by id" , response = String.class)
    public String greetidng(@AuthenticationPrincipal User user,
                            @PathVariable("id_goods") long id_goods,
                            @PathVariable("id_goods") long id_user) {

        Iterable<Goods> messages = goodsService.findById(id_goods);

        initialize();

        return "usernotice";
    }
}