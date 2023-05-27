
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.Consumidor;
import model.Jukebox;
import model.Produtor;
import restaurant.Chef;
import restaurant.Cliente;
import restaurant.GUI;

/* ***************************************************************
* Autor............: Rafaela Pereira Santos
* Matricula........: 202110838
* Inicio...........: 01/05/2023
* Ultima alteracao.: 14/05/2023
* Nome.............: TelaController
* Funcao...........: Controla as alteracoes na Inferface
*************************************************************** */
public class ControllerTela implements Initializable {

  @FXML 
  public ImageView imgView_Produtor;  

  @FXML
  public ImageView imgView_ButtonPlay;            // -- ImageView do Botão Play -- //
  public ImageView imgView_ButtonPlayInative;     // -- ImageView do Botão Play Desativado -- //
  public ImageView imgView_ButtonRestart;         // -- ImageView do Botão Restart -- //
  public ImageView imgView_ButtonRestartInative;  // -- ImageView do Botão Restart Inativo -- //
  public ImageView imgView_pauseC;                // -- ImageView do Botão Pause Consumidor -- //
  public ImageView imgView_playC;                 // -- ImageView do Botão Play Consumidor -- //
  public ImageView imgView_pauseP;                // -- ImageView do Botão Pause Produtor -- //
  public ImageView imgView_playP;                 // -- ImageView do Botão Play Produtor -- //  

  @FXML
  public CheckBox checkBox_Music;   // -- CheckBox da Musica -- //

  @FXML
  public ImageView imgView_Prato1;    // -- ImageView do Prato 1 -- //
  public ImageView imgView_Prato2;    // -- ImageView do Prato 2 -- //
  public ImageView imgView_Prato3;    // -- ImageView do Prato 3 -- //
  public ImageView imgView_Prato4;    // -- ImageView do Prato 4 -- //
  public ImageView imgView_Prato5;    // -- ImageView do Prato 5 -- //
  public ImageView imgView_Prato6;    // -- ImageView do Prato 6 -- //
  public ImageView imgView_Prato7;    // -- ImageView do Prato 7 -- //
  public ImageView imgView_Prato8;    // -- ImageView do Prato 8 -- //
  public ImageView imgView_Prato9;    // -- ImageView do Prato 9 -- //

  @FXML
  public Slider slider_Produtor;    // -- Slider do Produtor -- //
  public Slider slider_Consumidor;  // -- Slider do Consumidor -- //

  @FXML
  public Label label_Produtor;    // -- Label do Produtor -- //
  public Label label_Consumidor;  // -- Label do Consumidor -- //
  public Label label_TC;          // -- Label do Tempo do Produtor -- //
  public Label label_TP;          // -- Label do Tempo do Consumidor -- //

  @FXML
  public HBox HBOX_Sushis;   // -- HBox dos Pratos de Sushi -- //

  @FXML
  public ProgressBar progressBar_Cozinhando;    // -- ProgressBar do Produtor -- //
  public ProgressBar progressBar_Comendo;       // -- ProgressBar do Consumidor -- //  

  @FXML
  public Label labelPorcentagem_Cozinhando;   // -- Label do Produtor produzindo -- //
  public Label labelPorcentagem_Comendo;      // -- Label do Consumidor consumindo -- //

  private ArrayList <Produtor> chefList;        // -- ArrayList de Produtores -- //
  private ArrayList <Consumidor> clienteList;   // -- ArrayList de Consumidores -- //

  private ArrayList<Object> buffer;   // -- ArrayList do Buffer -- //
  private Semaphore cheio;    // -- Semaphore Cheio : Controla o numero de elemento do Buffer -- //
  private Semaphore vazio;    // -- Semaphore Vazio : Controla o numero de elementos vazios do Buffer -- //
  private Semaphore mutex;    // -- Semaphore Mutex : Garante que apenas UM Produtor ou UM Consumidor acesse o Buffer -- //

  public ImageView[] pratos = new ImageView[9];   // -- Vetor de ImageViews -- //

