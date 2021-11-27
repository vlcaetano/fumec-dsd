import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class TesteApp {

	public class RecebeMensagem implements MessageListener {

		/**
		 * M�todo que ser� acionado na chegada de uma mensagem
		 */
		public void onMessage(Message message) {
			try {
				if (message == null) {
					System.out.println("-------> Recebida mensagem nula");
				} else if (message instanceof TextMessage) {
					TextMessage m = (TextMessage) message;
					String msg = m.getText();
					System.out.println("-------> Recebida mensagem: " + msg);
					mensagens += msg + "\n";
					txtpnMensagens.setText(mensagens);
				} else {
					System.out.println("-------> Recebida mensagem de tipo desconhecido: " + message);
				}
			} catch (JMSException e) {
				System.out.println("-------> Recebida exce��o: " + e);
			}
		}
	}

	private String mensagens = "";
	private JTextPane txtpnMensagens;
	private JFrame frame;
	private JTextField textoCodigo;
	private String codigo;
	private JTextField textFieldMensagem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TesteApp window = new TesteApp();
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
	public TesteApp() {

		// Obten��o do contexto JNDI
		Context jndiContext = null;
		try {
			jndiContext = new InitialContext();
		} catch (NamingException e) {
			System.out.println("--------> Obten��o do contexto inicial falhou: " + e);
			System.exit(-1);
		}

		criarConexaoTopico(jndiContext);

		criarConexaoFila(jndiContext);

		initialize();
	}

	private void criarConexaoFila(Context jndiContext) {
		Random r = new Random();
		codigo = "" + (r.nextInt(9000) + 1000);

		// Obten��o da f�brica de conex�es e da fila de destino
		QueueConnectionFactory queueConnectionFactory = null;
		Queue queue = null;
		try {
			queueConnectionFactory = (QueueConnectionFactory) jndiContext.lookup("ConnectionFactory");
			queue = (Queue) jndiContext.lookup("TesteQueue");
		} catch (NamingException e) {
			System.out.println("--------> Falha no lookup JNDI: " + e);
			System.exit(-1);
		}

		QueueConnection queueConnection = null;
		try {
			// Cria��o da conex�o e da sess�o (falso indica que n�o utiliza transa��o)
			queueConnection = queueConnectionFactory.createQueueConnection();
			QueueSession session = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			// Cria��o do receptor
			QueueReceiver receiver = session.createReceiver(queue, "KEY = '" + codigo + "'");
			receiver.setMessageListener(new RecebeMensagem());
			queueConnection.start();
		} catch (JMSException e) {
			System.out.println("--------> Ocorreu exce��o: " + e);
		}
	}

	private void criarConexaoTopico(Context jndiContext) {

		// Obten��o da f�brica de conex�es e do t�pico de destino
		TopicConnectionFactory connectionFactory = null;
		Topic topic = null;
		try {
			connectionFactory = (TopicConnectionFactory) jndiContext.lookup("ConnectionFactory");
			topic = (Topic) jndiContext.lookup("TesteTopic");
		} catch (NamingException e) {
			System.out.println("--------> Falha no lookup JNDI: " + e);
			System.exit(-1);
		}

		TopicConnection connection = null;
		try {
			// Cria��o da conex�o e da sess�o (falso indica que n�o utiliza transa��o)
			connection = connectionFactory.createTopicConnection();
			TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

			// Cria��o do receptor
			TopicSubscriber subscriber = session.createSubscriber(topic);
			subscriber.setMessageListener(new RecebeMensagem());
			connection.start();
		} catch (JMSException e) {
			System.out.println("--------> Ocorreu exce��o: " + e);
		}
	}

	private void enviaMensagemFila(String textoMensagem, String codigo) {
		Context jndiContext = null;
		try {
			jndiContext = new InitialContext();
		} catch (NamingException e) {
			System.out.println("--------> Obten��o do contexto inicial falhou: " + e);
			System.exit(-1);
		}

		// Obten��o da f�brica de conex�es e da fila de destino
		ConnectionFactory connectionFactory = null;
		Destination destination = null;
		try {
			connectionFactory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");
			destination = (Destination) jndiContext.lookup("TesteQueue");
		} catch (NamingException e) {
			System.out.println("--------> Falha no lookup JNDI: " + e);
			System.exit(-1);
		}

		Connection connection = null;
		try {
			// Cria��o da conex�o e da sess�o (falso indica que n�o utiliza transa��o)
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// Cria��o do produtor de mensagens
			MessageProducer producer = session.createProducer(destination);

			// Cria��o e envio de uma mensagem de texto
			TextMessage message = session.createTextMessage();
			message.setStringProperty("KEY", codigo);
			message.setText(textoMensagem);

			// Envio da mensagem
			producer.send(message);

			System.out.println("--------> Mensagem enviada: " + message);
		} catch (JMSException e) {
			System.out.println("Exception occurred: " + e.toString());
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
				}
			}
		}
	}

	private void enviarMensagemTopico(String textoMensagem) {
		// Obt�m refer�ncias ao QueueConnectionFactory e � fila via JNDI
		TopicConnectionFactory connectionFactory = null;
		Topic topic = null;
		;
		try {
			Context jndiContext = new InitialContext();
			connectionFactory = (TopicConnectionFactory) jndiContext.lookup("ConnectionFactory");
			topic = (Topic) jndiContext.lookup("TesteTopic");
		} catch (NamingException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		// Conecta ao t�pico e inicia uma sess�o
		try {
			TopicConnection connection = connectionFactory.createTopicConnection();
			TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

			// Publica uma �nica mensagem
			TopicPublisher publisher = session.createPublisher(topic);
			TextMessage message = session.createTextMessage();
			message.setText(textoMensagem);
			publisher.publish(message);
			System.out.println("Mensagem publicada: " + message);

			// libera recursos
			publisher.close();
			session.close();
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 566, 354);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textoCodigo = new JTextField();
		textoCodigo.setBounds(74, 247, 86, 20);
		frame.getContentPane().add(textoCodigo);
		textoCodigo.setColumns(10);

		JLabel lblNewLabel = new JLabel("C\u00F3digo");
		lblNewLabel.setBounds(10, 250, 46, 14);
		frame.getContentPane().add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 530, 127);
		frame.getContentPane().add(scrollPane);
		
				txtpnMensagens = new JTextPane();
				scrollPane.setViewportView(txtpnMensagens);
				txtpnMensagens.setEditable(false);
				txtpnMensagens.setText(mensagens);

		JLabel lblNewLabel_1 = new JLabel("Codigo: " + codigo);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel_1.setBounds(10, 11, 530, 38);
		frame.getContentPane().add(lblNewLabel_1);

		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.setBounds(10, 277, 89, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String codigoEnvio = textoCodigo.getText();
				if (codigoEnvio.isBlank() || codigoEnvio.isEmpty()) {
					enviarMensagemTopico(textFieldMensagem.getText());
				} else {
					enviaMensagemFila(textFieldMensagem.getText(), codigoEnvio);
				}
				textFieldMensagem.setText("");
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		textFieldMensagem = new JTextField();
		textFieldMensagem.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					String codigoEnvio = textoCodigo.getText();
					if (codigoEnvio.isBlank() || codigoEnvio.isEmpty()) {
						enviarMensagemTopico(textFieldMensagem.getText());
					} else {
						enviaMensagemFila(textFieldMensagem.getText(), codigoEnvio);
					}
					textFieldMensagem.setText("");
				}
			}
		});
		textFieldMensagem.setBounds(10, 216, 530, 20);
		frame.getContentPane().add(textFieldMensagem);
		textFieldMensagem.setColumns(10);
	}
}
