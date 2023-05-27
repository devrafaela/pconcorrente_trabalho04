package model;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/* ***************************************************************
* Autor............: Rafaela Pereira Santos
* Matricula........: 202110838
* Inicio...........: 01/05/2023
* Ultima alteracao.: 07/05/2023
* Nome.............: Jukebox
* Funcao...........: Onde fica musica que vai tocar
*************************************************************** */
public abstract class Jukebox {

	/**
	 * Eh definido uma cosntante chamada 'DIRETORIO' do tipo String que representa o caminho
	 * para o diretorio onde estao armazenados os arquivos de musica que serao utilizados.
	 * Eh privada pois so pode ser acessada dentro dessa classe. E estatica pois indica que essa variavel
	 * pertence somente à classe em si, e nao a uma instancia especifica dela. Eh final pois nao pode ser alterada
	 * depois de ser inicializada.
	 */
	private static final String DIRETORIO = "./view/audio/";
	
	/**
	 * Eh definido uma variavel estatido do tipo Clip chamada 'clip'. A Classe Clip eh usada para reproduzir
	 * sons em Java. Eh privada pois so pode ser acessada dentro dessa classe. E estatica pois indica que essa variavel
	 * pertence somente à classe em si, e nao a uma instancia especifica dela.
	 */
	private static Clip clip; 
	

	/********************************************************************
	* Metodo: play
	* Funcao: Recebera uma String com o nome do arquivo de musica a ser
	* reproduzido usando a classe 'Clip'.
	* Parametros: Uma String chamada 'fileName'
	* Retorno: nao possui retorno
	****************************************************************** */
	public static void play (String fileName) {
		try {
			/**
			 * Eh criado uma variavel do tipo File chamada 'audio', onde esta sendo acesasdo um arquivo de musica
			 * a partir do caminho definido pela constante 'DIRETORYPATH' concatenado com o nome do arquivo passado
			 * como parametro. O metodo '.getClassLoader().getResource(...).getFile()' eh usado para obter a localizacao
			 * do arquivo no sistema de arquivos do projeto.
			 */
			File audio = new File(Jukebox.class.getClassLoader().getResource(DIRETORIO + fileName).getFile());
			// -- No if sera verificado se o arquivo existe -- //
			if (audio.exists()) {
			// -- Se o audio existir, eh criado uma instancia da classe 'AudioInputStream' chamada 'inputStream' a partir do arquivo de audio -- //
				System.out.println("[Audio encontrado com sucesso!]");
				AudioInputStream inptStream = AudioSystem.getAudioInputStream(audio);
				// -- A seguir, ocorre a criacao de uma instancia da classe 'Clip' usando o metodo '.getClip()', da classe 'AudioSystem' -- //
				// -- O 'Clip' eh um objeto que permite a reproducao de audio -- //
				clip = AudioSystem.getClip();
				// -- Eh aberto o arquivo de audio usando o metodo '.open()' e a instancia 'inputStream' criada anteriormente -- //
				clip.open(inptStream);
				// -- Abaixo eh definido que o audio seja reproduzido continuamente, usando o metodo '.loop()' -- /
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				// -- Eh inciado enfim a reproducao do audio usando o metodo ''.start()' -- //
				clip.start();
				// -- Caso o arquivo de audio nao exista, uma mensagem de erro eh impressa -- //
			} else { System.out.println("[Musica nao Encontrada :(]"); }
			// -- Se ocorrer alguma exececao ao tentar abrir o arquivo de audio, uma mensagem de erro eh impressa -- //	
		} catch (Exception e) {
			System.out.println("[Exececao encontrada]\n- Pilha: ");
			e.printStackTrace();
		} // fim do try-catch
	} // fim do metodo play


	/********************************************************************
	* Metodo: pause
	* Funcao: Se o audio estiver tocando ele podera ser pausado, se nao, 
	* podera ser iniciado
	* Parametros: sem parametros
	* Retorno: sem retorno
	****************************************************************** */
	public static void pause() {
		// -- O primeiro if verifica se a variavel 'clip' eh diferente de nulo (caso sim executa o segundo if) -- //
		if (clip != null) {		
			// -- O segundo if verifica se o audio esta em execucao atraves do metodo '.isRunning()' da classe 'Clip' -- //
			if(clip.isRunning()) {
				// -- Se o audio estiver em execucao, ele eh interrompido usando o metodo '.stop()', tambem de 'Clip' -- //
				clip.stop();
				// -- Se caso o audio nao estiver executando, ele eh iniciado atraves do metodo '.start()' tambem de 'Clip' -- //
			} else {
				clip.start();
			} // fim do else
		} // fim do primeiro if
	} // fim do metodo pause

} // fim da classe
