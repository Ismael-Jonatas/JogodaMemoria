

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.Scanner;

public class AplicacaoConsoleJogoMemoria {
	public static void main(String[] args) {
		Color[] paleta = {Color.RED, Color.BLUE,Color.YELLOW,Color.CYAN,Color.BLACK,Color.GRAY,Color.GREEN,Color.PINK};
		JogoMemoria jogo = new JogoMemoria(paleta);
		System.out.println(jogo);		//chama toString()

		Scanner teclado = new Scanner(System.in);
		int linha1, coluna1, linha2, coluna2;
	
		do{
			System.out.print("Digite a linha1:");	linha1 = teclado.nextInt();
			System.out.print("Digite a coluna1: ");	coluna1 = teclado.nextInt();
			System.out.print("Digite a linha2:");	linha2 = teclado.nextInt();
			System.out.print("Digite a coluna2: ");	coluna2 = teclado.nextInt();
			try {
				boolean resposta = jogo.adivinhar(linha1,coluna1,linha2,coluna2);
				if(resposta)
					System.out.println("\nadivinhou a cor: "+ getColorName( jogo.getCor(linha1,coluna1)) );
				else
					System.out.println("\ntente novamente outras duas posi��es");
			}
			catch(Exception e) {
				System.out.println("\naviso:"+e.getMessage());
			}
			System.out.println("total de acertos:"+jogo.getAcertos());
			System.out.println("total de tentativas:"+jogo.getTentativas());
			System.out.println(jogo);
        
			
		}while(!jogo.terminou());
		
		System.out.println("\n GAME OVER !!");
		System.out.println(jogo.getResultadoFinal());
	}
	
	
    public static String getColorName(Color c) {
        for (Field f : Color.class.getFields()) {
            try {
                if (f.getType() == Color.class && f.get(null).equals(c)) {
                    return f.getName();
                }
            } catch (java.lang.IllegalAccessException e) {
                // it should never get to here
            } 
        }
        return "unknown";
    }
}