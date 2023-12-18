package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.layout.AnchorPane;
import persistencia.ColmeiaDAO;
import persistencia.ProducaoDAO;
import persistencia.funcionarioDAO;
import persistencia.JataiDAO;
import persistencia.AmericanaDAO;

import dominio.Americana;
import dominio.Colmeia;
import dominio.Funcionario;
import dominio.Jatai;
import dominio.Producao;

import java.util.Date;

public class ProjetoPOOController implements Initializable {
	@FXML
	private AnchorPane login, funcionarios,colmeias,producao;
	@FXML
	//botao funcionario e login
	private Button btnOK,btnAlterarFuncioario,btnBuscaGeralFuncionario,
	btnExcluirFuncionario,btnIncluirFuncionario,btnBuscarFuncionario;
	@FXML
	//botao colmeia
	private Button btnBuscarColmeia,btnIncluirColmeia,btnAlterarColmeia,
	btnBuscaGeralColmeia,btnExcluirColmeia;
	
	@FXML
	//botao producao
	private Button btnProducaoTotal,btnAbelhaJatai,btnAbelhaAmericana, btnProducaoInserir, 
	btnBuscarTotal,btnAlterarProducao,btnExcluirProducao;
	
	@FXML
	//textField funcionarios e colmeia
	private TextField nomeFuncionario,matriculaFuncionario, emailFuncionario, IDColmeia
	,tipoColmeia,statusColmeia, IDProducaoProd;	
	
	@FXML
	//textField producao
	private TextField ProducaoProducao, tipoProducaoProd, IDProducao;
	
	@FXML
	private DatePicker diaProducao;
	
	@FXML
	//todas as labels
	private Label avisoTexto, teste, avisoTextoColmeia, avisoTextoProducao;
	
	@FXML 
	TableView<Producao> tabelaProducoes = new TableView<>();

