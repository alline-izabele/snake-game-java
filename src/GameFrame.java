import javax.swing.JFrame;

public class GameFrame extends JFrame{
	GameFrame(){
		//essa classe cria a janela do programa Java, porém vazia
		
		/*para que algo apareça nesta janela, foram feitas modificações
		 * e os métodos necessários, como por exemplo, background color,
		 * dimensões/tamanho da tela, configurações da cobra e maça, etc
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
