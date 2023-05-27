package restaurant;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/* ***************************************************************
* Autor............: Rafaela Pereira Santos
* Matricula........: 202110838
* Inicio...........: 01/05/2023
* Ultima alteracao.: 14/05/2023
* Nome.............: GUI
* Funcao...........: Define a GUI para o sistema de Restaurante
**************************************************************** */
public class GUI {
  private Label porcentagem;        // Porcentagem
  private Label tamanho;            // Tamanho da Porcentagem no ProgressBar
  private Slider slider;            // Slider com a Velocidade de Cozinhar/Comer
  private ProgressBar progressBar;  // Barra de Progresso
  private HBox HBox_imgView;        // Substitue o ObservableList com o conteudo do Buffer
  private Pessoa pessoa;            // Define uma pessoa, seja o cliente ou chef

  /* *********************************************************************************************
  * - Nome do Constutor: GUI
  * - Funcao: Usado para criar um novo GUI
  * - Parametros: 'porcentagem' do tipo Label, 'tamanho' do tipo Label, 'slider' do tipo Slider, 
  * 'pgbar' do tipo ProgressBar, 'hbox' do tipo HBox
  * - Retorno: Nao retorna nada
  * - Explicacao: Atribue os valores dos parametros aos atributos correspondentes do GUI
  ********************************************************************************************* */
  public GUI (Label porcentagem, Label tamanho, Slider slider, ProgressBar pgbar, HBox hbox) {
    this.porcentagem = porcentagem;
    this.tamanho = tamanho;
    this.slider = slider;
    this.progressBar = pgbar;
    this.HBox_imgView = hbox;
  }  // fim do GUI

  /* ***************************************************************
  * - Metodo: progresso
  * - Funcao: Atualizar o progresso de uma tarefa
  * - Parametros: 'concluido' e 'total' do tipo double
  * - Retorno: Sem retorno aparente
  *************************************************************** */
  public void progresso (double concluido, double total) {
    // -- Inicia uma nova Thread no JavaFX -- //
    Platform.runLater(() -> {
      // -- Calcula a porcentagem do progresso divindo 'concluindo' por 'total' e atribui a 'dec' -- //
      double dec = concluido / total;
      // -- Se 'progressBar' for diferente de null, seu valor eh atualizado com a porcentagem concluido -- //
      if (progressBar != null) { // if1
        progressBar.setProgress(dec);
      } // fim do if1

      // -- Verifica se 'porcentagem' eh deiferente de null e atualiza a porcentagem -- //
      if (porcentagem != null) {
        porcentagem.setText((int) (dec * 100) + "%");
      } else {
        pessoa.setPorcentagem((int) (dec * 100) + "%");
      } // fim do if-else
    }); // fim do Platform.runLater
  } // fim de progresso

  /* ***************************************************************
  * - Metodo: publishItem
  * - Funcao: Adiciona um item na Inteface
  * - Parametros: 'o' do tipo Object
  * - Retorno: Sem retorno
  *************************************************************** */
  public void publishItem(Object o) {
    // -- Inicia uma nova Thread do JavaFX -- //
    Platform.runLater(() -> {
      // -- Verifica se o objeto eh uma instancia de ImageView, se sim -- //
      if (o instanceof ImageView) {
        // -- O item eh adicionado dentro da HBox -- //
        HBox_imgView.getChildren().add((ImageView) o);
      } // fim do if
    }); // fim do Platform.runLater
  } // fim do publishItem

  /* ***************************************************************
  * - Metodo: removeItem
  * - Funcao: Remove um item da Interface
  * - Parametros: Sem parametro
  * - Retorno: Sem retorno
  *************************************************************** */
  public void removeItem() {
    // -- Inicia uma nova Thread do JavaFX -- //
    Platform.runLater(() -> {
      // -- Se a HBox nao estiver vazia, remove o primeiro elemento da lista com o metodo '.remove()' -- //
      if (!HBox_imgView.getChildren().isEmpty()) {
        HBox_imgView.getChildren().remove(0);
      } // fim do if
    }); // fim do Platform.runLater
  } // fim de removeItem

  public Slider getSlider() { return slider; }  // fim de getSlider

  public void setTamanho(Label tamanho) { this.tamanho = tamanho; }   // fim de setTamanho

  public Label getTamanho() { return tamanho; }   // fim de getTamanho

  public ProgressBar getProgressBar() { return progressBar; }   // fim de getProgressBar

} // fim da classe