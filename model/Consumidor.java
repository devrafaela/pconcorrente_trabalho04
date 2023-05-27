package model;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/* *********************************************************************************
* Autor............: Rafaela Pereira Santos
* Matricula........: 202110838
* Inicio...........: 11/05/2023
* Ultima alteracao.: 14/05/2023
* Nome.............: Consumidor
* Funcao...........: Tem como funcao implementar a logica para consumir um item
* de um Buffer compartilhado
********************************************************************************* */
public abstract class Consumidor extends Thread {

  private final ArrayList<Object> buffer;   // -- Eh declarado o Buffer do tipo ArrayList -- //
  private final Semaphore cheio;    // -- Eh declarado o Semaforo 'cheio'  -- //
  private final Semaphore vazio;    // -- Eh declarado o Semaforo 'vazio' -- //
  private final Semaphore mutex;    // -- Eh declarado o Semaaforo 'mutex' -- //

  /* ***********************************************************************************************
  * - Nome do Constutor: Consumidor
  * - Funcao: Cria um Consumidor e inicializa seus atributos
  * - Parametros: 'buffer' do tipo Arraylist, 'full', 'empty' e 'mutex' do tipo Semaphore
  * - Retorno: Nao possui retorno
  * - Explicacao: O 'buffer' eh inicializado e representa o Buffer compartilhado entre as Threads,
  * o 'full' eh um Semaforo que controla o numero de slots ocupados no Buffer, o 'empty' eh um Sema-
  * foro que controla o numero de slots vazios no Buffer e 'mutex', eh um Semaforo que garante a
  * exclusao mutua no acesso ao Buffer
  *********************************************************************************************** */
  public Consumidor (ArrayList<Object> buffer, Semaphore full, Semaphore empty, Semaphore mutex) {
    this.buffer = buffer;
    this.cheio = full;
    this.vazio = empty;
    this.mutex = mutex;
  } // fim de Consumidor

  /* ********************************************************************************
  * - Metodo: consumir_Sushi
  * - Funcao: Consome o Sushi
  * - Parametros: 'obj' do tipo Object, representa o objeto que vai ser consumido
  * - Retorno: Nao possui retorno
  ******************************************************************************** */
  protected abstract void consumir_Sushi (Object obj );  // fim de consumir_Sushi

  /* ********************************************************************************
  * - Metodo: removerr_Sushi
  * - Funcao: Remove o Sushi do Buffer
  * - Parametros: Nao possui parametros
  * - Retorno: Retorna o Sushi removido do Buffer
  ******************************************************************************** */
  private Object remover_Sushi() {
    Object ob = buffer.get(0);
    buffer.remove(0);
    return ob;
  } // fim de remover_Sushi

  /* ********************************************************************************
  * - Metodo: consumindo
  * - Funcao: Consome o Sushi do Buffer compartilhado
  * - Parametros: Nao recebe parametros
  * - Retorno: Nao possui retorno
  ******************************************************************************** */
  public void consumindo() throws InterruptedException {
    // -- O Semaforo 'cheio' eh adquirido, o que significa que um Sushi esta disponivel para ser consumido no Buffer -- //
    cheio.acquire();
    // -- Ele adquire o Semaforo 'mutex' para garantir que nenhuma outra Thread esteja acessando o buffer ao mesmo tempo que esta Thread -- //
    mutex.acquire();
    // -- O Sushi eh entao removido do Buffer e armazenado na variavel 'sushi' -- //
    Object sushi = remover_Sushi();
    // -- O Semaforo 'mutex' eh liberado para permitir que outras Threads acessem o Buffer -- //
    mutex.release();
    // -- O Semaforo 'vazio' eh liberado, indicando que um esperço no Buffer esta disponivel para ser preenchido -- //
    vazio.release();
    // -- Eh consumido o Sushi retirado do Buffer -- //
    consumir_Sushi(sushi);
  } // fim de consumindo
 
} // fim da classe Consumidor

