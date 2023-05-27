package model;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/* ****************************************************************************************
* Autor............: Rafaela Pereira Santos
* Matricula........: 202110838
* Inicio...........: 09/05/2023
* Ultima alteracao.: 13/05/2023
* Nome.............: Produtor
* Funcao...........: Tem como funcao implementar a logica para produzir um item
* de um Buffer compartilhado
***************************************************************************************** */
public abstract class Produtor extends Thread {

  private final ArrayList<Object> buffer;   // -- Eh declarado o Buffer do tipo ArrayList -- //
  private final Semaphore cheio;    // -- Eh declarado o Semaforo 'cheio'  -- //
  private final Semaphore vazio;    // -- Eh declarado o Semaforo 'vazio' -- //
  private final Semaphore mutex;    // -- Eh declarado o Semaaforo 'mutex' -- //

  /* ***********************************************************************************************
  * - Nome do Constutor: Produtor
  * - Funcao: Cria um Produtor e inicializa seus atributos
  * - Parametros: 'buffer' do tipo Arraylist, 'full', 'empty' e 'mutex' do tipo Semaphore
  * - Retorno: Nao possui retorno
  * - Explicacao: O 'buffer' eh inicializado e representa o Buffer compartilhado entre as Threads,
  * o 'full' eh um Semaforo que controla o numero de slots ocupados no Buffer, o 'empty' eh um Sema-
  * foro que controla o numero de slots vazios no Buffer e 'mutex', eh um Semaforo que garante a
  * exclusao mutua no acesso ao Buffer
  *********************************************************************************************** */
  public Produtor (ArrayList<Object> buffer, Semaphore full, Semaphore empty, Semaphore mutex) {
    this.buffer = buffer;
    this.cheio = full;
    this.vazio = empty;
    this.mutex = mutex;
  } // fim de Produtor

  /* *************************************************************************************************
  * - Metodo: produz_Sushi
  * - Funcao: Produz um Sushi
  * - Parametros: Nao possui parametros
  * - Retorno: Nao possui retorno
  * - Explicacao: Eh uma declaracao de metodo na classe abstrata, mas deve ser implementado nas classes
  * Concentras que extendem ela. Eh usado para fazer um Sushi
  **************************************************************************************************** */
  protected abstract Object produz_Sushi(); // // fim de produz_Sushi

  /* *************************************************************************************************
  * - Metodo: inserir_Sushi
  * - Funcao: Inserir um Sushi no Buffer
  * - Parametros: 'obj' do tipo Object
  * - Retorno: Nao possui retorno
  * - Explicacao: Tem como objetivo produzir Sushis e inseri-los no Buffer compartilhado
  **************************************************************************************************** */
  private void inserir_Sushi(Object obj) {  // inserir item
    buffer.add(obj);
  } // fim de inserir_Sushi

  /* ***************************************************************
  * - Metodo: nome do metodo
  * - Funcao: testa os valores para transmissao
  * - Parametros: descricao dos parametros recebidos
  * - Retorno: descricao do valor retornado
  *************************************************************** */
  protected void produzindoSushi() throws InterruptedException {
    // -- O metodo 'produz_Sushi()' eh cahamado e o retorno eh armazenado no Sushi --//
    Object sushi = produz_Sushi();
    // -- A Thread tenta adquirir o Semaforo 'vazio', que representa o numero de slots vazios no Buffer, e espera se o Semaforo estiver em 0 -- //
    vazio.acquire();
    // -- A Thread tenta adquirir o Semafoto 'mutex', que eh usado para garantir a exclusao mutua na manipulacao do Buffer, e espera se o Semafoto estiver em 0 -- //
    mutex.acquire();
    // -- Sushi eh inserido no Buffer -- //
    inserir_Sushi(sushi);
    // -- O Semaforo 'mutex' eh liberado para permitir que outras threads acessem o Buffer -- //
    mutex.release();
    // -- o Semaforo 'cheio', que representa o numero de slots preenchidos no Buffer, eh liberado para permitir que outras threads consumam os itens no Buffer -- //
    cheio.release();
  } // fim de produzindoSushi

} /// fim da classe Produtor


