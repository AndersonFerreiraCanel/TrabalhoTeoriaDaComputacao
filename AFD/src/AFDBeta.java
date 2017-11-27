import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument.BranchElement;

public class AFDBeta {
	public static void main(String[] args) {

		// ENTRADA DO ALFABETO
		String alfabeto;
		boolean validAlf = false;

		alfabeto = entrarAlf();
		validAlf = verificaAlf(alfabeto);

		while (validAlf) {
			alfabeto = entrarAlf();
			validAlf = verificaAlf(alfabeto);

			if (validAlf) {
				JOptionPane.showMessageDialog(null, "Dados invalidos!!!\n" + "Tente novamente.", "TENTE NOVAMENTE",
						JOptionPane.WARNING_MESSAGE);
			} else {
				entradaValidada();
			}
		}

		String[] alfArray = splitVirgula(alfabeto);

		char[] alf = new char[alfArray.length];
		for (int k = 0; k < alfArray.length; k++) {
			alf[k] = alfArray[k].charAt(0);
		}

		// ENTRADA DO CONJUNTO DE ESTADOS
		String estad;// = "q0,q1,q2,q3";
		boolean validEst = false;

		estad = entraEst();
		validEst = verificaEst(estad);
		validEst = checkEstEquals(estad);

		while (validEst) {

			estad = entraEst();
			validEst = verificaEst(estad);

			if (validEst) {
				JOptionPane.showMessageDialog(null, "Dados invalidos!!!\n" + "Tente novamente.", "TENTE NOVAMENTE",
						JOptionPane.WARNING_MESSAGE);
			}
		}

		String[] estArray = splitVirgula(estad);

		int estados = estArray.length;
		int[] est = new int[estados];

		for (int k = 0; k < estados; k++) {
			est[k] = k;
		}
		// --------------------------------------------------------------------------------------------------------------
		// ENTRADA FUNCAO DE TRANSIȃO modelo formal
		String delta = null;
		String[] funcDeltaAux = new String[alfArray.length * estArray.length];

		boolean valFunc2 = false;
		boolean valFunc = false;

		int c = 0, cont1 = 0;
		tutorialTransicao();
		for1: for (c = 0; c < (alfArray.length * estArray.length); c++) {
			delta = entraFunc();

			try {
				if (delta.equals(null)) {
				}
			} catch (Exception e) {
				entradaInvalida();
				JOptionPane.showMessageDialog(null, "Para sair use a tecla 's'!");
				c--;
				continue for1;
			}

			sair: if (delta.equalsIgnoreCase("S")) {
				break for1;
			}

			tutorial: if (delta.equalsIgnoreCase("I")) {
				tutorialTransicao();
				c--;
				continue for1;
			}

			anteriores: if (delta.equalsIgnoreCase("A")) {
				JOptionPane.showMessageDialog(null,
						"Transições informadas até o momento:\n" + Arrays.toString(funcDeltaAux));
				c--;
				continue for1;
			}

			valFunc = checkFunc(delta, estArray);
			// valFunc2 = checkEqualsFunc(delta, alfArray,
			// estArray,funcDeltaAux); //CORRIGIR VALIDADOR

			if (!valFunc) {
				funcDeltaAux[c] = delta;
				cont1++;
			} else {
				entradaInvalida();
				c--;
			}

		}
		// ---------TRATAMENTO FUNÇÃO DE TRANSIÇÃO
		String[] funcDelta = new String[alfArray.length * estArray.length];
		for (c = 0; c < cont1; c++) {
			funcDelta[c] = funcDeltaAux[c];
		}

		// -->>

		String[] estadoPartidaS = new String[funcDelta.length];
		String[] caracConsumidoS = new String[funcDelta.length];
		String[] estadoDestinoS = new String[funcDelta.length];

		int[] estadoPartida = new int[cont1];
		int[] estadoDestino = new int[cont1];
		char[] le = new char[cont1];

		// -->>

		for (int i = 0; i < funcDelta.length; i++) {
			if (funcDelta[i] == null) {
				break;
			}
			String[] p1 = funcDelta[i].split(";");
			String[] p2 = p1[0].split(",");
			estadoPartidaS[i] = p2[0];
			caracConsumidoS[i] = p2[1];
			estadoDestinoS[i] = p1[1];
		}

		int i = 0;
		int regra = 0;

		for (int p = 0; p < cont1; p++) {

			String aux = estadoPartidaS[i];
			estadoPartida[i] = Integer.parseInt(String.valueOf(aux.charAt(1)));

			aux = estadoDestinoS[i];
			estadoDestino[i] = Integer.parseInt(String.valueOf(aux.charAt(1)));

			aux = caracConsumidoS[i];
			le[i] = aux.charAt(0);
			i++;

		}

		// ----APOS O TRATAMENTO----------
		System.out.println("EP: " + Arrays.toString(estadoPartida));
		System.out.println("CC: " + Arrays.toString(le));
		System.out.println("ED: " + Arrays.toString(estadoDestino));

		// -------------------------------------------------
		// ENTRA COM ESTADO INICIAL
		int estadoi = entraEstIN(estArray);

		// -------------------------------------------------
		// ENTRA COM ESTADOS FINAIS
		boolean validEstFim = false;
		String estadosFinais = JOptionPane.showInputDialog(null, "\nEntre com o conjunto dos estados finais F:"
				+ "\nCada estado deve ser separado por virgula, sem espaços.\nEX: q0,q1,q2 ... e1,e2,e3 ...");

		String[] estFin = splitVirgula(estadosFinais);
		ArrayList lista = new ArrayList(Arrays.asList(estArray));

		// --------VALIDAÇÃO DOS ESTADOS FINAIS
		for (i = 0; i < estFin.length; i++) {
			if (lista.contains(estFin[i])) {
				validEstFim = verificaEst(estadosFinais);
				validEstFim = checkEstEquals(estadosFinais);
			} else {
				validEstFim = true;
				JOptionPane.showMessageDialog(null, "Estado no inexistente no conjunto de estados");
			}
		}

		while (validEstFim) {

			estadosFinais = JOptionPane.showInputDialog(null, "\nEntre com o conjunto dos estados finais F:"
					+ "\nCada estado deve ser separado por virgula.\nEX: q0,q1,q2");
			estFin = splitVirgula(estadosFinais);

			for (i = 0; i < estFin.length; i++) {
				if (lista.contains(estFin[i])) {
					validEstFim = verificaEst(estadosFinais);
					validEstFim = checkEstEquals(estadosFinais);
				} else {
					validEstFim = true;
					JOptionPane.showMessageDialog(null, "Estado inexistente no conjunto de estados");
				}
			}

			if (validEstFim) {
				JOptionPane.showMessageDialog(null, "TENTE NOVAMENTE!", "TENTE NOVAMENTE", JOptionPane.WARNING_MESSAGE);
			}
		}

		int qestadosf = 0;
		qestadosf = estFin.length;
		int[] estadosf = new int[qestadosf];

		for (int p = 0; p < qestadosf; p++) {
			String aux = estFin[p];
			estadosf[p] = Integer.parseInt(String.valueOf(aux.charAt(1)));
		}

		// -------------------------------------------------
		imprimirAutomato(alfArray, estArray, funcDelta, estadoi, estFin);
		// -------------------------------------------------
		// ------------------INSERE E VERIFICA PALAVRA
		String palavraS;
		boolean flagPal;
		// PALAVRAS
		do {
			int teste = 0;
			int teste1 = 0; // #CRIADO
			palavraS = JOptionPane.showInputDialog(null,
					"Entre com a palavra a ser verificada: \nPara conferir suas entradas digite anteriores digite 'I'\nPara sair digite s");
			if (palavraS.equalsIgnoreCase("s")) {
				break;
			}
			if (palavraS.equalsIgnoreCase("I")) {
				imprimirAutomato(alfArray, estArray, funcDelta, estadoi, estFin);
				palavraS = JOptionPane.showInputDialog(null,
						"Entre com a palavra a ser verificada: \nPara conferir suas entradas digite anteriores digite 'I'\nPara sair digite s");
				if (palavraS.equalsIgnoreCase("s")) {
					break;
				}
			}

			// -----VALIDAÇÃO DA PALAVRA
			flagPal = VerificaPalavra(palavraS, alfArray);
			if (!flagPal) {
				System.out.println("INVALIDA PALAVRA");
			} else {

				char[] palavra = palavraS.toCharArray();
				int palavralen = palavra.length;
				int estadoa = estadoi;
				int aux = 0;

				for (int p = 0; p < palavralen; p++) {
					int mudou = 0;
					int j = 0;

					for (int k = 0; k < cont1; k++) {

						if ((palavra[p] == le[k]) && (estadoa == estadoPartida[k])) {
							aux = estadoDestino[k];
							mudou = 1;
							break;
						} else {
							mudou = 0;
						}

					}

					if (mudou == 1) {
						estadoa = aux;
					} else {
						// JOptionPane.showMessageDialog(null,
						// "Nao tem regra para esse simbolo, ele não pertenbce
						// ao alfabeto informado.");
						teste1 = 1;// #ALTERADO
						break;
					}

					for (int k = 0; k < qestadosf; k++) {
						if (estadoa == estadosf[k]) {
							teste = 1;
						} else {
							teste = 0;
						}
					}
				}
				if (teste == 1) { // && teste1 !=1){//: #ALTERADO if teste ==
									// 1){
					JOptionPane.showMessageDialog(null, "PALAVRA ACEITA PELO AUTOMATO\n\n");
					// break;
				} else {
					JOptionPane.showMessageDialog(null, "PALAVRA NÃO ACEITA PELO AUTOMATO\n\n");
					// break;
				}
			}
		} while (!palavraS.equalsIgnoreCase("s"));
		JOptionPane.showMessageDialog(null, "Voce finalizou a aplicaçao, obrigado!");
	}

