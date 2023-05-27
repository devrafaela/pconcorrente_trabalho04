package restaurant;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import model.Consumidor;

/* *******************************************************************************
* Autor............: Rafaela Pereira Santos
* Matricula........: 202110838
* Inicio...........: 01/05/2023
* Ultima alteracao.: 14/05/2023
* Nome.............: Cliente
* Funcao...........: Representa um Cliente eque simula a acao de comer o Sushi
******************************************************************************* */
public class Cliente extends Consumidor {
  private final GUI rGUI;       // Representa uma instancia da classe GUI
  private Sushi sushiAtual;     // Representa meu Sushi atual
  private int termineiDeComer;  // Representa se Terminei de Fazer o sushi
  private boolean isPaused;     // Representa quando o Client esta pausado ou nao

  /* *************************************************************************************************
  * - Nome do Constutor: Cliente
  * - Funcao: Cria uma nova instancia de Cliente
  * - Parametros: 'buffer' do tipo ArrayList, 'full', 'empty' e 'mutex' do tipo Sempahore
  * - Retorno: Nao possui retorno
  * - Explicacao: Responsavel por criar uma nova instancia de CLiente, que representa o cliente que
  * consome os pratos de Sushis no programa
  ************************************************************************************************** */
  public Cliente (ArrayList<Object> buffer, Semaphore full, Semaphore empty, Semaphore mutex, GUI rGUI) {
    super(buffer, full, empty, mutex);
    this.rGUI = rGUI;
    this.isPaused = false;
  } // fim de Cliente

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


  /* **********************************************************************************************
  * - Metodo: jaComida
  * - Funcao: Simula o ato de comer um Sushi
  * - Parametros: Nao possui parametros
  * - Retorno: Retorna um booleano indicando se o Sushi atual ja foi completamente comido ou nao
  ********************************************************************************************** */ 
  public boolean jaComi() { // completed
    // -- Se o Sushi atual for nulo, entao nao ha Sushi para comer e o metodo retorna true -- //
    if (sushiAtual == null) { return true; } // fim do if1
    // -- Se 'termineiDeComer' for maior/igual ao tamanho do Sushi atual, isso significa que o sushi ja foi completamente comido e retorna true -- //
    if (termineiDeComer >= sushiAtual.getTamanhoSUSHI()) {
      termineiDeComer = 0;
      return true;
    } // fim do if2
    // -- Se nao, o progressBar eh atualizado na Interface com o valor das variaveis, e retorna false, indicando que ainda ha Sushi para comer -- //
   rGUI.progresso(termineiDeComer, sushiAtual.getTamanhoSUSHI());
   termineiDeComer++;
   return false;
  } // fim do jaComi


  /* ***************************************************************
  * - Metodo: isEating
  * - Funcao: Verifica se o Cliente esta comendo Sushi ou nao
  * - Parametros: Nao possui parametros
  * - Retorno: Retorna um booleano
  *************************************************************** */
  public boolean isEating() { 
    // -- Verifica se isPaused eh verdadeiro, se sim, ele retorna true, pois o Cliente nao esta comendo -- //
    if (isPaused) { return true; } // fim do if
    // -- Verifica se o Cliente ja comeu todo o Sushi, se sim: -- //
    if (jaComi()) { // if2
      try {
        // -- Reinicia a barra de progressBar para o proximo Sushi -- //
        rGUI.progresso(0, 0);
        System.out.printf("\n[Cliente esta ansioso para comer...]");
        // -- Responsavel por gerar uma pausa aleatoria para simular o cliente escolhendo e comendo o Sushi -- //
        consumindo();
      } catch (InterruptedException ie) {
        System.out.println("[Erro: CLIENTE.JAVA -> isEating()]");
        ie.printStackTrace();
      } // fim do try-catch
    } // fim do if2
    return true; 
  } // fim do isEating

  /* ***************************************************************
  * - Metodo: consumir_Sushi
  * - Funcao: Responsavel pela simulacao de um Restaurante de Sushi
  * - Parametros: 'o' do tipo Object
  * - Retorno: Nao possui retorno
  *************************************************************** */
  @Override
  protected void consumir_Sushi (Object o) {
    // -- 'sushiAtual' eh atualizada com o Sushi que o Cliente vai consumir -- //
    sushiAtual = (Sushi) o;
    System.out.printf("\n[Cliente comendo: " + sushiAtual.getNomeSUSHI() + " | tamanho: " + sushiAtual.getTamanhoSUSHI() + "]");
    // -- Eh removido um item da Interface -- //
    rGUI.removeItem();
  } // fim de consumir_Sushi


  /* *****************************************************************************************
  * - Metodo: run
  * - Funcao:Eh responsavel por executra a logica do Ciente enquanto ele estiver cozinhando
  * - Parametros:Nao possui parametros
  * - Retorno: Nao possui retorno
  ***************************************************************************************** */
  @Override
  public void run() {
    // -- Loop que continuara sendo executado enquando o Ciente estiver comendo -- //
    while (isEating()) {
      try {
        // -- Metodo que faz a Thread atual dormir por tempo determinado de tempo, o valor eh definido como = (1000 / valor do slider) -- //
        // -- que eh a velocidade da Producao de Sushi -- //
        sleep(1000 / (int) rGUI.getSlider().getValue());
      } catch (InterruptedException ie) {
        System.out.println("[Erro: CLIENTE.JAVA -> run()]");
        ie.printStackTrace();    
      } // fim do try-catch
    }// fim do while 
  } // fim de run

  public GUI getRGUI() { return rGUI; }   // fim do getRGUI
  public boolean isPaused () { return isPaused; }   // fim de isPaused
  public void setPaused (boolean isPaused) { this.isPaused = isPaused; }    // fim de setPaused

} // fim da classe