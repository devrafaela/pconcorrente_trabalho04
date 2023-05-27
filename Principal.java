
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/* ***************************************************************
* Autor............: Rafaela Pereira Santos
* Matricula........: 202110838
* Inicio...........: 01/05/2023
* Ultima alteracao.: 07/05/2023
* Nome.............: Principal
* Funcao...........: Inicializa o Programa
*************************************************************** */
public class Principal extends Application {

  public void start (Stage palco) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("view/Interface.fxml"));
    palco.setTitle("Sushi de Shein");
    palco.getIcons().add(new Image("/view/img/logo-nome.png"));
    palco.setScene(new Scene(root));
    palco.setResizable(false);
    palco.centerOnScreen();
    palco.setFullScreenExitHint("");
    palco.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    });
    palco.show();
    ControllerTela controllerTela = new ControllerTela();    
  } // fim do start

  public static void main(String[] args) {
    launch(args);
  } // fim de main

} // fim de classe Principal