  Chef produtor;        // -- Eh criado uma variavel 'produtor' do tipo Chef -- //
  Cliente consumidor;   // -- Eh criado uma variavel 'consumidor' do tipo Cliente -- //

  /* ****************************************************************************************************************
  * - Metodo: initialize
  * - Funcao: Define a funcao initialize que eh chamada quando a aplicacao eh iniciada
  * - Parametros: 'url' do tipo URL, 'rb' do tipo ResouceBundle, sao objetos para carregar os recursos da aplicacao
  * - Retorno: nao possui retorno
  **************************************************************************************************************** */
  @Override 
  public void initialize (URL url, ResourceBundle rb) {

    /* **************************************************************************************************************
    * - 'chefLis' :  eh do tipo ArrayList e eh inicializada como vazia
    * - 'clienteList' : eh do tipo ArrayList e eh inicializada como vazia
    * - 'buffer' :  eh do tipo ArrayList, mas nao eh inicializado , sera utilizado para armazenar os Sushis (itens) 
    * produzidos pelo Produtor   
    ************************************************************************************************************** */
    chefList = new ArrayList<>();
    clienteList = new ArrayList<>();

    /* *********************************************************************************************************************
    * - 'cheio' : eh do tipo Semaphore, eh usado para sinalizar quando o Buffer estah cheio e as Thread Produtora  precisam 
    * esperar que a lista seja consumida, ou que tenha pelo menos um espaco disponivel, para continuar a producao
    * - 'vazio' : eh do tipo Semaphore, sinaliza quando o buffer esta vazio e as Thread Consumidora precisam esperar que a 
    * lista seja preenchida, ou que pelo menos tenha um item, para poderem consumir
    * - 'mutex' : Eh usado para garantir a Exclusao Mutua entre as Threads que acessam o Buffer, evitando que duas ou mais 
    * Threads tentem acessa-lo simultaneamente
    ********************************************************************************************************************* */
    buffer = new ArrayList<>();
    cheio = new Semaphore(0);
    vazio = new Semaphore(9); // esto usando 1 produtor e 1 consumidor
    mutex = new Semaphore(1);
    
    // -- CRIACAO DO PRODUTOR E ADICAO DELE NO ARRAYLIST DE PRODUTORES -- //
    GUI chefGUI = new GUI(labelPorcentagem_Cozinhando, label_TP, slider_Produtor, progressBar_Cozinhando, HBOX_Sushis);
    produtor = new Chef(buffer, cheio, vazio, mutex, chefGUI);
    chefList.add(produtor);  
    // -- CRIACAO DO CONSUMIDOR E ADICAO DELE NO ARRAYLIST DE CONSUMIDORES -- //
    GUI clienteGUI = new GUI(labelPorcentagem_Comendo, label_TC, slider_Consumidor, progressBar_Comendo, HBOX_Sushis);
    consumidor = new Cliente(buffer, cheio, vazio, mutex, clienteGUI);
    clienteList.add(consumidor);

    // -- Defino um int de Quantidade Maxima de Pratos igual a  -- //
    int qntdMaximaDePratos = 9;

    /* -----------------------------------------------------------------------------------------------------------------
    * - Configuram o Slider, que eh utilizado para permitir que o usuario selecione um valor dentro de um intervalo
    * - 'slider_Consumidor.setMax(...)' : define o valor maximo que pode ser selecionado no slider, que eh igual a 9
    * - 'slider_Consumidor.setMin(...)' : define o valor minimo que pode ser selecionado no slider, que eh igual a 1
    * - 'slider_Consumidor.setValue(...)' : define o valor inicial do slider como 1
    * --------------------------------------------------------------------------------------------------------------- */
    slider_Consumidor.setMax(qntdMaximaDePratos);
    slider_Consumidor.setMin(1);
    slider_Consumidor.setValue(1);

    /* -----------------------------------------------------------------------------------------------------------------
    * - Configuram o Slider, que eh utilizado para permitir que o usuario selecione um valor dentro de um intervalo
    * - 'slider_Produtor.setMax(...)' : define o valor maximo que pode ser selecionado no slider, que eh igual a 9
    * - 'slider_Produtor.setMin(...)' : define o valor minimo que pode ser selecionado no slider, que eh igual a 1
    * - 'slider_Produtor.setValue(...)' : define o valor inicial do slider como 2
    * --------------------------------------------------------------------------------------------------------------- */
    slider_Produtor.setMax(qntdMaximaDePratos);
    slider_Produtor.setMin(1);
    slider_Produtor.setValue(3);    

    /* --------------------------------------------------------------------------------------------------------------------------------------------
    * - 'Platform.runLater(...)' :  usuado para executar tarefas na Thread da IU do JavaFX
    * - 'label_Consumidor.setText(...)' : atualiza o texto exibido na Label do Consumidor de acordo a posicao  do Slider escolhida pelo usuario
    * - 'label_Produtor.setText(...)' : atualiza o texto exibido na Label do Consumidor de acordo a posicao do Slider escolhida pelo usuario
    * - '(int)slider_XXXXXXXX.getValue()' : converte o valor obtido pelo Slider do Consumidor do tipo double para um valor int, e o 'getValue()' 7
    * retorna um valor decimal que vai ser convertido
    * ------------------------------------------------------------------------------------------------------------------------------------------ */
    Platform.runLater(() -> {
      label_Consumidor.setText("Preparando Sushi: " + (int)slider_Consumidor.getValue() + " prato/t");
      label_Produtor.setText("Preparando: " + (int)slider_Produtor.getValue() + " prato/t");
    }); // fim do Platform
    
  } // fim do initialize