	// ---------------METODOS-------------------------------------
	// ---- METODOS DE IMPRESSÃO DEINFORMAÇOES -

	// ENTRADA INVALIDA
	private static void entradaInvalida() {
		JOptionPane.showMessageDialog(null, "ENTRADA INVALIDA", "WARNING", JOptionPane.WARNING_MESSAGE);
	}

	// ENTRADA VALIDA
	private static void entradaValidada() {
		JOptionPane.showMessageDialog(null, "Entrada VERIFICADA\n");
	}

	// FUNÇAÕ DE TRANSIÇÃO INVALIDA
	private static void funcInval(String delta) {
		JOptionPane.showMessageDialog(null, "Sua entrada esta fora do modelo permitido!\n");
		tutorialTransicao();
	}

	// TUTORIAL DE ENTRADA PARA FUNȿO DE TRANSIȃO
	private static void tutorialTransicao() {
		JOptionPane.showMessageDialog(null, "ATENÇÃO AO MODELO PARA ENTRADA DA FUNÇÃO DE TRANSIÇÃO DE ESTADOS\n"
				+ "PASSO 1: Primeiro entre com o estado inicial - EX: q0\n" + "PASSO 2: DIGITE UMA VIRGULA \",\"\n"
				+ "PASSO 3: Entre com o caracter a ser consumido pelo estado inicial - EX: a\n"
				+ "PASSO 4: DIGITE PONTO E VIRGULA. \";\"\n" + "PASSO 5: Entre com o estado de destino - EX: q1\n"
				+ "PASSO 6: APERTE ENTER\n" + "A entrada deve estar da forma do exemplo abaixo\n" + "EX: q0,a;q1",
				"WARNING", JOptionPane.WARNING_MESSAGE);
	}