    @FXML
    private TableColumn<Producao, Float> colQuantidade;
    @FXML
    private TableColumn<Producao, Integer> colTipo;
    @FXML
    private TableColumn<Producao, Date> colData;
    @FXML
    private TableColumn<Producao, Integer> colID;
    @FXML
	private ObservableList<Producao> listaProducoes = FXCollections.observableArrayList();

	
	 @FXML
	    private void initialize() {
	        matriculaFuncionario.textProperty().addListener((observable, oldValue, newValue) -> {
	            btnIncluirFuncionario.setDisable(newValue.isEmpty());
	            colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
	            colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
	            colData.setCellValueFactory(new PropertyValueFactory<>("dia"));
	            colID.setCellValueFactory(new PropertyValueFactory<>("id"));
	        });
	    }
	
	
	// Event Listener on Button.onAction
	@FXML
	public void btnOKClicked(ActionEvent event) {
	login.setVisible(false);
	funcionarios.setVisible(true);
	colmeias.setVisible(false);
	producao.setVisible(false);
	}
	@FXML
	public void btnFuncionarioClicked(ActionEvent event) {
		login.setVisible(false);
		funcionarios.setVisible(true);
		colmeias.setVisible(false);
		producao.setVisible(false);
		}
	@FXML
	public void btnColmeiaClicked(ActionEvent event) {
		login.setVisible(false);
		funcionarios.setVisible(false);
		colmeias.setVisible(true);
		producao.setVisible(false);
		}
	@FXML
	public void btnProducaoClicked(ActionEvent event) {
		login.setVisible(false);
		funcionarios.setVisible(false);
		colmeias.setVisible(false);
		producao.setVisible(true);
		}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		login.setVisible(true);
		colmeias.setVisible(false);
		funcionarios.setVisible(false);
		producao.setVisible(false);
	}
	
	@FXML
	public void btnIncluirFuncionarioClicked(ActionEvent event) {
		funcionarioDAO inclusao = new funcionarioDAO();
		Funcionario funcionario = new Funcionario();
		
		if(nomeFuncionario.getText().isEmpty() || matriculaFuncionario.getText().isEmpty() || emailFuncionario.getText().isEmpty()) {
			avisoTexto.setText("Insira os campos corretamente");
		}else if(!matriculaFuncionario.getText().matches("\\d+")) {
			avisoTexto.setText("A area de matricula so pode conter numeros");
		}
		else {
			
			
			try {
			funcionario.setMatricula(matriculaFuncionario.getText());
			funcionario.setNome(nomeFuncionario.getText());
			funcionario.setEmail(emailFuncionario.getText());
			matriculaFuncionario.setText("");
			nomeFuncionario.setText("");
			emailFuncionario.setText("");
			
			
			avisoTexto.setText("Funcionario adcionado");
			inclusao.incluir(funcionario);
		}catch(NumberFormatException e) {
			avisoTexto.setText("Não foi possivel realizar a inclusao do funcionario");
		}
			}
		
	}
	@FXML
	public void btnExcluirFuncionario(ActionEvent event) {
	    funcionarioDAO excluir = new funcionarioDAO();
	    String conteudo = matriculaFuncionario.getText();

	    if (matriculaFuncionario.getText().trim().isEmpty()) {
	        avisoTexto.setText("Insira uma matrícula para realizar a exclusão");
	    } else if (!conteudo.matches("\\d+")) {
	        avisoTexto.setText("A área de matrícula só pode conter números.");
	    } else {
	        try {
	            
	            avisoTexto.setText("Funcionario excluido");
	            excluir.excluir(matriculaFuncionario.getText());
	            matriculaFuncionario.setText("");
	        } catch (NumberFormatException e) {
	            avisoTexto.setText("Impossivel excluir esta matricula");
	            matriculaFuncionario.setText("");
	        }
	    }
	}
	
	@FXML
	public void btnBuscaGeralFuncionario(ActionEvent event) {
		try {
		funcionarioDAO buscaGeral = new funcionarioDAO();

		buscaGeral.buscaGeral();
		}catch(NumberFormatException e) {
			avisoTexto.setText("Não foi possivel realizar a busca geral");
			
		}
		
	}
	@FXML
	public void btnBuscarFuncionario(ActionEvent event) {
		funcionarioDAO buscar = new funcionarioDAO();
		
		String conteudo = matriculaFuncionario.getText();
		 if (matriculaFuncionario.getText().trim().isEmpty()) {
		        avisoTexto.setText("Insira uma matrícula para realizar a busca");
		    } else if (!conteudo.matches("\\d+")) {
		        avisoTexto.setText("A área de matrícula só pode conter números.");
		    } else {
		        try {
		        	avisoTexto.setText("Busca realizada com sucesso");
		        	matriculaFuncionario.setText("");
		            buscar.buscar(matriculaFuncionario.getText());
		        } catch (NumberFormatException e) {
		            avisoTexto.setText("Não foi possivel realizar a busca desta matricula");
		            matriculaFuncionario.setText("");
		        }
		    }
		
		    }
	
	public void btnAlterarFuncionario(ActionEvent event) {
	    funcionarioDAO alterarFuncionario = new funcionarioDAO();
	    Funcionario funcionario = new Funcionario();
	    String conteudo = matriculaFuncionario.getText();

	    if (matriculaFuncionario.getText().trim().isEmpty()) {
	        avisoTexto.setText("Insira uma matrícula para realizar a busca");
	    } else if (!conteudo.matches("\\d+")) {
	        avisoTexto.setText("A área de matrícula só pode conter números.");
	    } else if (nomeFuncionario.getText().isEmpty() || matriculaFuncionario.getText().isEmpty() || emailFuncionario.getText().isEmpty()) {
	        avisoTexto.setText("Insira os campos corretamente");
	    } else {
	        try {
	            funcionario.setEmail(emailFuncionario.getText());
	            funcionario.setMatricula(matriculaFuncionario.getText());
	            funcionario.setNome(nomeFuncionario.getText());
	            alterarFuncionario.alterar(funcionario);
	            avisoTexto.setText("Funcionário Alterado");

	            nomeFuncionario.setText("");
	            matriculaFuncionario.setText("");
	            emailFuncionario.setText("");

	        } catch (Exception e) {
	            avisoTexto.setText("Não foi possível alterar este ID");
	            nomeFuncionario.setText("");
	            matriculaFuncionario.setText("");
	            emailFuncionario.setText("");
	        }
	    }
	}

	
	
	// COLMEIA
	// COLMEIA
	// COLMEIA
	// COLMEIA
	// COLMEIA
	// COLMEIA
	
	
	public void btnBuscarColmeia(ActionEvent event) {
		ColmeiaDAO buscarColmeia = new ColmeiaDAO();
		
		String conteudo = IDColmeia.getText();
		
		if (IDColmeia.getText().trim().isEmpty()) {
	        avisoTextoColmeia.setText("Insira um ID para realizar a busca");
	    } else if (!conteudo.matches("\\d+")) {
	        avisoTexto.setText("A área de ID só pode conter números.");
	    }else if(IDColmeia.getText().isEmpty()) {
	    	avisoTextoColmeia.setText("Insira os campos corretamente");
	    }else {
	    	try {
	    	
	    	int numero = Integer.parseInt(conteudo);
	    	buscarColmeia.buscar(numero);
	    	avisoTextoColmeia.setText("Realizando busca");
	    }catch(NumberFormatException e ) {
	    	avisoTextoColmeia.setText("Não foi possivel realizar a busca por este ID");
	    	IDColmeia.setText("");
		
	    }
	    }
		
	}
	
	public void btnIncluirColmeia(ActionEvent event) {
	    ColmeiaDAO incluirColmeia = new ColmeiaDAO();
	    Colmeia colmeia = new Colmeia();

	    String conteudo = IDColmeia.getText();
	    String tipo = tipoColmeia.getText();

	    if (IDColmeia.getText().trim().isEmpty()) {
	        avisoTextoColmeia.setText("Insira um ID para realizar a inclusão");
	    } else if (!conteudo.matches("\\d+")) {
	        avisoTextoColmeia.setText("A área de ID só pode conter números.");
	    } else if (tipoColmeia.getText().isEmpty()) {
	        avisoTextoColmeia.setText("Insira o tipo da colmeia");
	    } else {
	        try {
	            int numero = Integer.parseInt(conteudo);
	            int tipoNumero = Integer.parseInt(tipo);

	            if (tipoNumero < 1 || tipoNumero > 2) {
	                avisoTextoColmeia.setText("O tipo da colmeia só pode ser 1 ou 2");
	            } else if (statusColmeia.getText().isEmpty()) {
	                avisoTextoColmeia.setText("Indique um status para a colmeia");
	            } else {
	                colmeia.setId(numero);
	                colmeia.setStatus(statusColmeia.getText());
	                colmeia.setTipo(tipoNumero);
	                incluirColmeia.incluir(colmeia);

	                avisoTextoColmeia.setText("Inclusão de colmeia realizada com sucesso");
	                System.out.println("Tentando realizar a inclusao");
	                IDColmeia.setText("");
	                statusColmeia.setText("");
	                tipoColmeia.setText("");
	            }
	        } catch (NumberFormatException e) {
	            avisoTexto.setText("Não foi possível incluir a colmeia. Verifique os dados inseridos.");
	            IDColmeia.setText("");
	            tipoColmeia.setText("");
	            statusColmeia.setText("");
	        }
	    }
	}

	
	@FXML
	public void btnAlterarColmeia(ActionEvent event) {
	    ColmeiaDAO alterarColmeia = new ColmeiaDAO();
	    Colmeia colmeia = new Colmeia();

	    String conteudo = IDColmeia.getText();
	    String tipo = tipoColmeia.getText();

	    try {
	        if (conteudo.trim().isEmpty()) {
	            avisoTextoColmeia.setText("Insira um ID para realizar a busca");
	        } else if (!conteudo.matches("\\d+")) {
	            avisoTextoColmeia.setText("A área de ID só pode conter números.");
	        } else if (tipo.isEmpty()) {
	            avisoTextoColmeia.setText("Insira o tipo da colmeia");
	        } else {
	            int tipoNumero = Integer.parseInt(tipo);
	            
	            if (tipoNumero > 2 || tipoNumero < 1) {
	                avisoTextoColmeia.setText("O tipo da colmeia só pode ser 1 ou 2");
	            } else {
	                int numero = Integer.parseInt(conteudo);

	                colmeia.setId(numero);
	                colmeia.setTipo(tipoNumero);
	                colmeia.setStatus(statusColmeia.getText());

	                alterarColmeia.alterar(colmeia);

	                IDColmeia.setText("");
	                tipoColmeia.setText("");
	                statusColmeia.setText("");
	                avisoTextoColmeia.setText("Alteração realizada com sucesso");
	            }
	        }
	    } catch (NumberFormatException e) {
	        avisoTextoColmeia.setText("Os campos Tipo e ID devem ser números inteiros válidos.");
	        IDColmeia.setText("");
	        tipoColmeia.setText("");
	        statusColmeia.setText("");
	    }
	}

	public void btnBuscaGeralColmeia(ActionEvent event) {
		try {
		ColmeiaDAO buscaGeral = new ColmeiaDAO();
		buscaGeral.buscaGeral();
		avisoTextoColmeia.setText("Realizando a busca geral");
		}catch(NumberFormatException e) {
			avisoTextoColmeia.setText("Não foi possivel realizar a busca geral");
		}
	}
	
	public void btnExcluirColmeia(ActionEvent event) {
		ColmeiaDAO excluirColmeia = new ColmeiaDAO();
		
		String conteudo = IDColmeia.getText();
		
		if (IDColmeia.getText().trim().isEmpty()) {
	        avisoTextoColmeia.setText("Insira um ID para realizar a busca");
	    } else if (!conteudo.matches("\\d+")) {
	        avisoTextoColmeia.setText("A área de ID só pode conter números.");
	    }else if(IDColmeia.getText().isEmpty()) {
	    	avisoTextoColmeia.setText("Insira os campos corretamente");
	    }else {
	    	try {
	    		
	    	int numero = Integer.parseInt(conteudo);
	    	
	    	excluirColmeia.excluir(statusColmeia.getText(), numero);
	    	avisoTextoColmeia.setText("Exclusão realizada com sucesso");
	    	IDColmeia.setText("");
	    }catch(NumberFormatException e) {
	    	avisoTextoColmeia.setText("Não foi possivel excluir este ID");
	    	IDColmeia.setText("");
	    }
	    }
	}
	
	
	// PRODUÇÃO
	// PRODUÇÃO
	// PRODUÇÃO
	// PRODUÇÃO
	// PRODUÇÃO
	// PRODUÇÃO
	// PRODUÇÃO

	
	public void btnProducaoInserir(ActionEvent event) {
		JataiDAO inserirJatai = new JataiDAO();
		AmericanaDAO inserirAmericana = new AmericanaDAO();
		
		LocalDate localDate = diaProducao.getValue();
		

	    if (localDate == null) {
	        avisoTextoProducao.setText("Insira um dia para realizar a função.");
	    } else if (verificarFormatoData(localDate.toString())) {
	        avisoTextoProducao.setText("Insira uma data válida.");
	    } 
	    else if (!ProducaoProducao.getText().matches("\\d+")) {
	        avisoTextoProducao.setText("A área de produção só pode conter números.");
	    } else if (ProducaoProducao.getText().isEmpty()) {
	        avisoTextoProducao.setText("Insira a quantidade de produção.");
	    } else if(IDProducao.getText().isEmpty()){
	    	avisoTextoProducao.setText("Insira um ID");
	    } else if(!IDProducao.getText().matches("\\d+")){
	    	avisoTextoProducao.setText("O campo ID so pode incluir numeros");
	    } else if(tipoProducaoProd.getText().isEmpty()) {
	    	avisoTextoProducao.setText("Insira um tipo");
	    } else if(!tipoProducaoProd.getText().matches("\\d+")) {
	    	avisoTextoProducao.setText("O campo ID so pode conter numeros");
	    }else if(ProducaoProducao.getText().isEmpty() || IDProducao.getText().isEmpty() || tipoProducaoProd.getText().isEmpty()) {
	    	avisoTextoProducao.setText("Insira os campos corretamente");
	    }
	    
	    else{	
	    	
	    	String Producao = ProducaoProducao.getText();
		
		String tipo = tipoProducaoProd.getText();
	    	
	    	int tipoProducao = Integer.parseInt(tipo);
        	
        	if(tipoProducao > 2 ||tipoProducao < 1) {
        		avisoTextoProducao.setText("O tipo de colmeia so podem ser 1 e 2");
        	}
	    
	    	
	    	if(tipoProducao == 1) {
        		
	        	try {
	        	Jatai jatai = new Jatai();
	        	
	        	
	            java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
	            float qntProducao = Float.parseFloat(Producao);
	            int ID = Integer.parseInt(IDProducao.getText());

	            jatai.setDia(sqlDate);
	            jatai.setProducaoT(qntProducao);
	            jatai.setTipo(tipoProducao);
	            jatai.setID(ID);
	            diaProducao.setValue(null);  
	            ProducaoProducao.setText("");
	            IDProducao.setText("");
	            tipoProducaoProd.setText("");

	            inserirJatai.incluir(jatai);
	            avisoTextoColmeia.setText("Inserção de produção Jatai realizada com sucesso.");
	        	}catch (NumberFormatException e) {
	            avisoTextoColmeia.setText("Não foi possível inserir a produção de Jatai.");
	            diaProducao.setValue(null);  
	            ProducaoProducao.setText("");
	            tipoProducaoProd.setText("");
	            
	        	}
	    	}
	    	
	    	if(tipoProducao ==2) {
	    		
	    		try {
	    			Americana americana = new Americana();
	    			
	    			java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
		            float qntProducao = Float.parseFloat(Producao);
		            int ID = Integer.parseInt(IDProducao.getText());

		            americana.setDia(sqlDate);
		            americana.setProducaoT(qntProducao);
		            americana.setTipo(tipoProducao);
		            americana.setID(ID);
		            diaProducao.setValue(null);  // Limpar o DatePicker
		            ProducaoProducao.setText("");
		            IDProducao.setText("");
		            tipoProducaoProd.setText("");

		            inserirAmericana.incluir(americana);
		            avisoTextoColmeia.setText("Inserção de produção Americana realizada com sucesso.");
	    			
	    			
	    			
	    		}catch (NumberFormatException e) {
		            avisoTextoColmeia.setText("Não foi possível inserir a produção de Jatai.");
		            diaProducao.setValue(null);  // Limpar o DatePicker
		            ProducaoProducao.setText("");
		            tipoProducaoProd.setText("");
		        	}
	    		
	    		
	    	}
	    	}
	    	
	    	
	    	
	}

	
	
	public void btnBuscarProducao(ActionEvent event) {
	    if (IDProducao.getText().isEmpty()) {
	        avisoTextoProducao.setText("Insira um ID");
	    } else if (!IDProducao.getText().matches("\\d+")) {
	        avisoTextoProducao.setText("O campo ID só pode incluir números");
	    } else {
	        AmericanaDAO buscar = new AmericanaDAO();
	        int ID = Integer.parseInt(IDProducao.getText());
	        Producao producao = buscar.buscar(ID);

	        if (producao != null) {
	            listaProducoes.clear();
	            listaProducoes.add(producao);
	            tabelaProducoes.setItems(listaProducoes);
	            avisoTextoProducao.setText("O buscar foi realizado");
	        } else {
	            avisoTextoProducao.setText("Nenhum item encontrado para o ID fornecido.");
	        }
	    }
	}




	
	public void btnAlterarProducao(ActionEvent event) {
		
		LocalDate localDate = diaProducao.getValue();

		
		 if (localDate == null) {
		        avisoTextoProducao.setText("Insira um dia para realizar a função.");
		    } else if (verificarFormatoData(localDate.toString())) {
		        avisoTextoProducao.setText("Insira uma data válida.");
		    } 
		    else if (!ProducaoProducao.getText().matches("\\d+")) {
		        avisoTextoProducao.setText("A área de produção só pode conter números.");
		    } else if (ProducaoProducao.getText().isEmpty()) {
		        avisoTextoProducao.setText("Insira a quantidade de produção.");
		    } else if(IDProducao.getText().isEmpty()){
		    	avisoTextoProducao.setText("Insira um ID");
		    } else if(!IDProducao.getText().matches("\\d+")){
		    	avisoTextoProducao.setText("O campo ID so pode incluir numeros");
		    } else if(tipoProducaoProd.getText().isEmpty()) {
		    	avisoTextoProducao.setText("Insira um tipo");
		    } else if(!tipoProducaoProd.getText().matches("\\d+")) {
		    	avisoTextoProducao.setText("O campo ID so pode conter numeros");
		    }else if(ProducaoProducao.getText().isEmpty() || IDProducao.getText().isEmpty() || tipoProducaoProd.getText().isEmpty()) {
		    	avisoTextoProducao.setText("Insira os campos corretamente");
		    }else {
		    	
		    	int tipo = Integer.parseInt(tipoProducaoProd.getText());
		    	
		    
		    		JataiDAO AlterarJatai = new JataiDAO();
		    		Jatai jatai = new Jatai();
		    		int ID = Integer.parseInt(IDProducao.getText());
		    		int producao = Integer.parseInt(ProducaoProducao.getText());
		    		
		            java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);

		    		
		    		jatai.setDia(sqlDate);
		    		jatai.setID(ID);
		    		jatai.setProducaoT(producao);
		    		jatai.setTipo(tipo);
		    		
		    		AlterarJatai.alterar(jatai);
		    		
		    		diaProducao.setValue(null);
		    		IDProducao.setText("");
		    		ProducaoProducao.setText("");
		    		tipoProducaoProd.setText("");
		    		
		    		avisoTextoProducao.setText("Alteração realizada com sucesso");
		    		
		    	
		    }
		
	}
	
	public void btnExcluirProducao(ActionEvent event) {
		
		
		if(IDProducao.getText().isEmpty()){
    	avisoTextoProducao.setText("Insira um ID");
		} else if(!IDProducao.getText().matches("\\d+")){
    	avisoTextoProducao.setText("O campo ID so pode incluir numeros");
		}else {
			
			int ID = Integer.parseInt(IDProducao.getText());
			
			AmericanaDAO excluir = new AmericanaDAO();
			
			excluir.excluir(ID);
			IDProducao.setText("");
			ProducaoProducao.setText("");
			IDProducao.setText("");
			tipoProducaoProd.setText("");
			avisoTextoProducao.setText("Exclusão bem sucedida");
			
		}
		
		
		
	}
	
	public void btnProducaoTotal(ActionEvent event) {
		
	}
	
	public void btnAbelhaJatai(ActionEvent event) {
		JataiDAO abelhaJatai = new JataiDAO();
		abelhaJatai.buscaGeral();
	}
	
	public void btnAbelhaAmericana(ActionEvent event) {
		AmericanaDAO abelhaAmericana = new AmericanaDAO();
		abelhaAmericana.buscaGeral();
	}
	

	
	public void btnBuscarProducaoJatai(ActionEvent event) {
		
	}
	
	
	public void btnBuscarProducaoAmericana(ActionEvent event) {
		
	}
	
	
	private boolean verificarFormatoData(String texto) {
		String regex = "\\d{2}/\\d{2}/\\d{4}";
		return texto.matches(regex);
	}
	

	
}