  /* *****************************************************************************************************
  * - Metodo: controleSliders
  * - Funcao: Atualizar os valores dos Sliders de Consumidor e Produtor de Sushis, exibindos nas Labels
  * - Parametros: 'e' do tipo MouseEvent, representando um evento de mouse na Interface
  * - Retorno: Nao posssui retorno
  ****************************************************************************************************** */
  public void controleSliders (MouseEvent e) {
    // -- Eh verificado se o evento do Mouse foi no Slider do Consumidor, se sim: -- //
    if (e.getSource().equals(slider_Consumidor)) { // if1
      // Eh atualizado o texto da Label do Consumidor com a media do consumo de sushi por minuto, obtida atraves do metodo '.getValue()'
      label_Consumidor.setText("[Comendo em media:" + (int)slider_Consumidor.getValue() + " prato/t");
    } // fim do if1
    // -- Eh verificado se o evento do Mouse foi no Slider do Produtor, se sim: -- //
    // Eh atualizado o texto da Label do Produtor com a media do consumo de sushi por minuto, obtida atraves do metodo '.getValue()'
    if (e.getSource().equals(slider_Produtor)) { // if2
      label_Produtor.setText("Preparando: " + (int)slider_Produtor.getValue() + " prato/t");
    } // fim do if2
  } // fim de controlerSliders

