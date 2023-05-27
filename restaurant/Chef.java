package restaurant;
import java.util.concurrent.Semaphore;
import javafx.scene.image.ImageView;
import model.Produtor;
import java.util.ArrayList;
import java.util.Random;

/* ***************************************************************
* Autor............: Rafaela Pereira Santos
* Matricula........: 202110838
* Inicio...........: 09/05/2023
* Ultima alteracao.: 14/05/2023
* Nome.............: Chef
* Funcao...........: Representa o Chef que cozinha os Sushis
*************************************************************** */
public class Chef extends Produtor {
  private final GUI rGUI;   // Representa uma instancia da classe GUI
  private Sushi sushiAtual;  // Representa meu Sushi atual
  private int termineiDeFazer; // Representa se Terminei de Fzer o Sushi
  private boolean isPausedP; // Representa quando o Chef esta pausado ou nao

  /* ******************************************************************************************
  * - Nome do Constutor: Chef
  * - Funcao: Cria uma nova instancia de Chef
  * - Parametros: 'buffer' do tipo ArrayList, 'full', 'empty' e 'mutex' do tipo Sempahore
  * - Retorno: Nao possui retorno
  * - Explicacao: Responsavel por criar uma nova instancia de Chef, que representa o Chef que
  * cozinha os pratos de Sushis no programa
  ******************************************************************************************* */
  public Chef (ArrayList<Object> buffer, Semaphore full, Semaphore empty, Semaphore mutex, GUI rGUI) {
    super(buffer, full, empty, mutex);
    this.rGUI = rGUI;
    sushiAtual = selecionaSushi();
    rGUI.publishItem("[Chef estah cozinahndo : " + sushiAtual.getNomeSUSHI() + "]");
    this.isPausedP = false;    
  } // fim de Chef

  /* *************************************************************************
  * - Metodo: interrupt
  * - Funcao: Interrompe a execucao da Thread em que ele esta sendo chamado
  * - Parametros: Sem parametro
  * - Retorno: Sem retorno
  ************************************************************************* */
  @Override
  public synchronized void interrupt(){    
    // -- Define o valor do progressBar como 0 -- //
    rGUI.getProgressBar().setProgress(0);
    // -- A Thread eh interrompida pela classe pai -- //
    super.stop();
  } // fim de interrupt

  /* ************************************************************************************
  * - Metodo: selecionaSushi
  * - Funcao: Ele seleciona aleatoriamente um Sushi a partir de uma lista pre-definida
  * - Parametros: descricao dos parametros recebidos
  * - Retorno: descricao do valor retornado
  ************************************************************************************ */
  private Sushi selecionaSushi() {
    switch (new Random().nextInt(9)) {
      case 0:
       return new Sushi("Sushi 1", Sushi.OUTRO_PRATO, new ImageView ("/view/img/prato1.png"));
      case 1:
        return new Sushi("Sushi 2", Sushi.SUSHI_GRANDE,  new ImageView ("/view/img/prato2.png"));
      case 2:
        return new Sushi("Sushi 3", Sushi.SUSHI_PEQ,  new ImageView ("/view/img/prato3.png"));
      case 3:
        return new Sushi("Sushi 4", Sushi.SUSHI_PEQ,  new ImageView ("/view/img/prato4.png"));
      case 4:
        return new Sushi("Sushi 5", Sushi.SUSHI_MEDIO,  new ImageView ("/view/img/prato5.png"));
      case 5:
       return new Sushi("Sushi 6", Sushi.SUSHI_GRANDE,  new ImageView ("/view/img/prato6.png"));
      case 6:
        return new Sushi("Sushi 7", Sushi.OUTRO_PRATO,  new ImageView ("/view/img/prato7.png"));
      case 7:
        return new Sushi("Sushi 8", Sushi.SUSHI_PEQ,  new ImageView ("/view/img/prato8.png"));
      case 8:
        return new Sushi("Sushi 9", Sushi.SUSHI_MEDIO,  new ImageView ("/view/img/prato9.png"));
      default:
        return new Sushi("Sushi Especial", Sushi.OUTRO_PRATO,  new ImageView ("/view/img/prato5.png"));     
    } // fim do switch
  } // fim de selecionaSushi

