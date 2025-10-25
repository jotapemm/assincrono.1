import dao.ConnectionFactory;
import model.Consulta;
import view.VisaoPrincipal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        VisaoPrincipal visaoPrincipal = new VisaoPrincipal();
        visaoPrincipal.executar();
        
        System.out.println("\nSaindo do sistema...");
    }
}
