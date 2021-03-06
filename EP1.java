/*
	Integrantes da dupla:
	
	Gregório Assagra de Almeida Filho	10856824
	Mateus Saldanha				10882873
	
*/

import java.util.*;

// classe que representa uma matriz de valores do tipo double.

class Matriz {

	// constante para ser usada na comparacao de valores double.
	// Se a diferenca absoluta entre dois valores double for menor
	// do que o valor definido por esta constante, eles devem ser
	// considerados iguais.
	public static final double SMALL = 0.000001;

	private int lin, col;
	private double [][] m;
	private boolean isInversa = false;

	// metodo estatico que cria uma matriz identidade de tamanho n x n.

	public static Matriz identidade(int n){

		Matriz mat = new Matriz(n, n);
		for(int i = 0; i < mat.lin; i++) mat.m[i][i] = 1;
		return mat;
	}

	public void setInversa(){ isInversa = true; }
	// construtor que cria uma matriz de n linhas por m colunas com todas as entradas iguais a zero.

	public Matriz(int n, int m){

		this.lin = n;
		this.col = m;
		this.m = new double[lin][col];
	}

	public void set(int i, int j, double valor){

		m[i][j] = valor;
	}

	public double get(int i, int j){

		return m[i][j];
	}

	// metodo que imprime as entradas da matriz.

	public void imprime(){

		for(int i = 0; i < lin; i++){

			for(int j = 0; j < col; j++){

				System.out.printf("%7.2f ", m[i][j]);
			}

			System.out.println();
		}
	}

	// metodo que imprime a matriz expandida formada pela combinacao da matriz que 
	// chama o metodo com a matriz "agregada" recebida como parametro. Ou seja, cada 
	// linha da matriz impressa possui as entradas da linha correspondente da matriz 
	// que chama o metodo, seguida das entradas da linha correspondente em "agregada".

	public void imprime(Matriz agregada){

		for(int i = 0; i < lin; i++){

			for(int j = 0; j < col; j++){

				System.out.printf("%7.2f ", m[i][j]);
			}

			System.out.print(" |");

			for(int j = 0; j < agregada.col; j++){

				System.out.printf("%7.2f ", agregada.m[i][j]);
			}

			System.out.println();
		}
	}

	// metodo que troca as linhas i1 e i2 de lugar.

	private void trocaLinha(int i1, int i2){

		double[] d1, d2;
		d1 = m[i1].clone(); d2 = m[i2].clone();

		m[i1] = d2; m[i2] = d1;

		// TODO: implementar este metodo.
	}

	// metodo que multiplica as entradas da linha i pelo escalar k

	private void multiplicaLinha(int i, double k){
		double[] linha = m[i].clone();
		for(int c = 0; c < linha.length; c++) {
			linha[c] = linha[c] * k;
		}

		m[i] = linha;

		// TODO: implementar este metodo.
	}

	// metodo que faz a seguinte combinacao de duas linhas da matriz:
	//	
	// 	(linha i1) = (linha i1) + (linha i2 * k)
	//

	private void combinaLinhas(int i1, int i2, double k){
		double[] d1, d2;

		d1 = m[i1].clone(); d2 = m[i2].clone();

		for(int c = 0; c < d2.length; c++) {
			d2[c] = d2[c] * k;
		}

		for(int c = 0; c < d1.length; c++){
			d1[c] = d1[c] + d2[c];
		}

		m[i1] = d1;

		// TODO: implementar este metodo.
	}

	// metodo que procura, a partir da linha ini, a linha com uma entrada nao nula que
	// esteja o mais a esquerda possivel dentre todas as linhas. Os indices da linha e da 
	// coluna referentes a entrada nao nula encontrada sao devolvidos como retorno do metodo.
	// Este metodo ja esta pronto para voces usarem na implementacao da eliminacao gaussiana
	// e eleminacao de Gauss-Jordan.

	private int [] encontraLinhaPivo(int ini){

		int pivo_col, pivo_lin;

		pivo_lin = lin;
		pivo_col = col;

		for(int i = ini; i < lin; i++){

			int j;

			for(j = 0; j < col; j++) if(Math.abs(m[i][j]) > SMALL) break;

			if(j < pivo_col) {

				pivo_lin = i;
				pivo_col = j;
			}

			//System.out.println("Pivot: linha-> "+pivo_lin+" | coluna->"+pivo_col);
		}

		return new int [] { pivo_lin, pivo_col };
	}

	// metodo que implementa a eliminacao gaussiana, que coloca a matriz (que chama o metodo)
	// na forma escalonada. As operacoes realizadas para colocar a matriz na forma escalonada 
	// tambem devem ser aplicadas na matriz "agregada" caso esta seja nao nula. Este metodo 
	// tambem deve calcular e devolver o determinante da matriz que invoca o metodo. Assumimos 
	// que a matriz que invoca este metodo eh uma matriz quadrada.