  /* **********************************************************************
  * - Metodo: jaProduzi
  * - Funcao: Verifica se o chef ja produziu o sushi atual por completo
  * - Parametros: Nao possui parametro
  * - Retorno: 'true' para Sushi concluido, 'false' para Sushi em preparo
  ********************************************************************** */
  private boolean jaProduzi() { // completed
    // -- Verifica se o numero de ingredientes produzis eh maior ou igual ao tamanho do SUshi atual, se sim: -- //
    if (termineiDeFazer >= sushiAtual.getTamanhoSUSHI()) {
      // -- Se eu terminei, o numero de ingredientes eh setado para 0 -- //
      termineiDeFazer = 0;
      // -- Retorna true para dizer que ja concluiu -- //
      return true;
    } // fim do if
    // -- Atualiza o progressBar -- //
    rGUI.progresso(termineiDeFazer, sushiAtual.getTamanhoSUSHI());
    System.out.printf("\n[Chef estah cozinhando o: " + sushiAtual.getNomeSUSHI() + " | tamanho total: " + (termineiDeFazer + 1) + "/" + sushiAtual.getTamanhoSUSHI() + "]"); 
    // -- Incrementa o numero de ingredientes produzidos -- //
    termineiDeFazer++;
    // -- Retorna false para dizer que ainda nao concluiu -- //
    return false;
  } // fim do jaProduzi

  /* **********************************************************************************
  * - Metodo: isCooking
  * - Funcao: Responsavel por verificar se o Chef esta cozinhando um Sushi atualmente
  * - Parametros: Sem parametros
  * - Retorno: 
  *********************************************************************************** */
  private boolean isCooking() {
    // -- Verifica se a flag eh verdaedira, se sim, retorna false, para indicar que a o Chef nao esta cozinhando -- //
    if (isPausedP) { return false; } // fim do if1

    // -- Verifica se o Chef ja produziu o Sushi atual, se sim: -- //
    if (jaProduzi()) {
      try {
        // -- Atualiza o progressBar na Interface para indicar que o Sushi atual foi completamente produzido -- //
        rGUI.progresso(sushiAtual.getTamanhoSUSHI(), sushiAtual.getTamanhoSUSHI());
        // -- Espera um tempo aleatorio para simular o procresso de producao do Sushi -- //
        produzindoSushi();
        // -- Publica a imagem do Sushi na Interface -- //
        rGUI.publishItem(sushiAtual.getImageView());
        // -- Seleciona um novo tipo de Sushi aleatoriamente -- //
        sushiAtual = selecionaSushi();
      } catch (InterruptedException ie) {
        System.out.println("[Erro: CHEF.JAVA -> isCooking()]");
        ie.printStackTrace();
      } // fim do try-catch
    } // fim do jaProduzi
    return false;
  } // fim de isCooking

  /* *****************************************************************************************************
  * - Metodo: produz_Sushi
  * - Funcao: Eh responsavel por produzir um objeto do tipo Object e adiciona-lo ao buffer compartilhado
  * - Parametros:Nao possui parametros
  * - Retorno: Retorna apenas 'sushiAtual'
  ***************************************************************************************************** */
    @Override
    protected Object produz_Sushi() { return sushiAtual; } // fim de produz_Sushi

  /* *****************************************************************************************************
  * - Metodo: run
  * - Funcao:Eh responsavel por executra a logica do Chef enquanto ele estiver cozinhando
  * - Parametros:Nao possui parametros
  * - Retorno: Nao possui retorno
  ***************************************************************************************************** */
    @Override
    public void run() {
      // -- Loop que continuara sendo executado enquando o Chef estiver cozinhando -- //
      while(!isCooking()) {
        try {
          // -- Metodo que faz a Thread atual dormir por tempo determinado de tempo, o valor eh definido como = (1000 / valor do slider) -- //
          // -- que eh a velocidade da Producao de Sushi -- //
          sleep(1000 / (int) rGUI.getSlider().getValue());
        } catch (InterruptedException ie) {
          System.out.println("[Erro: CHEF.JAVA -> run()]");
          ie.printStackTrace();
        } // fim do try-catch
      } // fim do while
    } // fim de run

    public GUI getGUI () { return rGUI; }   // fim de getGUI
    public boolean isPausedP () { return isPausedP; }   // fim de isPausedP
    public void setPausedP (boolean isPaused) { this.isPausedP = isPaused; }    // fim de setPausedP

  } // fim da classe
  

  

    