import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;
//todas as bibliotecas envolvendo o awt (e awt.event), foram resumidas e incluidas com o *
//mesma coisa feita com o javax.swing

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;										//TAMANHO DOS OBJETOS NA TELA
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;	//OBJETOS QUE CABEM NA TELA, ""PIXELS""
	static final int DELAY = 75;											//DO JOGO, rapido
	
	//a seguir, as coordenadas x e y armazenarão todas as partes da cobra
	//sendo que, a cobra pode ficar do tamanho do jogo no fim
	final int x[] = new int[GAME_UNITS]; 
	final int y[] = new int[GAME_UNITS];
	
	int bodyParts = 6;
	int applesEaten;	//maças comidas
	//coodernadas x e y da maça no mapa
	int appleX;
	int appleY;
	
	// direção inicial aleatória da cobra no inicio do jogo
	//nesse caso, foi escolhido o R para right
	char direction = 'R'; 
	boolean running = false;
	Timer timer;	//javax.swing
	Random random;	//util.random
	
	//construtor da "tela do jogo"/painel em si, que está dento da janela (classe(GameFrame))
	
	GamePanel(){
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	//após setada a tela do jogo, ele é iniciado
	
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		if(running) {
			/*
			//transformando a tela numa matriz, para melhor visualização dos pixels/gameunits
			//PARTE OPCIONAL DO TUTORIAL
		
			//screenheight/unitsize forma bloquinhos de unitsize através da altura da tela
			for(int i = 0; i <(SCREEN_HEIGHT/UNIT_SIZE); i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			 */
		
			//desenhando a maça
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
			//desenhando o corpo da cobra
			for(int i = 0; i < bodyParts; i++) {
				if(i == 0) {	//se i = 0, estamos na cabeça (primeiro quadradinho)
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(new Color(60, 120, 0));
					g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			//game over
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 75));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score:" +applesEaten, (SCREEN_WIDTH- metrics.stringWidth("Score:" + applesEaten))/2, g.getFont().getSize());

		}
		else {
			gameOver(g);
			
		}
		
	}
	
	public void newApple() {
		//a coordenada x da maça é um random inteiro ao longo da largura da tela
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
	}
	
	public void move() {
		for(int i = bodyParts; i> 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction) {
		case 'U':	//up
			y[0] = y[0] - UNIT_SIZE;	//primeira coordenada da cabeça da cobra
			break;
		case 'D':	//down
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':	//left
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	
	public void checkApple() {
		//verificando a maça
		
		if((x[0] == appleX) && (y[0] == appleY)) {
			//se a cabeça da cobra tiver a mesma coordenada da maça
			bodyParts++;
			applesEaten++;
			newApple();
		}
		
	}
	
	public void checkCollisions() {
		
		//verifica se cabeça colide no corpo
		for(int i = bodyParts; i > 0; i--) {
			/*a seguinte condição verifica se há colisão da cabeça
			 * da cobra em seu próprio corpo
			 * no caso, se x/y[0] = parte do corpo atual
			 * */
			if((x[0] == x[i]) && (y[0] == y[i])){
				running = false;	//gameover
			}
		}
		
		//verifica se cabeça toca uma borda da tela no eixo x
		
		if(x[0] < 0) {
			running = false;	//borda esquerda
		}
		if(x[0] > SCREEN_WIDTH) {
			running = false;	//borda direita
		}
		
		//verifica se cabeça toca uma borda da tela no eixo y
		
		if(y[0] < 0) {
			running = false;	//borda superior
		}
		if(y[0] > SCREEN_HEIGHT) {
			running = false;	//borda inferior
		}
		
		//se gameover, o timer vai parar
		if(!running) {
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g) {
		
		//pontuação 
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score:" +applesEaten, (SCREEN_WIDTH- metrics1.stringWidth("Score:" + applesEaten))/2, g.getFont().getSize());

		
		// texto de fim de jogo
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH- metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT / 2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
		
	}
	
	//classe que coleta as teclas apertadas no teclado
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			
			//esqueda e direita
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			
			//cima e baixo
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}
}
