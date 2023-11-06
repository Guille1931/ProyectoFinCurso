package interficie2repaso;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class Vsecundaria extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnImportar;
	private JButton btnCerrar;



	private JList<String> listClientes;

	//Creamos la lista para recoger los datos que vienen del fichero, porque importSTR te devuelve una lista de strings
	private List<String> valImp = new ArrayList<>();

	private IOficheros ioSecundaira = new IOficheros();

	public Vsecundaria() {
		setTitle("Listado de clientes");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 394, 236);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		iniciarComponentes();
	}

	private void iniciarComponentes() {

		listClientes = new JList<>();

		//Tanto el combo como el list como el jtable necesitan un modelo de datos en este caso Defaultcomboboxmodel lo dejamos vacio o null
		listClientes.setModel(new DefaultComboBoxModel<>());
		listClientes.setBounds(10, 10, 358, 149);
		contentPane.add(listClientes);

		btnImportar = new JButton("Importar");
		btnImportar.addActionListener(this);
		btnImportar.setBounds(10, 169, 85, 21);
		contentPane.add(btnImportar);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(this);
		btnCerrar.setBounds(283, 169, 85, 21);
		contentPane.add(btnCerrar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (btnImportar==e.getSource()) {

			//Agarramos en valImp los valores que vienen desde el archivo
			valImp = ioSecundaira.importStr("C:\\DAT\\valoresTMP.txt");
			//Para pasar los valores de una lista a un array necesitamos el array del tamaño de la lista
			String[] miarray = new String[valImp.size()];
			for (int i=0;i<valImp.size();i++) {
				//Cojemos el valor de la lista get y lo pasamos al array =
				miarray[i]=valImp.get(i);
			}
			//ASignamos al modelo el array con los elementos
			listClientes.setModel(new DefaultComboBoxModel<>(miarray));
		}

		if (btnCerrar==e.getSource()) {
			this.setVisible(false);
		}

	}
}