	// IMPRIME ENTRADAS
	private static void imprimirAutomato(String[] alf, String[] est, String[] funcDelta, int estIn, String[] estFin) {
		JOptionPane.showMessageDialog(null,
				"**************************************************\n" + "\tIMPRIMINDO ENTRADAS DO AUTOMATO\n\n"
						+ "\tO alfabeto: Σ \n" + "\tOs estados: Q = {S1, S2...}\n" + "\tAs transicoes: (δ: Q × Σ → Q)\n"
						+ "\tO  estado Ininicial: q0\n" + "\tO conjunto dos estados finais: F\n"
						+ "\tM = (Q, Σ, (δ: Q × Σ → Q), q0, F)\n" + "\tΣ   = " + Arrays.toString(alf) + "\n" + ""
						+ "\tQ   = " + Arrays.toString(est) + "\n" + "\tδ   = " + Arrays.toString(funcDelta) + "\n" + ""
						+ "\tq0  = q" + estIn + "\n" + "" + "\tF   = " + Arrays.toString(estFin) + "\n" + ""
						+ "**************************************************");
	}

	// ---------------------------METODOS DE ENTRADAS DE DADOS

	// ENTRA ALFABETO
	private static String entrarAlf() {
		String alfabeto = JOptionPane.showInputDialog(null,
				"Entre com o alfabeto Σ:\nCada caracter deve ser separado por virgula, sem espaço.\nEX: a,b,c,d,e ...\n");
		return alfabeto;
	}

	// ENTRA ESTADO
	private static String entraEst() {
		String estados = JOptionPane.showInputDialog(null,
				"ATENÇÃO AO MODELO DE INSERÇÃO NO CONJUNTO DE ESTADOS\nCada estado deve ser "
						+ "separado por virgula, sem espaço.\n" + "EX: q0,q1,q2 ... e1,e2,e3...");
		return estados;
	}