	public double formaEscalonada(Matriz agregada){
		double det = -1.0;
		//imprime(agregada);
		//double unit = 1.0;

		//metodo que coloca 0 na coluna x abaixo de inteiros maiores que 0
		for (int i = 0; i < m.length; i++) {

			int j;

			for (j = i; j < m[i].length; j++)
				if (Math.abs(m[j][i]) < SMALL) {
					int[] coor = encontraLinhaPivo(j);
					try {
						if (Math.abs(m[j][i]) < Math.abs(m[coor[0]][coor[1]])) {

							trocaLinha(j, coor[0]);
							agregada.trocaLinha(j, coor[0]);


							det = det * -1;

						}
					}catch (ArrayIndexOutOfBoundsException e){ continue; }

				}


			for (int k = i + 1; k < m.length; k++) {
				double factor = -1;

				//if(factor < (SMALL* -1)) factor = Math.abs(factor);
				if(Math.abs(m[i][i]) > SMALL) factor = m[k][i] / m[i][i];

				combinaLinhas(k, i, factor * -1);
				agregada.combinaLinhas(k, i, factor * -1);

			}
		}

		//imprime(agregada);

		for (int i = 0; i < m.length; i++) {


			int j;

			for (j = i; j < m[i].length; j++)
				if (Math.abs(m[i][j]) > SMALL) {
					det = det * (m[i][j]);
					double factor = (1.0/m[i][j]);
					multiplicaLinha(i, factor);
					agregada.multiplicaLinha(i, factor);
					break;
				}


			for (j = 0; j < m[i].length; j++) if (Math.abs(m[i][j]) < SMALL) m[i][j] = 0.0;

		}

		// TODO: implementar este metodo.

		//imprime(agregada);
		double d = 0;
		for(int k = 0; k < m[m.length-1].length; k++){
			d = d + m[m.length-1][k];
		}

		if(Math.abs(d) < SMALL)
			return 0.0;
		return det;
	}

	// metodo que implementa a eliminacao de Gauss-Jordan, que coloca a matriz (que chama o metodo)
	// na forma escalonada reduzida. As operacoes realizadas para colocar a matriz na forma escalonada 
	// reduzida tambem devem ser aplicadas na matriz "agregada" caso esta seja nao nula. Assumimos que
	// a matriz que invoca esta metodo eh uma matriz quadrada. Não se pode assumir, contudo, que esta
	// matriz ja esteja na forma escalonada (mas voce pode usar o metodo acima para isso).

	public void formaEscalonadaReduzida(Matriz agregada){ //concertar
		double det = formaEscalonada(agregada);
		double d = 0;
		for(int k = 0; k < m[m.length-1].length; k++){
			d = d + m[m.length-1][k];
		}
		if(Math.abs(d) < SMALL && !isInversa && agregada.get(m.length -1, 0) != 0){
			System.out.println("sistema sem solução");
			System.exit(0);
		}else if(Math.abs(d) < SMALL && !isInversa && agregada.get(m.length -1, 0) == 0){
			System.out.println("sistema possui diversas soluções");
			System.exit(0);
		}else if(Math.abs(d) < SMALL && isInversa){
			System.out.println("matriz singular");
			System.exit(0);
		}

		for(int i = m.length - 1; i > 0; i--){

			for (int k = i-1; k >=0; k--) {
				double factor = -1;

				if(Math.abs(m[i][i]) > SMALL) factor = m[k][i] / m[i][i];

				combinaLinhas(k, i, factor * -1);
				agregada.combinaLinhas(k, i, factor * -1);

			}
		}
		//imprime(agregada);
		return;

		// TODO: implementar este metodo.		
	}
}

// Classe "executavel".

public class EP1 {

	// metodo principal.

	public static void main(String [] args){

		Scanner in = new Scanner(System.in);	// Scanner para facilitar a leitura de dados a partir da entrada padrao.
		String operacao = in.next();		// le, usando o scanner, a string que determina qual operacao deve ser realizada.
		int n = in.nextInt();			// le a dimensão da matriz a ser manipulada pela operacao escolhida.

		// TODO: completar este metodo.

		if("resolve".equals(operacao)){
			Matriz agregada = new Matriz(n, 1);
			Matriz matrix = new Matriz(n, n);

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++)
					matrix.set(i, j, in.nextInt());
				agregada.set(i, 0, in.nextInt());

			}

			matrix.formaEscalonadaReduzida(agregada);

			//matrix.imprime();
			agregada.imprime();
		}
		else if("inverte".equals(operacao)){
			Matriz matrix = new Matriz(n, n);
			Matriz agregada = Matriz.identidade(n);

			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++)
					matrix.set(i, j, in.nextInt());
			}

			matrix.setInversa();
			matrix.formaEscalonadaReduzida(agregada);
			agregada.imprime();

		}
		else if("determinante".equals(operacao)){
			Matriz agregada = Matriz.identidade(n);
			Matriz matrix = new Matriz(n, n);

			//int h = 0;
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++)
					matrix.set(i, j, in.nextInt());
				//agregada.set(h, 0, in.nextInt());

			}

			double retorno = matrix.formaEscalonada(agregada);

			System.out.printf("%7.2f \n", retorno);

		}
		else {
			System.out.println("Operação desconhecida!");
			System.exit(1);
		}
	}
}
