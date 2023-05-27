package restaurant;
import javafx.scene.image.ImageView;

/* ***************************************************************
* Autor............: Rafaela Pereira Santos
* Matricula........: 202110838
* Inicio...........: 01/05/2023
* Ultima alteracao.: 14/05/2023
* Nome.............: Sushi
* Funcao...........: Fornece um Sushi e o seu 'tamanho'
*************************************************************** */
public class Sushi {

  // -- Eh definido abaixo diferentes tamanhos de pratos de Sushi -- //
  public static final int OUTRO_PRATO = 8;
  public static final int SUSHI_GRANDE = 6;
  public static final int SUSHI_MEDIO = 5;
  public static final int SUSHI_PEQ = 3;

  // -- Eh definido duas variaveis de instancia, que sao informacoes do prato de Sushi --//
  private String nomeDoSushi;   // -- Armazena o nome do Sushi -- //
  private int tamanhoDoSushi;   // -- Armazena o tamanho do Sushi -- //
  private ImageView imgSushi;
  /* **********************************************************************************************
  * - Nome do Constutor: Sushi
  * - Funcao: Cria e inicializa um Sushi
  * - Parametros: 'nome' do tipo String, que representa o nome do Sushi, e 'tam' do tipo int que
  * representa o tamanho do sushi
  * - Retorno: Nao possui retorno
  * - Explicacao: Quando eh chamado, ele cria um novo objeto Sushi e inicializa seus atributos
  * 'nomeDoSushi' e 'tamanhoDoSushi' com os valores passados como parametros
  ********************************************************************************************* */  
  public Sushi (String nome, int tam, ImageView imgSushi) {
    this.nomeDoSushi=nome;
    this.tamanhoDoSushi=tam;
    this.imgSushi = imgSushi;
  } // fim do construtor

  public String getNomeSUSHI() { return nomeDoSushi; }  // fim do getNomeSUSHI

  public int getTamanhoSUSHI() { return tamanhoDoSushi; }  // fim do getTamanhoSUSHI

  public ImageView getImageView() { return imgSushi; }  // fim de getImageView

  public void setName(String nome) { this.nomeDoSushi = nome; }  // fim do setName

  public void setTamanho(int tam) { this.tamanhoDoSushi = tam; }  // fim do setTamanho

} // fim da classe Sushi