	// ENTRA ESTADO INICIAL
	private static int entraEstIN(String[] estArray) {
		boolean validador = true;
		ArrayList lista = new ArrayList(Arrays.asList(estArray));

		String estadoInicial;
		do {
			estadoInicial = JOptionPane.showInputDialog(null, "ESTADO INICIAL:\nEntre com o estado inicial q0: ");

			if (estadoInicial.length() < 3 || estadoInicial.length() > 3) {
				validador = false;
			} else {
				entradaInvalida();
				validador = true;

			}

			if (lista.contains(estadoInicial)) {
				validador = false;
			} else {
				validador = true;
				JOptionPane.showMessageDialog(null, "Estado no inexistente no conjunto de estados.");
			}

		} while (validador);

		int estadoIn = Integer.parseInt(String.valueOf(estadoInicial.charAt(1)));
		return estadoIn;
	}

	// ENTRAR PALAVRA
	private static String entrarPalavra() {
		String palavra = JOptionPane.showInputDialog(null, "entre com a palavra a ser verificada pelo afd:\n"
				+ "caso queira sair, digite 's'\n" + "caso queira imprimir o automato digite 'i' \n" + " ");
		return palavra;
	}

	// ENTRADA DA FUNÇÃO DE TRANSIÇAO
	private static String entraFunc() {

		String delta = JOptionPane.showInputDialog(null,
				"\nEntre com as transiçãos de estado (δ: Q × Σ → Q):\n" + "\nPara ver o tutorial  novamente : 'i'"
						+ "\nPara sair : 's'" + "\nVer entradas anteriores 'a'" + "\n ");
		return delta;
	}

	// SPLIT PARA SEPARAR ENTRADAS QUE CONTEM VIRGULA
	private static String[] splitVirgula(String valor) {
		return valor.split(",");// Divide a String quando ocorrer ","
	}

	// ---------METEDOS VALIDADORES

	// VERIFICADOR DE ENTRADA DE DADOS PARA O ALFABETO
	private static boolean verificaAlf(String alfabeto) {
		boolean validador = false;

		// ENTRADA VAZIA

		// try {
		// if (alfabeto.equals(null)) {
		// }
		// } catch (Exception e) {
		// JOptionPane.showMessageDialog(null, "Não permitida a saida nesse etapa!");
		// return validador = true;
		// }

		// ENTRADA VAZIA
		if (alfabeto.equals("") || alfabeto.length() < 1) {
			JOptionPane.showMessageDialog(null, "Tamanho do alfabeto inferior ao permitido!", "WARNING",
					JOptionPane.WARNING_MESSAGE);
			entradaInvalida();
			return validador = true;
		}

		// ENTRADA INICIANDO PELA VIRGULA
		if (alfabeto.charAt(0) == ',') {
			JOptionPane.showMessageDialog(null, "Não começe a inserção pela virgula", "WARNING",
					JOptionPane.WARNING_MESSAGE);
			entradaInvalida();
		}
		// CARACTERE IGUAIS
		int w = 1;
		for1: for (int k = 0; k < alfabeto.length(); k = k + 2) {
			if (k == alfabeto.length() || w == alfabeto.length()) {
				break for1;
			}
			if (alfabeto.charAt(w) != ',') {
				entradaInvalida();
				return validador = true;
			}
			w = w + 2;
			for (int j = k + 2; j < alfabeto.length(); j = j + 2) {

				if (alfabeto.charAt(k) == alfabeto.charAt(j)) {
					entradaInvalida();
					JOptionPane.showMessageDialog(null, "Você entrou com caracteres iguais no alfabeto!\n"
							+ alfabeto.charAt(k) + " = " + alfabeto.charAt(j), "WARNING", JOptionPane.WARNING_MESSAGE);
					return validador = true;
				}
			}

		}
		JOptionPane.showMessageDialog(null, "ENTRADA VERIFICADA");
		return validador;
	}

