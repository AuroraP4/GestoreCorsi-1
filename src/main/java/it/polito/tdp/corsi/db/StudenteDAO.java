package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.corsi.model.*;

public class StudenteDAO {

	public List<Studente> getIscrittiCorso(String codins) {
		String sql = "select s.matricola, s.cognome, s.nome, s.CDS "
				+ "from studente s, iscrizione i "
				+ "where s.matricola = i.matricola and i.codins = ?";

		List<Studente> risultato = new ArrayList<Studente>();

		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				risultato.add(new Studente(rs.getInt("matricola"), rs.getString("cognome"),
						rs.getString("nome"), rs.getString("CDS")) );
			}

			rs.close();
			st.close();
			conn.close();
			return risultato;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Errore connessione al database");
			e.printStackTrace();
			return null;
		}
	}


	public List<Divisione> getDivisioneStudentiCorso(String codins) {
		String sql = "select s.CDS, count(*) as n "
				+ "from studente s, iscrizione i "
				+ "where s.matricola = i.matricola and i.codins = ? "
				+ "group by s.CDS";

		List<Divisione> risultato = new ArrayList<Divisione>();

		Connection conn = ConnectDB.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				risultato.add(new Divisione(rs.getString("CDS"), rs.getInt("n")));
			}
			rs.close();
			st.close();
			conn.close();
			return risultato;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Errore connessione al database");
			e.printStackTrace();
			return null;
		}


	}

}
