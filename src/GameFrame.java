import javax.swing.JFrame;

public class GameFrame extends JFrame{
	GameFrame(){
		//essa classe cria a janela do programa Java, por�m vazia
		
		/*para que algo apare�a nesta janela, foram feitas modifica��es
		 * e os m�todos necess�rios, como por exemplo, background color,
		 * dimens�es/tamanho da tela, configura��es da cobra e ma�a, etc
		*/
		
		this.add(new GamePanel());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