	// VERIFICADOR DE ENTRADA DE DADOS PARA O CONJUNTO DE ESTADOS
	private static boolean verificaEst(String estados) {
		boolean validador = false;
		// CLIQUE NO BOTAO CANCELAR

		// try {
		// if (estados.equals(null)) {
		// }
		// } catch (Exception e) {
		// entradaInvalida();
		// JOptionPane.showMessageDialog(null, "Não permitida a saida nesse etapa!");
		// return validador = true;
		// }

		// ESTADO COM TAMANHO INFERIOR AO PERMITIDO
		if (estados.length() < 1 || estados.equals("")) {
			JOptionPane.showMessageDialog(null, "Tamanho do conjunto inferior ao permitido!");
			entradaInvalida();
			return validador = true;
		}

		// INSERÇÃO DE ESTADOS NÃO PODE COMEÇAR PELA VIRGULA
		if (estados.charAt(0) == ',') {
			JOptionPane.showMessageDialog(null, "Não começe a inserção pela virgula");
			entradaInvalida();
			return validador = true;
		}

		// VALIDADOR DE FORMATAÇÃO DO CONJUNTO DE ESTADOS
		ValidadorDeEstados: for (int w = 0; w < estados.length(); w++) {
			w++;
			w++;
			if (w == estados.length()) {
				break ValidadorDeEstados;
			}
			validarVirgulaEstadoAte9Estados: if (estados.length() <= 29) {
				if (estados.charAt(w) != ',') {
					JOptionPane.showMessageDialog(null, "Fora do modelo permitido!", "WARNING",
							JOptionPane.WARNING_MESSAGE);
					return validador = true;
					// w=estados.length();
				} else {
					validador = false;
				}
			}

			validarVirgulaEstadoAcima10Estados: if (estados.length() >= 30) {
				w++;
				if (estados.charAt(w) != ',') {
					JOptionPane.showMessageDialog(null, "Fora do modelo permitido!", "WARNING",
							JOptionPane.WARNING_MESSAGE);
					return validador = true;
					// w=estados.length();
				} else {
					validador = false;
				}
			}

		}

		// ESTADOS IGUIAS
		if (!checkEstEquals(estados)) {
			JOptionPane.showMessageDialog(null, "Entrada VERIFICADA");
		}
		return validador;// = false;
	}

	// ESTADOS IGUAIS --- CORRIGIR VALIDADOR DE ESTADOS
	private static boolean checkEstEquals(String estados) {
		boolean validador = false;

		try {
			if (estados.equals(null)) {
			}
		} catch (Exception e) {
			return validador = true;
		}
		String[] estAux = estados.split(",");

		for (int i = 0; i < estAux.length; i++) {
			for (int j = i + 1; j < estAux.length; j++) {
				if (estAux[i].equals(estAux[j])) {
					JOptionPane.showMessageDialog(null, "Existem elementos iguais no conjunto!", "WARNING",
							JOptionPane.WARNING_MESSAGE);
					i = estAux.length;
					return validador = true;
				}
			}
		}

		return validador = false;
	}

	// VALIDADOR PARA FUNÇÃO DE TRANSIÇÃO
	private static boolean checkFunc(String delta, String[] estArray) {
		boolean validador = false;

		try {
			if (delta.equals(null)) {
			}
		} catch (Exception e) {
			entradaInvalida();
			return validador = true;
		}

		if (estArray.length > 9) {
			if (delta.length() < 7 || delta.length() > 9) {
				funcInval(delta);
				return validador = true;
			}
		} else {

			if (delta.length() < 7 || delta.length() > 7) {
				funcInval(delta);
				return validador = true;
			}
		}

		validadorDeVirgulaEPtEVirgula: if (delta.charAt(2) != ',' || delta.charAt(4) != ';') {
			funcInval(delta);
			return validador = true;

		}

		return false;
	}

	// VERIFICA IGUALDADE DAS FUNÇÕE DE TRANSIÇÃO -- CORRIGIR
	private static boolean checkEqualsFunc(String delta, String[] alfArray, String[] estArray, String[] funcDeltaAux) {
		boolean validador = false;
		for1: for (int i = 0; i < alfArray.length * estArray.length - 1; i++) {
			for (int j = i + 1; j < alfArray.length * estArray.length; j++) {
				if (funcDeltaAux[i] == funcDeltaAux[j]) {
					JOptionPane.showMessageDialog(null, "ENTRADAS IGUIAS " + funcDeltaAux[i] + " e " + funcDeltaAux[j],
							"WARNING", JOptionPane.WARNING_MESSAGE);
					// funcInval(delta);
					validador = true;
					break for1;
				}
			}

		}
		return validador;
	}

	// VERIFICA SE PALAVRA PERTENCE AO ALFABETO
	private static boolean VerificaPalavra(String palavra, String[] alf) {

		int cont = 0;
		for1: for (int x = 0; x < palavra.length(); x++) {
			String caracPalavra = "" + palavra.charAt(x);
			for (int y = 0; y < alf.length; y++) {
				if (caracPalavra.equals(alf[y])) {
					cont++;
				}
			}
		}

		if (cont == palavra.length()) {
			// JOptionPane.showMessageDialog(null, "TODOS OS SIMBOLOS PERTENCEM
			// AO ALFABETO!!!\n\n");
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "CONTÉM SIMBOLOS NÃO PERTENCENTES AO ALFABETO!!!\n\n", "WARNING",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
}