  /* **************************************************************************************************************
  * - Metodo: buttonIniciar
  * - Funcao: Inicia a simulacao do programa realizando uma serie de acoes na Interface quando o botao eh clicado
  * - Parametros: 'me', do tipo MouseEvent, que representa um evento de clique do Mouse, eh usado para verificar se
  * o evento ocorreu no 'buttonIniciar'
  * - Retorno: Nao posssui retorno
  ************************************************************************************************************** */
  @FXML
  public void buttonIniciar (MouseEvent me) {
    // -- O if1 verifica se o evento gerado na Interface foi quando o Botao Play foi clicado, se sim: -- //
    if (me.getSource().equals(imgView_ButtonPlay)) { // if1
      // -- Deixa o CheckBox da Musica selecionado -- //
      checkBox_Music.setSelected(true);
      // -- Define uma nova ImageView para o Botao Iniciar -- //
      imgView_ButtonPlay.setImage(new Image("view/img/button-play-inative.png"));
      // -- Seta a imagem do Botao de Pausar do Consumidor para a ImageView -- //
      imgView_pauseC.setImage(new Image("view/img/pause-c.png"));
      // -- Seta a imagem do Botao de Pausar do Produtor para a ImageView -- //
      imgView_pauseP.setImage(new Image("view/img/pause-p.png"));
      // -- Deixa a ImageView do Botao de Pausar do Consumidor visivel -- //
      imgView_pauseC.setVisible(true);
      // -- Deixa a ImageView do Botao de Pausar do Produtor visivel -- //
      imgView_pauseP.setVisible(true);
      // -- Desabilita o Botao Play -- //
      imgView_ButtonPlay.setDisable(true);
      // -- Define uma nova ImageView para o Botao Reiniciar -- // 
      imgView_ButtonRestart.setImage(new Image("view/img/button-restart.png"));
      // -- Deixa a ImageView do Botao Reiniciar visivel -- //
      imgView_ButtonRestart.setVisible(true);
      // -- Imprime msg no Terminal -- //
      System.out.println("[iniciando simulacao]");
      // -- Inicia a reproducao da trilha sonora do projeto -- //
      Jukebox.play("trilha.wav");
      // -- Eh dado start nas Threads -- //
      produtor.start();
      consumidor.start();
    } // fim do if1
  } // fim do buttonIniciar


  /* **************************************************************************************************************
  * - Metodo: buttonPaused
  * - Funcao: ...
  * - Parametros: 'me', do tipo MouseEvent, que representa um evento de clique do Mouse, eh usado para verificar se
  * o evento ocorreu no Botao de Pause
  * - Retorno: Nao posssui retorno
  ************************************************************************************************************** */
  @FXML
  public void buttonPausedC (MouseEvent mE) {
    // -- Verifica se o Consumidor nao esta pausado, se nao estiver: -- //
    if (!consumidor.isPaused()) {
      // -- Define o estado do Consumidor como pausado -- //
      consumidor.setPaused(true);
      // -- Mostra mensagem no terminal -- //
      System.out.println("\n\n\t[CONSUMIDOR PAUSADO]");
      // -- Define uma nova ImageView para o Botao de Pausar -- //
      imgView_pauseC.setImage(new Image("view/img/play-c.png")); 
    // -- Se o Consumidor estiver pausado, executa o bloco a seguir: -- //
    } else {
      // -- Define uma nova ImageView para o Botao de Pausar -- //
      imgView_pauseC.setImage(new Image("view/img/pause-c.png"));
      // -- Define o estado do Consumidor como despausado -- //
      consumidor.setPaused(false);
      // -- Mostra mensagem no terminal -- //
      System.out.println("\n\n\t[CONSUMIDOR DESPAUSADO]");
    } // fim do if-else
  } // fim do buttonPaused

  @FXML
  public void buttonPausedP (MouseEvent mE) {
    // -- Verifica se o Produtor nao esta pausado, se nao estiver: -- //
    if (!produtor.isPausedP()) {
      // -- Define o estado do Produtor como pausado -- //
      produtor.setPausedP(true);
      // -- Mostra mensagem no terminal -- //
      System.out.println("\n\n\t[PRODUTOR PAUSADO]");
      // -- Define uma nova ImageView para o Botao de Pausar -- //
      imgView_pauseP.setImage(new Image("view/img/play-p.png"));     
      // -- Se o Produtor estiver pausado, executa o bloco a seguir: -- //
    } else {
      // -- Define uma nova ImageView para o Botao de Pausar -- //
      imgView_pauseP.setImage(new Image("view/img/pause-p.png"));
      // -- Define o estado do Produtor como despausado -- //
      produtor.setPausedP(false);
      System.out.println("\n\n\t[PRODUTOR DESPAUSADO]");
    } // fim do if-else
  } // fim do buttonPaused

