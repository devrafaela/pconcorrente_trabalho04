package restaurant;

import javafx.beans.property.SimpleStringProperty;

/* ***************************************************************
* Autor............: Rafaela Pereira Santos
* Matricula........: 202110838
* Inicio...........: 11/05/2023
* Ultima alteracao.: 13/05/2023
* Nome.............: Pessoa
* Funcao...........: Representa uma pessoa que esta envolvida na
* preparacao ou consumo de um Sushi
*************************************************************** */
public class Pessoa {
  private SimpleStringProperty nomePessoa;    // -- Representa o Nome da Pessoa envolvida na Producao/Consumo do Sushi -- //
  private SimpleStringProperty sushi;         // -- Representa o Tipo de Sushi que esta sendo Preparado/Consumido -- //
  private SimpleStringProperty tamanho;       // --  Representa o Tamanho do Sushi (grande, medio ou pequeno) -- //
  private SimpleStringProperty porcentagem;   // -- Representa a Porcentagem de Conclusao da Preparacao/Consumo do Sushi -- //

  /* **************************************************************************************
  * - Nome do Constutor: Pessoa
  * - Funcao: Cria um objeto Pessoa e inicializa os atributos da classe com valorez vazios
  * - Parametros: Nao possui parametros
  * - Retorno: Nao possui retorno
  * - Explicacao: Dentro dele eh criado um obj 'SimpleStringProperty' para cada atributo,
  * o valor inicial de cada atributo eh uma String vazia
  *************************************************************************************** */
  public Pessoa() {
    this.nomePessoa = new SimpleStringProperty("");
    this.sushi = new SimpleStringProperty("");
    this.tamanho = new SimpleStringProperty("");
    this.porcentagem = new SimpleStringProperty("");
  } // fim do construtor Pessoa

  public String getNomePessoa() { return nomePessoa.get(); }                   // fim do getNomePessoa
  public SimpleStringProperty nomePessoaProperty() { return nomePessoa; }      // fim do nomePessoaProperty

  public String getSushi() { return sushi.get(); }                            // fim do getSushi
  public SimpleStringProperty sushiProperty() { return sushi; }               // fim do sushiProperty

  public String getTamanho() { return tamanho.get(); }                        // fim do getTamanho
  public SimpleStringProperty tamanhoProperty() { return tamanho; }           // fim do tamanhoProperty

  public String getPorcentagem() { return porcentagem.get(); }                // fim de getPorcentagem
  public SimpleStringProperty porcentagemProperty() { return porcentagem; }   // fim de porcentagemProperty

  public void setTamanho (String tamanho) { this.tamanho.set(tamanho); }                // fim de setTamanho
  public void setNomePessoa (String nomePessoa) { this.nomePessoa.set(nomePessoa); }    // fim de setNomePessoa

  public void setSushi (String sushi) { this.sushi.set(sushi); }                            // fim de setSushi
  public void setPorcentagem (String porcentagem) { this.porcentagem.set(porcentagem); }    // fim de setPorcentagem
} // fim da classe Pessoa
