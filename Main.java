import java.util.Arrays;

class Gauss {

    public static final double SMALL = 0.000001;

    private int lin, col;
    private double[][] m;

    public Gauss(int n, int m) {
        this.lin = n;
        this.col = m;
        this.m = new double[lin][col];
    }

    public void set(int i, int j, double valor) {

        m[i][j] = valor;
    }

    private void trocaLinha(int i1, int i2) {

        double[] d1, d2;
        d1 = m[i1].clone();
        d2 = m[i2].clone();

        m[i1] = d2;
        m[i2] = d1;

        // TODO: implementar este metodo.
    }

    private void multiplicaLinha(int i, double k) {
        double[] linha = m[i].clone();
        for (int c = 0; c < linha.length; c++) {
            linha[c] = linha[c] * k;
        }

        m[i] = linha;

        // TODO: implementar este metodo.
    }

    private void combinaLinhas(int i1, int i2, double k) {
        double[] d1, d2;

        d1 = m[i1].clone();
        d2 = m[i2].clone();

        for (int c = 0; c < d2.length; c++) {
            d2[c] = d2[c] * k;
        }

        for (int c = 0; c < d1.length; c++) {
            d1[c] = d1[c] + d2[c];
        }

        m[i1] = d1;

        // TODO: implementar este metodo.
    }

    private int[] encontraLinhaPivo(int ini) {

        int pivo_col, pivo_lin;

        pivo_lin = lin;
        pivo_col = col;

        for (int i = ini; i < lin; i++) {

            int j;

            for (j = 0; j < col; j++) if (Math.abs(m[i][j]) > SMALL) break;

            if (j < pivo_col) {

                pivo_lin = i;
                pivo_col = j;
            }
        }

        return new int[]{pivo_lin, pivo_col};
    }

// metodo que implementa a eliminacao gaussiana, que coloca a matriz (que chama o metodo)
// na forma escalonada. As operacoes realizadas para colocar a matriz na forma escalonada
// tambem devem ser aplicadas na matriz "agregada" caso esta seja nao nula. Este metodo
// tambem deve calcular e devolver o determinante da matriz que invoca o metodo. Assumimos
// que a matriz que invoca este metodo eh uma matriz quadrada.

    public void formaEscalonada() {

        //metodo que coloca 0 na coluna x abaixo de inteiros maiores que 0
        for (int i = 0; i < m.length; i++) {

            int j;

            for (j = i; j < m[i].length; j++)
                if (Math.abs(m[j][i]) < SMALL) {
                    int[] coor = encontraLinhaPivo(j);

                    if (Math.abs(m[j][i]) < Math.abs(m[coor[0]][coor[1]]))
                        trocaLinha(j, coor[0]);
                }


            for (int k = i + 1; k < m.length; k++) {
                double factor = m[k][i] / m[i][i];

                //if(factor < (SMALL* -1)) factor = Math.abs(factor);

                combinaLinhas(k, i, factor * -1);


            }
        }

        for (int i = 0; i < m.length; i++) {
            int j;

            for (j = i; j < m[i].length; j++)
                if (m[i][j] != 0) {
                    multiplicaLinha(i, 1 / m[i][j]);
                    break;
                }


            for (j = 0; j < m[i].length; j++) if (Math.abs(m[i][j]) < SMALL) m[i][j] = 0.0;
            for (j = 0; j < m[i].length; j++) if(Math.abs(Math.ceil(m[i][j]) % m[i][j]) < SMALL &&
                                                 Math.abs(Math.ceil(m[i][j]) - m[i][j]) < SMALL) m[i][j] = Math.ceil(m[i][j]);

            for (j = 0; j < m[i].length; j++) if (Math.abs(m[i][j]) < SMALL) m[i][j] = 0.0;

        }


        return;
    }

    public void formaEscalonadaReduzida() {

        formaEscalonada();


        for(int i = m.length - 1; i > 0; i--){

            for (int k = m.length -1; k >= i && k >0; k--) {
                double factor;
                if((m[i][k] > SMALL || m[i][k] < SMALL*-1) && (m[i-1][k] > SMALL || m[i-1][k] < SMALL*-1))
                    factor = m[i-1][k] / m[i][k];
                else continue;

                //if(factor < (SMALL* -1)) factor = Math.abs(factor);

                combinaLinhas(i-1, i, factor * -1);


            }
        }

        return;


        // TODO: implementar este metodo.
    }

}



public class Main {

    public static void main(String[] args) {

        double[][] matriz = new double[][]{{4.0, 1.0, 2.5, 2.0, 1.0},
                {9.0, 1.0, 2.0, 7.0, 3.5},
                {-6.5, 5.0, 1.0, 0.0, 3.5},
                {0.0, 0.0, 1.0, 2.0, 3.0},
                {6.0, 1.0, 1.0, 2.5, 3.5}};

        Gauss gg = new Gauss(5, 5);


        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                gg.set(i, j, matriz[i][j]);

            }
        }

        gg.formaEscalonadaReduzida();


        return;

    }

}