  @FXML
  public void buttonRestart (MouseEvent mE) {
    // -- Interrompe a execucao do Produtor -- //
    produtor.interrupt();
    // -- Interrompe a execucao do Consumidor -- //
    consumidor.interrupt();

    // -- Cria um novo Semaphore com valor 0, indica quando o buffer estah cheio -- //
    cheio = new Semaphore(0);
    // -- Cria um novo Semaphore com valor 9, indica que o buffer contem 9 elementos -- //
    vazio = new Semaphore(9);
    // -- Cria um novo Semaphore com 1, garante que somente uma Thread acesse o buffer por vez -- //
    mutex = new Semaphore(1);

    // -- CRIACAO DO PRODUTOR E ADICAO DELE NO ARRAYLIST DE PRODUTORES -- //
    GUI chefGUI = new GUI(labelPorcentagem_Cozinhando, label_TP, slider_Produtor, progressBar_Cozinhando, HBOX_Sushis);
    produtor = new Chef(buffer, cheio, vazio, mutex, chefGUI);
    chefList.add(produtor);  
    // -- CRIACAO DO CONSUMIDOR E ADICAO DELE NO ARRAYLIST DE CONSUMIDORES -- //
    GUI clienteGUI = new GUI(labelPorcentagem_Comendo, label_TC, slider_Consumidor, progressBar_Comendo, HBOX_Sushis);
    consumidor = new Cliente(buffer, cheio, vazio, mutex, clienteGUI);
    clienteList.add(consumidor);

    // -- Limpando o buffer -- //
    buffer.clear();    
    // -- Limpando o HBox de Sushis -- //
    HBOX_Sushis.getChildren().clear();

    // -- Resetando os Sliders: -- //
    slider_Consumidor.setMax(9);
    slider_Consumidor.setMin(1);
    slider_Consumidor.setValue(1);
    slider_Produtor.setMax(9);
    slider_Produtor.setMin(1);
    slider_Produtor.setValue(1);

    // -- Iniciando as Threads -- //
    consumidor.start();
    produtor.start();

  } // fim do buttonRestart



  /* **************************************************************************************************************
  * - Metodo: checkBoxMusic
  * - Funcao: Controlar a reproducao de uma musica
  * - Parametros: 'event', do tipo ActionEvent, que representa um evento de clique no CheckBox da musica
  * - Retorno: Nao posssui retorno
  ************************************************************************************************************** */
  @FXML
  public void checkBoxMusic (ActionEvent event) {    
    // -- Eh verificado se 'checkBox_Music' esta selecionada, se sim: -- //
    if (checkBox_Music.isSelected()) {
      // -- A musica eh tocada pela classe Jukebox e o metodo '.play(...)' -- //
      Jukebox.play("trilha.wav");
    // -- Se nao: -- //
    } else {
      // -- A musica eh pausada pela classe Jukebox e o metodo '.pause()' -- //
      Jukebox.pause();
    } // fim do if-else    
  } // fim do checkBoxMusic


  /* **************************************************************************************************************
  * - Metodo: checkBoxPause
  * - Funcao: Responsavel por pausar a reproducao de uma musica, quando o CheckBox eh clicado
  * - Parametros: 'event', do tipo ActionEvent, que representa um evento de clique no CheckBox da musica
  * - Retorno: Nao posssui retorno
  ************************************************************************************************************** */
  @FXML
  public void checkBoxPause (ActionEvent event) {
    // -- Verifica se o evento foi de clique no CheckBox, se sim: -- //
    if (event.getSource().equals(checkBox_Music)) {
      // -- A classe Jukebox pausa a musica -- //
      Jukebox.pause();
    } // fim do if
  /* ---------------------------------------------------------------------------------------------
  * - se o CheckBox ESTIVER SELECIONADO, e eu clicar nele para desmarcar, a musica eh PAUSADA 
  * - se o CheckBox NAO ESTIVER SELECIONADO, e eu clicar nele para marcar, a musica eh INICIADA
  * ------------------------------------------------------------------------------------------- */
  } // fim de checkBoxPause

} // fim de ControllerTela