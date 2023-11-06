package interficie2repaso;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class Vprincipal extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	//Establecemos los componentes en global para poder tener siempre acceso a ellos
	private JButton btnGuardar;
	private JButton btnConsultar;
	private JTextField textNombre;
	private JTextField textDni;
	private JTextField textBanco;

	//Para añadir los clientes usando la clase (set y get) hay que crear el objeto
	private Cliente clientes = new Cliente();
	private IOficheros ioPrincipal = new IOficheros();

	//Creamos la segunda ventana secundaria para ser activada desde el boton correspondiente
	private Vsecundaria secundaria = new Vsecundaria();


	//Para guardar los datos y pasarlos a donde sea hay que crear la lista
	private List<String> valores = new ArrayList<>();
	private JButton btnCrear;

	public Vprincipal() {

		setTitle("Cloientes pro (VENTANA PRINCIPAL)");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 317, 203);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//Creamos el método para separar los componentes de las acciones
		iniciarComponentes();
	}

	private void iniciarComponentes() {

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(27, 22, 80, 13);
		contentPane.add(lblNombre);

		textNombre = new JTextField();
		textNombre.setBounds(125, 19, 165, 19);
		contentPane.add(textNombre);
		textNombre.setColumns(10);

		textDni = new JTextField();
		textDni.setColumns(10);
		textDni.setBounds(125, 45, 165, 19);
		contentPane.add(textDni);

		JLabel lblDni = new JLabel("DNI");
		lblDni.setBounds(27, 48, 80, 13);
		contentPane.add(lblDni);

		textBanco = new JTextField();
		textBanco.setColumns(10);
		textBanco.setBounds(125, 71, 165, 19);
		contentPane.add(textBanco);

		JLabel lblBanco = new JLabel("Banco");
		lblBanco.setBounds(27, 74, 80, 13);
		contentPane.add(lblBanco);

		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(this);
		btnGuardar.setBounds(27, 100, 123, 21);
		contentPane.add(btnGuardar);

		btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(this);
		btnConsultar.setBounds(27, 131, 123, 21);
		contentPane.add(btnConsultar);

		btnCrear = new JButton("Crear");
		btnCrear.addActionListener(this);
		btnCrear.setBounds(167, 100, 123, 21);
		contentPane.add(btnCrear);
	}

	//Aqui tenemos las acciones que realizan los botones al ser pulsados porque capturan el listener correspondiente
	@Override
	public void actionPerformed(ActionEvent e) {
		if (btnCrear==e.getSource()) {
			añadirValores();
			limpiarValores();
		}

		if (btnGuardar==e.getSource()) {
			guardarValores();
			limpiarValores();
		}

		if (btnConsultar==e.getSource()) {
			//Aqui llamamos a la ventana secundaira porque ya tenemos el objeto creado al incio
			secundaria.setVisible(true);

		}
	}

	private void limpiarValores() {
		textNombre.setText(null);
		textDni.setText(null);
		textBanco.setText(null);
	}

	private void guardarValores() {
		ioPrincipal.exportarStr(valores,"C:\\DAT\\valoresTMP.txt", false);

	}

	private void añadirValores() {
		//Asignar el valor al objeto capturando el valor de la interficie
		clientes.setNombre(textNombre.getText());
		clientes.setDni(textDni.getText());
		clientes.setBanco(textBanco.getText());

		//una vez caputrados los valores e insertados en el objeto lo pasamos a la lista
		valores.add(clientes.preparar());
		JOptionPane.showMessageDialog(null, "Cliente creado con exito");
		//Si es un combobox o lista usar toString
	}
}
