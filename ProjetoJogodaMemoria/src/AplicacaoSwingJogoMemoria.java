import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;


public class AplicacaoSwingJogoMemoria  {
	private JFrame frame;
	private JLabel[][] matriz;
	private JLabel label1;
	private JLabel label3;
	private JLabel label2;
	private JLabel primeiroselecionado, segundoselecionado;
	private JButton button;
	private final int TAMANHO=40;	//largura e altura de um label (pixels)
	private JogoMemoria jogo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AplicacaoSwingJogoMemoria window = new AplicacaoSwingJogoMemoria();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AplicacaoSwingJogoMemoria() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Jogo da Memoria - IFPB/POO");
		frame.setBounds(100, 100, 348, 301);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		label1 = new JLabel("Inicie o jogo");
		label1.setBounds(10, 203, 289, 14);
		frame.getContentPane().add(label1);

		label2 = new JLabel("");
		label2.setFont(new Font("Arial", Font.BOLD, 13));
		label2.setBounds(186, 55, 146, 23);
		frame.getContentPane().add(label2);

		label3 = new JLabel("");
		label3.setFont(new Font("Arial", Font.BOLD, 13));
		label3.setBounds(186, 78, 138, 23);
		frame.getContentPane().add(label3);
		
		//criar a matriz de labels e os 16 labels
		matriz = new JLabel[4][4];
		for(int i=0; i < 4; i++){
			for(int j=0; j < 4; j++){
				matriz[i][j] = new JLabel("");				//cria um label
				frame.getContentPane().add(matriz[i][j]);	//adiciona o label no frame
			}
		}


		button = new JButton("(Re)iniciar");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { 
				Color[] paleta = {Color.RED, Color.BLUE,Color.YELLOW,Color.CYAN,Color.BLACK,Color.GRAY,Color.GREEN,Color.PINK};
				jogo = new JogoMemoria(paleta);
			
				label1.setText("Clique o mouse em uma das 16 posições: ");
				label2.setText("Acertos: 0");
				label3.setText("Tentativas: 0");
				primeiroselecionado = null;		//guarda o primeiro label selecionado
				segundoselecionado = null;		//guarda o segundo label selecionado
				//inicializar a matriz de labels
				for(int l=0; l<4; l++){			//uma linha da matriz
					for(int c=0; c<4; c++){		//uma coluna da matriz
						matriz[l][c].setBounds(l*TAMANHO, c*TAMANHO, TAMANHO, TAMANHO);		//(x,y,largura,altura)
						matriz[l][c].setOpaque(true);
						matriz[l][c].setBackground(Color.WHITE);	//fundo
						matriz[l][c].setBorder(new LineBorder(Color.BLACK, 1, true)); //arredondado
						//registrar evento de click do mouse para o label da posicao l,c
						//existirao 16 mouselisteners no total - todos com a mesma implementacao do click
						matriz[l][c].addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {	
								JLabel labelclicado = (JLabel) e.getSource();	//guarda o label que acabou se ser clicado
								int linha = labelclicado.getY()/TAMANHO;		//calcula a linha dele na matriz
								int coluna = labelclicado.getX()/TAMANHO;		//calcula a coluna dele na matriz
								//teste
								System.out.println("posicao (linha,coluna) do label clicado =>"+  linha + "," + coluna);
								
								Color cor=null;
								try {
									cor = jogo.getCor(linha,coluna);	//obtem a cor (objeto Color) baseado na posicao do label
								}
								catch(Exception erro) {
									label1.setText("erro:"+ erro.getMessage());
								}

								//-----------PROCESSAR CLICK UNICO DO MOUSE----------------------------
								if(e.getClickCount()==1) {
									if(primeiroselecionado == null) 
									{ 	//primeiro label selecionado deve ser pintado
										primeiroselecionado = labelclicado;
										primeiroselecionado.setBackground(cor);  //colorir com a cor obtida acima
										label1.setText("selecione uma nova posição");
										segundoselecionado = null;
									}
									else 
										if(segundoselecionado == null && labelclicado!= primeiroselecionado) 
										{	//segundo label selecionado deve ser pintado
											segundoselecionado = labelclicado;
											segundoselecionado.setBackground(cor); //colorir com a cor obtida acima
											int coluna1 = primeiroselecionado.getX()/TAMANHO;
											int linha1 = primeiroselecionado.getY()/TAMANHO;
											int coluna2 = segundoselecionado.getX()/TAMANHO;
											int linha2 = segundoselecionado.getY()/TAMANHO;
											
											boolean acertou=false;
											try {
												acertou = jogo.adivinhar(linha1,coluna1,linha2,coluna2);
											}
											catch(Exception erro) {
												label1.setText("erro:"+ erro.getMessage());
											}

											if(acertou){
												label1.setText("vc acertou a cor: "+ getColorName(cor));
												label2.setText("Acertos: "+ jogo.getAcertos());
												//remover o evento mouselistener dos dois labels iguais, impedindo 
												//que esses labels possam ser clicados novamente.
												//considerando que um label possui um array de mouselisteners,
												//remove-se somente o primeiro objeto mouselistener
												primeiroselecionado.removeMouseListener(primeiroselecionado.getMouseListeners()[0]);
												segundoselecionado.removeMouseListener(segundoselecionado.getMouseListeners()[0]);
												primeiroselecionado=null;
												segundoselecionado=null;
											}	
											else{
												//errou
												label1.setText("errou - click 2 vezes para ocultar");
											}
											label3.setText("Tentativas: "+ jogo.getTentativas());


										}
								}//fim click unico
								else 
									//-----------PROCESSAR CLICK DUPLO DO MOUSE--------------
									if(e.getClickCount()==2) {
										if(primeiroselecionado!=null && segundoselecionado!=null 
												&& primeiroselecionado!=segundoselecionado) {
											//ocultar os dois lebels selecionados
											primeiroselecionado.setBackground(Color.WHITE);
											segundoselecionado.setBackground(Color.WHITE);
											primeiroselecionado=null;
											segundoselecionado=null;
											label1.setText("selecione novamente");
										}

									}//fim click duplo

								if (jogo.terminou()) {
									//exibir o resultado do jogo
									label1.setText(jogo.getResultadoFinal() + " - reinicie o jogo");
									//remover o mouselistener de todos os labels(impedindo o clique)
									for(int a=0; a < 4; a++)
										for(int b=0; b < 4; b++)
											if(matriz[a][b].getMouseListeners().length>0)
												matriz[a][b].removeMouseListener(matriz[a][b].getMouseListeners()[0]);
								}

							} //fim mouseClicked()
						});  //fim MouseAdapter()

					} //for j
				}// for i
			}//actionPerformed
		});//ActionListener
		button.setBounds(221, 21, 103, 23);
		frame.getContentPane().add(button);
	}

	public String getColorName(Color c) {
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