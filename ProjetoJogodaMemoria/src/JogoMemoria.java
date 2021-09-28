import java.awt.Color;
import java.util.*; 

public class JogoMemoria {
	
	//ATRIBUTOS
	/* uma matriz para armazenar (em dobro) as 8 cores especificadas no conjunto de cores. */
	private Color[][] cores = new Color[4][4];
	/* uma matriz para armazenar �X� nas posi��es das cores que foram localizadas. Pode ser inicializada com �-� ou outro simbolo. */
	private String[][] marcadores = new String[4][4];
	/* constante que define o m�ximo de tentativas*/
	private int maxTentativas = 0;
	/* constante que define o n�mero de acertos*/
	private int acertos = 0;

	//METODOS
	/* construtor que recebe da aplica��o os 8 objetos da classe Color e os utiliza na inicializa��o da matriz de cores. 
	 * O array paleta n�o pode ter a cor branca (Color.WHITE) */
	public JogoMemoria (Color[] paleta) {
		
		for (int i = 0; i < this.marcadores.length; i++) {
			for (int j = 0; j < this.marcadores.length; j++) {
				this.marcadores[i][j] = "-";
			}
		}
		
		int contador = 0;
		for (int i = 0; i < this.cores.length; i ++) {
            for (int j = 0; j < this.cores[i].length; j++) {
                if (contador >= paleta.length) {
                    contador = 0;
                }
                this.cores[i][j] = paleta[contador];
                contador += 1;
            }

        }
		
		
        Random rand = new Random();

        for (int i = 0; i < this.cores.length; i ++) {
            for (int j = 0; j < this.cores[i].length; j++) {
                int randomIndexToSwap = rand.nextInt(this.cores[i].length);
                Color temp = this.cores[i][randomIndexToSwap];
                this.cores[i][randomIndexToSwap] = this.cores[i][j];
                this.cores[i][j] = temp;
            }
        }
		
	}

	/* valida se as duas posi��es est�o dentro dos limites (entre 0 a 3) e retorna true somente se as duas posi��es 
	 * da matriz de cores possuem o mesmo objeto, atualizando a matriz de marcadores, o total de tentativas e total de acertos. 
	 * Este m�todo deve lan�ar a exce��o �posi��o invalida� caso algum dos par�metros seja inv�lido. */
	public boolean adivinhar (int linha1, int coluna1, int linha2, int coluna2){
		
		try {
			
			if (this.cores[linha1][coluna1].equals(this.cores[linha2][coluna2])) {
				this.acertos++;
				this.maxTentativas++;
				this.marcadores[linha1][coluna1] = "X";
				this.marcadores[linha2][coluna2] = "X";
				return true;
			}else {
				this.maxTentativas++;
				return false;
			}
		}catch (Exception e) {
			throw new ExceptionJogoMemoria(" !Entrada invalida!\n");
		}
	}
	
	/* retorna true se o total de acertos for 8 ou se total de tentativas for 30 */
	public boolean terminou() {
		
		if (this.maxTentativas == 30) {
			return true;
		}else if(this.acertos == 8) {
			return true;
		}else {
			return false;
		}
	}
	
	/* retorna o objeto Color armazenado na matriz de cores na posi��o indicada */
	public Color getCor(int linha, int coluna) {
		 return this.cores[linha][coluna];
		
	}
	
	/* retorna o resultado �acertou com N tentativas� ou �terminou com X tentativas� */
	public String getResultadoFinal(){
		 return "Acertou " + this.acertos + " de 8, com " + this.maxTentativas + " tentativas"; 
		
	}
	
	/* retorna o contador de acertos do jogo */
	public int getAcertos() {
		return this.acertos;
		
	}
	
	/* retorna o contador de tentativas j� feitas no jogo */
	public int getTentativas() {
		return this.maxTentativas;
		
	}
	
	/* retorna uma string, contendo o conte�do da matriz de marcadores */
	public String toString() {
		String saida = " ";
		
		for (int l = 0; l < this.marcadores.length; l++) {
			for (int c = 0; c < this.marcadores[0].length; c++) {
				saida += this.marcadores[l][c] + " ";
				
			}
			saida += "\n ";
		}
		
		return saida;
	}
	
}